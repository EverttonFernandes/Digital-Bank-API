package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.application.converter.TransferRequestConverter;
import com.cwi.digitalbankapi.application.dto.TransferRequest;
import com.cwi.digitalbankapi.application.dto.TransferResponse;
import com.cwi.digitalbankapi.domain.account.exception.AccountNotFoundException;
import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import com.cwi.digitalbankapi.domain.notification.gateway.TransferCompletedEventPublisher;
import com.cwi.digitalbankapi.domain.notification.model.TransferCompletedEvent;
import com.cwi.digitalbankapi.domain.statement.model.AccountMovement;
import com.cwi.digitalbankapi.domain.statement.model.AccountMovementType;
import com.cwi.digitalbankapi.domain.statement.repository.AccountMovementRepository;
import com.cwi.digitalbankapi.domain.transfer.model.Transfer;
import com.cwi.digitalbankapi.domain.transfer.model.TransferCommand;
import com.cwi.digitalbankapi.domain.transfer.specification.CompositeTransferSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TransferService {

    private final AccountRepository accountRepository;
    private final AccountMovementRepository accountMovementRepository;
    private final TransferCompletedEventPublisher transferCompletedEventPublisher;
    private final TransferRequestConverter transferRequestConverter;
    private final CompositeTransferSpecification compositeTransferSpecification;

    public TransferService(
        AccountRepository accountRepository,
        AccountMovementRepository accountMovementRepository,
        TransferCompletedEventPublisher transferCompletedEventPublisher,
        TransferRequestConverter transferRequestConverter,
        CompositeTransferSpecification compositeTransferSpecification
    ) {
        this.accountRepository = accountRepository;
        this.accountMovementRepository = accountMovementRepository;
        this.transferCompletedEventPublisher = transferCompletedEventPublisher;
        this.transferRequestConverter = transferRequestConverter;
        this.compositeTransferSpecification = compositeTransferSpecification;
    }

    @Transactional
    public TransferResponse transfer(TransferRequest transferRequest) {
        TransferCommand transferCommand = transferRequestConverter.convert(transferRequest);

        List<Account> lockedAccountList = accountRepository.findAccountsByIdentifiersWithPessimisticLock(
            transferCommand.sourceAccountId(),
            transferCommand.targetAccountId()
        );

        Account sourceAccount = lockedAccountList.stream()
            .filter(account -> account.getId().equals(transferCommand.sourceAccountId()))
            .findFirst()
            .orElseThrow(() -> new AccountNotFoundException(transferCommand.sourceAccountId()));

        Account targetAccount = lockedAccountList.stream()
            .filter(account -> account.getId().equals(transferCommand.targetAccountId()))
            .findFirst()
            .orElseThrow(() -> new AccountNotFoundException(transferCommand.targetAccountId()));

        Transfer transfer = new Transfer(sourceAccount, targetAccount, transferCommand.amount());

        compositeTransferSpecification.ensureSatisfiedBy(transfer);

        sourceAccount.debit(transfer.amount());
        targetAccount.credit(transfer.amount());

        String transferReference = UUID.randomUUID().toString();
        OffsetDateTime movementCreatedAt = OffsetDateTime.now();

        accountRepository.saveAccounts(List.of(sourceAccount, targetAccount));
        accountMovementRepository.saveAccountMovements(List.of(
            new AccountMovement(
                null,
                sourceAccount.getId(),
                transferReference,
                AccountMovementType.DEBIT,
                transfer.amount(),
                "Debito gerado pela transferencia para a conta " + targetAccount.getId() + ".",
                movementCreatedAt
            ),
            new AccountMovement(
                null,
                targetAccount.getId(),
                transferReference,
                AccountMovementType.CREDIT,
                transfer.amount(),
                "Credito recebido da transferencia da conta " + sourceAccount.getId() + ".",
                movementCreatedAt
            )
        ));

        transferCompletedEventPublisher.publish(new TransferCompletedEvent(
            sourceAccount.getId(),
            sourceAccount.getOwnerName(),
            targetAccount.getId(),
            targetAccount.getOwnerName(),
            transferReference,
            transfer.amount(),
            movementCreatedAt
        ));

        return new TransferResponse(
            sourceAccount.getId(),
            targetAccount.getId(),
            transferReference,
            transfer.amount(),
            sourceAccount.getBalance(),
            targetAccount.getBalance()
        );
    }
}

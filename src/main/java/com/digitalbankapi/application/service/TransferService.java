package com.digitalbankapi.application.service;

import com.digitalbankapi.application.converter.TransferDTOConverter;
import com.digitalbankapi.application.dto.TransferDTO;
import com.digitalbankapi.application.dto.TransferResponseDTO;
import com.digitalbankapi.domain.account.exception.AccountNotFoundException;
import com.digitalbankapi.domain.account.model.Account;
import com.digitalbankapi.domain.account.repository.AccountRepository;
import com.digitalbankapi.domain.notification.gateway.TransferCompletedEventPublisher;
import com.digitalbankapi.domain.statement.model.AccountMovement;
import com.digitalbankapi.domain.statement.repository.AccountMovementRepository;
import com.digitalbankapi.domain.transfer.model.Transfer;
import com.digitalbankapi.domain.transfer.specification.CompositeTransferSpecification;
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
    private final TransferDTOConverter transferRequestConverter;
    private final CompositeTransferSpecification compositeTransferSpecification;

    public TransferService(
            AccountRepository accountRepository,
            AccountMovementRepository accountMovementRepository,
            TransferCompletedEventPublisher transferCompletedEventPublisher,
            TransferDTOConverter transferRequestConverter,
            CompositeTransferSpecification compositeTransferSpecification
    ) {
        this.accountRepository = accountRepository;
        this.accountMovementRepository = accountMovementRepository;
        this.transferCompletedEventPublisher = transferCompletedEventPublisher;
        this.transferRequestConverter = transferRequestConverter;
        this.compositeTransferSpecification = compositeTransferSpecification;
    }

    @Transactional
    public TransferResponseDTO transfer(TransferDTO transferRequest) {
        List<Account> lockedAccountList = accountRepository.findAccountsByIdentifiersWithPessimisticLock(
                transferRequest.sourceAccountId(),
                transferRequest.targetAccountId()
        );

        Account sourceAccount = lockedAccountList.stream()
                .filter(account -> account.getId().equals(transferRequest.sourceAccountId()))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException(transferRequest.sourceAccountId()));

        Account targetAccount = lockedAccountList.stream()
                .filter(account -> account.getId().equals(transferRequest.targetAccountId()))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException(transferRequest.targetAccountId()));

        Transfer transfer = transferRequestConverter.convert(transferRequest, sourceAccount, targetAccount);

        compositeTransferSpecification.ensureSatisfiedBy(transfer);

        transfer.apply();

        String transferReference = UUID.randomUUID().toString();
        OffsetDateTime movementCreatedAt = OffsetDateTime.now();

        accountRepository.saveAccounts(List.of(sourceAccount, targetAccount));
        List<AccountMovement> accountMovementList = transfer.createAccountMovements(transferReference, movementCreatedAt);
        accountMovementRepository.saveAccountMovements(accountMovementList);

        transferCompletedEventPublisher.publish(transfer.createTransferCompletedEvent(transferReference, movementCreatedAt));

        return new TransferResponseDTO(
                sourceAccount.getId(),
                targetAccount.getId(),
                transferReference,
                transfer.amount(),
                sourceAccount.getBalance(),
                targetAccount.getBalance()
        );
    }
}

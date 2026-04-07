package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.application.converter.TransferRequestConverter;
import com.cwi.digitalbankapi.application.dto.TransferRequest;
import com.cwi.digitalbankapi.application.dto.TransferResponse;
import com.cwi.digitalbankapi.domain.account.exception.AccountNotFoundException;
import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import com.cwi.digitalbankapi.domain.transfer.model.Transfer;
import com.cwi.digitalbankapi.domain.transfer.model.TransferCommand;
import com.cwi.digitalbankapi.domain.transfer.specification.CompositeTransferSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransferRequestConverter transferRequestConverter;
    private final CompositeTransferSpecification compositeTransferSpecification;

    public TransferService(
        AccountRepository accountRepository,
        TransferRequestConverter transferRequestConverter,
        CompositeTransferSpecification compositeTransferSpecification
    ) {
        this.accountRepository = accountRepository;
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

        accountRepository.saveAccounts(List.of(sourceAccount, targetAccount));

        return new TransferResponse(
            sourceAccount.getId(),
            targetAccount.getId(),
            transfer.amount(),
            sourceAccount.getBalance(),
            targetAccount.getBalance()
        );
    }
}

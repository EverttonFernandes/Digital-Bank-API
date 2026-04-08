package com.digitalbankapi.application.service;

import com.digitalbankapi.domain.account.exception.AccountNotFoundException;
import com.digitalbankapi.domain.account.model.Account;
import com.digitalbankapi.domain.account.repository.AccountRepository;
import com.digitalbankapi.domain.notification.gateway.TransferCompletedObserver;
import com.digitalbankapi.domain.notification.model.AccountNotification;
import com.digitalbankapi.domain.notification.model.AccountNotificationStatus;
import com.digitalbankapi.domain.notification.model.TransferCompletedEvent;
import com.digitalbankapi.domain.notification.repository.AccountNotificationRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegisterTransferNotificationObserver implements TransferCompletedObserver {

    private final AccountRepository accountRepository;
    private final AccountNotificationRepository accountNotificationRepository;

    public RegisterTransferNotificationObserver(
            AccountRepository accountRepository,
            AccountNotificationRepository accountNotificationRepository
    ) {
        this.accountRepository = accountRepository;
        this.accountNotificationRepository = accountNotificationRepository;
    }

    @Override
    public void handle(TransferCompletedEvent transferCompletedEvent) {
        Account sourceAccount = findExistingAccountById(transferCompletedEvent.sourceAccountId());
        Account targetAccount = findExistingAccountById(transferCompletedEvent.targetAccountId());

        accountNotificationRepository.saveAccountNotifications(List.of(
                new AccountNotification(
                        null,
                        sourceAccount,
                        transferCompletedEvent.transferReference(),
                        AccountNotificationStatus.REGISTERED,
                        "Transferencia enviada com sucesso para a conta " + transferCompletedEvent.targetAccountId() + ".",
                        transferCompletedEvent.completedAt()
                ),
                new AccountNotification(
                        null,
                        targetAccount,
                        transferCompletedEvent.transferReference(),
                        AccountNotificationStatus.REGISTERED,
                        "Transferencia recebida com sucesso da conta " + transferCompletedEvent.sourceAccountId() + ".",
                        transferCompletedEvent.completedAt()
                )
        ));
    }

    private Account findExistingAccountById(Long accountId) {
        return accountRepository.findAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}

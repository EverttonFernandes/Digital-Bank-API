package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.domain.notification.gateway.TransferCompletedObserver;
import com.cwi.digitalbankapi.domain.notification.model.AccountNotification;
import com.cwi.digitalbankapi.domain.notification.model.AccountNotificationStatus;
import com.cwi.digitalbankapi.domain.notification.model.TransferCompletedEvent;
import com.cwi.digitalbankapi.domain.notification.repository.AccountNotificationRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegisterTransferNotificationObserver implements TransferCompletedObserver {

    private final AccountNotificationRepository accountNotificationRepository;

    public RegisterTransferNotificationObserver(AccountNotificationRepository accountNotificationRepository) {
        this.accountNotificationRepository = accountNotificationRepository;
    }

    @Override
    public void handle(TransferCompletedEvent transferCompletedEvent) {
        accountNotificationRepository.saveAccountNotifications(List.of(
            new AccountNotification(
                null,
                transferCompletedEvent.sourceAccountId(),
                transferCompletedEvent.transferReference(),
                AccountNotificationStatus.REGISTERED,
                "Transferencia enviada com sucesso para a conta " + transferCompletedEvent.targetAccountId() + ".",
                transferCompletedEvent.completedAt()
            ),
            new AccountNotification(
                null,
                transferCompletedEvent.targetAccountId(),
                transferCompletedEvent.transferReference(),
                AccountNotificationStatus.REGISTERED,
                "Transferencia recebida com sucesso da conta " + transferCompletedEvent.sourceAccountId() + ".",
                transferCompletedEvent.completedAt()
            )
        ));
    }
}

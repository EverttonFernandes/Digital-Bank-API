package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.domain.notification.model.AccountNotification;
import com.cwi.digitalbankapi.domain.notification.model.TransferCompletedEvent;
import com.cwi.digitalbankapi.domain.notification.repository.AccountNotificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;

class RegisterTransferNotificationObserverTest {

    private final AccountNotificationRepository accountNotificationRepository = mock(AccountNotificationRepository.class);
    private final RegisterTransferNotificationObserver registerTransferNotificationObserver =
        new RegisterTransferNotificationObserver(accountNotificationRepository);

    @Test
    @DisplayName("Deve registrar notificacoes para origem e destino quando a transferencia for concluida")
    void shouldRegisterNotificationsForSourceAndTargetWhenTransferIsCompleted() {
        // GIVEN
        TransferCompletedEvent transferCompletedEvent = new TransferCompletedEvent(
            1L,
            "Ana Souza",
            2L,
            "Bruno Lima",
            "transfer-reference-001",
            new BigDecimal("200.00"),
            OffsetDateTime.now()
        );
        ArgumentCaptor<List<AccountNotification>> accountNotificationListCaptor = ArgumentCaptor.forClass(List.class);

        // WHEN
        registerTransferNotificationObserver.handle(transferCompletedEvent);

        // THEN
        BDDMockito.then(accountNotificationRepository).should()
            .saveAccountNotifications(accountNotificationListCaptor.capture());
        Assertions.assertEquals(2, accountNotificationListCaptor.getValue().size());
        Assertions.assertEquals(1L, accountNotificationListCaptor.getValue().get(0).accountId());
        Assertions.assertEquals(2L, accountNotificationListCaptor.getValue().get(1).accountId());
        Assertions.assertEquals(
            "transfer-reference-001",
            accountNotificationListCaptor.getValue().get(0).transferReference()
        );
        Assertions.assertEquals(
            "transfer-reference-001",
            accountNotificationListCaptor.getValue().get(1).transferReference()
        );
    }
}

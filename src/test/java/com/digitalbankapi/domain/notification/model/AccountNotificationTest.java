package com.digitalbankapi.domain.notification.model;

import com.digitalbankapi.domain.account.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

class AccountNotificationTest {

    @Test
    @DisplayName("Deve expor todos os dados da notificacao")
    void shouldExposeAllNotificationData() {
        OffsetDateTime createdAt = OffsetDateTime.parse("2026-04-08T00:00:00Z");
        Account account = new Account(1L, "Ana", new BigDecimal("100.00"), createdAt, createdAt);
        AccountNotification accountNotification = new AccountNotification(
                1L,
                account,
                "ref-1",
                AccountNotificationStatus.REGISTERED,
                "Mensagem",
                createdAt
        );

        Assertions.assertEquals(1L, accountNotification.getId());
        Assertions.assertEquals(account, accountNotification.getAccount());
        Assertions.assertEquals(1L, accountNotification.getAccountId());
        Assertions.assertEquals("ref-1", accountNotification.getTransferReference());
        Assertions.assertEquals(AccountNotificationStatus.REGISTERED, accountNotification.getNotificationStatus());
        Assertions.assertEquals("Mensagem", accountNotification.getMessage());
        Assertions.assertEquals(createdAt, accountNotification.getCreatedAt());
    }
}

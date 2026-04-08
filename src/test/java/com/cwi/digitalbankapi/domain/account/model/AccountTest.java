package com.cwi.digitalbankapi.domain.account.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

class AccountTest {

    @Test
    @DisplayName("Deve abrir conta com saldo inicial e datas preenchidas")
    void shouldOpenAccountWithInitialBalanceAndFilledDates() {
        Account account = Account.open("Maria Silva", new BigDecimal("350.00"));

        Assertions.assertNull(account.getId());
        Assertions.assertEquals("Maria Silva", account.getOwnerName());
        Assertions.assertEquals(new BigDecimal("350.00"), account.getBalance());
        Assertions.assertNotNull(account.getCreatedAt());
        Assertions.assertNotNull(account.getUpdatedAt());
    }

    @Test
    @DisplayName("Deve debitar saldo da conta e atualizar data de modificacao")
    void shouldDebitAccountBalanceAndUpdateModificationDate() {
        // GIVEN
        OffsetDateTime accountCreationDate = OffsetDateTime.parse("2026-04-07T00:00:00Z");
        OffsetDateTime accountUpdateDate = OffsetDateTime.parse("2026-04-07T00:00:00Z");
        Account account = new Account(
                1L,
                "Ana Souza",
                new BigDecimal("1250.00"),
                accountCreationDate,
                accountUpdateDate
        );

        // WHEN
        account.debit(new BigDecimal("200.00"));

        // THEN
        Assertions.assertEquals(new BigDecimal("1050.00"), account.getBalance());
        Assertions.assertFalse(account.getUpdatedAt().isEqual(accountUpdateDate));
    }

    @Test
    @DisplayName("Deve creditar saldo da conta e atualizar data de modificacao")
    void shouldCreditAccountBalanceAndUpdateModificationDate() {
        // GIVEN
        OffsetDateTime accountCreationDate = OffsetDateTime.parse("2026-04-07T00:00:00Z");
        OffsetDateTime accountUpdateDate = OffsetDateTime.parse("2026-04-07T00:00:00Z");
        Account account = new Account(
                2L,
                "Bruno Lima",
                new BigDecimal("980.50"),
                accountCreationDate,
                accountUpdateDate
        );

        // WHEN
        account.credit(new BigDecimal("200.00"));

        // THEN
        Assertions.assertEquals(new BigDecimal("1180.50"), account.getBalance());
        Assertions.assertFalse(account.getUpdatedAt().isEqual(accountUpdateDate));
    }
}

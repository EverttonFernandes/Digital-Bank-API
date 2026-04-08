package com.digitalbankapi.domain.account.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

class AccountMutatorsTest {

    @Test
    @DisplayName("Deve permitir ajustar saldo e data de atualizacao manualmente")
    void shouldAllowSettingBalanceAndUpdatedAtManually() {
        OffsetDateTime createdAt = OffsetDateTime.parse("2026-04-08T00:00:00Z");
        OffsetDateTime updatedAt = OffsetDateTime.parse("2026-04-08T01:00:00Z");
        Account account = new Account(1L, "Ana", new BigDecimal("100.00"), createdAt, createdAt);

        account.setBalance(new BigDecimal("150.00"));
        account.setUpdatedAt(updatedAt);

        Assertions.assertEquals(new BigDecimal("150.00"), account.getBalance());
        Assertions.assertEquals(updatedAt, account.getUpdatedAt());
    }
}

package com.digitalbankapi.domain.statement.model;

import com.digitalbankapi.domain.account.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

class AccountMovementTest {

    @Test
    @DisplayName("Deve expor todos os dados da movimentacao")
    void shouldExposeAllMovementData() {
        OffsetDateTime createdAt = OffsetDateTime.parse("2026-04-08T00:00:00Z");
        Account account = new Account(1L, "Ana", new BigDecimal("100.00"), createdAt, createdAt);
        AccountMovement accountMovement = new AccountMovement(
                1L,
                account,
                "ref-1",
                AccountMovementType.DEBIT,
                new BigDecimal("10.00"),
                "Debito",
                createdAt
        );

        Assertions.assertEquals(1L, accountMovement.getId());
        Assertions.assertEquals(account, accountMovement.getAccount());
        Assertions.assertEquals(1L, accountMovement.getAccountId());
        Assertions.assertEquals("ref-1", accountMovement.getTransferReference());
        Assertions.assertEquals(AccountMovementType.DEBIT, accountMovement.getMovementType());
        Assertions.assertEquals(new BigDecimal("10.00"), accountMovement.getAmount());
        Assertions.assertEquals("Debito", accountMovement.getDescription());
        Assertions.assertEquals(createdAt, accountMovement.getCreatedAt());
    }
}

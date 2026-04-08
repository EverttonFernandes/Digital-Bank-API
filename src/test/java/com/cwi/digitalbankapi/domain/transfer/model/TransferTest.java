package com.cwi.digitalbankapi.domain.transfer.model;

import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.statement.model.AccountMovementType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

class TransferTest {

    @Test
    @DisplayName("Deve aplicar transferencia nas contas de origem e destino")
    void shouldApplyTransferOnSourceAndTargetAccounts() {
        Account sourceAccount = new Account(1L, "Ana Souza", new BigDecimal("1000.00"), OffsetDateTime.now(), OffsetDateTime.now());
        Account targetAccount = new Account(2L, "Bruno Lima", new BigDecimal("500.00"), OffsetDateTime.now(), OffsetDateTime.now());
        Transfer transfer = new Transfer(sourceAccount, targetAccount, new BigDecimal("150.00"));

        transfer.apply();

        Assertions.assertEquals(new BigDecimal("850.00"), sourceAccount.getBalance());
        Assertions.assertEquals(new BigDecimal("650.00"), targetAccount.getBalance());
    }

    @Test
    @DisplayName("Deve gerar movimentacoes de debito e credito a partir da transferencia")
    void shouldCreateDebitAndCreditMovementsFromTransfer() {
        Account sourceAccount = new Account(1L, "Ana Souza", new BigDecimal("1000.00"), OffsetDateTime.now(), OffsetDateTime.now());
        Account targetAccount = new Account(2L, "Bruno Lima", new BigDecimal("500.00"), OffsetDateTime.now(), OffsetDateTime.now());
        Transfer transfer = new Transfer(sourceAccount, targetAccount, new BigDecimal("150.00"));

        List<com.cwi.digitalbankapi.domain.statement.model.AccountMovement> accountMovementList =
                transfer.createAccountMovements("transfer-reference-001", OffsetDateTime.parse("2026-04-08T00:00:00Z"));

        Assertions.assertEquals(2, accountMovementList.size());
        Assertions.assertEquals(AccountMovementType.DEBIT, accountMovementList.get(0).getMovementType());
        Assertions.assertEquals(AccountMovementType.CREDIT, accountMovementList.get(1).getMovementType());
        Assertions.assertEquals("transfer-reference-001", accountMovementList.get(0).getTransferReference());
    }

    @Test
    @DisplayName("Deve gerar evento de notificacao a partir da transferencia")
    void shouldCreateTransferCompletedEventFromTransfer() {
        Account sourceAccount = new Account(1L, "Ana Souza", new BigDecimal("1000.00"), OffsetDateTime.now(), OffsetDateTime.now());
        Account targetAccount = new Account(2L, "Bruno Lima", new BigDecimal("500.00"), OffsetDateTime.now(), OffsetDateTime.now());
        Transfer transfer = new Transfer(sourceAccount, targetAccount, new BigDecimal("150.00"));

        var transferCompletedEvent = transfer.createTransferCompletedEvent(
                "transfer-reference-001",
                OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );

        Assertions.assertEquals(1L, transferCompletedEvent.sourceAccountId());
        Assertions.assertEquals(2L, transferCompletedEvent.targetAccountId());
        Assertions.assertEquals("transfer-reference-001", transferCompletedEvent.transferReference());
        Assertions.assertEquals(new BigDecimal("150.00"), transferCompletedEvent.transferredAmount());
    }
}

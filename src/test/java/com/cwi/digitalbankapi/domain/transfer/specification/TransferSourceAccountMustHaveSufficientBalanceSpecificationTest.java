package com.cwi.digitalbankapi.domain.transfer.specification;

import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.transfer.exception.InsufficientAccountBalanceException;
import com.cwi.digitalbankapi.domain.transfer.model.Transfer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

class TransferSourceAccountMustHaveSufficientBalanceSpecificationTest {

    private final TransferSourceAccountMustHaveSufficientBalanceSpecification transferSourceAccountMustHaveSufficientBalanceSpecification =
        new TransferSourceAccountMustHaveSufficientBalanceSpecification();

    @Test
    @DisplayName("Deve permitir transferencia quando a conta de origem possuir saldo suficiente")
    void shouldAllowTransferWhenSourceAccountHasSufficientBalance() {
        // GIVEN
        Account sourceAccount = new Account(1L, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());
        Account targetAccount = new Account(2L, "Bruno Lima", new BigDecimal("980.50"), OffsetDateTime.now(), OffsetDateTime.now());
        Transfer transfer = new Transfer(sourceAccount, targetAccount, new BigDecimal("100.00"));

        // WHEN
        Assertions.assertDoesNotThrow(() -> transferSourceAccountMustHaveSufficientBalanceSpecification.ensureSatisfiedBy(transfer));

        // THEN
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("Deve rejeitar transferencia quando a conta de origem nao possuir saldo suficiente")
    void shouldRejectTransferWhenSourceAccountDoesNotHaveSufficientBalance() {
        // GIVEN
        Account sourceAccount = new Account(1L, "Ana Souza", new BigDecimal("50.00"), OffsetDateTime.now(), OffsetDateTime.now());
        Account targetAccount = new Account(2L, "Bruno Lima", new BigDecimal("980.50"), OffsetDateTime.now(), OffsetDateTime.now());
        Transfer transfer = new Transfer(sourceAccount, targetAccount, new BigDecimal("100.00"));

        // WHEN
        InsufficientAccountBalanceException exception = Assertions.assertThrows(
            InsufficientAccountBalanceException.class,
            () -> transferSourceAccountMustHaveSufficientBalanceSpecification.ensureSatisfiedBy(transfer)
        );

        // THEN
        Assertions.assertEquals("INSUFFICIENT_ACCOUNT_BALANCE", exception.getKey());
        Assertions.assertEquals("A conta de origem nao possui saldo suficiente para a transferencia.", exception.getValue());
    }
}

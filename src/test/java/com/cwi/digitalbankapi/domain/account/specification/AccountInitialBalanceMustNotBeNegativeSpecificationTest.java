package com.cwi.digitalbankapi.domain.account.specification;

import com.cwi.digitalbankapi.domain.account.exception.AccountInitialBalanceMustNotBeNegativeException;
import com.cwi.digitalbankapi.domain.account.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

class AccountInitialBalanceMustNotBeNegativeSpecificationTest {

    private final AccountInitialBalanceMustNotBeNegativeSpecification accountInitialBalanceMustNotBeNegativeSpecification =
        new AccountInitialBalanceMustNotBeNegativeSpecification();

    @Test
    @DisplayName("Deve aceitar criacao de conta quando o saldo inicial for zero")
    void shouldAcceptAccountCreationWhenInitialBalanceIsZero() {
        // GIVEN
        Account account = new Account(null, "Maria Silva", BigDecimal.ZERO, OffsetDateTime.now(), OffsetDateTime.now());

        // WHEN
        accountInitialBalanceMustNotBeNegativeSpecification.ensureSatisfiedBy(account);

        // THEN
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("Deve rejeitar criacao de conta quando o saldo inicial for negativo")
    void shouldRejectAccountCreationWhenInitialBalanceIsNegative() {
        // GIVEN
        Account account = new Account(null, "Maria Silva", new BigDecimal("-1.00"), OffsetDateTime.now(), OffsetDateTime.now());

        // WHEN
        AccountInitialBalanceMustNotBeNegativeException accountInitialBalanceMustNotBeNegativeException = Assertions.assertThrows(
            AccountInitialBalanceMustNotBeNegativeException.class,
            () -> accountInitialBalanceMustNotBeNegativeSpecification.ensureSatisfiedBy(account)
        );

        // THEN
        Assertions.assertEquals(
            "ACCOUNT_INITIAL_BALANCE_MUST_NOT_BE_NEGATIVE",
            accountInitialBalanceMustNotBeNegativeException.getKey()
        );
        Assertions.assertEquals(
            "O saldo inicial da conta nao pode ser negativo.",
            accountInitialBalanceMustNotBeNegativeException.getValue()
        );
    }
}

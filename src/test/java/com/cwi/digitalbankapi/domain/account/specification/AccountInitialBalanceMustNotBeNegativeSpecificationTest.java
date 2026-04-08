package com.cwi.digitalbankapi.domain.account.specification;

import com.cwi.digitalbankapi.domain.account.exception.AccountInitialBalanceMustNotBeNegativeException;
import com.cwi.digitalbankapi.domain.account.model.AccountCreationCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AccountInitialBalanceMustNotBeNegativeSpecificationTest {

    private final AccountInitialBalanceMustNotBeNegativeSpecification accountInitialBalanceMustNotBeNegativeSpecification =
        new AccountInitialBalanceMustNotBeNegativeSpecification();

    @Test
    @DisplayName("Deve aceitar criacao de conta quando o saldo inicial for zero")
    void shouldAcceptAccountCreationWhenInitialBalanceIsZero() {
        // GIVEN
        AccountCreationCommand accountCreationCommand = new AccountCreationCommand("Maria Silva", BigDecimal.ZERO);

        // WHEN
        accountInitialBalanceMustNotBeNegativeSpecification.ensureSatisfiedBy(accountCreationCommand);

        // THEN
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("Deve rejeitar criacao de conta quando o saldo inicial for negativo")
    void shouldRejectAccountCreationWhenInitialBalanceIsNegative() {
        // GIVEN
        AccountCreationCommand accountCreationCommand = new AccountCreationCommand("Maria Silva", new BigDecimal("-1.00"));

        // WHEN
        AccountInitialBalanceMustNotBeNegativeException accountInitialBalanceMustNotBeNegativeException = Assertions.assertThrows(
            AccountInitialBalanceMustNotBeNegativeException.class,
            () -> accountInitialBalanceMustNotBeNegativeSpecification.ensureSatisfiedBy(accountCreationCommand)
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

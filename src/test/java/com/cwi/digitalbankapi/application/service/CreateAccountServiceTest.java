package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.application.converter.AccountResponseConverter;
import com.cwi.digitalbankapi.application.converter.CreateAccountRequestConverter;
import com.cwi.digitalbankapi.application.dto.AccountResponse;
import com.cwi.digitalbankapi.application.dto.CreateAccountRequest;
import com.cwi.digitalbankapi.domain.account.exception.AccountInitialBalanceMustNotBeNegativeException;
import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import com.cwi.digitalbankapi.domain.account.specification.AccountInitialBalanceMustNotBeNegativeSpecification;
import com.cwi.digitalbankapi.domain.account.specification.CompositeAccountCreationSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class CreateAccountServiceTest {

    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final CreateAccountService createAccountService = new CreateAccountService(
        accountRepository,
        new CreateAccountRequestConverter(),
        new CompositeAccountCreationSpecification(List.of(
            new AccountInitialBalanceMustNotBeNegativeSpecification()
        )),
        new AccountResponseConverter()
    );

    @Test
    @DisplayName("Deve criar conta bancaria quando os dados informados forem validos")
    void shouldCreateBankAccountWhenProvidedDataIsValid() {
        // GIVEN
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("Maria Silva", new BigDecimal("350.00"));
        Account createdAccount = new Account(
            4L,
            "Maria Silva",
            new BigDecimal("350.00"),
            OffsetDateTime.parse("2026-04-07T00:00:00Z"),
            OffsetDateTime.parse("2026-04-07T00:00:00Z")
        );

        BDDMockito.given(accountRepository.saveAccount(any(Account.class))).willReturn(createdAccount);

        // WHEN
        AccountResponse accountResponse = createAccountService.createAccount(createAccountRequest);

        // THEN
        Assertions.assertEquals(4L, accountResponse.id());
        Assertions.assertEquals("Maria Silva", accountResponse.ownerName());
        Assertions.assertEquals(new BigDecimal("350.00"), accountResponse.balance());
        BDDMockito.then(accountRepository).should().saveAccount(any(Account.class));
    }

    @Test
    @DisplayName("Deve rejeitar criacao de conta bancaria quando o saldo inicial for negativo")
    void shouldRejectBankAccountCreationWhenInitialBalanceIsNegative() {
        // GIVEN
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("Maria Silva", new BigDecimal("-1.00"));

        // WHEN
        AccountInitialBalanceMustNotBeNegativeException accountInitialBalanceMustNotBeNegativeException = Assertions.assertThrows(
            AccountInitialBalanceMustNotBeNegativeException.class,
            () -> createAccountService.createAccount(createAccountRequest)
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

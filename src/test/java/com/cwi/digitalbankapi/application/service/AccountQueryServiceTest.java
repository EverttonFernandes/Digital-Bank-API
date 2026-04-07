package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.application.converter.AccountResponseConverter;
import com.cwi.digitalbankapi.application.dto.AccountResponse;
import com.cwi.digitalbankapi.domain.account.exception.AccountNotFoundException;
import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

class AccountQueryServiceTest {

    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final AccountQueryService accountQueryService = new AccountQueryService(
        accountRepository,
        new AccountResponseConverter()
    );

    @Test
    @DisplayName("Deve listar contas cadastradas com nome do titular e saldo")
    void shouldListRegisteredAccountsWithOwnerNameAndBalance() {
        // GIVEN
        List<Account> registeredAccounts = List.of(
            new Account(
                1L,
                "Ana Souza",
                new BigDecimal("1250.00"),
                OffsetDateTime.parse("2026-04-07T00:00:00Z"),
                OffsetDateTime.parse("2026-04-07T00:00:00Z")
            ),
            new Account(
                2L,
                "Bruno Lima",
                new BigDecimal("980.50"),
                OffsetDateTime.parse("2026-04-07T00:00:00Z"),
                OffsetDateTime.parse("2026-04-07T00:00:00Z")
            )
        );

        BDDMockito.given(accountRepository.findAllAccounts()).willReturn(registeredAccounts);

        // WHEN
        List<AccountResponse> accountResponseList = accountQueryService.listAllAccounts();

        // THEN
        Assertions.assertEquals(2, accountResponseList.size());
        Assertions.assertEquals("Ana Souza", accountResponseList.get(0).ownerName());
        Assertions.assertEquals(new BigDecimal("980.50"), accountResponseList.get(1).balance());
    }

    @Test
    @DisplayName("Deve buscar conta existente pelo identificador")
    void shouldFindExistingAccountByIdentifier() {
        // GIVEN
        Account existingAccount = new Account(
            1L,
            "Ana Souza",
            new BigDecimal("1250.00"),
            OffsetDateTime.parse("2026-04-07T00:00:00Z"),
            OffsetDateTime.parse("2026-04-07T00:00:00Z")
        );

        BDDMockito.given(accountRepository.findAccountById(1L)).willReturn(Optional.of(existingAccount));

        // WHEN
        AccountResponse accountResponse = accountQueryService.findAccountById(1L);

        // THEN
        Assertions.assertEquals(1L, accountResponse.id());
        Assertions.assertEquals("Ana Souza", accountResponse.ownerName());
        Assertions.assertEquals(new BigDecimal("1250.00"), accountResponse.balance());
    }

    @Test
    @DisplayName("Deve lançar excecao quando buscar conta inexistente")
    void shouldThrowExceptionWhenTryingToFindNonExistingAccount() {
        // GIVEN
        BDDMockito.given(accountRepository.findAccountById(99L)).willReturn(Optional.empty());

        // WHEN
        AccountNotFoundException accountNotFoundException = Assertions.assertThrows(
            AccountNotFoundException.class,
            () -> accountQueryService.findAccountById(99L)
        );

        // THEN
        Assertions.assertEquals("ACCOUNT_NOT_FOUND", accountNotFoundException.getKey());
        Assertions.assertEquals(
            "Conta nao encontrada para o identificador 99.",
            accountNotFoundException.getValue()
        );
    }
}

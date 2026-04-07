package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.application.dto.AccountMovementResponse;
import com.cwi.digitalbankapi.domain.account.exception.AccountNotFoundException;
import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import com.cwi.digitalbankapi.domain.statement.model.AccountMovement;
import com.cwi.digitalbankapi.domain.statement.model.AccountMovementType;
import com.cwi.digitalbankapi.domain.statement.repository.AccountMovementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

class AccountMovementQueryServiceTest {

    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final AccountMovementRepository accountMovementRepository = mock(AccountMovementRepository.class);
    private final AccountMovementQueryService accountMovementQueryService = new AccountMovementQueryService(
        accountRepository,
        accountMovementRepository
    );

    @Test
    @DisplayName("Deve consultar movimentacoes da conta quando a conta existir")
    void shouldFindAccountMovementsWhenAccountExists() {
        // GIVEN
        Long accountId = 1L;
        Account account = new Account(accountId, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());
        AccountMovement debitMovement = new AccountMovement(
            1L,
            accountId,
            "transfer-reference-001",
            AccountMovementType.DEBIT,
            new BigDecimal("200.00"),
            "Debito gerado pela transferencia para a conta 2.",
            OffsetDateTime.now()
        );

        BDDMockito.given(accountRepository.findAccountById(accountId)).willReturn(Optional.of(account));
        BDDMockito.given(accountMovementRepository.findAccountMovementsByAccountId(accountId)).willReturn(List.of(debitMovement));

        // WHEN
        List<AccountMovementResponse> accountMovementResponseList = accountMovementQueryService.findAccountMovementsByAccountId(accountId);

        // THEN
        Assertions.assertEquals(1, accountMovementResponseList.size());
        Assertions.assertEquals("DEBIT", accountMovementResponseList.get(0).movementType());
        Assertions.assertEquals("transfer-reference-001", accountMovementResponseList.get(0).transferReference());
    }

    @Test
    @DisplayName("Deve rejeitar consulta de movimentacoes quando a conta nao existir")
    void shouldRejectAccountMovementQueryWhenAccountDoesNotExist() {
        // GIVEN
        Long accountId = 99L;

        BDDMockito.given(accountRepository.findAccountById(accountId)).willReturn(Optional.empty());

        // WHEN
        AccountNotFoundException exception = Assertions.assertThrows(
            AccountNotFoundException.class,
            () -> accountMovementQueryService.findAccountMovementsByAccountId(accountId)
        );

        // THEN
        Assertions.assertEquals("ACCOUNT_NOT_FOUND", exception.getKey());
        Assertions.assertEquals("Conta nao encontrada para o identificador 99.", exception.getValue());
    }
}

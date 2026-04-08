package com.digitalbankapi.infrastructure.persistence.repository;

import com.digitalbankapi.domain.statement.model.AccountMovement;
import com.digitalbankapi.domain.statement.model.AccountMovementType;
import com.digitalbankapi.domain.account.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;

class AccountMovementRepositoryImplTest {

    private final AccountMovementJpaRepository springDataAccountMovementJpaRepository = mock(AccountMovementJpaRepository.class);
    private final AccountMovementRepositoryImpl postgreSqlAccountMovementRepository = new AccountMovementRepositoryImpl(
            springDataAccountMovementJpaRepository
    );

    @Test
    @DisplayName("Deve salvar movimentacoes usando referencia relacional da conta persistente")
    void shouldSaveMovementsUsingRelationalReferenceFromPersistedAccount() {
        // GIVEN
        Account accountReference = new Account(1L, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());
        AccountMovement accountMovement = new AccountMovement(
                null,
                accountReference,
                "transfer-reference-001",
                AccountMovementType.DEBIT,
                new BigDecimal("200.00"),
                "Debito de teste.",
                OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );

        // WHEN
        postgreSqlAccountMovementRepository.saveAccountMovements(List.of(accountMovement));

        // THEN
        ArgumentCaptor<List<AccountMovement>> accountMovementListArgumentCaptor = ArgumentCaptor.forClass(List.class);
        BDDMockito.then(springDataAccountMovementJpaRepository).should().saveAll(accountMovementListArgumentCaptor.capture());
        Assertions.assertEquals(1, accountMovementListArgumentCaptor.getValue().size());
        Assertions.assertEquals(1L, accountMovementListArgumentCaptor.getValue().get(0).getAccount().getId());
        Assertions.assertEquals("transfer-reference-001", accountMovementListArgumentCaptor.getValue().get(0).getTransferReference());
    }

    @Test
    @DisplayName("Deve buscar movimentacoes por identificador da conta mantendo o mapeamento para o dominio")
    void shouldFindMovementsByAccountIdentifierKeepingMappingToDomain() {
        // GIVEN
        Account accountReference = new Account(1L, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());
        AccountMovement accountMovement = new AccountMovement(
                10L,
                accountReference,
                "transfer-reference-001",
                AccountMovementType.DEBIT,
                new BigDecimal("200.00"),
                "Debito de teste.",
                OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );

        BDDMockito.given(springDataAccountMovementJpaRepository.findByAccountIdOrderByCreatedAtDescIdDesc(1L))
                .willReturn(List.of(accountMovement));

        // WHEN
        List<AccountMovement> accountMovementList = postgreSqlAccountMovementRepository.findAccountMovementsByAccountId(1L);

        // THEN
        Assertions.assertEquals(1, accountMovementList.size());
        Assertions.assertEquals(1L, accountMovementList.get(0).getAccountId());
        Assertions.assertEquals("transfer-reference-001", accountMovementList.get(0).getTransferReference());
    }
}

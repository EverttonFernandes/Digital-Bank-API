package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.domain.statement.model.AccountMovement;
import com.cwi.digitalbankapi.domain.statement.model.AccountMovementType;
import com.cwi.digitalbankapi.infrastructure.persistence.entity.AccountEntity;
import com.cwi.digitalbankapi.infrastructure.persistence.entity.AccountMovementEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;

class PostgreSqlAccountMovementRepositoryTest {

    private final SpringDataAccountMovementJpaRepository springDataAccountMovementJpaRepository = mock(SpringDataAccountMovementJpaRepository.class);
    private final SpringDataAccountJpaRepository springDataAccountJpaRepository = mock(SpringDataAccountJpaRepository.class);
    private final PostgreSqlAccountMovementRepository postgreSqlAccountMovementRepository = new PostgreSqlAccountMovementRepository(
        springDataAccountMovementJpaRepository,
        springDataAccountJpaRepository
    );

    @Test
    @DisplayName("Deve salvar movimentacoes usando referencia relacional da conta persistente")
    void shouldSaveMovementsUsingRelationalReferenceFromPersistedAccount() {
        // GIVEN
        AccountMovement accountMovement = new AccountMovement(
            null,
            1L,
            "transfer-reference-001",
            AccountMovementType.DEBIT,
            new BigDecimal("200.00"),
            "Debito de teste.",
            OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );
        AccountEntity accountEntityReference = new AccountEntity(1L, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());

        BDDMockito.given(springDataAccountJpaRepository.getReferenceById(1L)).willReturn(accountEntityReference);

        // WHEN
        postgreSqlAccountMovementRepository.saveAccountMovements(List.of(accountMovement));

        // THEN
        ArgumentCaptor<List<AccountMovementEntity>> accountMovementEntityListArgumentCaptor = ArgumentCaptor.forClass(List.class);
        BDDMockito.then(springDataAccountMovementJpaRepository).should().saveAll(accountMovementEntityListArgumentCaptor.capture());
        Assertions.assertEquals(1, accountMovementEntityListArgumentCaptor.getValue().size());
        Assertions.assertEquals(1L, accountMovementEntityListArgumentCaptor.getValue().get(0).getAccountEntity().getId());
        Assertions.assertEquals("transfer-reference-001", accountMovementEntityListArgumentCaptor.getValue().get(0).getTransferReference());
    }

    @Test
    @DisplayName("Deve buscar movimentacoes por identificador da conta mantendo o mapeamento para o dominio")
    void shouldFindMovementsByAccountIdentifierKeepingMappingToDomain() {
        // GIVEN
        AccountEntity accountEntityReference = new AccountEntity(1L, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());
        AccountMovementEntity accountMovementEntity = new AccountMovementEntity(
            accountEntityReference,
            "transfer-reference-001",
            AccountMovementType.DEBIT,
            new BigDecimal("200.00"),
            "Debito de teste.",
            OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );

        BDDMockito.given(springDataAccountMovementJpaRepository.findByAccountEntityIdOrderByCreatedAtDescIdDesc(1L))
            .willReturn(List.of(accountMovementEntity));

        // WHEN
        List<AccountMovement> accountMovementList = postgreSqlAccountMovementRepository.findAccountMovementsByAccountId(1L);

        // THEN
        Assertions.assertEquals(1, accountMovementList.size());
        Assertions.assertEquals(1L, accountMovementList.get(0).accountId());
        Assertions.assertEquals("transfer-reference-001", accountMovementList.get(0).transferReference());
    }
}

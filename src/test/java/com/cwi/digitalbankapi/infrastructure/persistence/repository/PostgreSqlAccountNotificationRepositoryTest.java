package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.domain.notification.model.AccountNotification;
import com.cwi.digitalbankapi.domain.notification.model.AccountNotificationStatus;
import com.cwi.digitalbankapi.infrastructure.persistence.entity.AccountEntity;
import com.cwi.digitalbankapi.infrastructure.persistence.entity.AccountNotificationEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;

class PostgreSqlAccountNotificationRepositoryTest {

    private final SpringDataAccountNotificationJpaRepository springDataAccountNotificationJpaRepository = mock(SpringDataAccountNotificationJpaRepository.class);
    private final SpringDataAccountJpaRepository springDataAccountJpaRepository = mock(SpringDataAccountJpaRepository.class);
    private final PostgreSqlAccountNotificationRepository postgreSqlAccountNotificationRepository = new PostgreSqlAccountNotificationRepository(
        springDataAccountNotificationJpaRepository,
        springDataAccountJpaRepository
    );

    @Test
    @DisplayName("Deve salvar notificacoes usando referencia relacional da conta persistente")
    void shouldSaveNotificationsUsingRelationalReferenceFromPersistedAccount() {
        // GIVEN
        AccountNotification accountNotification = new AccountNotification(
            null,
            1L,
            "transfer-reference-001",
            AccountNotificationStatus.REGISTERED,
            "Notificacao de teste.",
            OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );
        AccountEntity accountEntityReference = new AccountEntity(1L, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());

        BDDMockito.given(springDataAccountJpaRepository.getReferenceById(1L)).willReturn(accountEntityReference);

        // WHEN
        postgreSqlAccountNotificationRepository.saveAccountNotifications(List.of(accountNotification));

        // THEN
        ArgumentCaptor<List<AccountNotificationEntity>> accountNotificationEntityListArgumentCaptor = ArgumentCaptor.forClass(List.class);
        BDDMockito.then(springDataAccountNotificationJpaRepository).should().saveAll(accountNotificationEntityListArgumentCaptor.capture());
        Assertions.assertEquals(1, accountNotificationEntityListArgumentCaptor.getValue().size());
        Assertions.assertEquals(1L, accountNotificationEntityListArgumentCaptor.getValue().get(0).getAccountEntity().getId());
        Assertions.assertEquals("transfer-reference-001", accountNotificationEntityListArgumentCaptor.getValue().get(0).getTransferReference());
    }

    @Test
    @DisplayName("Deve buscar notificacoes por identificador da conta mantendo o mapeamento para o dominio")
    void shouldFindNotificationsByAccountIdentifierKeepingMappingToDomain() {
        // GIVEN
        AccountEntity accountEntityReference = new AccountEntity(1L, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());
        AccountNotificationEntity accountNotificationEntity = new AccountNotificationEntity(
            accountEntityReference,
            "transfer-reference-001",
            AccountNotificationStatus.REGISTERED,
            "Notificacao de teste.",
            OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );

        BDDMockito.given(springDataAccountNotificationJpaRepository.findByAccountEntityIdOrderByCreatedAtDescIdDesc(1L))
            .willReturn(List.of(accountNotificationEntity));

        // WHEN
        List<AccountNotification> accountNotificationList = postgreSqlAccountNotificationRepository.findAccountNotificationsByAccountId(1L);

        // THEN
        Assertions.assertEquals(1, accountNotificationList.size());
        Assertions.assertEquals(1L, accountNotificationList.get(0).accountId());
        Assertions.assertEquals("transfer-reference-001", accountNotificationList.get(0).transferReference());
    }
}

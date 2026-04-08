package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.domain.notification.model.AccountNotification;
import com.cwi.digitalbankapi.domain.notification.model.AccountNotificationStatus;
import com.cwi.digitalbankapi.domain.account.model.Account;
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
    private final PostgreSqlAccountNotificationRepository postgreSqlAccountNotificationRepository = new PostgreSqlAccountNotificationRepository(
        springDataAccountNotificationJpaRepository
    );

    @Test
    @DisplayName("Deve salvar notificacoes usando referencia relacional da conta persistente")
    void shouldSaveNotificationsUsingRelationalReferenceFromPersistedAccount() {
        // GIVEN
        Account accountReference = new Account(1L, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());
        AccountNotification accountNotification = new AccountNotification(
            null,
            accountReference,
            "transfer-reference-001",
            AccountNotificationStatus.REGISTERED,
            "Notificacao de teste.",
            OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );

        // WHEN
        postgreSqlAccountNotificationRepository.saveAccountNotifications(List.of(accountNotification));

        // THEN
        ArgumentCaptor<List<AccountNotification>> accountNotificationListArgumentCaptor = ArgumentCaptor.forClass(List.class);
        BDDMockito.then(springDataAccountNotificationJpaRepository).should().saveAll(accountNotificationListArgumentCaptor.capture());
        Assertions.assertEquals(1, accountNotificationListArgumentCaptor.getValue().size());
        Assertions.assertEquals(1L, accountNotificationListArgumentCaptor.getValue().get(0).getAccount().getId());
        Assertions.assertEquals("transfer-reference-001", accountNotificationListArgumentCaptor.getValue().get(0).getTransferReference());
    }

    @Test
    @DisplayName("Deve buscar notificacoes por identificador da conta mantendo o mapeamento para o dominio")
    void shouldFindNotificationsByAccountIdentifierKeepingMappingToDomain() {
        // GIVEN
        Account accountReference = new Account(1L, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());
        AccountNotification accountNotification = new AccountNotification(
            10L,
            accountReference,
            "transfer-reference-001",
            AccountNotificationStatus.REGISTERED,
            "Notificacao de teste.",
            OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );

        BDDMockito.given(springDataAccountNotificationJpaRepository.findByAccountIdOrderByCreatedAtDescIdDesc(1L))
            .willReturn(List.of(accountNotification));

        // WHEN
        List<AccountNotification> accountNotificationList = postgreSqlAccountNotificationRepository.findAccountNotificationsByAccountId(1L);

        // THEN
        Assertions.assertEquals(1, accountNotificationList.size());
        Assertions.assertEquals(1L, accountNotificationList.get(0).getAccountId());
        Assertions.assertEquals("transfer-reference-001", accountNotificationList.get(0).getTransferReference());
    }
}

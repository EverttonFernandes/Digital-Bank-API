package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.application.dto.AccountNotificationResponse;
import com.cwi.digitalbankapi.domain.account.exception.AccountNotFoundException;
import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import com.cwi.digitalbankapi.domain.notification.model.AccountNotification;
import com.cwi.digitalbankapi.domain.notification.model.AccountNotificationStatus;
import com.cwi.digitalbankapi.domain.notification.repository.AccountNotificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

class AccountNotificationQueryServiceTest {

    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final AccountNotificationRepository accountNotificationRepository = mock(AccountNotificationRepository.class);
    private final AccountNotificationQueryService accountNotificationQueryService = new AccountNotificationQueryService(
        accountRepository,
        accountNotificationRepository
    );

    @Test
    @DisplayName("Deve consultar notificacoes da conta quando a conta existir")
    void shouldFindAccountNotificationsWhenAccountExists() {
        // GIVEN
        Long accountId = 1L;
        Account account = new Account(accountId, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());
        AccountNotification accountNotification = new AccountNotification(
            1L,
            accountId,
            "transfer-reference-001",
            AccountNotificationStatus.REGISTERED,
            "Transferencia enviada com sucesso para a conta 2.",
            OffsetDateTime.now()
        );

        BDDMockito.given(accountRepository.findAccountById(accountId)).willReturn(Optional.of(account));
        BDDMockito.given(accountNotificationRepository.findAccountNotificationsByAccountId(accountId))
            .willReturn(List.of(accountNotification));

        // WHEN
        List<AccountNotificationResponse> accountNotificationResponseList = accountNotificationQueryService
            .findAccountNotificationsByAccountId(accountId);

        // THEN
        Assertions.assertEquals(1, accountNotificationResponseList.size());
        Assertions.assertEquals("REGISTERED", accountNotificationResponseList.get(0).notificationStatus());
        Assertions.assertEquals("transfer-reference-001", accountNotificationResponseList.get(0).transferReference());
    }

    @Test
    @DisplayName("Deve rejeitar consulta de notificacoes quando a conta nao existir")
    void shouldRejectAccountNotificationQueryWhenAccountDoesNotExist() {
        // GIVEN
        Long accountId = 99L;

        BDDMockito.given(accountRepository.findAccountById(accountId)).willReturn(Optional.empty());

        // WHEN
        AccountNotFoundException accountNotFoundException = Assertions.assertThrows(
            AccountNotFoundException.class,
            () -> accountNotificationQueryService.findAccountNotificationsByAccountId(accountId)
        );

        // THEN
        Assertions.assertEquals("ACCOUNT_NOT_FOUND", accountNotFoundException.getKey());
        Assertions.assertEquals("Conta nao encontrada para o identificador 99.", accountNotFoundException.getValue());
    }
}

package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.application.converter.TransferRequestConverter;
import com.cwi.digitalbankapi.application.dto.TransferRequest;
import com.cwi.digitalbankapi.application.dto.TransferResponse;
import com.cwi.digitalbankapi.domain.account.exception.AccountNotFoundException;
import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import com.cwi.digitalbankapi.domain.notification.gateway.TransferCompletedEventPublisher;
import com.cwi.digitalbankapi.domain.statement.repository.AccountMovementRepository;
import com.cwi.digitalbankapi.domain.transfer.specification.CompositeTransferSpecification;
import com.cwi.digitalbankapi.domain.transfer.specification.TransferAccountsMustBeDifferentSpecification;
import com.cwi.digitalbankapi.domain.transfer.specification.TransferSourceAccountMustHaveSufficientBalanceSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

class TransferServiceTest {

    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final AccountMovementRepository accountMovementRepository = mock(AccountMovementRepository.class);
    private final TransferCompletedEventPublisher transferCompletedEventPublisher = mock(TransferCompletedEventPublisher.class);
    private final TransferService transferService = new TransferService(
        accountRepository,
        accountMovementRepository,
        transferCompletedEventPublisher,
        new TransferRequestConverter(),
        new CompositeTransferSpecification(List.of(
            new TransferAccountsMustBeDifferentSpecification(),
            new TransferSourceAccountMustHaveSufficientBalanceSpecification()
        ))
    );

    @Test
    @DisplayName("Deve transferir saldo entre contas quando a transferencia for valida")
    void shouldTransferBalanceBetweenAccountsWhenTransferIsValid() {
        // GIVEN
        Account sourceAccount = new Account(1L, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());
        Account targetAccount = new Account(2L, "Bruno Lima", new BigDecimal("980.50"), OffsetDateTime.now(), OffsetDateTime.now());
        TransferRequest transferRequest = new TransferRequest(1L, 2L, new BigDecimal("200.00"));

        BDDMockito.given(accountRepository.findAccountsByIdentifiersWithPessimisticLock(1L, 2L))
            .willReturn(List.of(sourceAccount, targetAccount));
        BDDMockito.given(accountRepository.saveAccounts(List.of(sourceAccount, targetAccount))).willReturn(List.of(sourceAccount, targetAccount));

        // WHEN
        TransferResponse transferResponse = transferService.transfer(transferRequest);

        // THEN
        Assertions.assertEquals(1L, transferResponse.sourceAccountId());
        Assertions.assertEquals(2L, transferResponse.targetAccountId());
        Assertions.assertTrue(transferResponse.transferReference() != null && !transferResponse.transferReference().isBlank());
        Assertions.assertEquals(new BigDecimal("200.00"), transferResponse.transferredAmount());
        Assertions.assertEquals(new BigDecimal("1050.00"), transferResponse.sourceAccountBalance());
        Assertions.assertEquals(new BigDecimal("1180.50"), transferResponse.targetAccountBalance());
        BDDMockito.then(transferCompletedEventPublisher).should().publish(BDDMockito.any());
    }

    @Test
    @DisplayName("Deve rejeitar transferencia quando a conta de origem nao existir")
    void shouldRejectTransferWhenSourceAccountDoesNotExist() {
        // GIVEN
        TransferRequest transferRequest = new TransferRequest(99L, 2L, new BigDecimal("10.00"));

        BDDMockito.given(accountRepository.findAccountsByIdentifiersWithPessimisticLock(99L, 2L))
            .willReturn(List.of(
                new Account(2L, "Bruno Lima", new BigDecimal("980.50"), OffsetDateTime.now(), OffsetDateTime.now())
            ));

        // WHEN
        AccountNotFoundException accountNotFoundException = Assertions.assertThrows(
            AccountNotFoundException.class,
            () -> transferService.transfer(transferRequest)
        );

        // THEN
        Assertions.assertEquals("ACCOUNT_NOT_FOUND", accountNotFoundException.getKey());
        Assertions.assertEquals("Conta nao encontrada para o identificador 99.", accountNotFoundException.getValue());
    }
}

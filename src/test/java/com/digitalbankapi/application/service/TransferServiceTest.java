package com.digitalbankapi.application.service;

import com.digitalbankapi.application.converter.TransferDTOConverter;
import com.digitalbankapi.application.dto.TransferDTO;
import com.digitalbankapi.application.dto.TransferResponseDTO;
import com.digitalbankapi.domain.account.exception.AccountResourceBusyException;
import com.digitalbankapi.domain.account.exception.AccountNotFoundException;
import com.digitalbankapi.domain.account.model.Account;
import com.digitalbankapi.domain.account.repository.AccountRepository;
import com.digitalbankapi.domain.notification.gateway.TransferCompletedEventPublisher;
import com.digitalbankapi.domain.statement.repository.AccountMovementRepository;
import com.digitalbankapi.domain.transfer.specification.CompositeTransferSpecification;
import com.digitalbankapi.domain.transfer.specification.TransferAccountsMustBeDifferentSpecification;
import com.digitalbankapi.domain.transfer.specification.TransferSourceAccountMustHaveSufficientBalanceSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.dao.CannotAcquireLockException;

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
            new TransferDTOConverter(),
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
        TransferDTO transferRequest = new TransferDTO(1L, 2L, new BigDecimal("200.00"));

        BDDMockito.given(accountRepository.findAccountsByIdentifiersWithPessimisticLock(1L, 2L))
                .willReturn(List.of(sourceAccount, targetAccount));
        BDDMockito.given(accountRepository.saveAccounts(List.of(sourceAccount, targetAccount))).willReturn(List.of(sourceAccount, targetAccount));

        // WHEN
        TransferResponseDTO transferResponse = transferService.transfer(transferRequest);

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
        TransferDTO transferRequest = new TransferDTO(99L, 2L, new BigDecimal("10.00"));

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

    @Test
    @DisplayName("Deve retornar erro semantico quando as contas estiverem ocupadas por outra transferencia concorrente")
    void shouldReturnSemanticErrorWhenAccountsAreBusyByAnotherConcurrentTransfer() {
        // GIVEN
        TransferDTO transferRequest = new TransferDTO(1L, 2L, new BigDecimal("10.00"));

        BDDMockito.given(accountRepository.findAccountsByIdentifiersWithPessimisticLock(1L, 2L))
                .willThrow(new CannotAcquireLockException("lock timeout"));

        // WHEN
        AccountResourceBusyException accountResourceBusyException = Assertions.assertThrows(
                AccountResourceBusyException.class,
                () -> transferService.transfer(transferRequest)
        );

        // THEN
        Assertions.assertEquals("ACCOUNT_RESOURCE_BUSY", accountResourceBusyException.getKey());
        Assertions.assertEquals(
                "Uma das contas envolvidas esta temporariamente em processamento. Tente novamente em instantes.",
                accountResourceBusyException.getValue()
        );
        Assertions.assertEquals(org.springframework.http.HttpStatus.CONFLICT, accountResourceBusyException.getHttpStatus());
    }
}

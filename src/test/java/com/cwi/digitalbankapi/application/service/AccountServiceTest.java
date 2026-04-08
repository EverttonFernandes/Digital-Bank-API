package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.application.converter.AccountResponseConverter;
import com.cwi.digitalbankapi.application.converter.CreateAccountRequestConverter;
import com.cwi.digitalbankapi.application.dto.AccountMovementResponse;
import com.cwi.digitalbankapi.application.dto.AccountNotificationResponse;
import com.cwi.digitalbankapi.application.dto.AccountResponse;
import com.cwi.digitalbankapi.application.dto.CreateAccountRequest;
import com.cwi.digitalbankapi.domain.account.exception.AccountInitialBalanceMustNotBeNegativeException;
import com.cwi.digitalbankapi.domain.account.exception.AccountNotFoundException;
import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import com.cwi.digitalbankapi.domain.account.specification.AccountInitialBalanceMustNotBeNegativeSpecification;
import com.cwi.digitalbankapi.domain.account.specification.CompositeAccountCreationSpecification;
import com.cwi.digitalbankapi.domain.notification.model.AccountNotification;
import com.cwi.digitalbankapi.domain.notification.model.AccountNotificationStatus;
import com.cwi.digitalbankapi.domain.notification.repository.AccountNotificationRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class AccountServiceTest {

    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final AccountMovementRepository accountMovementRepository = mock(AccountMovementRepository.class);
    private final AccountNotificationRepository accountNotificationRepository = mock(AccountNotificationRepository.class);
    private final AccountService accountService = new AccountService(
        accountRepository,
        accountMovementRepository,
        accountNotificationRepository,
        new CreateAccountRequestConverter(),
        new CompositeAccountCreationSpecification(List.of(
            new AccountInitialBalanceMustNotBeNegativeSpecification()
        )),
        new AccountResponseConverter()
    );

    @Test
    @DisplayName("Deve criar conta bancaria quando os dados informados forem validos")
    void shouldCreateBankAccountWhenProvidedDataIsValid() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("Maria Silva", new BigDecimal("350.00"));
        Account createdAccount = new Account(
            4L,
            "Maria Silva",
            new BigDecimal("350.00"),
            OffsetDateTime.parse("2026-04-07T00:00:00Z"),
            OffsetDateTime.parse("2026-04-07T00:00:00Z")
        );

        BDDMockito.given(accountRepository.saveAccount(any(Account.class))).willReturn(createdAccount);

        AccountResponse accountResponse = accountService.createAccount(createAccountRequest);

        Assertions.assertEquals(4L, accountResponse.id());
        Assertions.assertEquals("Maria Silva", accountResponse.ownerName());
        Assertions.assertEquals(new BigDecimal("350.00"), accountResponse.balance());
        BDDMockito.then(accountRepository).should().saveAccount(any(Account.class));
    }

    @Test
    @DisplayName("Deve rejeitar criacao de conta bancaria quando o saldo inicial for negativo")
    void shouldRejectBankAccountCreationWhenInitialBalanceIsNegative() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("Maria Silva", new BigDecimal("-1.00"));

        AccountInitialBalanceMustNotBeNegativeException exception = Assertions.assertThrows(
            AccountInitialBalanceMustNotBeNegativeException.class,
            () -> accountService.createAccount(createAccountRequest)
        );

        Assertions.assertEquals("ACCOUNT_INITIAL_BALANCE_MUST_NOT_BE_NEGATIVE", exception.getKey());
        Assertions.assertEquals("O saldo inicial da conta nao pode ser negativo.", exception.getValue());
    }

    @Test
    @DisplayName("Deve listar contas cadastradas com nome do titular e saldo")
    void shouldListRegisteredAccountsWithOwnerNameAndBalance() {
        List<Account> registeredAccounts = List.of(
            new Account(1L, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.parse("2026-04-07T00:00:00Z"), OffsetDateTime.parse("2026-04-07T00:00:00Z")),
            new Account(2L, "Bruno Lima", new BigDecimal("980.50"), OffsetDateTime.parse("2026-04-07T00:00:00Z"), OffsetDateTime.parse("2026-04-07T00:00:00Z"))
        );

        BDDMockito.given(accountRepository.findAllAccounts()).willReturn(registeredAccounts);

        List<AccountResponse> accountResponseList = accountService.listAllAccounts();

        Assertions.assertEquals(2, accountResponseList.size());
        Assertions.assertEquals("Ana Souza", accountResponseList.get(0).ownerName());
        Assertions.assertEquals(new BigDecimal("980.50"), accountResponseList.get(1).balance());
    }

    @Test
    @DisplayName("Deve buscar conta existente pelo identificador")
    void shouldFindExistingAccountByIdentifier() {
        Account existingAccount = new Account(
            1L,
            "Ana Souza",
            new BigDecimal("1250.00"),
            OffsetDateTime.parse("2026-04-07T00:00:00Z"),
            OffsetDateTime.parse("2026-04-07T00:00:00Z")
        );

        BDDMockito.given(accountRepository.findAccountById(1L)).willReturn(Optional.of(existingAccount));

        AccountResponse accountResponse = accountService.findAccountById(1L);

        Assertions.assertEquals(1L, accountResponse.id());
        Assertions.assertEquals("Ana Souza", accountResponse.ownerName());
        Assertions.assertEquals(new BigDecimal("1250.00"), accountResponse.balance());
    }

    @Test
    @DisplayName("Deve consultar movimentacoes da conta quando a conta existir")
    void shouldFindAccountMovementsWhenAccountExists() {
        Long accountId = 1L;
        Account account = new Account(accountId, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());
        AccountMovement debitMovement = new AccountMovement(
            1L,
            account,
            "transfer-reference-001",
            AccountMovementType.DEBIT,
            new BigDecimal("200.00"),
            "Debito gerado pela transferencia para a conta 2.",
            OffsetDateTime.now()
        );

        BDDMockito.given(accountRepository.findAccountById(accountId)).willReturn(Optional.of(account));
        BDDMockito.given(accountMovementRepository.findAccountMovementsByAccountId(accountId)).willReturn(List.of(debitMovement));

        List<AccountMovementResponse> accountMovementResponseList = accountService.findAccountMovementsByAccountId(accountId);

        Assertions.assertEquals(1, accountMovementResponseList.size());
        Assertions.assertEquals("DEBIT", accountMovementResponseList.get(0).movementType());
        Assertions.assertEquals("transfer-reference-001", accountMovementResponseList.get(0).transferReference());
    }

    @Test
    @DisplayName("Deve consultar notificacoes da conta quando a conta existir")
    void shouldFindAccountNotificationsWhenAccountExists() {
        Long accountId = 1L;
        Account account = new Account(accountId, "Ana Souza", new BigDecimal("1250.00"), OffsetDateTime.now(), OffsetDateTime.now());
        AccountNotification accountNotification = new AccountNotification(
            1L,
            account,
            "transfer-reference-001",
            AccountNotificationStatus.REGISTERED,
            "Transferencia enviada com sucesso para a conta 2.",
            OffsetDateTime.now()
        );

        BDDMockito.given(accountRepository.findAccountById(accountId)).willReturn(Optional.of(account));
        BDDMockito.given(accountNotificationRepository.findAccountNotificationsByAccountId(accountId))
            .willReturn(List.of(accountNotification));

        List<AccountNotificationResponse> accountNotificationResponseList = accountService.findAccountNotificationsByAccountId(accountId);

        Assertions.assertEquals(1, accountNotificationResponseList.size());
        Assertions.assertEquals("REGISTERED", accountNotificationResponseList.get(0).notificationStatus());
        Assertions.assertEquals("transfer-reference-001", accountNotificationResponseList.get(0).transferReference());
    }

    @Test
    @DisplayName("Deve lançar excecao quando buscar conta inexistente")
    void shouldThrowExceptionWhenTryingToFindNonExistingAccount() {
        BDDMockito.given(accountRepository.findAccountById(99L)).willReturn(Optional.empty());

        AccountNotFoundException accountNotFoundException = Assertions.assertThrows(
            AccountNotFoundException.class,
            () -> accountService.findAccountById(99L)
        );

        Assertions.assertEquals("ACCOUNT_NOT_FOUND", accountNotFoundException.getKey());
        Assertions.assertEquals("Conta nao encontrada para o identificador 99.", accountNotFoundException.getValue());
    }
}

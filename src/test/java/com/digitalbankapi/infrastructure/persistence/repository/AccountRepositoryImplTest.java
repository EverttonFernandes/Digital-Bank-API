package com.digitalbankapi.infrastructure.persistence.repository;

import com.digitalbankapi.domain.account.model.Account;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

class AccountRepositoryImplTest {

    private final AccountJpaRepository accountJpaRepository = mock(AccountJpaRepository.class);
    private final EntityManager entityManager = mock(EntityManager.class);
    private final Session session = mock(Session.class);
    private final AccountRepositoryImpl accountRepository = new AccountRepositoryImpl(accountJpaRepository, entityManager);

    @Test
    @DisplayName("Deve listar contas em ordem crescente por identificador")
    void shouldFindAllAccountsSortedByIdentifier() {
        List<Account> accountList = List.of(account(1L, "Ana", "100.00"));
        BDDMockito.given(accountJpaRepository.findAll(any(Sort.class))).willReturn(accountList);

        List<Account> response = accountRepository.findAllAccounts();

        ArgumentCaptor<Sort> sortArgumentCaptor = ArgumentCaptor.forClass(Sort.class);
        BDDMockito.then(accountJpaRepository).should().findAll(sortArgumentCaptor.capture());
        Assertions.assertEquals("id: ASC", sortArgumentCaptor.getValue().toString());
        Assertions.assertSame(accountList, response);
    }

    @Test
    @DisplayName("Deve buscar conta por identificador")
    void shouldFindAccountByIdentifier() {
        Account account = account(1L, "Ana", "100.00");
        BDDMockito.given(accountJpaRepository.findById(1L)).willReturn(Optional.of(account));

        Optional<Account> response = accountRepository.findAccountById(1L);

        Assertions.assertTrue(response.isPresent());
        Assertions.assertSame(account, response.get());
    }

    @Test
    @DisplayName("Deve configurar lock timeout antes de buscar contas com lock pessimista")
    void shouldConfigureLockTimeoutBeforeSearchingAccountsWithPessimisticLock() throws Exception {
        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        List<Account> accountList = List.of(account(1L, "Ana", "100.00"), account(2L, "Bruno", "200.00"));

        BDDMockito.given(entityManager.unwrap(Session.class)).willReturn(session);
        BDDMockito.willAnswer(invocation -> {
            Work work = invocation.getArgument(0);
            BDDMockito.given(connection.createStatement()).willReturn(statement);
            work.execute(connection);
            return null;
        }).given(session).doWork(any(Work.class));
        BDDMockito.given(accountJpaRepository.findAccountsByIdentifiersWithPessimisticLock(1L, 2L)).willReturn(accountList);

        List<Account> response = accountRepository.findAccountsByIdentifiersWithPessimisticLock(1L, 2L);

        BDDMockito.then(statement).should().execute("SET LOCAL lock_timeout = '3s'");
        BDDMockito.then(accountJpaRepository).should().findAccountsByIdentifiersWithPessimisticLock(1L, 2L);
        Assertions.assertSame(accountList, response);
    }

    @Test
    @DisplayName("Deve salvar conta individual")
    void shouldSaveSingleAccount() {
        Account account = account(1L, "Ana", "100.00");
        BDDMockito.given(accountJpaRepository.save(account)).willReturn(account);

        Account response = accountRepository.saveAccount(account);

        Assertions.assertSame(account, response);
    }

    @Test
    @DisplayName("Deve sincronizar saldo e data antes de salvar lista de contas")
    void shouldSynchronizeBalanceAndUpdatedAtBeforeSavingAccountList() {
        OffsetDateTime updatedAt = OffsetDateTime.parse("2026-04-08T00:00:00Z");
        Account persistedSource = account(1L, "Ana", "100.00");
        Account persistedTarget = account(2L, "Bruno", "200.00");
        Account requestedSource = account(1L, "Ana", "80.00");
        Account requestedTarget = account(2L, "Bruno", "220.00");
        requestedSource.setUpdatedAt(updatedAt);
        requestedTarget.setUpdatedAt(updatedAt);

        BDDMockito.given(accountJpaRepository.findAllById(List.of(1L, 2L)))
                .willReturn(List.of(persistedSource, persistedTarget));
        BDDMockito.given(accountJpaRepository.saveAll(any())).willAnswer(invocation -> invocation.getArgument(0));

        List<Account> response = accountRepository.saveAccounts(List.of(requestedSource, requestedTarget));

        Assertions.assertEquals(new BigDecimal("80.00"), persistedSource.getBalance());
        Assertions.assertEquals(new BigDecimal("220.00"), persistedTarget.getBalance());
        Assertions.assertEquals(updatedAt, persistedSource.getUpdatedAt());
        Assertions.assertEquals(updatedAt, persistedTarget.getUpdatedAt());
        Assertions.assertEquals(2, response.size());
        BDDMockito.then(accountJpaRepository).should().findAllById(eq(List.of(1L, 2L)));
    }

    private Account account(Long id, String ownerName, String balance) {
        return new Account(
                id,
                ownerName,
                new BigDecimal(balance),
                OffsetDateTime.parse("2026-04-08T00:00:00Z"),
                OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );
    }
}

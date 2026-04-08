package com.digitalbankapi.infrastructure.persistence.repository;

import com.digitalbankapi.domain.account.model.Account;
import com.digitalbankapi.domain.account.repository.AccountRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private static final String ACCOUNT_LOCK_TIMEOUT = "3s";

    private final AccountJpaRepository springDataAccountJpaRepository;
    private final EntityManager entityManager;

    public AccountRepositoryImpl(
            AccountJpaRepository springDataAccountJpaRepository,
            EntityManager entityManager
    ) {
        this.springDataAccountJpaRepository = springDataAccountJpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Account> findAllAccounts() {
        return springDataAccountJpaRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Optional<Account> findAccountById(Long accountIdentifier) {
        return springDataAccountJpaRepository.findById(accountIdentifier);
    }

    @Override
    public List<Account> findAccountsByIdentifiersWithPessimisticLock(Long sourceAccountIdentifier, Long targetAccountIdentifier) {
        entityManager.unwrap(Session.class).doWork(connection -> {
            try (var statement = connection.createStatement()) {
                statement.execute("SET LOCAL lock_timeout = '" + ACCOUNT_LOCK_TIMEOUT + "'");
            }
        });

        return springDataAccountJpaRepository.findAccountsByIdentifiersWithPessimisticLock(
                sourceAccountIdentifier,
                targetAccountIdentifier
        );
    }

    @Override
    public Account saveAccount(Account account) {
        return springDataAccountJpaRepository.save(account);
    }

    @Override
    public List<Account> saveAccounts(List<Account> accountList) {
        List<Account> lockedAccountList = springDataAccountJpaRepository.findAllById(
                accountList.stream()
                        .map(Account::getId)
                        .toList()
        );

        for (Account persistedAccount : lockedAccountList) {
            Account account = accountList.stream()
                    .filter(currentAccount -> currentAccount.getId().equals(persistedAccount.getId()))
                    .findFirst()
                    .orElseThrow();

            persistedAccount.setBalance(account.getBalance());
            persistedAccount.setUpdatedAt(account.getUpdatedAt());
        }

        return springDataAccountJpaRepository.saveAll(lockedAccountList);
    }
}

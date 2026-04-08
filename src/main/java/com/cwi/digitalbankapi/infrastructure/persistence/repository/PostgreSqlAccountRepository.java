package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import com.cwi.digitalbankapi.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostgreSqlAccountRepository implements AccountRepository {

    private final SpringDataAccountJpaRepository springDataAccountJpaRepository;

    public PostgreSqlAccountRepository(SpringDataAccountJpaRepository springDataAccountJpaRepository) {
        this.springDataAccountJpaRepository = springDataAccountJpaRepository;
    }

    @Override
    public List<Account> findAllAccounts() {
        return springDataAccountJpaRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public Optional<Account> findAccountById(Long accountIdentifier) {
        return springDataAccountJpaRepository.findById(accountIdentifier)
            .map(this::toDomain);
    }

    @Override
    public List<Account> findAccountsByIdentifiersWithPessimisticLock(Long sourceAccountIdentifier, Long targetAccountIdentifier) {
        return springDataAccountJpaRepository.findAccountsByIdentifiersWithPessimisticLock(
                sourceAccountIdentifier,
                targetAccountIdentifier
            )
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public Account saveAccount(Account account) {
        AccountEntity accountEntity = new AccountEntity(
            account.getId(),
            account.getOwnerName(),
            account.getBalance(),
            account.getCreatedAt(),
            account.getUpdatedAt()
        );

        return toDomain(springDataAccountJpaRepository.save(accountEntity));
    }

    @Override
    public List<Account> saveAccounts(List<Account> accountList) {
        List<AccountEntity> lockedAccountEntityList = springDataAccountJpaRepository.findAllById(
            accountList.stream()
                .map(Account::getId)
                .toList()
        );

        for (AccountEntity accountEntity : lockedAccountEntityList) {
            Account account = accountList.stream()
                .filter(currentAccount -> currentAccount.getId().equals(accountEntity.getId()))
                .findFirst()
                .orElseThrow();

            accountEntity.setBalance(account.getBalance());
            accountEntity.setUpdatedAt(account.getUpdatedAt());
        }

        return springDataAccountJpaRepository.saveAll(lockedAccountEntityList)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    private Account toDomain(AccountEntity accountEntity) {
        return new Account(
            accountEntity.getId(),
            accountEntity.getOwnerName(),
            accountEntity.getBalance(),
            accountEntity.getCreatedAt(),
            accountEntity.getUpdatedAt()
        );
    }
}

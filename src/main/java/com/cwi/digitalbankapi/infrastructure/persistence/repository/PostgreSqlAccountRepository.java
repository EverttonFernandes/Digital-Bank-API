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

package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJpaRepository springDataAccountJpaRepository;

    public AccountRepositoryImpl(AccountJpaRepository springDataAccountJpaRepository) {
        this.springDataAccountJpaRepository = springDataAccountJpaRepository;
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

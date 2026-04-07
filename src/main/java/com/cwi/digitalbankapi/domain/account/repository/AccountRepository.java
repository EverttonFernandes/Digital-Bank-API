package com.cwi.digitalbankapi.domain.account.repository;

import com.cwi.digitalbankapi.domain.account.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    List<Account> findAllAccounts();

    Optional<Account> findAccountById(Long accountIdentifier);

    List<Account> findAccountsByIdentifiersWithPessimisticLock(Long sourceAccountIdentifier, Long targetAccountIdentifier);

    List<Account> saveAccounts(List<Account> accountList);
}

package com.digitalbankapi.domain.account.specification;

import com.digitalbankapi.domain.account.model.Account;

public interface AccountCreationSpecification {

    void ensureSatisfiedBy(Account account);
}

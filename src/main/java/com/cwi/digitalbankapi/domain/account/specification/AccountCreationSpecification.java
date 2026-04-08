package com.cwi.digitalbankapi.domain.account.specification;

import com.cwi.digitalbankapi.domain.account.model.Account;

public interface AccountCreationSpecification {

    void ensureSatisfiedBy(Account account);
}

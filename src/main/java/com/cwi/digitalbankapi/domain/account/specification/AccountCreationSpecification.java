package com.cwi.digitalbankapi.domain.account.specification;

import com.cwi.digitalbankapi.domain.account.model.AccountCreationCommand;

public interface AccountCreationSpecification {

    void ensureSatisfiedBy(AccountCreationCommand accountCreationCommand);
}

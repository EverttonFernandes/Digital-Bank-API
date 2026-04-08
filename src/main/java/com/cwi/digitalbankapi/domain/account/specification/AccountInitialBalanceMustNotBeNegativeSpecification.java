package com.cwi.digitalbankapi.domain.account.specification;

import com.cwi.digitalbankapi.domain.account.exception.AccountInitialBalanceMustNotBeNegativeException;
import com.cwi.digitalbankapi.domain.account.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountInitialBalanceMustNotBeNegativeSpecification implements AccountCreationSpecification {

    @Override
    public void ensureSatisfiedBy(Account account) {
        if (account.getBalance().signum() < 0) {
            throw new AccountInitialBalanceMustNotBeNegativeException();
        }
    }
}

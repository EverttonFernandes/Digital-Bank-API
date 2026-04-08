package com.digitalbankapi.domain.account.specification;

import com.digitalbankapi.domain.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompositeAccountCreationSpecification {

    private final List<AccountCreationSpecification> accountCreationSpecificationList;

    public CompositeAccountCreationSpecification(List<AccountCreationSpecification> accountCreationSpecificationList) {
        this.accountCreationSpecificationList = accountCreationSpecificationList;
    }

    public void ensureSatisfiedBy(Account account) {
        for (AccountCreationSpecification accountCreationSpecification : accountCreationSpecificationList) {
            accountCreationSpecification.ensureSatisfiedBy(account);
        }
    }
}

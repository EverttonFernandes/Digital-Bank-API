package com.cwi.digitalbankapi.domain.account.specification;

import com.cwi.digitalbankapi.domain.account.model.AccountCreationCommand;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompositeAccountCreationSpecification {

    private final List<AccountCreationSpecification> accountCreationSpecificationList;

    public CompositeAccountCreationSpecification(List<AccountCreationSpecification> accountCreationSpecificationList) {
        this.accountCreationSpecificationList = accountCreationSpecificationList;
    }

    public void ensureSatisfiedBy(AccountCreationCommand accountCreationCommand) {
        for (AccountCreationSpecification accountCreationSpecification : accountCreationSpecificationList) {
            accountCreationSpecification.ensureSatisfiedBy(accountCreationCommand);
        }
    }
}

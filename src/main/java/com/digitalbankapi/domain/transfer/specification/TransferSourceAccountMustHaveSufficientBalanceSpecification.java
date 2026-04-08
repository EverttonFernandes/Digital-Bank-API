package com.digitalbankapi.domain.transfer.specification;

import com.digitalbankapi.domain.transfer.exception.InsufficientAccountBalanceException;
import com.digitalbankapi.domain.transfer.model.Transfer;
import org.springframework.stereotype.Component;

@Component
public class TransferSourceAccountMustHaveSufficientBalanceSpecification implements TransferSpecification {

    @Override
    public void ensureSatisfiedBy(Transfer transfer) {
        if (transfer.sourceAccount().getBalance().compareTo(transfer.amount()) < 0) {
            throw new InsufficientAccountBalanceException();
        }
    }
}

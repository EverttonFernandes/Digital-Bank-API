package com.digitalbankapi.domain.transfer.specification;

import com.digitalbankapi.domain.transfer.exception.SourceAndTargetAccountsMustBeDifferentException;
import com.digitalbankapi.domain.transfer.model.Transfer;
import org.springframework.stereotype.Component;

@Component
public class TransferAccountsMustBeDifferentSpecification implements TransferSpecification {

    @Override
    public void ensureSatisfiedBy(Transfer transfer) {
        if (transfer.sourceAccount().getId().equals(transfer.targetAccount().getId())) {
            throw new SourceAndTargetAccountsMustBeDifferentException();
        }
    }
}

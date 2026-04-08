package com.digitalbankapi.domain.transfer.specification;

import com.digitalbankapi.domain.transfer.model.Transfer;

public interface TransferSpecification {

    void ensureSatisfiedBy(Transfer transfer);
}

package com.cwi.digitalbankapi.domain.transfer.specification;

import com.cwi.digitalbankapi.domain.transfer.model.Transfer;

public interface TransferSpecification {

    void ensureSatisfiedBy(Transfer transfer);
}

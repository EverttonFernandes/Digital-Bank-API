package com.digitalbankapi.domain.transfer.specification;

import com.digitalbankapi.domain.transfer.model.Transfer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompositeTransferSpecification {

    private final List<TransferSpecification> transferSpecificationList;

    public CompositeTransferSpecification(List<TransferSpecification> transferSpecificationList) {
        this.transferSpecificationList = transferSpecificationList;
    }

    public void ensureSatisfiedBy(Transfer transfer) {
        for (TransferSpecification transferSpecification : transferSpecificationList) {
            transferSpecification.ensureSatisfiedBy(transfer);
        }
    }
}

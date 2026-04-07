package com.cwi.digitalbankapi.application.converter;

import com.cwi.digitalbankapi.application.dto.TransferRequest;
import com.cwi.digitalbankapi.domain.transfer.exception.TransferAmountMustBePositiveException;
import com.cwi.digitalbankapi.domain.transfer.model.TransferCommand;
import org.springframework.stereotype.Component;

@Component
public class TransferRequestConverter {

    public TransferCommand convert(TransferRequest transferRequest) {
        if (transferRequest.amount() == null || transferRequest.amount().signum() <= 0) {
            throw new TransferAmountMustBePositiveException();
        }

        return new TransferCommand(
            transferRequest.sourceAccountId(),
            transferRequest.targetAccountId(),
            transferRequest.amount()
        );
    }
}

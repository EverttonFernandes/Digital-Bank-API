package com.cwi.digitalbankapi.domain.transfer.exception;

import com.cwi.digitalbankapi.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class TransferAmountMustBePositiveException extends BusinessException {

    public TransferAmountMustBePositiveException() {
        super(
                "TRANSFER_AMOUNT_MUST_BE_POSITIVE",
                "O valor da transferencia deve ser maior que zero.",
                HttpStatus.BAD_REQUEST
        );
    }
}

package com.cwi.digitalbankapi.domain.transfer.exception;

import com.cwi.digitalbankapi.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class SourceAndTargetAccountsMustBeDifferentException extends BusinessException {

    public SourceAndTargetAccountsMustBeDifferentException() {
        super(
            "SOURCE_AND_TARGET_ACCOUNTS_MUST_BE_DIFFERENT",
            "Conta de origem e conta de destino devem ser diferentes.",
            HttpStatus.BAD_REQUEST
        );
    }
}

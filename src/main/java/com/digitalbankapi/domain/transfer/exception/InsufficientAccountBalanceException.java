package com.digitalbankapi.domain.transfer.exception;

import com.digitalbankapi.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class InsufficientAccountBalanceException extends BusinessException {

    public InsufficientAccountBalanceException() {
        super(
                "INSUFFICIENT_ACCOUNT_BALANCE",
                "A conta de origem nao possui saldo suficiente para a transferencia.",
                HttpStatus.BAD_REQUEST
        );
    }
}

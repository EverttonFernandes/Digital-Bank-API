package com.digitalbankapi.domain.account.exception;

import com.digitalbankapi.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AccountInitialBalanceMustNotBeNegativeException extends BusinessException {

    public AccountInitialBalanceMustNotBeNegativeException() {
        super(
                "ACCOUNT_INITIAL_BALANCE_MUST_NOT_BE_NEGATIVE",
                "O saldo inicial da conta nao pode ser negativo.",
                HttpStatus.BAD_REQUEST
        );
    }
}

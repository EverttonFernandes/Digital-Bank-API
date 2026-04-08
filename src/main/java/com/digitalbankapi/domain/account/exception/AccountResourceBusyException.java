package com.digitalbankapi.domain.account.exception;

import com.digitalbankapi.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AccountResourceBusyException extends BusinessException {

    public AccountResourceBusyException() {
        super(
                "ACCOUNT_RESOURCE_BUSY",
                "Uma das contas envolvidas esta temporariamente em processamento. Tente novamente em instantes.",
                HttpStatus.CONFLICT
        );
    }
}

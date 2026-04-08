package com.digitalbankapi.domain.account.exception;

import com.digitalbankapi.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends BusinessException {

    public AccountNotFoundException(Long accountIdentifier) {
        super(
                "ACCOUNT_NOT_FOUND",
                "Conta nao encontrada para o identificador " + accountIdentifier + ".",
                HttpStatus.NOT_FOUND
        );
    }
}

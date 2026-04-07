package com.cwi.digitalbankapi.domain.account.exception;

import com.cwi.digitalbankapi.shared.exception.BusinessException;

public class AccountNotFoundException extends BusinessException {

    public AccountNotFoundException(Long accountIdentifier) {
        super(
            "ACCOUNT_NOT_FOUND",
            "Conta nao encontrada para o identificador " + accountIdentifier + "."
        );
    }
}

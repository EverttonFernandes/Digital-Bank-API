package com.digitalbankapi.application.converter;

import com.digitalbankapi.application.dto.AccountCreateDTO;
import com.digitalbankapi.domain.account.model.Account;
import com.digitalbankapi.shared.exception.InvalidRequestDataException;
import org.springframework.stereotype.Component;

@Component
public class AccountCreateDTOConverter {

    public Account convert(AccountCreateDTO createAccountRequest) {
        if (createAccountRequest.ownerName() == null || createAccountRequest.ownerName().isBlank()) {
            throw new InvalidRequestDataException("O campo ownerName e obrigatorio.");
        }

        if (createAccountRequest.initialBalance() == null) {
            throw new InvalidRequestDataException("O campo initialBalance e obrigatorio.");
        }

        return Account.open(createAccountRequest.ownerName().trim(), createAccountRequest.initialBalance());
    }
}

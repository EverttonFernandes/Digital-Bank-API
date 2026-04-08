package com.cwi.digitalbankapi.application.converter;

import com.cwi.digitalbankapi.application.dto.CreateAccountRequest;
import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.shared.exception.InvalidRequestDataException;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountRequestConverter {

    public Account convert(CreateAccountRequest createAccountRequest) {
        if (createAccountRequest.ownerName() == null || createAccountRequest.ownerName().isBlank()) {
            throw new InvalidRequestDataException("O campo ownerName e obrigatorio.");
        }

        if (createAccountRequest.initialBalance() == null) {
            throw new InvalidRequestDataException("O campo initialBalance e obrigatorio.");
        }

        return Account.open(createAccountRequest.ownerName().trim(), createAccountRequest.initialBalance());
    }
}

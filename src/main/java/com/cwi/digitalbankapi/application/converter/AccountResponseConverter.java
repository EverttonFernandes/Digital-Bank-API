package com.cwi.digitalbankapi.application.converter;

import com.cwi.digitalbankapi.application.dto.AccountResponse;
import com.cwi.digitalbankapi.domain.account.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountResponseConverter {

    public AccountResponse convert(Account account) {
        return new AccountResponse(
            account.id(),
            account.ownerName(),
            account.balance()
        );
    }
}

package com.cwi.digitalbankapi.application.converter;

import com.cwi.digitalbankapi.application.dto.AccountDTO;
import com.cwi.digitalbankapi.domain.account.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDTOConverter {

    public AccountDTO convert(Account account) {
        return new AccountDTO(
            account.getId(),
            account.getOwnerName(),
            account.getBalance()
        );
    }
}

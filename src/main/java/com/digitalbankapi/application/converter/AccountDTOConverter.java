package com.digitalbankapi.application.converter;

import com.digitalbankapi.application.dto.AccountDTO;
import com.digitalbankapi.domain.account.model.Account;
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

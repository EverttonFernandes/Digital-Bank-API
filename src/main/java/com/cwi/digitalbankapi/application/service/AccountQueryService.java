package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.application.converter.AccountResponseConverter;
import com.cwi.digitalbankapi.application.dto.AccountResponse;
import com.cwi.digitalbankapi.domain.account.exception.AccountNotFoundException;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountQueryService {

    private final AccountRepository accountRepository;
    private final AccountResponseConverter accountResponseConverter;

    public AccountQueryService(
        AccountRepository accountRepository,
        AccountResponseConverter accountResponseConverter
    ) {
        this.accountRepository = accountRepository;
        this.accountResponseConverter = accountResponseConverter;
    }

    public List<AccountResponse> listAllAccounts() {
        return accountRepository.findAllAccounts()
            .stream()
            .map(accountResponseConverter::convert)
            .toList();
    }

    public AccountResponse findAccountById(Long accountIdentifier) {
        return accountRepository.findAccountById(accountIdentifier)
            .map(accountResponseConverter::convert)
            .orElseThrow(() -> new AccountNotFoundException(accountIdentifier));
    }
}

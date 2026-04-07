package com.cwi.digitalbankapi.api.controller;

import com.cwi.digitalbankapi.application.dto.AccountResponse;
import com.cwi.digitalbankapi.application.service.AccountQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountQueryService accountQueryService;

    public AccountController(AccountQueryService accountQueryService) {
        this.accountQueryService = accountQueryService;
    }

    @GetMapping
    public List<AccountResponse> listAllAccounts() {
        return accountQueryService.listAllAccounts();
    }

    @GetMapping("/{accountIdentifier}")
    public AccountResponse findAccountById(@PathVariable Long accountIdentifier) {
        return accountQueryService.findAccountById(accountIdentifier);
    }
}

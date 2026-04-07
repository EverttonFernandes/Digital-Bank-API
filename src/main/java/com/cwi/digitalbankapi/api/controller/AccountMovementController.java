package com.cwi.digitalbankapi.api.controller;

import com.cwi.digitalbankapi.application.dto.AccountMovementResponse;
import com.cwi.digitalbankapi.application.service.AccountMovementQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/movements")
public class AccountMovementController {

    private final AccountMovementQueryService accountMovementQueryService;

    public AccountMovementController(AccountMovementQueryService accountMovementQueryService) {
        this.accountMovementQueryService = accountMovementQueryService;
    }

    @GetMapping
    public List<AccountMovementResponse> findAccountMovementsByAccountId(@PathVariable Long accountId) {
        return accountMovementQueryService.findAccountMovementsByAccountId(accountId);
    }
}

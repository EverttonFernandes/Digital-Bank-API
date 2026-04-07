package com.cwi.digitalbankapi.api.controller;

import com.cwi.digitalbankapi.application.dto.AccountNotificationResponse;
import com.cwi.digitalbankapi.application.service.AccountNotificationQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/notifications")
public class AccountNotificationController {

    private final AccountNotificationQueryService accountNotificationQueryService;

    public AccountNotificationController(AccountNotificationQueryService accountNotificationQueryService) {
        this.accountNotificationQueryService = accountNotificationQueryService;
    }

    @GetMapping
    public List<AccountNotificationResponse> findAccountNotificationsByAccountId(@PathVariable Long accountId) {
        return accountNotificationQueryService.findAccountNotificationsByAccountId(accountId);
    }
}

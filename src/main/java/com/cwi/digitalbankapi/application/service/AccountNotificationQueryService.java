package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.application.dto.AccountNotificationResponse;
import com.cwi.digitalbankapi.domain.account.exception.AccountNotFoundException;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import com.cwi.digitalbankapi.domain.notification.repository.AccountNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountNotificationQueryService {

    private final AccountRepository accountRepository;
    private final AccountNotificationRepository accountNotificationRepository;

    public AccountNotificationQueryService(
        AccountRepository accountRepository,
        AccountNotificationRepository accountNotificationRepository
    ) {
        this.accountRepository = accountRepository;
        this.accountNotificationRepository = accountNotificationRepository;
    }

    public List<AccountNotificationResponse> findAccountNotificationsByAccountId(Long accountId) {
        accountRepository.findAccountById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));

        return accountNotificationRepository.findAccountNotificationsByAccountId(accountId)
            .stream()
            .map(accountNotification -> new AccountNotificationResponse(
                accountNotification.accountId(),
                accountNotification.transferReference(),
                accountNotification.notificationStatus().name(),
                accountNotification.message(),
                accountNotification.createdAt()
            ))
            .toList();
    }
}

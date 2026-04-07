package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.application.dto.AccountMovementResponse;
import com.cwi.digitalbankapi.domain.account.exception.AccountNotFoundException;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import com.cwi.digitalbankapi.domain.statement.repository.AccountMovementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountMovementQueryService {

    private final AccountRepository accountRepository;
    private final AccountMovementRepository accountMovementRepository;

    public AccountMovementQueryService(
        AccountRepository accountRepository,
        AccountMovementRepository accountMovementRepository
    ) {
        this.accountRepository = accountRepository;
        this.accountMovementRepository = accountMovementRepository;
    }

    public List<AccountMovementResponse> findAccountMovementsByAccountId(Long accountId) {
        accountRepository.findAccountById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));

        return accountMovementRepository.findAccountMovementsByAccountId(accountId)
            .stream()
            .map(accountMovement -> new AccountMovementResponse(
                accountMovement.accountId(),
                accountMovement.transferReference(),
                accountMovement.movementType().name(),
                accountMovement.amount(),
                accountMovement.description(),
                accountMovement.createdAt()
            ))
            .toList();
    }
}

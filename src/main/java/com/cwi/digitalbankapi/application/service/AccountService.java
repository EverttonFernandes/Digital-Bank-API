package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.application.converter.AccountResponseConverter;
import com.cwi.digitalbankapi.application.converter.CreateAccountRequestConverter;
import com.cwi.digitalbankapi.application.dto.AccountMovementResponse;
import com.cwi.digitalbankapi.application.dto.AccountNotificationResponse;
import com.cwi.digitalbankapi.application.dto.AccountResponse;
import com.cwi.digitalbankapi.application.dto.CreateAccountRequest;
import com.cwi.digitalbankapi.domain.account.exception.AccountNotFoundException;
import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import com.cwi.digitalbankapi.domain.account.specification.CompositeAccountCreationSpecification;
import com.cwi.digitalbankapi.domain.notification.repository.AccountNotificationRepository;
import com.cwi.digitalbankapi.domain.statement.repository.AccountMovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMovementRepository accountMovementRepository;
    private final AccountNotificationRepository accountNotificationRepository;
    private final CreateAccountRequestConverter createAccountRequestConverter;
    private final CompositeAccountCreationSpecification compositeAccountCreationSpecification;
    private final AccountResponseConverter accountResponseConverter;

    public AccountService(
        AccountRepository accountRepository,
        AccountMovementRepository accountMovementRepository,
        AccountNotificationRepository accountNotificationRepository,
        CreateAccountRequestConverter createAccountRequestConverter,
        CompositeAccountCreationSpecification compositeAccountCreationSpecification,
        AccountResponseConverter accountResponseConverter
    ) {
        this.accountRepository = accountRepository;
        this.accountMovementRepository = accountMovementRepository;
        this.accountNotificationRepository = accountNotificationRepository;
        this.createAccountRequestConverter = createAccountRequestConverter;
        this.compositeAccountCreationSpecification = compositeAccountCreationSpecification;
        this.accountResponseConverter = accountResponseConverter;
    }

    @Transactional
    public AccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        Account accountToBeCreated = createAccountRequestConverter.convert(createAccountRequest);

        compositeAccountCreationSpecification.ensureSatisfiedBy(accountToBeCreated);

        return accountResponseConverter.convert(accountRepository.saveAccount(accountToBeCreated));
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

    public List<AccountMovementResponse> findAccountMovementsByAccountId(Long accountId) {
        findExistingAccountById(accountId);

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

    public List<AccountNotificationResponse> findAccountNotificationsByAccountId(Long accountId) {
        findExistingAccountById(accountId);

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

    private Account findExistingAccountById(Long accountId) {
        return accountRepository.findAccountById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}

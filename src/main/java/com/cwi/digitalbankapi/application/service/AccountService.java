package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.application.converter.AccountDTOConverter;
import com.cwi.digitalbankapi.application.converter.AccountCreateDTOConverter;
import com.cwi.digitalbankapi.application.dto.AccountMovementDTO;
import com.cwi.digitalbankapi.application.dto.AccountNotificationDTO;
import com.cwi.digitalbankapi.application.dto.AccountDTO;
import com.cwi.digitalbankapi.application.dto.AccountCreateDTO;
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
    private final AccountCreateDTOConverter createAccountRequestConverter;
    private final CompositeAccountCreationSpecification compositeAccountCreationSpecification;
    private final AccountDTOConverter accountResponseConverter;

    public AccountService(
        AccountRepository accountRepository,
        AccountMovementRepository accountMovementRepository,
        AccountNotificationRepository accountNotificationRepository,
        AccountCreateDTOConverter createAccountRequestConverter,
        CompositeAccountCreationSpecification compositeAccountCreationSpecification,
        AccountDTOConverter accountResponseConverter
    ) {
        this.accountRepository = accountRepository;
        this.accountMovementRepository = accountMovementRepository;
        this.accountNotificationRepository = accountNotificationRepository;
        this.createAccountRequestConverter = createAccountRequestConverter;
        this.compositeAccountCreationSpecification = compositeAccountCreationSpecification;
        this.accountResponseConverter = accountResponseConverter;
    }

    @Transactional
    public AccountDTO createAccount(AccountCreateDTO createAccountRequest) {
        Account accountToBeCreated = createAccountRequestConverter.convert(createAccountRequest);

        compositeAccountCreationSpecification.ensureSatisfiedBy(accountToBeCreated);

        return accountResponseConverter.convert(accountRepository.saveAccount(accountToBeCreated));
    }

    public List<AccountDTO> listAllAccounts() {
        return accountRepository.findAllAccounts()
            .stream()
            .map(accountResponseConverter::convert)
            .toList();
    }

    public AccountDTO findAccountById(Long accountIdentifier) {
        return accountRepository.findAccountById(accountIdentifier)
            .map(accountResponseConverter::convert)
            .orElseThrow(() -> new AccountNotFoundException(accountIdentifier));
    }

    public List<AccountMovementDTO> findAccountMovementsByAccountId(Long accountId) {
        findExistingAccountById(accountId);

        return accountMovementRepository.findAccountMovementsByAccountId(accountId)
            .stream()
            .map(accountMovement -> new AccountMovementDTO(
                accountMovement.getAccountId(),
                accountMovement.getTransferReference(),
                accountMovement.getMovementType().name(),
                accountMovement.getAmount(),
                accountMovement.getDescription(),
                accountMovement.getCreatedAt()
            ))
            .toList();
    }

    public List<AccountNotificationDTO> findAccountNotificationsByAccountId(Long accountId) {
        findExistingAccountById(accountId);

        return accountNotificationRepository.findAccountNotificationsByAccountId(accountId)
            .stream()
            .map(accountNotification -> new AccountNotificationDTO(
                accountNotification.getAccountId(),
                accountNotification.getTransferReference(),
                accountNotification.getNotificationStatus().name(),
                accountNotification.getMessage(),
                accountNotification.getCreatedAt()
            ))
            .toList();
    }

    private Account findExistingAccountById(Long accountId) {
        return accountRepository.findAccountById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));
    }
}

package com.cwi.digitalbankapi.application.service;

import com.cwi.digitalbankapi.application.converter.AccountResponseConverter;
import com.cwi.digitalbankapi.application.converter.CreateAccountRequestConverter;
import com.cwi.digitalbankapi.application.dto.AccountResponse;
import com.cwi.digitalbankapi.application.dto.CreateAccountRequest;
import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.account.model.AccountCreationCommand;
import com.cwi.digitalbankapi.domain.account.repository.AccountRepository;
import com.cwi.digitalbankapi.domain.account.specification.CompositeAccountCreationSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class CreateAccountService {

    private final AccountRepository accountRepository;
    private final CreateAccountRequestConverter createAccountRequestConverter;
    private final CompositeAccountCreationSpecification compositeAccountCreationSpecification;
    private final AccountResponseConverter accountResponseConverter;

    public CreateAccountService(
        AccountRepository accountRepository,
        CreateAccountRequestConverter createAccountRequestConverter,
        CompositeAccountCreationSpecification compositeAccountCreationSpecification,
        AccountResponseConverter accountResponseConverter
    ) {
        this.accountRepository = accountRepository;
        this.createAccountRequestConverter = createAccountRequestConverter;
        this.compositeAccountCreationSpecification = compositeAccountCreationSpecification;
        this.accountResponseConverter = accountResponseConverter;
    }

    @Transactional
    public AccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        AccountCreationCommand accountCreationCommand = createAccountRequestConverter.convert(createAccountRequest);

        compositeAccountCreationSpecification.ensureSatisfiedBy(accountCreationCommand);

        OffsetDateTime accountCreationDate = OffsetDateTime.now();
        Account accountToBeCreated = new Account(
            null,
            accountCreationCommand.ownerName(),
            accountCreationCommand.initialBalance(),
            accountCreationDate,
            accountCreationDate
        );

        Account createdAccount = accountRepository.saveAccount(accountToBeCreated);

        return accountResponseConverter.convert(createdAccount);
    }
}

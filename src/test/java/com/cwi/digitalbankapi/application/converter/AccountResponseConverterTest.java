package com.cwi.digitalbankapi.application.converter;

import com.cwi.digitalbankapi.application.dto.AccountResponse;
import com.cwi.digitalbankapi.domain.account.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

class AccountResponseConverterTest {

    private final AccountResponseConverter accountResponseConverter = new AccountResponseConverter();

    @Test
    @DisplayName("Deve converter conta de dominio em resposta de conta")
    void shouldConvertDomainAccountIntoAccountResponse() {
        Account account = new Account(
            1L,
            "Ana Souza",
            new BigDecimal("1250.00"),
            OffsetDateTime.parse("2026-04-07T00:00:00Z"),
            OffsetDateTime.parse("2026-04-07T00:00:00Z")
        );

        AccountResponse accountResponse = accountResponseConverter.convert(account);

        Assertions.assertEquals(1L, accountResponse.id());
        Assertions.assertEquals("Ana Souza", accountResponse.ownerName());
        Assertions.assertEquals(new BigDecimal("1250.00"), accountResponse.balance());
    }
}

package com.digitalbankapi.application.converter;

import com.digitalbankapi.application.dto.AccountCreateDTO;
import com.digitalbankapi.domain.account.model.Account;
import com.digitalbankapi.shared.exception.InvalidRequestDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AccountCreateDTOConverterTest {

    private final AccountCreateDTOConverter createAccountRequestConverter = new AccountCreateDTOConverter();

    @Test
    @DisplayName("Deve converter requisicao valida de criacao de conta em agregado de dominio")
    void shouldConvertValidAccountCreateDTOIntoDomainAccount() {
        // GIVEN
        AccountCreateDTO createAccountRequest = new AccountCreateDTO(" Maria Silva ", new BigDecimal("350.00"));

        // WHEN
        Account account = createAccountRequestConverter.convert(createAccountRequest);

        // THEN
        Assertions.assertNull(account.getId());
        Assertions.assertEquals("Maria Silva", account.getOwnerName());
        Assertions.assertEquals(new BigDecimal("350.00"), account.getBalance());
    }

    @Test
    @DisplayName("Deve rejeitar requisicao de criacao de conta quando o nome do titular nao for informado")
    void shouldRejectAccountCreateDTOWhenOwnerNameIsNotProvided() {
        // GIVEN
        AccountCreateDTO createAccountRequest = new AccountCreateDTO(null, new BigDecimal("350.00"));

        // WHEN
        InvalidRequestDataException invalidRequestDataException = Assertions.assertThrows(
                InvalidRequestDataException.class,
                () -> createAccountRequestConverter.convert(createAccountRequest)
        );

        // THEN
        Assertions.assertEquals("INVALID_REQUEST_DATA", invalidRequestDataException.getKey());
        Assertions.assertEquals("O campo ownerName e obrigatorio.", invalidRequestDataException.getValue());
    }

    @Test
    @DisplayName("Deve rejeitar requisicao de criacao de conta quando o nome do titular estiver em branco")
    void shouldRejectAccountCreateDTOWhenOwnerNameIsBlank() {
        // GIVEN
        AccountCreateDTO createAccountRequest = new AccountCreateDTO("   ", new BigDecimal("350.00"));

        // WHEN
        InvalidRequestDataException invalidRequestDataException = Assertions.assertThrows(
                InvalidRequestDataException.class,
                () -> createAccountRequestConverter.convert(createAccountRequest)
        );

        // THEN
        Assertions.assertEquals("INVALID_REQUEST_DATA", invalidRequestDataException.getKey());
        Assertions.assertEquals("O campo ownerName e obrigatorio.", invalidRequestDataException.getValue());
    }

    @Test
    @DisplayName("Deve rejeitar requisicao de criacao de conta quando o saldo inicial nao for informado")
    void shouldRejectAccountCreateDTOWhenInitialBalanceIsNotProvided() {
        // GIVEN
        AccountCreateDTO createAccountRequest = new AccountCreateDTO("Maria Silva", null);

        // WHEN
        InvalidRequestDataException invalidRequestDataException = Assertions.assertThrows(
                InvalidRequestDataException.class,
                () -> createAccountRequestConverter.convert(createAccountRequest)
        );

        // THEN
        Assertions.assertEquals("INVALID_REQUEST_DATA", invalidRequestDataException.getKey());
        Assertions.assertEquals("O campo initialBalance e obrigatorio.", invalidRequestDataException.getValue());
    }
}

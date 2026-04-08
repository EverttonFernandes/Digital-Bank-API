package com.cwi.digitalbankapi.application.converter;

import com.cwi.digitalbankapi.application.dto.CreateAccountRequest;
import com.cwi.digitalbankapi.domain.account.model.AccountCreationCommand;
import com.cwi.digitalbankapi.shared.exception.InvalidRequestDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class CreateAccountRequestConverterTest {

    private final CreateAccountRequestConverter createAccountRequestConverter = new CreateAccountRequestConverter();

    @Test
    @DisplayName("Deve converter requisicao valida de criacao de conta em comando de dominio")
    void shouldConvertValidCreateAccountRequestIntoDomainCommand() {
        // GIVEN
        CreateAccountRequest createAccountRequest = new CreateAccountRequest(" Maria Silva ", new BigDecimal("350.00"));

        // WHEN
        AccountCreationCommand accountCreationCommand = createAccountRequestConverter.convert(createAccountRequest);

        // THEN
        Assertions.assertEquals("Maria Silva", accountCreationCommand.ownerName());
        Assertions.assertEquals(new BigDecimal("350.00"), accountCreationCommand.initialBalance());
    }

    @Test
    @DisplayName("Deve rejeitar requisicao de criacao de conta quando o nome do titular nao for informado")
    void shouldRejectCreateAccountRequestWhenOwnerNameIsNotProvided() {
        // GIVEN
        CreateAccountRequest createAccountRequest = new CreateAccountRequest(null, new BigDecimal("350.00"));

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
    void shouldRejectCreateAccountRequestWhenOwnerNameIsBlank() {
        // GIVEN
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("   ", new BigDecimal("350.00"));

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
    void shouldRejectCreateAccountRequestWhenInitialBalanceIsNotProvided() {
        // GIVEN
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("Maria Silva", null);

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

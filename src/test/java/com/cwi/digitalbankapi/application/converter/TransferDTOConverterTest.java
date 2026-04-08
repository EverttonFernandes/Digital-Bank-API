package com.cwi.digitalbankapi.application.converter;

import com.cwi.digitalbankapi.application.dto.TransferDTO;
import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.transfer.exception.TransferAmountMustBePositiveException;
import com.cwi.digitalbankapi.domain.transfer.model.Transfer;
import com.cwi.digitalbankapi.shared.exception.InvalidRequestDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import java.time.OffsetDateTime;

class TransferDTOConverterTest {

    private final TransferDTOConverter transferRequestConverter = new TransferDTOConverter();

    @Test
    @DisplayName("Deve converter requisicao de transferencia valida em agregado de transferencia")
    void shouldConvertValidTransferDTOIntoTransfer() {
        // GIVEN
        TransferDTO transferRequest = new TransferDTO(1L, 2L, new BigDecimal("150.00"));
        Account sourceAccount = new Account(1L, "Ana Souza", new BigDecimal("900.00"), OffsetDateTime.now(), OffsetDateTime.now());
        Account targetAccount = new Account(2L, "Bruno Lima", new BigDecimal("800.00"), OffsetDateTime.now(), OffsetDateTime.now());

        // WHEN
        Transfer transfer = transferRequestConverter.convert(transferRequest, sourceAccount, targetAccount);

        // THEN
        Assertions.assertEquals(1L, transfer.sourceAccount().getId());
        Assertions.assertEquals(2L, transfer.targetAccount().getId());
        Assertions.assertEquals(new BigDecimal("150.00"), transfer.amount());
    }

    @Test
    @DisplayName("Deve rejeitar requisicao de transferencia quando o valor for menor ou igual a zero")
    void shouldRejectTransferDTOWhenAmountIsLessThanOrEqualToZero() {
        // GIVEN
        TransferDTO transferRequest = new TransferDTO(1L, 2L, BigDecimal.ZERO);

        // WHEN
        TransferAmountMustBePositiveException exception = Assertions.assertThrows(
                TransferAmountMustBePositiveException.class,
                () -> transferRequestConverter.convert(transferRequest, mockAccount(1L), mockAccount(2L))
        );

        // THEN
        Assertions.assertEquals("TRANSFER_AMOUNT_MUST_BE_POSITIVE", exception.getKey());
        Assertions.assertEquals("O valor da transferencia deve ser maior que zero.", exception.getValue());
    }

    @Test
    @DisplayName("Deve rejeitar requisicao de transferencia quando a conta de origem nao for informada")
    void shouldRejectTransferDTOWhenSourceAccountIdIsNotProvided() {
        // GIVEN
        TransferDTO transferRequest = new TransferDTO(null, 2L, new BigDecimal("150.00"));

        // WHEN
        InvalidRequestDataException exception = Assertions.assertThrows(
                InvalidRequestDataException.class,
                () -> transferRequestConverter.convert(transferRequest, mockAccount(1L), mockAccount(2L))
        );

        // THEN
        Assertions.assertEquals("INVALID_REQUEST_DATA", exception.getKey());
        Assertions.assertEquals("O campo sourceAccountId e obrigatorio.", exception.getValue());
    }

    @Test
    @DisplayName("Deve rejeitar requisicao de transferencia quando a conta de destino nao for informada")
    void shouldRejectTransferDTOWhenTargetAccountIdIsNotProvided() {
        // GIVEN
        TransferDTO transferRequest = new TransferDTO(1L, null, new BigDecimal("150.00"));

        // WHEN
        InvalidRequestDataException exception = Assertions.assertThrows(
                InvalidRequestDataException.class,
                () -> transferRequestConverter.convert(transferRequest, mockAccount(1L), mockAccount(2L))
        );

        // THEN
        Assertions.assertEquals("INVALID_REQUEST_DATA", exception.getKey());
        Assertions.assertEquals("O campo targetAccountId e obrigatorio.", exception.getValue());
    }

    @Test
    @DisplayName("Deve rejeitar requisicao de transferencia quando o valor nao for informado")
    void shouldRejectTransferDTOWhenAmountIsNotProvided() {
        // GIVEN
        TransferDTO transferRequest = new TransferDTO(1L, 2L, null);

        // WHEN
        InvalidRequestDataException exception = Assertions.assertThrows(
                InvalidRequestDataException.class,
                () -> transferRequestConverter.convert(transferRequest, mockAccount(1L), mockAccount(2L))
        );

        // THEN
        Assertions.assertEquals("INVALID_REQUEST_DATA", exception.getKey());
        Assertions.assertEquals("O campo amount e obrigatorio.", exception.getValue());
    }

    private Account mockAccount(Long accountId) {
        return new Account(accountId, "Conta " + accountId, BigDecimal.TEN, OffsetDateTime.now(), OffsetDateTime.now());
    }
}

package com.cwi.digitalbankapi.application.converter;

import com.cwi.digitalbankapi.application.dto.TransferRequest;
import com.cwi.digitalbankapi.domain.transfer.exception.TransferAmountMustBePositiveException;
import com.cwi.digitalbankapi.domain.transfer.model.TransferCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class TransferRequestConverterTest {

    private final TransferRequestConverter transferRequestConverter = new TransferRequestConverter();

    @Test
    @DisplayName("Deve converter requisicao de transferencia valida em comando de transferencia")
    void shouldConvertValidTransferRequestIntoTransferCommand() {
        // GIVEN
        TransferRequest transferRequest = new TransferRequest(1L, 2L, new BigDecimal("150.00"));

        // WHEN
        TransferCommand transferCommand = transferRequestConverter.convert(transferRequest);

        // THEN
        Assertions.assertEquals(1L, transferCommand.sourceAccountId());
        Assertions.assertEquals(2L, transferCommand.targetAccountId());
        Assertions.assertEquals(new BigDecimal("150.00"), transferCommand.amount());
    }

    @Test
    @DisplayName("Deve rejeitar requisicao de transferencia quando o valor for menor ou igual a zero")
    void shouldRejectTransferRequestWhenAmountIsLessThanOrEqualToZero() {
        // GIVEN
        TransferRequest transferRequest = new TransferRequest(1L, 2L, BigDecimal.ZERO);

        // WHEN
        TransferAmountMustBePositiveException exception = Assertions.assertThrows(
            TransferAmountMustBePositiveException.class,
            () -> transferRequestConverter.convert(transferRequest)
        );

        // THEN
        Assertions.assertEquals("TRANSFER_AMOUNT_MUST_BE_POSITIVE", exception.getKey());
        Assertions.assertEquals("O valor da transferencia deve ser maior que zero.", exception.getValue());
    }
}

package com.digitalbankapi.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "TransferResponseDTO", description = "Resposta retornada apos transferencia concluida com sucesso.")
public record TransferResponseDTO(
        @Schema(description = "Identificador da conta de origem.", example = "1")
        Long sourceAccountId,
        @Schema(description = "Identificador da conta de destino.", example = "2")
        Long targetAccountId,
        @Schema(description = "Referencia unica da transferencia.", example = "4d2f91fb-daf5-4ea7-8db2-757ca1b89c30")
        String transferReference,
        @Schema(description = "Valor transferido.", example = "200.00")
        BigDecimal transferredAmount,
        @Schema(description = "Saldo final da conta de origem apos a transferencia.", example = "1050.00")
        BigDecimal sourceAccountBalance,
        @Schema(description = "Saldo final da conta de destino apos a transferencia.", example = "1180.50")
        BigDecimal targetAccountBalance
) {
}

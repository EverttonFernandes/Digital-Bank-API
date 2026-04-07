package com.cwi.digitalbankapi.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(name = "TransferRequest", description = "Payload de entrada para transferencia entre contas.")
public record TransferRequest(
    @NotNull(message = "O campo sourceAccountId e obrigatorio.")
    @Schema(description = "Identificador da conta de origem.", example = "1")
    Long sourceAccountId,
    @NotNull(message = "O campo targetAccountId e obrigatorio.")
    @Schema(description = "Identificador da conta de destino.", example = "2")
    Long targetAccountId,
    @NotNull(message = "O campo amount e obrigatorio.")
    @Schema(description = "Valor a ser transferido.", example = "200.00")
    BigDecimal amount
) {
}

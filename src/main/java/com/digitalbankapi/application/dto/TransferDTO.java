package com.digitalbankapi.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(name = "TransferDTO", description = "Payload de entrada para transferencia entre contas.")
public record TransferDTO(
        @NotNull(message = "O campo sourceAccountId e obrigatorio.")
        @Schema(
                description = "Identificador da conta que tera o saldo debitado.",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long sourceAccountId,
        @NotNull(message = "O campo targetAccountId e obrigatorio.")
        @Schema(
                description = "Identificador da conta que recebera o saldo creditado.",
                example = "2",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long targetAccountId,
        @NotNull(message = "O campo amount e obrigatorio.")
        @Schema(
                description = "Valor monetario que sera transferido da origem para o destino. Deve ser maior que zero.",
                example = "200.00",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        BigDecimal amount
) {
}

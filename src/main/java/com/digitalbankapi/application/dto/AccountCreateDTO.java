package com.digitalbankapi.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(name = "AccountCreateDTO", description = "Payload de entrada para criacao de uma nova conta bancaria.")
public record AccountCreateDTO(
        @NotBlank(message = "O campo ownerName e obrigatorio.")
        @Schema(
                description = "Nome que identifica o titular da conta no dominio da API.",
                example = "Maria Silva",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String ownerName,
        @NotNull(message = "O campo initialBalance e obrigatorio.")
        @Schema(
                description = "Saldo com o qual a conta sera criada. O valor nao pode ser negativo.",
                example = "350.00",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        BigDecimal initialBalance
) {
}

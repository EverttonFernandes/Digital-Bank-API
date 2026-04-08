package com.cwi.digitalbankapi.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(name = "CreateAccountRequest", description = "Payload de entrada para criacao de uma nova conta bancaria.")
public record CreateAccountRequest(
    @NotBlank(message = "O campo ownerName e obrigatorio.")
    @Schema(description = "Nome do titular da nova conta.", example = "Maria Silva")
    String ownerName,
    @NotNull(message = "O campo initialBalance e obrigatorio.")
    @Schema(description = "Saldo inicial da nova conta.", example = "350.00")
    BigDecimal initialBalance
) {
}

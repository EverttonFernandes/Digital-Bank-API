package com.cwi.digitalbankapi.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "AccountDTO", description = "Resposta com os dados principais de uma conta.")
public record AccountDTO(
    @Schema(description = "Identificador da conta.", example = "1")
    Long id,
    @Schema(description = "Nome do titular da conta.", example = "Ana Souza")
    String ownerName,
    @Schema(description = "Saldo atual da conta.", example = "1250.00")
    BigDecimal balance
) {
}

package com.cwi.digitalbankapi.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Schema(name = "AccountMovementDTO", description = "Resposta com uma movimentacao financeira da conta.")
public record AccountMovementDTO(
        @Schema(description = "Identificador da conta associada a movimentacao.", example = "1")
        Long accountId,
        @Schema(description = "Referencia da transferencia que originou a movimentacao.", example = "4d2f91fb-daf5-4ea7-8db2-757ca1b89c30")
        String transferReference,
        @Schema(description = "Tipo da movimentacao financeira.", example = "DEBIT")
        String movementType,
        @Schema(description = "Valor movimentado.", example = "200.00")
        BigDecimal amount,
        @Schema(description = "Descricao legivel da movimentacao.", example = "Debito gerado pela transferencia para a conta 2.")
        String description,
        @Schema(description = "Data e hora de criacao da movimentacao.", example = "2026-04-07T10:15:30Z")
        OffsetDateTime createdAt
) {
}

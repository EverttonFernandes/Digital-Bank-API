package com.cwi.digitalbankapi.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(name = "AccountNotificationResponse", description = "Resposta com uma notificacao registrada para a conta.")
public record AccountNotificationResponse(
    @Schema(description = "Identificador da conta associada a notificacao.", example = "1")
    Long accountId,
    @Schema(description = "Referencia da transferencia relacionada.", example = "4d2f91fb-daf5-4ea7-8db2-757ca1b89c30")
    String transferReference,
    @Schema(description = "Status da notificacao.", example = "REGISTERED")
    String notificationStatus,
    @Schema(description = "Mensagem registrada para a conta.", example = "Transferencia enviada com sucesso para a conta 2.")
    String message,
    @Schema(description = "Data e hora de criacao da notificacao.", example = "2026-04-07T10:15:30Z")
    OffsetDateTime createdAt
) {
}

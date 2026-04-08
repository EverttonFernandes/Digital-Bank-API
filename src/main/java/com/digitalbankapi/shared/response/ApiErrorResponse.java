package com.digitalbankapi.shared.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiErrorResponse", description = "Resposta padronizada de erro da API.")
public record ApiErrorResponse(
        @Schema(description = "Chave semantica do erro, usada para identificar a falha de forma estavel.", example = "ACCOUNT_NOT_FOUND")
        String key,
        @Schema(description = "Mensagem legivel que explica o problema encontrado para o consumidor da API.", example = "Conta nao encontrada para o identificador 99.")
        String value
) {
}

package com.digitalbankapi.shared.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiErrorResponse", description = "Resposta padronizada de erro da API.")
public record ApiErrorResponse(
        @Schema(description = "Identificador semantico do erro.", example = "ACCOUNT_NOT_FOUND")
        String key,
        @Schema(description = "Descricao legivel do erro ocorrido.", example = "Conta nao encontrada para o identificador 99.")
        String value
) {
}

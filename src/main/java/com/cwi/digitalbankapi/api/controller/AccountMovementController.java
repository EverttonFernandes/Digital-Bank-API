package com.cwi.digitalbankapi.api.controller;

import com.cwi.digitalbankapi.application.dto.AccountMovementResponse;
import com.cwi.digitalbankapi.application.service.AccountMovementQueryService;
import com.cwi.digitalbankapi.shared.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/movements")
@Tag(name = "Account Movements", description = "Operacoes de consulta de movimentacoes financeiras.")
public class AccountMovementController {

    private final AccountMovementQueryService accountMovementQueryService;

    public AccountMovementController(AccountMovementQueryService accountMovementQueryService) {
        this.accountMovementQueryService = accountMovementQueryService;
    }

    @GetMapping
    @Operation(summary = "Listar movimentacoes da conta", description = "Retorna o historico financeiro gerado pelas transferencias da conta.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Movimentacoes retornadas com sucesso.",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountMovementResponse.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Conta nao encontrada.",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
    public List<AccountMovementResponse> findAccountMovementsByAccountId(@PathVariable Long accountId) {
        return accountMovementQueryService.findAccountMovementsByAccountId(accountId);
    }
}

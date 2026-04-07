package com.cwi.digitalbankapi.api.controller;

import com.cwi.digitalbankapi.application.dto.AccountResponse;
import com.cwi.digitalbankapi.application.service.AccountQueryService;
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
@RequestMapping("/accounts")
@Tag(name = "Accounts", description = "Operacoes de consulta de contas.")
public class AccountController {

    private final AccountQueryService accountQueryService;

    public AccountController(AccountQueryService accountQueryService) {
        this.accountQueryService = accountQueryService;
    }

    @GetMapping
    @Operation(summary = "Listar contas", description = "Retorna as contas pre-carregadas com nome do titular e saldo atual.")
    @ApiResponse(
        responseCode = "200",
        description = "Lista de contas retornada com sucesso.",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountResponse.class)))
    )
    public List<AccountResponse> listAllAccounts() {
        return accountQueryService.listAllAccounts();
    }

    @GetMapping("/{accountIdentifier}")
    @Operation(summary = "Buscar conta por identificador", description = "Retorna os dados de uma conta especifica.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Conta retornada com sucesso.",
            content = @Content(schema = @Schema(implementation = AccountResponse.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Conta nao encontrada.",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
    public AccountResponse findAccountById(@PathVariable Long accountIdentifier) {
        return accountQueryService.findAccountById(accountIdentifier);
    }
}

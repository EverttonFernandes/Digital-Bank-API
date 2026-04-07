package com.cwi.digitalbankapi.api.controller;

import com.cwi.digitalbankapi.application.dto.AccountNotificationResponse;
import com.cwi.digitalbankapi.application.service.AccountNotificationQueryService;
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
@RequestMapping("/accounts/{accountId}/notifications")
@Tag(name = "Account Notifications", description = "Operacoes de consulta de notificacoes registradas para a conta.")
public class AccountNotificationController {

    private final AccountNotificationQueryService accountNotificationQueryService;

    public AccountNotificationController(AccountNotificationQueryService accountNotificationQueryService) {
        this.accountNotificationQueryService = accountNotificationQueryService;
    }

    @GetMapping
    @Operation(summary = "Listar notificacoes da conta", description = "Retorna as notificacoes geradas para a conta apos transferencias concluidas.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Notificacoes retornadas com sucesso.",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = AccountNotificationResponse.class)))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Conta nao encontrada.",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
    public List<AccountNotificationResponse> findAccountNotificationsByAccountId(@PathVariable Long accountId) {
        return accountNotificationQueryService.findAccountNotificationsByAccountId(accountId);
    }
}

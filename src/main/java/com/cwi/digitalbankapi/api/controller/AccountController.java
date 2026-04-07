package com.cwi.digitalbankapi.api.controller;

import com.cwi.digitalbankapi.api.assembler.AccountRepresentationModelAssembler;
import com.cwi.digitalbankapi.api.representation.AccountMovementRepresentationModel;
import com.cwi.digitalbankapi.api.representation.AccountMovementCollectionRepresentationModel;
import com.cwi.digitalbankapi.api.representation.AccountNotificationCollectionRepresentationModel;
import com.cwi.digitalbankapi.api.representation.AccountNotificationRepresentationModel;
import com.cwi.digitalbankapi.api.representation.AccountCollectionRepresentationModel;
import com.cwi.digitalbankapi.api.representation.AccountRepresentationModel;
import com.cwi.digitalbankapi.application.dto.AccountResponse;
import com.cwi.digitalbankapi.application.service.AccountMovementQueryService;
import com.cwi.digitalbankapi.application.service.AccountNotificationQueryService;
import com.cwi.digitalbankapi.application.service.AccountQueryService;
import com.cwi.digitalbankapi.shared.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Accounts", description = "Operacoes de consulta de contas.")
public class AccountController {

    private final AccountQueryService accountQueryService;
    private final AccountMovementQueryService accountMovementQueryService;
    private final AccountNotificationQueryService accountNotificationQueryService;
    private final AccountRepresentationModelAssembler accountRepresentationModelAssembler;
    private final com.cwi.digitalbankapi.api.assembler.AccountMovementRepresentationModelAssembler accountMovementRepresentationModelAssembler;
    private final com.cwi.digitalbankapi.api.assembler.AccountNotificationRepresentationModelAssembler accountNotificationRepresentationModelAssembler;

    public AccountController(
        AccountQueryService accountQueryService,
        AccountMovementQueryService accountMovementQueryService,
        AccountNotificationQueryService accountNotificationQueryService,
        AccountRepresentationModelAssembler accountRepresentationModelAssembler,
        com.cwi.digitalbankapi.api.assembler.AccountMovementRepresentationModelAssembler accountMovementRepresentationModelAssembler,
        com.cwi.digitalbankapi.api.assembler.AccountNotificationRepresentationModelAssembler accountNotificationRepresentationModelAssembler
    ) {
        this.accountQueryService = accountQueryService;
        this.accountMovementQueryService = accountMovementQueryService;
        this.accountNotificationQueryService = accountNotificationQueryService;
        this.accountRepresentationModelAssembler = accountRepresentationModelAssembler;
        this.accountMovementRepresentationModelAssembler = accountMovementRepresentationModelAssembler;
        this.accountNotificationRepresentationModelAssembler = accountNotificationRepresentationModelAssembler;
    }

    @GetMapping
    @Operation(summary = "Listar contas", description = "Retorna as contas pre-carregadas com nome do titular e saldo atual.")
    @ApiResponse(
        responseCode = "200",
        description = "Lista de contas retornada com sucesso.",
        content = @Content(schema = @Schema(implementation = AccountCollectionRepresentationModel.class))
    )
    public CollectionModel<AccountRepresentationModel> listAllAccounts() {
        List<AccountRepresentationModel> accountRepresentationModelList = accountQueryService.listAllAccounts()
            .stream()
            .map(accountRepresentationModelAssembler::toModel)
            .toList();

        return CollectionModel.of(accountRepresentationModelList, linkTo(methodOn(AccountController.class).listAllAccounts()).withSelfRel());
    }

    @GetMapping("/{accountIdentifier}")
    @Operation(summary = "Buscar conta por identificador", description = "Retorna os dados de uma conta especifica.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Conta retornada com sucesso.",
            content = @Content(schema = @Schema(implementation = AccountRepresentationModel.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Conta nao encontrada.",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
    public AccountRepresentationModel findAccountById(@PathVariable Long accountIdentifier) {
        return accountRepresentationModelAssembler.toModel(accountQueryService.findAccountById(accountIdentifier));
    }

    @GetMapping("/{accountId}/movements")
    @Operation(summary = "Listar movimentacoes da conta", description = "Retorna o historico financeiro gerado pelas transferencias da conta em formato HAL.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Movimentacoes retornadas com sucesso.",
            content = @Content(schema = @Schema(implementation = AccountMovementCollectionRepresentationModel.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Conta nao encontrada.",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
    public CollectionModel<AccountMovementRepresentationModel> findAccountMovements(@PathVariable Long accountId) {
        List<AccountMovementRepresentationModel> accountMovementRepresentationModelList = accountMovementQueryService.findAccountMovementsByAccountId(accountId)
            .stream()
            .map(accountMovementRepresentationModelAssembler::toModel)
            .toList();

        return CollectionModel.of(
            accountMovementRepresentationModelList,
            linkTo(methodOn(AccountController.class).findAccountMovements(accountId)).withSelfRel(),
            linkTo(methodOn(AccountController.class).findAccountById(accountId)).withRel("account")
        );
    }

    @GetMapping("/{accountId}/notifications")
    @Operation(summary = "Listar notificacoes da conta", description = "Retorna as notificacoes geradas para a conta apos transferencias concluidas em formato HAL.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Notificacoes retornadas com sucesso.",
            content = @Content(schema = @Schema(implementation = AccountNotificationCollectionRepresentationModel.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Conta nao encontrada.",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
    public CollectionModel<AccountNotificationRepresentationModel> findAccountNotifications(@PathVariable Long accountId) {
        List<AccountNotificationRepresentationModel> accountNotificationRepresentationModelList =
            accountNotificationQueryService.findAccountNotificationsByAccountId(accountId)
                .stream()
                .map(accountNotificationRepresentationModelAssembler::toModel)
                .toList();

        return CollectionModel.of(
            accountNotificationRepresentationModelList,
            linkTo(methodOn(AccountController.class).findAccountNotifications(accountId)).withSelfRel(),
            linkTo(methodOn(AccountController.class).findAccountById(accountId)).withRel("account")
        );
    }
}

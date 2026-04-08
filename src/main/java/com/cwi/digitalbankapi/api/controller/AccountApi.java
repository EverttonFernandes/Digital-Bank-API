package com.cwi.digitalbankapi.api.controller;

import com.cwi.digitalbankapi.api.assembler.AccountRepresentationModelAssembler;
import com.cwi.digitalbankapi.api.representation.AccountMovementRepresentationModel;
import com.cwi.digitalbankapi.api.representation.AccountMovementCollectionRepresentationModel;
import com.cwi.digitalbankapi.api.representation.AccountNotificationCollectionRepresentationModel;
import com.cwi.digitalbankapi.api.representation.AccountNotificationRepresentationModel;
import com.cwi.digitalbankapi.api.representation.AccountCollectionRepresentationModel;
import com.cwi.digitalbankapi.api.representation.AccountRepresentationModel;
import com.cwi.digitalbankapi.application.dto.AccountCreateDTO;
import com.cwi.digitalbankapi.application.service.AccountService;
import com.cwi.digitalbankapi.shared.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Accounts", description = "Operacoes de consulta de contas.")
public class AccountApi {

    private final AccountService accountService;
    private final AccountRepresentationModelAssembler accountRepresentationModelAssembler;
    private final com.cwi.digitalbankapi.api.assembler.AccountMovementRepresentationModelAssembler accountMovementRepresentationModelAssembler;
    private final com.cwi.digitalbankapi.api.assembler.AccountNotificationRepresentationModelAssembler accountNotificationRepresentationModelAssembler;

    public AccountApi(
        AccountService accountService,
        AccountRepresentationModelAssembler accountRepresentationModelAssembler,
        com.cwi.digitalbankapi.api.assembler.AccountMovementRepresentationModelAssembler accountMovementRepresentationModelAssembler,
        com.cwi.digitalbankapi.api.assembler.AccountNotificationRepresentationModelAssembler accountNotificationRepresentationModelAssembler
    ) {
        this.accountService = accountService;
        this.accountRepresentationModelAssembler = accountRepresentationModelAssembler;
        this.accountMovementRepresentationModelAssembler = accountMovementRepresentationModelAssembler;
        this.accountNotificationRepresentationModelAssembler = accountNotificationRepresentationModelAssembler;
    }

    @PostMapping
    @Operation(summary = "Criar nova conta bancaria", description = "Cria uma nova conta bancaria, persiste o recurso e retorna a representacao HAL da conta criada.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Conta criada com sucesso.",
            content = @Content(schema = @Schema(implementation = AccountRepresentationModel.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Erro de validacao de entrada ou regra de negocio.",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
    public ResponseEntity<AccountRepresentationModel> createAccount(@Valid @RequestBody AccountCreateDTO createAccountRequest) {
        AccountRepresentationModel accountRepresentationModel = accountRepresentationModelAssembler.toModel(
            accountService.createAccount(createAccountRequest)
        );

        return ResponseEntity.created(URI.create(accountRepresentationModel.getRequiredLink("self").getHref()))
            .body(accountRepresentationModel);
    }

    @GetMapping
    @Operation(summary = "Listar contas", description = "Retorna as contas pre-carregadas com nome do titular e saldo atual.")
    @ApiResponse(
        responseCode = "200",
        description = "Lista de contas retornada com sucesso.",
        content = @Content(schema = @Schema(implementation = AccountCollectionRepresentationModel.class))
    )
    public CollectionModel<AccountRepresentationModel> listAllAccounts() {
        List<AccountRepresentationModel> accountRepresentationModelList = accountService.listAllAccounts()
            .stream()
            .map(accountRepresentationModelAssembler::toModel)
            .toList();

        return CollectionModel.of(accountRepresentationModelList, linkTo(methodOn(AccountApi.class).listAllAccounts()).withSelfRel());
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
        return accountRepresentationModelAssembler.toModel(accountService.findAccountById(accountIdentifier));
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
        List<AccountMovementRepresentationModel> accountMovementRepresentationModelList = accountService.findAccountMovementsByAccountId(accountId)
            .stream()
            .map(accountMovementRepresentationModelAssembler::toModel)
            .toList();

        return CollectionModel.of(
            accountMovementRepresentationModelList,
            linkTo(methodOn(AccountApi.class).findAccountMovements(accountId)).withSelfRel(),
            linkTo(methodOn(AccountApi.class).findAccountById(accountId)).withRel("account")
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
            accountService.findAccountNotificationsByAccountId(accountId)
                .stream()
                .map(accountNotificationRepresentationModelAssembler::toModel)
                .toList();

        return CollectionModel.of(
            accountNotificationRepresentationModelList,
            linkTo(methodOn(AccountApi.class).findAccountNotifications(accountId)).withSelfRel(),
            linkTo(methodOn(AccountApi.class).findAccountById(accountId)).withRel("account")
        );
    }
}

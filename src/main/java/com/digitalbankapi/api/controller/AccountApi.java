package com.digitalbankapi.api.controller;

import com.digitalbankapi.api.assembler.AccountRepresentationModelAssembler;
import com.digitalbankapi.api.representation.AccountMovementRepresentationModel;
import com.digitalbankapi.api.representation.AccountMovementCollectionRepresentationModel;
import com.digitalbankapi.api.representation.AccountNotificationCollectionRepresentationModel;
import com.digitalbankapi.api.representation.AccountNotificationRepresentationModel;
import com.digitalbankapi.api.representation.AccountCollectionRepresentationModel;
import com.digitalbankapi.api.representation.AccountRepresentationModel;
import com.digitalbankapi.application.dto.AccountCreateDTO;
import com.digitalbankapi.application.service.AccountService;
import com.digitalbankapi.infrastructure.config.OpenApiExamples;
import com.digitalbankapi.shared.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
@Tag(name = "Accounts", description = "Operacoes para criar conta, listar contas e navegar pelos recursos financeiros associados.")
public class AccountApi {

    private final AccountService accountService;
    private final AccountRepresentationModelAssembler accountRepresentationModelAssembler;
    private final com.digitalbankapi.api.assembler.AccountMovementRepresentationModelAssembler accountMovementRepresentationModelAssembler;
    private final com.digitalbankapi.api.assembler.AccountNotificationRepresentationModelAssembler accountNotificationRepresentationModelAssembler;

    public AccountApi(
            AccountService accountService,
            AccountRepresentationModelAssembler accountRepresentationModelAssembler,
            com.digitalbankapi.api.assembler.AccountMovementRepresentationModelAssembler accountMovementRepresentationModelAssembler,
            com.digitalbankapi.api.assembler.AccountNotificationRepresentationModelAssembler accountNotificationRepresentationModelAssembler
    ) {
        this.accountService = accountService;
        this.accountRepresentationModelAssembler = accountRepresentationModelAssembler;
        this.accountMovementRepresentationModelAssembler = accountMovementRepresentationModelAssembler;
        this.accountNotificationRepresentationModelAssembler = accountNotificationRepresentationModelAssembler;
    }

    @PostMapping
    @Operation(
            summary = "Criar nova conta bancaria",
            description = """
                    Use este endpoint quando quiser abrir uma nova conta.

                    Como usar:
                    1. informe o nome do titular em `ownerName`
                    2. informe o saldo inicial em `initialBalance`
                    3. execute a operacao
                    4. use os links HAL da resposta para consultar a conta criada, suas movimentacoes e futuras notificacoes
                    """
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Dados necessarios para abrir uma nova conta bancaria.",
            content = @Content(
                    schema = @Schema(implementation = AccountCreateDTO.class),
                    examples = @ExampleObject(
                            name = "CriacaoDeConta",
                            summary = "Exemplo de abertura de conta",
                            value = OpenApiExamples.ACCOUNT_CREATE_REQUEST
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Conta criada com sucesso.",
                    content = @Content(
                            schema = @Schema(implementation = AccountRepresentationModel.class),
                            examples = @ExampleObject(
                                    name = "ContaCriada",
                                    summary = "Resposta de sucesso com links HAL",
                                    value = OpenApiExamples.ACCOUNT_CREATE_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro de validacao de entrada ou regra de negocio.",
                    content = @Content(
                            schema = @Schema(implementation = ApiErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "RequisicaoInvalida",
                                    summary = "Exemplo de erro ao criar conta",
                                    value = OpenApiExamples.INVALID_ACCOUNT_REQUEST_ERROR
                            )
                    )
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
    @Operation(
            summary = "Listar contas",
            description = """
                    Use este endpoint para descobrir as contas disponiveis e seus identificadores.

                    Esta costuma ser a melhor porta de entrada para:
                    - encontrar contas para transferencia
                    - validar o saldo atual antes de uma operacao
                    - navegar pelos links HAL de movimentos e notificacoes
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de contas retornada com sucesso.",
            content = @Content(
                    schema = @Schema(implementation = AccountCollectionRepresentationModel.class),
                    examples = @ExampleObject(
                            name = "ListaDeContas",
                            summary = "Colecao HAL de contas",
                            value = OpenApiExamples.ACCOUNT_LIST_RESPONSE
                    )
            )
    )
    public CollectionModel<AccountRepresentationModel> listAllAccounts() {
        List<AccountRepresentationModel> accountRepresentationModelList = accountService.listAllAccounts()
                .stream()
                .map(accountRepresentationModelAssembler::toModel)
                .toList();

        return CollectionModel.of(accountRepresentationModelList, linkTo(methodOn(AccountApi.class).listAllAccounts()).withSelfRel());
    }

    @GetMapping("/{accountIdentifier}")
    @Operation(
            summary = "Buscar conta por identificador",
            description = """
                    Retorna os dados atuais de uma conta especifica.

                    Use esta operacao para:
                    - confirmar o saldo apos uma transferencia
                    - recuperar a representacao HAL de uma conta especifica
                    - seguir para as rotas relacionadas por meio dos links da resposta
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Conta retornada com sucesso.",
                    content = @Content(
                            schema = @Schema(implementation = AccountRepresentationModel.class),
                            examples = @ExampleObject(
                                    name = "ContaEncontrada",
                                    summary = "Conta retornada com links HAL",
                                    value = OpenApiExamples.ACCOUNT_CREATE_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conta nao encontrada.",
                    content = @Content(
                            schema = @Schema(implementation = ApiErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "ContaNaoEncontrada",
                                    summary = "Erro ao consultar conta inexistente",
                                    value = OpenApiExamples.ACCOUNT_NOT_FOUND_ERROR
                            )
                    )
            )
    })
    public AccountRepresentationModel findAccountById(
            @Parameter(description = "Identificador numerico da conta que sera consultada.", example = "1")
            @PathVariable Long accountIdentifier
    ) {
        return accountRepresentationModelAssembler.toModel(accountService.findAccountById(accountIdentifier));
    }

    @GetMapping("/{accountId}/movements")
    @Operation(
            summary = "Listar movimentacoes da conta",
            description = """
                    Exibe o historico financeiro de uma conta.

                    Este endpoint ajuda a entender o efeito real de uma transferencia, mostrando:
                    - debitos gerados para a conta de origem
                    - creditos gerados para a conta de destino
                    - referencia da transferencia associada
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Movimentacoes retornadas com sucesso.",
                    content = @Content(
                            schema = @Schema(implementation = AccountMovementCollectionRepresentationModel.class),
                            examples = @ExampleObject(
                                    name = "MovimentacoesDaConta",
                                    summary = "Colecao HAL de movimentacoes",
                                    value = OpenApiExamples.MOVEMENT_LIST_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conta nao encontrada.",
                    content = @Content(
                            schema = @Schema(implementation = ApiErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "ContaNaoEncontrada",
                                    summary = "Erro ao consultar movimentacoes de conta inexistente",
                                    value = OpenApiExamples.ACCOUNT_NOT_FOUND_ERROR
                            )
                    )
            )
    })
    public CollectionModel<AccountMovementRepresentationModel> findAccountMovements(
            @Parameter(description = "Identificador da conta cujo historico financeiro sera consultado.", example = "1")
            @PathVariable Long accountId
    ) {
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
    @Operation(
            summary = "Listar notificacoes da conta",
            description = """
                    Retorna as notificacoes registradas para a conta apos transferencias concluidas.

                    Use este endpoint para verificar se a API registrou o efeito comunicacional da transferencia,
                    tanto para a conta de origem quanto para a conta de destino.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Notificacoes retornadas com sucesso.",
                    content = @Content(
                            schema = @Schema(implementation = AccountNotificationCollectionRepresentationModel.class),
                            examples = @ExampleObject(
                                    name = "NotificacoesDaConta",
                                    summary = "Colecao HAL de notificacoes",
                                    value = OpenApiExamples.NOTIFICATION_LIST_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conta nao encontrada.",
                    content = @Content(
                            schema = @Schema(implementation = ApiErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "ContaNaoEncontrada",
                                    summary = "Erro ao consultar notificacoes de conta inexistente",
                                    value = OpenApiExamples.ACCOUNT_NOT_FOUND_ERROR
                            )
                    )
            )
    })
    public CollectionModel<AccountNotificationRepresentationModel> findAccountNotifications(
            @Parameter(description = "Identificador da conta cujas notificacoes serao consultadas.", example = "1")
            @PathVariable Long accountId
    ) {
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

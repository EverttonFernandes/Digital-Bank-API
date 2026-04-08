package com.digitalbankapi.api.controller;

import com.digitalbankapi.api.assembler.TransferRepresentationModelAssembler;
import com.digitalbankapi.api.representation.TransferRepresentationModel;
import com.digitalbankapi.application.dto.TransferDTO;
import com.digitalbankapi.application.service.TransferService;
import com.digitalbankapi.infrastructure.config.OpenApiExamples;
import com.digitalbankapi.shared.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfers")
@Tag(name = "Transfers", description = "Operacao principal da API para mover saldo entre contas e depois navegar para movimentos e notificacoes.")
public class TransferApi {

    private final TransferService transferService;
    private final TransferRepresentationModelAssembler transferRepresentationModelAssembler;

    public TransferApi(TransferService transferService, TransferRepresentationModelAssembler transferRepresentationModelAssembler) {
        this.transferService = transferService;
        this.transferRepresentationModelAssembler = transferRepresentationModelAssembler;
    }

    @PostMapping
    @Operation(
            summary = "Transferir valores entre contas",
            description = """
                    Use este endpoint para mover saldo entre duas contas validas.

                    Fluxo recomendado:
                    1. identifique as contas usando `GET /accounts`
                    2. informe a conta de origem, a conta de destino e o valor
                    3. execute a transferencia
                    4. use os links HAL da resposta para validar saldos, movimentacoes e notificacoes geradas
                    """
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Dados necessarios para transferir saldo entre duas contas existentes.",
            content = @Content(
                    schema = @Schema(implementation = TransferDTO.class),
                    examples = @ExampleObject(
                            name = "TransferenciaEntreContas",
                            summary = "Exemplo de transferencia valida",
                            value = OpenApiExamples.TRANSFER_REQUEST
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transferencia concluida com sucesso.",
                    content = @Content(
                            schema = @Schema(implementation = TransferRepresentationModel.class),
                            examples = @ExampleObject(
                                    name = "TransferenciaConcluida",
                                    summary = "Resposta com links para navegar pelos proximos passos",
                                    value = OpenApiExamples.TRANSFER_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro de validacao de entrada ou regra de negocio.",
                    content = @Content(
                            schema = @Schema(implementation = ApiErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "TransferenciaInvalida",
                                    summary = "Exemplo de erro de regra de negocio",
                                    value = OpenApiExamples.INVALID_TRANSFER_REQUEST_ERROR
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
                                    summary = "Exemplo de erro quando uma das contas nao existe",
                                    value = OpenApiExamples.ACCOUNT_NOT_FOUND_ERROR
                            )
                    )
            )
    })
    public TransferRepresentationModel transfer(@Valid @RequestBody TransferDTO transferRequest) {
        return transferRepresentationModelAssembler.toModel(transferService.transfer(transferRequest));
    }
}

package com.cwi.digitalbankapi.api.controller;

import com.cwi.digitalbankapi.api.assembler.TransferRepresentationModelAssembler;
import com.cwi.digitalbankapi.api.representation.TransferRepresentationModel;
import com.cwi.digitalbankapi.application.dto.TransferDTO;
import com.cwi.digitalbankapi.application.service.TransferService;
import com.cwi.digitalbankapi.shared.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
@Tag(name = "Transfers", description = "Operacao principal de transferencia entre contas.")
public class TransferApi {

    private final TransferService transferService;
    private final TransferRepresentationModelAssembler transferRepresentationModelAssembler;

    public TransferApi(TransferService transferService, TransferRepresentationModelAssembler transferRepresentationModelAssembler) {
        this.transferService = transferService;
        this.transferRepresentationModelAssembler = transferRepresentationModelAssembler;
    }

    @PostMapping
    @Operation(summary = "Transferir valores entre contas", description = "Realiza a transferencia entre duas contas validas e retorna os saldos finais da operacao.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transferencia concluida com sucesso.",
                    content = @Content(schema = @Schema(implementation = TransferRepresentationModel.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro de validacao de entrada ou regra de negocio.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conta nao encontrada.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    public TransferRepresentationModel transfer(@Valid @RequestBody TransferDTO transferRequest) {
        return transferRepresentationModelAssembler.toModel(transferService.transfer(transferRequest));
    }
}

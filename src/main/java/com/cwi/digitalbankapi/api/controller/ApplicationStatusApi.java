package com.cwi.digitalbankapi.api.controller;

import com.cwi.digitalbankapi.application.service.ApplicationStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
@Tag(name = "Application Status", description = "Operacao simples de status da aplicacao.")
public class ApplicationStatusApi {

    private final ApplicationStatusService applicationStatusService;

    public ApplicationStatusApi(ApplicationStatusService applicationStatusService) {
        this.applicationStatusService = applicationStatusService;
    }

    @GetMapping
    @Operation(summary = "Consultar status da aplicacao", description = "Retorna uma resposta simples indicando que a aplicacao esta em execucao.")
    @ApiResponse(
        responseCode = "200",
        description = "Status retornado com sucesso.",
        content = @Content(schema = @Schema(example = "{\"application\":\"digital-bank-api\",\"status\":\"running\"}"))
    )
    public Map<String, String> getApplicationStatus() {
        return applicationStatusService.getApplicationStatus();
    }
}

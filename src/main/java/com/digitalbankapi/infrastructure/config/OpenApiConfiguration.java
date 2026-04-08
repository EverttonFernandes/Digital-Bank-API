package com.digitalbankapi.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Digital Bank API",
                version = "v1",
                description = "API REST simplificada para gestao de contas, transferencias, movimentacoes e notificacoes de um banco digital.",
                contact = @Contact(
                        name = "Digital Bank API"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Ambiente local")
        }
)
public class OpenApiConfiguration {
}

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
                description = """
                        API REST para operacoes bancarias basicas com foco em clareza de contrato, HATEOAS e testabilidade.

                        Jornada recomendada no Swagger:
                        1. Use `POST /accounts` para criar uma nova conta.
                        2. Use `GET /accounts` para descobrir os identificadores disponiveis.
                        3. Use `POST /transfers` para transferir saldo entre duas contas validas.
                        4. Use `GET /accounts/{id}/movements` para conferir o historico financeiro gerado.
                        5. Use `GET /accounts/{id}/notifications` para consultar as notificacoes registradas apos a transferencia.

                        Cada endpoint principal foi documentado com exemplos reais de request, response e erros esperados.
                        """,
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

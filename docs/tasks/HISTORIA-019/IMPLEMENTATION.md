# IMPLEMENTATION — HISTORIA-019

## Contexto da História

O contrato OpenAPI já existe, mas ainda não entrega uma experiência realmente guiada para quem precisa aprender a usar a
API direto pelo Swagger.

## Objetivo da Entrega Atual

Refatorar a documentação Swagger/OpenAPI para que ela ensine o uso da API e torne explícitos os cenários principais de
criação de conta, transferência, consulta de movimentações e consulta de notificações.

## Referência

- [HISTORIA-019-EXPERIENCIA-DE-USO-DO-SWAGGER-E-CONTRATO-OPENAPI.md](docs/jira-pessoal/historias/HISTORIA-019-EXPERIENCIA-DE-USO-DO-SWAGGER-E-CONTRATO-OPENAPI.md)

## Escopo Incluído

- enriquecer `OpenApiConfiguration`
- enriquecer `AccountApi`
- enriquecer `TransferApi`
- enriquecer DTOs e representation models expostos
- adicionar exemplos reais de request e response
- documentar respostas de erro por endpoint

## Estratégia Técnica

1. criar a história e registrar o plano
2. definir exemplos reutilizáveis para OpenAPI
3. enriquecer a descrição geral da API
4. documentar requests e responses dos endpoints principais
5. revisar schema dos DTOs e HAL models
6. validar `make unit-test`
7. validar `make functional-test`
8. validar o contrato gerado da OpenAPI

## Checklist de Conclusão

- [x] história criada no backlog
- [x] `POST /accounts` ensina a criar conta
- [x] `POST /transfers` ensina a transferir valores
- [x] `GET /accounts/{id}/movements` explica a leitura do histórico
- [x] `GET /accounts/{id}/notifications` explica a leitura das notificações
- [x] DTOs documentados atributo por atributo
- [x] respostas de erro com exemplo
- [x] OpenAPI com descrição geral orientada à jornada
- [x] testes unitários verdes
- [x] testes funcionais verdes

## Resultado Final da História

O Swagger foi elevado de inventário técnico para guia real de consumo da API.

Convergências principais:

- descrição geral da API passou a ensinar a jornada principal de uso
- `POST /accounts` e `POST /transfers` agora têm exemplos reais de request e response
- endpoints de consulta de movimentos e notificações passaram a explicar claramente o objetivo de cada coleção
- responses de erro relevantes passaram a ter exemplos explícitos
- DTOs e representations ganharam descrições mais pedagógicas atributo por atributo

Durante a validação final, foi necessário corrigir também os métodos derivados dos repositórios JPA de movimentações e
notificações para que a aplicação subisse corretamente em runtime após o rebuild do container.

Validação final:

- `make unit-test`
- `make functional-test`
- `GET /api/status`
- `GET /v3/api-docs`

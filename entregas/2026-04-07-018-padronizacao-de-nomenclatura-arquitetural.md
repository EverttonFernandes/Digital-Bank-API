# Entrega 018 — Padronização de Nomenclatura Arquitetural

## Resumo Executivo

Esta entrega padronizou a nomenclatura das classes principais do projeto para alinhar o back-end ao estilo do
`wishlist-api`.

O objetivo foi remover heterogeneidade de naming entre camadas, sem alterar o comportamento da API.

## O Que Foi Padronizado

- classes de endpoint passaram para o padrão `*Api`
- DTOs passaram para o padrão `*DTO`
- converters passaram para o padrão `*DTOConverter`
- repositórios JPA passaram para o padrão `*JpaRepository`
- implementações concretas passaram para o padrão `*RepositoryImpl`

## Exemplos de Renomeação

- `AccountController` -> `AccountApi`
- `TransferController` -> `TransferApi`
- `CreateAccountRequest` -> `AccountCreateDTO`
- `TransferRequest` -> `TransferDTO`
- `AccountResponseConverter` -> `AccountDTOConverter`
- `SpringDataAccountJpaRepository` -> `AccountJpaRepository`
- `PostgreSqlAccountRepository` -> `AccountRepositoryImpl`

## Tradução Entre Técnica e Negócio

Do ponto de vista técnico, a entrega reduz ruído cognitivo e melhora a previsibilidade da base.

Do ponto de vista de negócio, isso facilita manutenção, onboarding e apresentação técnica da solução, porque a
nomenclatura passa a seguir um padrão único e defensável.

## Evidências de Validação

Validação final executada:

- `make unit-test`
- `make functional-test`

Resultado:

- suíte unitária verde
- suíte funcional verde com `23/23` testes passando

## Impacto Final

O projeto ficou com vocabulário mais uniforme entre API, aplicação e persistência, sem regressão funcional.

# HISTORIA-018 — Padronização de Nomenclatura Arquitetural

## Tipo

- refatoracao estrutural

## Objetivo da História

Padronizar a nomenclatura das classes do projeto para ficar coerente com o estilo observado no `wishlist-api`,
eliminando nomes mistos como `Controller`, `Request`, `Response`, `SpringData*JpaRepository` e `PostgreSql*Repository`
quando eles divergem do padrão desejado.

## Referência Principal

- `wishlist-api`

## Padrão Alvo

- endpoints nomeados como `*Api`
- DTOs nomeados como `*DTO`
- converters nomeados como `*DTOConverter`
- services nomeados como `*Service`
- interfaces de repositório nomeadas como `*Repository`
- implementações concretas nomeadas como `*RepositoryImpl`
- repositórios JPA nomeados como `*JpaRepository`

## Exemplos do Padrão

- `AccountApi`
- `TransferApi`
- `AccountCreateDTO`
- `TransferDTO`
- `AccountDTOConverter`
- `AccountCreateDTOConverter`
- `AccountRepository`
- `AccountRepositoryImpl`
- `AccountJpaRepository`

## Critérios de Aceite

- todas as classes principais fora do padrão devem ser renomeadas
- imports, testes e wiring Spring devem continuar consistentes
- `make unit-test` deve passar
- `make functional-test` deve passar
- a refatoração não pode alterar o comportamento da API

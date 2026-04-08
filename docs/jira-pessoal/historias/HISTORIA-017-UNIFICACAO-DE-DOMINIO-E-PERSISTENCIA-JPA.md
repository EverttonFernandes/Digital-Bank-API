# HISTORIA-017 — Unificação de Domínio e Persistência com JPA

## Tipo

- refatoracao estrutural

## Objetivo da História

Eliminar a duplicação entre modelos de domínio e entidades JPA, aproximando o projeto do padrão observado no `wishlist-api` e no `umovme-business`, onde o próprio domínio é persistido diretamente.

## Referências Arquiteturais

- [padrao-umovme-team.md](/home/umov/Documents/ProjetosPessoais/DigitalBankAPI/docs/padrao-umovme-team.md)
- `wishlist-api` como referência principal de simplificação

## Valor de Negócio

Esta história reduz redundância estrutural, simplifica repositórios concretos e deixa a solução mais fácil de entender, manter e defender tecnicamente.

## Problema Atual

Hoje o projeto mantém:

- domínio em `domain`
- entidades JPA em `infrastructure/persistence/entity`
- conversões redundantes entre os dois modelos

Isso aumenta custo de manutenção e polui o código sem gerar ganho proporcional para este contexto.

## Critérios de Aceite

- o domínio deve passar a refletir o mapeamento JPA/Hibernate gradualmente
- a duplicação entre `domain` e `entity` deve ser reduzida por fatias seguras
- `PostgreSql*Repository` deve ficar mais simples
- `make functional-test` deve continuar verde ao longo da refatoração
- `make unit-test` deve ser revalidado ao fim de cada iteração relevante
- nenhuma regressão funcional pode ser introduzida

## Estratégia de Execução

Primeira fatia segura:

- unificar `Account` com o mapeamento relacional JPA
- eliminar `AccountEntity`
- adaptar repositórios e entidades relacionais dependentes

Próximas fatias naturais:

- unificar `AccountMovement`
- unificar `AccountNotification`
- revisar impacto nos repositórios concretos e nos testes

## Garantia Principal

- `make functional-test`

## Dependências

- `HISTORIA-016`

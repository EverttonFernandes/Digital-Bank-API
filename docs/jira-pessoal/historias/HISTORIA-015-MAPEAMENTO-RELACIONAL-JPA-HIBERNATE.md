# HISTORIA-015 — Mapeamento Relacional JPA Hibernate

## Tipo

- evolucao arquitetural

## Objetivo da Historia

Refatorar a camada de persistencia para explicitar o mapeamento relacional entre as entidades que ja possuem tabela no
banco, usando anotacoes JPA/Hibernate mais completas e coerentes com o modelo relacional real.

## Esclarecimento Arquitetural Importante

O projeto ja usa `Spring Data JPA` e `Hibernate` na camada de persistencia.

O ponto que esta historia busca evoluir nao e “adotar JPA do zero”, e sim:

- tornar explicitas as relacoes entre as entidades persistentes
- reduzir o uso de chaves estrangeiras como campos escalares isolados quando o relacionamento ja existe no banco
- deixar a camada `infrastructure.persistence.entity` mais fiel ao modelo relacional

Esta historia nao deve colapsar o dominio dentro da persistencia nem transformar a camada `domain` em entidade JPA
apenas por conveniencia.

## Valor de Negocio

Esta entrega aumenta a maturidade tecnica da persistencia, melhora legibilidade para banca tecnica e reduz ambiguidade
entre o modelo do banco e o modelo persistido pela aplicacao.

## Problema Atual

Hoje as entidades persistentes principais ja existem:

- `AccountEntity`
- `AccountMovementEntity`
- `AccountNotificationEntity`

Mas parte dos relacionamentos ainda esta representada apenas por identificadores escalares, por exemplo:

- `account_id` em movimentacoes
- `account_id` em notificacoes

Isso funciona, mas deixa a camada relacional menos expressiva do que poderia ser.

## Objetivo Tecnico

Revisar e ajustar:

- `@ManyToOne`
- `@OneToMany`
- `@JoinColumn`
- carregamento e navegacao entre entidades persistentes
- repositorios que podem se beneficiar de relacoes explicitas
- mapeamento entre entidade persistente e dominio

## Critérios de Aceite

- a historia deve manter a separacao entre `domain` e `infrastructure`
- as entidades persistentes devem refletir melhor os relacionamentos reais do banco
- `AccountMovementEntity` deve se relacionar com `AccountEntity`
- `AccountNotificationEntity` deve se relacionar com `AccountEntity`
- se fizer sentido, `AccountEntity` pode expor colecoes persistentes para movimentacoes e notificacoes com parcimonia
- os repositorios devem continuar coerentes com a nova modelagem
- os testes unitarios impactados devem ser ajustados
- TODOS os testes funcionais existentes devem continuar passando ao final
- a historia nao pode introduzir regressao no comportamento da API

## Restrições Arquiteturais

- nao transformar o dominio em entidade JPA
- nao eliminar a conversao entre persistencia e dominio apenas para reduzir codigo
- nao introduzir cascatas ou fetches agressivos sem necessidade real
- manter a modelagem pragmatica

## Riscos e Pontos de Atenção

- relacoes bidirecionais podem introduzir acoplamento e problemas de serializacao
- `fetch` inadequado pode piorar performance
- mudancas de mapeamento podem quebrar consultas e seeders funcionais
- a suite funcional completa deve permanecer como gate obrigatorio da historia

## Dependencias

- `HISTORIA-005`
- `HISTORIA-006`
- `HISTORIA-010`
- `HISTORIA-014`

## Proxima Historia Natural

- refinamentos adicionais de persistencia e possivel introducao de entidades de transferencia apenas se houver ganho
  real de dominio

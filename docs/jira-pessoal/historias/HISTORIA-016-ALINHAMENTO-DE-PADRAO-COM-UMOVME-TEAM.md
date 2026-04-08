# HISTORIA-016 — Alinhamento de Padrão com umovme-team

## Tipo

- evolucao arquitetural

## Objetivo da Historia

Refatorar TODO o back-end do projeto para aproximar seu padrão de código ao contexto `umovme-team` do repositório `umovme-business`, preservando comportamento funcional da API e usando a suíte funcional como garantia principal de não regressão.

## Referência Arquitetural

Referência formal desta história:

- [padrao-umovme-team.md](/home/umov/Documents/ProjetosPessoais/DigitalBankAPI/docs/padrao-umovme-team.md)

## Valor de Negocio

Esta história aumenta a consistência estrutural do projeto e melhora a defesa técnica da solução, aproximando a base de código de um padrão arquitetural já validado e maduro que o autor conhece bem.

## Problema Atual

Embora o projeto já tenha boa separação entre `api`, `application`, `domain` e `infrastructure`, ainda existe heterogeneidade na distribuição de responsabilidades.

O padrão do `umovme-team` é mais uniforme em:

- controllers finos
- services orquestradores
- converters fortes
- specifications compostas
- agregados mais centrais
- infraestrutura mais explicitamente adaptadora do contexto

## Critérios de Aceite

- deve existir documento de referência do padrão alvo
- controllers devem ficar mais finos e centrados em HTTP
- services devem ficar mais claramente posicionados como orquestradores
- converters devem assumir formalmente a montagem dos comandos/agregados
- specifications devem ficar mais explícitas e compostas onde fizer sentido
- infraestrutura deve refletir papel mais consistente de adaptador do domínio
- a arquitetura resultante deve continuar clara e pragmática
- `make functional-test` deve continuar passando ao final
- testes unitários afetados devem ser ajustados depois da refatoração estrutural
- não pode haver regressão funcional dos endpoints existentes

## Regra de Garantia

A garantia primária desta história será:

- `make functional-test`

Se a suíte funcional estiver verde, a refatoração estrutural preservou os comportamentos observáveis da API.

Depois disso:

- os testes unitários quebrados devem ser ajustados no próprio fluxo do `ralph-loop`
- o fechamento da história só ocorre quando unitários e funcionais estiverem verdes

## Escopo Inicial da Refatoração

- revisar controllers
- revisar services
- revisar converters
- revisar agregado de conta e fluxo de transferência
- revisar specifications
- revisar repositórios concretos da infraestrutura
- revisar consistência entre fatias de conta, transferência, movimentação e notificação

## Dependencias

- `HISTORIA-014`
- `HISTORIA-015`

## Proxima Historia Natural

- refinamentos adicionais de ergonomia e simplificação depois da convergência estrutural

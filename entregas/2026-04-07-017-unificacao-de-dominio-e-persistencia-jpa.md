# Entrega 017 — Unificação de Domínio e Persistência JPA

## Resumo Executivo

Esta entrega eliminou a duplicação estrutural remanescente entre domínio e entidades JPA/Hibernate.

Ao final da história, os próprios modelos de domínio persistentes passaram a ser usados diretamente pela camada de persistência, seguindo o padrão mais simples e direto observado no `wishlist-api`.

## O Que Foi Eliminado

- `AccountEntity`
- `AccountMovementEntity`
- `AccountNotificationEntity`
- mapeamentos redundantes de `entity -> domain` nos repositórios concretos

## O Que Passou a Ser Persistido Diretamente

- `Account`
- `AccountMovement`
- `AccountNotification`

## Tradução Entre Técnica e Negócio

Do ponto de vista técnico, a solução ficou mais simples, com menos classes duplicadas e menos transformação acidental entre camadas.

Do ponto de vista de negócio, isso reduz custo de manutenção e facilita a evolução da API sem aumentar complexidade estrutural desnecessária.

## Comportamentos Preservados

Continuaram funcionando sem regressão:

- criação de conta
- listagem e busca de contas
- transferência entre contas
- geração e consulta de movimentações
- geração e consulta de notificações
- tratamento padronizado de erros

## Evidências de Validação

Validação final executada:

- `make unit-test`
- `make functional-test`

Resultado:

- suíte unitária verde
- suíte funcional verde com `23/23` testes passando

## Decisão Arquitetural Relevante

O projeto saiu de um desenho com separação redundante entre `domain` e `entity` e passou a um desenho mais direto:

- domínio persistido diretamente
- repositórios concretos mais simples
- menos mapeamento manual
- menor ruído estrutural

## Impacto Final

Esta entrega conclui a limpeza arquitetural que vinha sendo pedida desde a revisão da `HISTORIA-015`, deixando o back-end mais enxuto, mais coerente e mais alinhado ao padrão desejado.

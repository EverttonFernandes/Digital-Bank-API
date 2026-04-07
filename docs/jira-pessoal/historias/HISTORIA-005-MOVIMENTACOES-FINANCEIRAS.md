# HISTORIA-005 — Movimentações Financeiras

## Tipo

- funcional

## Objetivo da História

Registrar e consultar os lançamentos financeiros gerados pela transferência.

## Valor de Negócio

Esta história dá rastreabilidade ao dinheiro movimentado.

Em linguagem de negócio:

- a transferência deixa de ser apenas um efeito no saldo
- passa a existir histórico consultável do que aconteceu

## Critérios de Aceite

- transferência deve gerar débito na conta de origem
- transferência deve gerar crédito na conta de destino
- deve ser possível consultar movimentações por conta
- retornos devem refletir apenas operações concluídas
- ordenação por data deve ser coerente

## Dependências

- `HISTORIA-004`

## Próxima História Natural

- `HISTORIA-006` Notificação pós-transferência

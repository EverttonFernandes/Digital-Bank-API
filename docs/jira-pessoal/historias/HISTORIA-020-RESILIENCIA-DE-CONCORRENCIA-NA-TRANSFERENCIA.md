# HISTORIA-020 - RESILIENCIA DE CONCORRENCIA NA TRANSFERENCIA

## Objetivo de Negócio

Garantir que o cliente da API receba uma resposta clara e previsível quando uma transferência disputar contas que já
estão em processamento por outra transação concorrente.

## Objetivo Técnico

Adicionar resiliência explícita ao fluxo de lock pessimista, convertendo falhas de contenção em resposta semântica,
intuitiva e estável para o consumidor da API.

## Problema Atual

O projeto já utiliza lock pessimista para proteger a transferência, mas ainda não define:

- timeout explícito para espera do lock
- mensagem amigável para o consumidor quando a conta estiver ocupada
- status HTTP específico para contenção concorrente

## Resultado Esperado

Quando uma transferência não puder prosseguir porque uma das contas está ocupada por outra transação, a API deve
retornar:

- status code adequado
- `key` padronizada
- `value` compreensível para o consumidor

## Critérios de Aceite

- a transferência deve usar timeout explícito de lock pessimista
- falhas de contenção concorrente devem ser traduzidas para exceção semântica do domínio/aplicação
- a API deve responder com status HTTP adequado
- a resposta deve seguir o contrato `key/value`
- o Swagger deve documentar esse cenário
- testes unitários devem cobrir o novo comportamento
- `make unit-test` deve ficar verde
- `make functional-test` deve permanecer verde

# Entrega 020 — Resiliência de Concorrência na Transferência

## O que foi entregue

Esta entrega tornou o caso de contenção concorrente da transferência previsível para o consumidor da API.

Agora, quando uma conta estiver temporariamente ocupada por outra operação concorrente, a API pode responder com erro
semântico em vez de depender de falha técnica inesperada.

## Tradução entre Técnica e Negócio

Antes desta entrega, o lock pessimista protegia o saldo, mas a resposta ao cliente ainda podia degradar para erro
técnico caso a contenção ultrapassasse o que o banco conseguisse resolver naturalmente.

Depois desta entrega:

- a espera pelo lock passou a ter limite explícito
- o caso de conta ocupada ganhou mensagem clara
- o cliente passou a receber `409 Conflict` com contrato `key/value`

## Melhorias Técnicas

- timeout explícito no lock pessimista do repositório
- exceção `AccountResourceBusyException`
- tradução da contenção no `TransferService`
- documentação do `409` no Swagger
- testes unitários adicionais cobrindo serviço e handler

## Como validar

- `make unit-test`
- `make functional-test`
- executar transferências concorrentes sobre as mesmas contas

## Resultado esperado

Quando houver contenção concorrente na mesma conta, a API deve retornar uma resposta previsível e amigável ao
consumidor, sem depender de um `500` técnico genérico.

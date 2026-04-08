# IMPLEMENTATION — HISTORIA-020

## Contexto da História

O lock pessimista já existe, mas ainda falta transformar contenção concorrente em resposta HTTP semântica.

## Objetivo da Entrega Atual

Tornar o caso de concorrência da transferência previsível e amigável para o consumidor da API.

## Referência

- [HISTORIA-020-RESILIENCIA-DE-CONCORRENCIA-NA-TRANSFERENCIA.md](/home/umov/Documents/ProjetosPessoais/DigitalBankAPI/docs/jira-pessoal/historias/HISTORIA-020-RESILIENCIA-DE-CONCORRENCIA-NA-TRANSFERENCIA.md)

## Estratégia Técnica

1. criar a história no backlog
2. adicionar timeout explícito no lock pessimista
3. introduzir exceção semântica para recurso de conta ocupado
4. capturar exceções de lock no `TransferService`
5. documentar o `409` no Swagger
6. cobrir com testes unitários
7. validar `make unit-test`
8. validar `make functional-test`

## Checklist de Conclusão

- [x] timeout de lock configurado
- [x] exceção semântica criada
- [x] `TransferService` traduz contenção concorrente
- [x] resposta HTTP adequada documentada
- [x] testes unitários verdes
- [x] testes funcionais verdes

## Resultado Final da História

O fluxo de transferência deixou de depender de falha técnica genérica para cenários de contenção concorrente.

Convergências principais:

- o lock pessimista ganhou timeout explícito
- falhas de contenção passaram a ser traduzidas para `AccountResourceBusyException`
- a API agora pode responder com `409 Conflict` e payload `key/value` semântico
- o Swagger da transferência passou a documentar esse cenário

Validação final:

- `make unit-test`
- `make functional-test`

# Entrega 005 — Movimentacoes Financeiras

## Identificacao da Entrega

- Data da entrega: 2026-04-07
- Ordem cronologica da entrega: 005
- Nome da entrega: Movimentacoes financeiras
- Historia, fatia ou objetivo atendido: `HISTORIA-005`
- Tipo de versao sugerida: `MINOR`

## Resumo Executivo

Esta entrega fez a transferencia deixar rastros claros no dominio do banco digital. A partir dela, transferir dinheiro nao altera apenas saldo: a API tambem registra o debito na conta de origem, o credito na conta de destino e disponibiliza esse historico para consulta.

Na pratica, isso aumenta a auditabilidade da operacao principal do sistema e deixa o desafio mais coerente com um contexto financeiro real.

## Linguagem Ubiqua da Entrega

- `Movimentacao`: registro consultavel de um evento financeiro na conta
- `Debito`: saida de valor da conta de origem
- `Credito`: entrada de valor na conta de destino
- `Referencia de transferencia`: identificador que conecta os dois lados da mesma operacao
- `Historico da conta`: lista ordenada das movimentacoes daquela conta

## Problema de Negocio

Antes desta entrega, a API conseguia transferir saldo, mas nao oferecia historico financeiro do que tinha acontecido. Isso enfraquecia a rastreabilidade da operacao e limitava a capacidade de explicar o estado atual de uma conta.

Era necessario que cada transferencia deixasse evidencias consultaveis para origem e destino.

## O Que Foi Entregue

- foi criada a tabela `account_movement`
- foi criado o modelo de dominio de movimentacao financeira
- a transferencia passou a gerar um debito para a conta de origem
- a transferencia passou a gerar um credito para a conta de destino
- ambas as movimentacoes passaram a compartilhar a mesma referencia de transferencia
- foi criado o endpoint `GET /accounts/{id}/movements`
- foram criados testes unitarios da consulta de movimentacoes
- foram criados testes funcionais de sucesso e falha para o historico
- a infraestrutura funcional foi ajustada para respeitar o estado acumulado da suite e limpar a base em ordem segura

## O Que Nao Foi Entregue

- notificacao pos-transferencia
- padronizacao global completa de mensagens da API
- ampliacao total da suite funcional para todas as proximas historias

## Traducao Entre Tecnica e Negocio

- `Persistencia de debito e credito na mesma transacao`
  Impacto no dominio: saldo e historico passam a nascer juntos como parte da mesma operacao
  Beneficio para o negocio: evita que o sistema mostre saldo alterado sem o respectivo rastro financeiro

- `Referencia unica da transferencia`
  Impacto no dominio: conecta os dois lancamentos da mesma operacao
  Beneficio para o negocio: facilita auditoria e leitura do historico

- `Consulta por conta`
  Impacto no dominio: torna o extrato minimo da conta acessivel pela API
  Beneficio para o negocio: aumenta transparencia sobre o que aconteceu com o dinheiro

## Regras de Negocio Atendidas

- transferencia concluida deve gerar debito para a conta de origem
- transferencia concluida deve gerar credito para a conta de destino
- as duas movimentacoes devem estar vinculadas a mesma transferencia
- conta inexistente nao pode ser consultada como se tivesse historico valido
- o historico retornado deve refletir apenas operacoes concluidas

## Endpoints ou Comportamentos Disponibilizados

- `GET /accounts/{id}/movements`
  Objetivo: consultar o historico financeiro da conta
  Resultado esperado: retornar as movimentacoes registradas para a conta ou falhar com `ACCOUNT_NOT_FOUND` quando a conta nao existir

## Estrategia Tecnica Aplicada

A transferencia passou a gerar dois objetos de dominio `AccountMovement`, um de debito e outro de credito, persistidos na mesma transacao em que os saldos sao atualizados. A consulta do historico foi separada em `AccountMovementQueryService`, preservando a responsabilidade do fluxo de transferencia e mantendo a leitura em endpoint proprio.

Na base funcional, a suite em Jest foi endurecida para refletir o estado acumulado da execucao completa. A preparacao continua ocorrendo uma unica vez no inicio e o rollback total ocorre somente ao final, com limpeza ordenada entre `account_movement` e `account`.

## Evidencias de Validacao

- `make functional-test` com `11` testes passando
- cenario funcional de sucesso para `GET /accounts/{id}/movements`
- cenario funcional de falha para conta inexistente em `GET /accounts/{id}/movements`
- transferencia funcional comprovando que a referencia retornada pela API e a mesma das movimentacoes persistidas
- `GET /actuator/health` retornando `200`

## Arquivos ou Modulos Mais Relevantes

- `src/main/java/com/cwi/digitalbankapi/application/service/TransferService.java`
- `src/main/java/com/cwi/digitalbankapi/application/service/AccountMovementQueryService.java`
- `src/main/java/com/cwi/digitalbankapi/api/controller/AccountMovementController.java`
- `src/main/java/com/cwi/digitalbankapi/domain/statement`
- `src/main/resources/db/migration/V2__create_account_movement_table.sql`
- `__functional_tests__/src/endpoints/accounts/GET/accountMovementsGetSuccess.spec.js`

## Riscos, Limitacoes ou Pendencias

- notificacoes ainda nao acompanham a transferencia
- swagger ainda nao documenta detalhadamente o novo endpoint
- a suite funcional ainda sera ampliada nas historias seguintes

## Relacao com a Spec Principal

Esta entrega concretiza a parte da spec que fala em consulta de movimentacoes financeiras. Ela transforma a transferencia em uma operacao auditavel e observavel do ponto de vista do dominio.

## Pronto Para Fechamento de Versao

- esta entrega esta documentada em ordem cronologica
- a documentacao reflete o que realmente foi implementado
- a classificacao semantica proposta esta coerente
- o documento pode acompanhar o commit de fechamento da versao
- a entrega esta pronta para ser associada a uma tag semantica

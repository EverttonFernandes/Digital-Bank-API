# Entrega 014 — Criacao de Conta Bancaria

## Identificacao da Entrega

- Data da entrega: 2026-04-08
- Ordem cronologica da entrega: 014
- Nome da entrega: Criacao de conta bancaria
- Historia, fatia ou objetivo atendido: `HISTORIA-014`
- Tipo de versao sugerida: `MINOR`

## Resumo Executivo

Esta entrega completou o ciclo basico de vida da conta dentro da API ao introduzir a criacao de contas por `POST /accounts`.

Na pratica, a API deixou de depender apenas de contas pre-carregadas e passou a permitir abertura de nova conta com persistencia real, semantica REST coerente e response navegavel em `HAL`.

## Linguagem Ubiqua da Entrega

- `Criacao de conta`: abertura de um novo recurso `Account` na colecao `/accounts`
- `Saldo inicial`: valor de abertura da conta
- `Location`: header HTTP que indica onde o recurso criado pode ser consultado
- `HAL`: formato de resposta com `_links`

## Problema de Negocio

Antes desta entrega, a API permitia consultar contas existentes, transferir valores, consultar movimentacoes e consultar notificacoes, mas nao permitia abrir uma nova conta pelo contrato publico.

Isso deixava a solucao funcionalmente correta para o desafio original, mas ainda incompleta como ciclo de vida do recurso `Account`.

## O Que Foi Entregue

- endpoint `POST /accounts`
- request DTO de criacao com `ownerName` e `initialBalance`
- validacao de entrada e regra de saldo inicial nao negativo
- persistencia real da nova conta no banco
- retorno `201 Created` com header `Location`
- body de sucesso em `HAL`
- Swagger refletindo request e responses reais
- testes unitarios da nova fatia
- testes funcionais BDD de sucesso e falha
- ajuste no rollback funcional para destruir toda a massa criada ao final da suite

## O Que Nao Foi Entregue

- atualizacao de conta por `PUT` ou `PATCH`
- remocao de conta por `DELETE`
- regras de limite ou bloqueio de conta

## Traducao Entre Tecnica e Negocio

- `POST /accounts`
  Impacto no dominio: abre uma nova conta no sistema
  Beneficio para o negocio: elimina dependencia exclusiva de seed inicial

- `201 Created + Location`
  Impacto no dominio: formaliza corretamente a criacao do recurso
  Beneficio para o negocio: mostra maturidade de design da API

- `HAL na conta criada`
  Impacto no dominio: a propria resposta indica como consultar a conta, suas movimentacoes e notificacoes
  Beneficio para o negocio: melhora UX da API para quem integra ou avalia o sistema

## Regras de Negocio Atendidas

- o nome do titular e obrigatorio
- o saldo inicial e obrigatorio
- o saldo inicial nao pode ser negativo
- a conta criada nasce sem movimentacoes
- a conta criada nasce sem notificacoes

## Endpoints ou Comportamentos Disponibilizados

- `POST /accounts`
  Objetivo: criar nova conta bancaria
  Resultado esperado: retornar `201 Created`, `Location` e representacao `HAL` da conta criada

## Estrategia Tecnica Aplicada

O corte adotado reaproveitou a arquitetura existente:

- request DTO na `api/application`
- converter dedicado
- specification de criacao de conta
- service de criacao
- persistencia via repositório já existente
- reuso do `AccountRepresentationModelAssembler` introduzido na `HISTORIA-013`

Tambem foi adicionada migration para que o identificador de conta passe a ser gerado pelo banco em novas insercoes, preservando os registros seedados ja existentes.

## Evidencias de Validacao

- `make unit-test` com `BUILD SUCCESS`
- `make functional-test` com `23` testes passando
- `/v3/api-docs` retornando `200` com documentacao de `POST /accounts`
- `POST /accounts` validado com cenarios de sucesso e falha

## Arquivos ou Modulos Mais Relevantes

- `src/main/java/com/cwi/digitalbankapi/api/controller/AccountController.java`
- `src/main/java/com/cwi/digitalbankapi/application/dto/CreateAccountRequest.java`
- `src/main/java/com/cwi/digitalbankapi/application/converter/CreateAccountRequestConverter.java`
- `src/main/java/com/cwi/digitalbankapi/application/service/CreateAccountService.java`
- `src/main/java/com/cwi/digitalbankapi/domain/account/specification/*`
- `src/main/resources/db/migration/V4__make_account_identifier_generated_by_default.sql`
- `__functional_tests__/src/endpoints/accounts/POST/*`

## Riscos, Limitacoes ou Pendencias

- o proximo passo natural do dominio e revisar o mapeamento relacional da camada de persistencia
- atualizacao e remocao de conta continuam fora do escopo

## Relacao com a Spec Principal

Esta entrega aproveita a abertura do enunciado original, que permitia base pre-carregada ou cadastro basico de conta. O projeto agora passa a atender os dois caminhos.

## Pronto Para Fechamento de Versao

- esta entrega esta documentada em ordem cronologica
- a documentacao reflete o comportamento real da API
- os testes obrigatorios passaram
- a entrega esta pronta para commit e tag semantica

# Entrega 003 — Gestao Basica de Contas

## Identificacao da Entrega

- Data da entrega: 2026-04-07
- Ordem cronologica da entrega: 003
- Nome da entrega: Gestao basica de contas
- Historia, fatia ou objetivo atendido: `HISTORIA-003`
- Tipo de versao sugerida: `MINOR`

## Resumo Executivo

Esta entrega colocou as contas do banco digital em funcionamento. A aplicacao passou a iniciar com contas pre-carregadas e agora permite consultar tanto a lista de contas quanto uma conta especifica pelo identificador.

Na pratica, isso resolve a ausencia de dados reais para demonstracao e cria a primeira capacidade funcional do dominio. Quem avalia o projeto ja consegue enxergar titulares e saldos pela API, o que prepara a base para a historia de transferencia.

## Linguagem Ubiqua da Entrega

- `Conta`: representacao bancária que possui identificador, titular e saldo
- `Titular`: pessoa dona da conta consultada
- `Saldo`: valor monetario atualmente disponivel na conta
- `Seed inicial`: carga de dados pre-definida para que o sistema ja nasca com contas disponiveis

## Problema de Negocio

Antes desta entrega, a aplicacao possuia apenas a base estrutural e de infraestrutura. Ainda nao havia um conceito funcional de conta que pudesse ser consultado pela API.

Para o desafio, isso era um bloqueio relevante, porque transferencia e movimentacao dependem da existencia de contas reais. O sistema precisava passar a iniciar com contas disponiveis e permitir que o avaliador enxergasse nome do titular e saldo sem preparacao manual do banco.

## O Que Foi Entregue

- foi criado o modelo de dominio de conta
- foi criada a persistencia de contas em PostgreSQL
- foi adicionada migration com seed inicial de tres contas
- foi disponibilizado o endpoint `GET /accounts`
- foi disponibilizado o endpoint `GET /accounts/{id}`
- foi adicionado tratamento para conta inexistente com resposta `key/value`
- foram criados testes unitarios para conversao e consulta de contas

## O Que Nao Foi Entregue

- transferencia entre contas
- movimentacoes financeiras
- notificacao
- criacao manual de novas contas pela API
- suite funcional em Jest

## Traducao Entre Tecnica e Negocio

- `Migration com seed inicial`
  Impacto no dominio: garante que o sistema possua contas validas desde o primeiro boot
  Beneficio para o negocio: facilita demonstracao, testes e validacao sem operacao manual no banco

- `Separacao entre dominio, aplicacao, api e persistencia`
  Impacto no dominio: preserva a conta como conceito central sem acoplar regra de negocio ao banco ou ao controller
  Beneficio para o negocio: reduz retrabalho e torna as proximas historias mais seguras para evoluir

- `Tratamento de conta inexistente com key/value`
  Impacto no dominio: deixa explicito quando uma consulta falhou porque a conta nao existe
  Beneficio para o negocio: melhora entendimento do erro e prepara o padrao para respostas futuras

## Regras de Negocio Atendidas

- o sistema deve possuir contas pre-carregadas
- deve ser possivel listar contas
- deve ser possivel buscar conta por identificador
- saldo e titular devem ser retornados corretamente
- conta inexistente deve ser tratada de forma consistente

## Endpoints ou Comportamentos Disponibilizados

- `GET /accounts`
  Objetivo: listar as contas disponiveis no banco digital
  Resultado esperado: retorna identificador, titular e saldo das contas pre-carregadas

- `GET /accounts/{id}`
  Objetivo: consultar uma conta especifica
  Resultado esperado: retorna a conta quando ela existir ou `404` com `key/value` quando ela nao existir

## Estrategia Tecnica Aplicada

A entrega foi construida com repositorio de dominio para contas, adaptador de persistencia em PostgreSQL, migration para criar tabela e seed inicial, service de consulta e controller REST dedicado.

Tambem foi usado um converter para transformar conta de dominio em resposta da API, preservando a separacao entre modelo de negocio e contrato exposto ao consumidor.

## Evidencias de Validacao

- `make unit-test` com `BUILD SUCCESS`
- `GET /accounts` retornando tres contas pre-carregadas
- `GET /accounts/1` retornando `200 OK`
- `GET /accounts/99` retornando `404 Not Found` com `ACCOUNT_NOT_FOUND`
- `GET /actuator/health` retornando `200 OK`

## Arquivos ou Modulos Mais Relevantes

- `src/main/java/com/cwi/digitalbankapi/domain/account`
- `src/main/java/com/cwi/digitalbankapi/application/service/AccountQueryService.java`
- `src/main/java/com/cwi/digitalbankapi/api/controller/AccountController.java`
- `src/main/java/com/cwi/digitalbankapi/infrastructure/persistence`
- `src/main/resources/db/migration/V1__create_account_table_and_seed_initial_accounts.sql`

## Riscos, Limitacoes ou Pendencias

- a criacao de contas pela API continua fora do escopo
- o tratamento global de erros ainda sera ampliado na `HISTORIA-007`
- os testes funcionais em Jest continuam planejados para historia especifica posterior

## Relacao com a Spec Principal

Esta entrega concretiza a parte da spec que exige gestao basica de contas e base pre-carregada para validacao do sistema. Ela tambem prepara o terreno tecnico e de negocio para a transferencia entre contas.

## Pronto Para Fechamento de Versao

- esta entrega esta documentada em ordem cronologica
- a documentacao reflete o que realmente foi implementado
- a classificacao semantica proposta esta coerente
- o documento pode acompanhar o commit de fechamento da versao
- a entrega esta pronta para ser associada a uma tag semantica

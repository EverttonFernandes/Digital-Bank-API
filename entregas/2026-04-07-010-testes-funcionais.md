# Entrega 010 — Testes Funcionais

## Identificacao da Entrega

- Data da entrega: 2026-04-07
- Ordem cronologica da entrega: 010
- Nome da entrega: Testes funcionais
- Historia, fatia ou objetivo atendido: `HISTORIA-010`
- Tipo de versao sugerida: `MINOR`

## Resumo Executivo

Esta entrega consolidou a prova end-to-end da API. Depois dela, os fluxos principais do banco digital passaram a ser validados com massa previsivel, chamadas reais aos endpoints e confirmacao do estado final do sistema.

Na pratica, isso aumenta a confianca da entrega como um todo, porque a API deixa de ser validada apenas por comportamento interno e passa a ser comprovada de ponta a ponta no ambiente real do projeto.

## Linguagem Ubiqua da Entrega

- `Fixture`: massa de dados nomeada e previsivel usada para montar cenario de negocio
- `Seeder`: mecanismo que persiste a massa de dados no banco antes da suite
- `Rollback`: limpeza total da massa funcional ao final da execucao
- `Teste funcional`: validacao end-to-end de um comportamento exposto pela API
- `Estado final`: situacao real do sistema depois que a operacao foi executada

## Problema de Negocio

Antes desta entrega, o projeto ja tinha um setup inicial de testes funcionais, mas ele ainda precisava ser tratado como um ativo formal da solucao. Era necessario comprovar com clareza que contas, transferencias, movimentacoes e notificacoes funcionavam de ponta a ponta com dados reais e comportamento previsivel.

Sem isso, a confianca na entrega ficaria concentrada demais em testes unitarios e em verificacoes manuais.

## O Que Foi Entregue

- foi consolidada a estrutura funcional em `Jest` criada nas historias anteriores
- foram organizados `fixtures` e `seeders` para preparar massa deterministica no banco
- a suite passou a subir a massa antes da execucao total e destruir tudo ao final
- foram mantidos cenarios separados de sucesso e falha
- os testes passaram a validar o estado final por `GET` sempre que o comportamento exposto permitia isso
- falhas de negocio passaram a validar mensagens `key` e `value`
- a suite funcional passou a cobrir contas, transferencias, movimentacoes e notificacoes

## O Que Nao Foi Entregue

- cobertura funcional para cenarios fora do escopo do desafio
- integracao com pipeline externa de qualidade

## Traducao Entre Tecnica e Negocio

- `Seeders com fixtures nomeadas`
  Impacto no dominio: criam cenarios previsiveis para contas, movimentacoes e notificacoes
  Beneficio para o negocio: aumentam confiabilidade da validacao e evitam dependencia de base manual

- `Rollback total ao fim da suite`
  Impacto no dominio: garante isolamento da massa funcional entre execucoes
  Beneficio para o negocio: evita sujeira de ambiente e reduz flakiness

- `Validacao do estado final por GET`
  Impacto no dominio: confirma que a operacao produziu o efeito real esperado
  Beneficio para o negocio: prova que a API nao apenas responde corretamente, mas realmente altera o sistema como deveria

## Regras de Negocio Atendidas

- contas podem ser listadas e consultadas
- transferencia valida atualiza os saldos corretos
- transferencia invalida nao altera indevidamente o estado do sistema
- movimentacoes associadas a transferencia podem ser consultadas
- notificacoes pos-transferencia podem ser consultadas
- falhas de negocio retornam `key` e `value` coerentes com o problema ocorrido

## Endpoints ou Comportamentos Disponibilizados

- `GET /accounts`
  Objetivo: listar contas disponiveis
  Resultado esperado: retorna as contas persistidas na massa funcional

- `GET /accounts/{accountId}`
  Objetivo: consultar uma conta especifica
  Resultado esperado: retorna a conta quando ela existe ou erro padronizado quando nao existe

- `POST /transfers`
  Objetivo: realizar transferencia entre contas
  Resultado esperado: executa a transferencia ou retorna falha coerente com a regra de negocio

- `GET /accounts/{accountId}/movements`
  Objetivo: consultar historico financeiro da conta
  Resultado esperado: retorna debit e credit gerados pelas transferencias

- `GET /accounts/{accountId}/notifications`
  Objetivo: consultar notificacoes da conta
  Resultado esperado: retorna notificacoes geradas apos transferencia bem-sucedida

## Estrategia Tecnica Aplicada

A base funcional foi consolidada com `Jest`, `supertest` e `Sequelize`, mantendo seeders e fixtures previsiveis. O fluxo operacional da suite ficou centralizado em um runner que prepara a massa no inicio, executa todos os cenarios e destroi tudo ao final.

Os testes seguem `GIVEN / WHEN / THEN`, separacao entre sucesso e falha e validacao end-to-end por meio da propria API.

## Evidencias de Validacao

- `make functional-test` com `16` testes passando
- cenarios de sucesso e falha separados por endpoint
- validacao de `status code`, payload, mensagens `key/value` e estado final por `GET`
- ausencia de limpeza por `beforeEach` ou `beforeAll`, mantendo o ciclo de massa por suite completa

## Arquivos ou Modulos Mais Relevantes

- `__functional_tests__/runFunctionalTests.js`
- `__functional_tests__/seed.js`
- `__functional_tests__/rollback.js`
- `__functional_tests__/src/fixtures/local.json`
- `__functional_tests__/src/seeders/account/account.seeder.js`
- `__functional_tests__/src/seeders/accountMovement/accountMovement.seeder.js`
- `__functional_tests__/src/seeders/accountNotification/accountNotification.seeder.js`
- `__functional_tests__/src/endpoints/transfers/POST/transfersPostSuccess.spec.js`
- `__functional_tests__/src/endpoints/transfers/POST/transfersPostFailure.spec.js`

## Riscos, Limitacoes ou Pendencias

- a suite ainda depende do ambiente local completo para execucao
- novos endpoints futuros devem seguir o mesmo padrao funcional para manter consistencia

## Relacao com a Spec Principal

Esta entrega concretiza o requisito nao funcional de testes funcionais end-to-end, usando seeders, fixtures, mensagens padronizadas e validacao do efeito real da API.

## Pronto Para Fechamento de Versao

- esta entrega esta documentada em ordem cronologica
- a documentacao reflete o que realmente foi implementado
- a classificacao semantica proposta esta coerente
- o documento pode acompanhar o commit de fechamento da versao
- a entrega esta pronta para ser associada a uma tag semantica

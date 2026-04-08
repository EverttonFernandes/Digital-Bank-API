# Entrega 008 — Swagger OpenAPI

## Identificacao da Entrega

- Data da entrega: 2026-04-07
- Ordem cronologica da entrega: 008
- Nome da entrega: Swagger OpenAPI
- Historia, fatia ou objetivo atendido: `HISTORIA-008`
- Tipo de versao sugerida: `MINOR`

## Resumo Executivo

Esta entrega tornou os contratos da API navegaveis e mais simples de validar. Depois dela, a aplicacao passou a expor um
OpenAPI claro para contas, transferencias, movimentacoes, notificacoes e respostas de erro.

Isso melhora a demonstracao tecnica do projeto e reduz a friccao para quem quiser entender rapidamente o que a API faz.

## Linguagem Ubiqua da Entrega

- `OpenAPI`: contrato formal da API em formato JSON
- `Swagger UI`: interface navegavel para explorar os endpoints
- `Schema`: descricao estruturada de request ou response
- `Tag`: agrupamento funcional de endpoints
- `Contrato de erro`: formato padronizado das respostas de falha

## Problema de Negocio

Antes desta entrega, a API ja possuia endpoints funcionais, mas a leitura dos contratos dependia de inspeção direta no
codigo ou de tentativa e erro. Isso dificultava avaliacao, demonstracao e integracao.

Era necessario expor a documentacao navegavel de forma coerente com o comportamento real da API.

## O Que Foi Entregue

- foi criada a configuracao global do OpenAPI
- foram adicionadas tags e descricoes para os controllers principais
- foram documentados os payloads de transferencia e as respostas principais
- foram documentadas as respostas de erro padronizadas
- foi mantido o acesso local ao Swagger pela rota `/swagger-ui.html`
- foi validada a exposicao do contrato por `/v3/api-docs`

## O Que Nao Foi Entregue

- customizacao visual do Swagger
- exemplos exaustivos para todos os cenarios internos
- autenticacao ou seguranca no contrato da UI

## Traducao Entre Tecnica e Negocio

- `Schemas anotados nos DTOs`
  Impacto no dominio: tornam explicitos os contratos de entrada e saida da API
  Beneficio para o negocio: facilitam validacao da solucao pela banca tecnica

- `Tags por area funcional`
  Impacto no dominio: agrupam endpoints por linguagem de negocio
  Beneficio para o negocio: melhoram navegacao e entendimento rapido da API

- `Contrato de erro visivel no OpenAPI`
  Impacto no dominio: mostra que falhas tambem fazem parte do contrato
  Beneficio para o negocio: aumenta previsibilidade para integradores

## Regras de Negocio Atendidas

- endpoints principais devem estar documentados
- payloads e respostas devem estar claros
- swagger deve ficar acessivel no ambiente local

## Endpoints ou Comportamentos Disponibilizados

- `/swagger-ui.html`
  Objetivo: abrir a documentacao navegavel da API
  Resultado esperado: redirecionar para a UI do Swagger

- `/v3/api-docs`
  Objetivo: expor o contrato OpenAPI em JSON
  Resultado esperado: retornar tags, paths e schemas alinhados ao codigo real

## Estrategia Tecnica Aplicada

Foi criada uma configuracao global simples de OpenAPI com metadados do projeto. Os DTOs principais receberam `@Schema` e
os controllers receberam `@Tag`, `@Operation` e respostas documentadas apenas onde isso adiciona valor real.

A historia foi validada por compilacao e pela leitura direta do `/v3/api-docs` em runtime, garantindo que a documentacao
publicada corresponde ao que a aplicacao realmente expoe.

## Evidencias de Validacao

- `make unit-test` com `BUILD SUCCESS`
- `GET /v3/api-docs` retornando `200`
- `GET /swagger-ui.html` retornando `302` para `/swagger-ui/index.html`
- presenca de tags e schemas de contas, transferencias, movimentacoes, notificacoes e erro padronizado

## Arquivos ou Modulos Mais Relevantes

- `src/main/java/com/avaliadora/digitalbankapi/infrastructure/config/OpenApiConfiguration.java`
- `src/main/java/com/avaliadora/digitalbankapi/api/controller/AccountController.java`
- `src/main/java/com/avaliadora/digitalbankapi/api/controller/TransferController.java`
- `src/main/java/com/avaliadora/digitalbankapi/application/dto/TransferRequest.java`
- `src/main/java/com/avaliadora/digitalbankapi/shared/response/ApiErrorResponse.java`

## Riscos, Limitacoes ou Pendencias

- novas historias devem manter os schemas atualizados
- ainda pode ser util enriquecer alguns exemplos no futuro, sem exagero

## Relacao com a Spec Principal

Esta entrega atende a exigencia de Swagger/OpenAPI e torna a solucao mais facil de validar, demonstrar e consumir.

## Pronto Para Fechamento de Versao

- esta entrega esta documentada em ordem cronologica
- a documentacao reflete o que realmente foi implementado
- a classificacao semantica proposta esta coerente
- o documento pode acompanhar o commit de fechamento da versao
- a entrega esta pronta para ser associada a uma tag semantica

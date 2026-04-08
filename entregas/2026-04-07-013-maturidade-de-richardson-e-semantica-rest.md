# Entrega 013 — Maturidade de Richardson e Semantica REST

## Identificacao da Entrega

- Data da entrega: 2026-04-07
- Ordem cronologica da entrega: 013
- Nome da entrega: Maturidade de Richardson e semantica REST
- Historia, fatia ou objetivo atendido: `HISTORIA-013`
- Tipo de versao sugerida: `MINOR`

## Resumo Executivo

Esta entrega elevou a maturidade da interface HTTP da API sem alterar o dominio central do projeto. O resultado foi uma
API mais navegavel, mais coerente com REST e mais preparada para apresentacao tecnica.

Na pratica, contas, transferencias, movimentacoes e notificacoes passaram a devolver links navegaveis, permitindo que o
proprio response indique recursos relacionados e proximos passos do consumidor.

## Linguagem Ubiqua da Entrega

- `HATEOAS`: forma de a propria resposta da API expor links para recursos relacionados
- `HAL`: formato de representacao que organiza `_links` e `_embedded`
- `Recurso`: entidade de negocio exposta pela API
- `Colecao`: conjunto navegavel de recursos relacionados
- `Semantica REST`: uso coerente de rotas, recursos e verbos HTTP

## Problema de Negocio

Antes desta entrega, a API ja funcionava bem e usava recursos e verbos HTTP coerentes, mas as respostas ainda eram pouco
navegaveis. O consumidor precisava conhecer previamente varias rotas para continuar a exploracao da API.

Era necessario tornar a interface mais autoexplicativa e mais alinhada com uma defesa tecnica madura de REST.

## O Que Foi Entregue

- foi adicionada a dependencia `Spring HATEOAS`
- contas passaram a devolver links para conta, movimentacoes e notificacoes relacionadas
- transferencias passaram a devolver links para contas, movimentacoes e notificacoes relacionadas
- colecoes de movimentacoes e notificacoes passaram a usar envelope HAL com `_embedded` e `_links`
- o Swagger passou a refletir os models HATEOAS e os envelopes de colecao
- os testes unitarios e funcionais foram ajustados para validar o novo contrato sem regressao
- o comando de teste unitario foi corrigido para evitar estouro de disco no ambiente containerizado

## O Que Nao Foi Entregue

- novo comportamento de negocio
- novos casos de uso de conta como `PATCH`, `PUT` ou `DELETE` sem necessidade real do dominio
- recurso consultavel de transferencia por referencia

## Traducao Entre Tecnica e Negocio

- `Spring HATEOAS`
  Impacto no dominio: faz a resposta apontar para recursos relacionados do proprio banco digital
  Beneficio para o negocio: deixa a API mais guiada, mais clara e mais profissional para avaliacao e integracao

- `HAL para colecoes`
  Impacto no dominio: organiza listas de contas, movimentacoes e notificacoes com links e recursos embutidos
  Beneficio para o negocio: facilita navegacao do cliente e melhora legibilidade do contrato da API

- `Swagger coerente com os novos responses`
  Impacto no dominio: documenta corretamente atributos, links e envelopes retornados
  Beneficio para o negocio: reduz discrepancia entre documentacao e comportamento real da API

## Regras de Negocio Atendidas

- as regras de transferencia, movimentacao e notificacao continuam as mesmas
- a navegacao entre recursos relacionados passou a ser exposta pela propria interface da API
- o contrato de erro padronizado foi preservado

## Endpoints ou Comportamentos Disponibilizados

- `GET /accounts`
  Objetivo: listar contas com envelope HAL
  Resultado esperado: retornar contas e links de navegacao da colecao

- `GET /accounts/{accountId}`
  Objetivo: consultar conta individual com links relacionados
  Resultado esperado: retornar conta com links para movimentacoes e notificacoes

- `POST /transfers`
  Objetivo: realizar transferencia e orientar o consumidor para recursos relacionados
  Resultado esperado: retornar dados da transferencia com links navegaveis

- `GET /accounts/{accountId}/movements`
  Objetivo: consultar movimentacoes com envelope HAL
  Resultado esperado: retornar movimentacoes e links da conta e da colecao

- `GET /accounts/{accountId}/notifications`
  Objetivo: consultar notificacoes com envelope HAL
  Resultado esperado: retornar notificacoes e links da conta e da colecao

## Estrategia Tecnica Aplicada

O caminho adotado foi o menor corte seguro: manter as rotas principais do dominio e evoluir apenas a representacao
devolvida. Isso evitou regressao de semantica e concentrou o ganho na navegacao e na clareza do contrato.

Foram criados `RepresentationModel` e assemblers dedicados para cada response principal, com links gerados a partir dos
proprios controllers.

## Evidencias de Validacao

- `make unit-test` com `BUILD SUCCESS`
- `make functional-test` com `16` testes passando
- `GET /swagger-ui.html` retornando redirecionamento esperado
- `GET /v3/api-docs` retornando `200`
- OpenAPI expondo models HATEOAS e colecoes HAL

## Arquivos ou Modulos Mais Relevantes

- `pom.xml`
- `Makefile`
- `src/main/java/com/avaliadora/digitalbankapi/api/controller/AccountController.java`
- `src/main/java/com/avaliadora/digitalbankapi/api/controller/TransferController.java`
- `src/main/java/com/avaliadora/digitalbankapi/api/assembler/*`
- `src/main/java/com/avaliadora/digitalbankapi/api/representation/*`
- `src/test/java/com/avaliadora/digitalbankapi/api/assembler/*`
- `__functional_tests__/src/endpoints/**/*`

## Riscos, Limitacoes ou Pendencias

- ainda nao existe recurso consultavel de transferencia por referencia
- a proxima evolucao natural seria decidir se o dominio realmente precisa expor `PUT`, `PATCH` ou `DELETE` para conta

## Relacao com a Spec Principal

Esta entrega aprofunda a qualidade arquitetural da API alem do escopo minimo do desafio, reforcando semantica REST,
discoverability e coerencia entre contrato e documentacao.

## Pronto Para Fechamento de Versao

- esta entrega esta documentada em ordem cronologica
- a documentacao reflete o que realmente foi implementado
- a classificacao semantica proposta esta coerente
- o documento pode acompanhar o commit de fechamento da versao
- a entrega esta pronta para ser associada a uma tag semantica

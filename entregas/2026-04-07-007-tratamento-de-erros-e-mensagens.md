# Entrega 007 — Tratamento De Erros E Mensagens

## Identificacao da Entrega

- Data da entrega: 2026-04-07
- Ordem cronologica da entrega: 007
- Nome da entrega: Tratamento de erros e mensagens
- Historia, fatia ou objetivo atendido: `HISTORIA-007`
- Tipo de versao sugerida: `MINOR`

## Resumo Executivo

Esta entrega estabilizou a forma como a API comunica falhas. Depois dela, os consumidores da API passam a receber respostas mais previsiveis, com `key` e `value` claros tanto para regras de negocio quanto para erros de entrada invalida.

Isso fortalece a legibilidade da API e torna os testes funcionais mais confiaveis, porque o contrato de erro deixa de depender de comportamento implicito do framework.

## Linguagem Ubiqua da Entrega

- `Erro de regra de negocio`: falha causada por uma restricao do dominio
- `Erro de entrada invalida`: falha causada por payload ausente, malformado ou inconsistente
- `key`: identificador semantico do erro
- `value`: explicacao legivel do que aconteceu
- `Contrato de erro`: formato estavel da resposta retornada pela API

## Problema de Negocio

Antes desta entrega, a API ja respondia com `key` e `value` em alguns cenarios de dominio, mas ainda havia inconsistencias quando o payload chegava incompleto ou com JSON invalido. Isso fragilizava quem consome a API e tambem reduzia a confianca dos testes funcionais.

Era necessario unificar esse comportamento sob um contrato simples e previsivel.

## O Que Foi Entregue

- foi consolidado o handler global de excecoes da API
- foi criado o erro `INVALID_REQUEST_DATA`
- payload sem campo obrigatorio passou a retornar erro padronizado
- corpo JSON invalido passou a retornar erro padronizado
- parametro com formato invalido passou a retornar erro padronizado
- foram criados testes unitarios para o handler global
- foram criados cenarios funcionais para payload obrigatorio ausente e corpo de requisicao invalido

## O Que Nao Foi Entregue

- internacionalizacao completa de mensagens
- catalogo externo de mensagens
- padronizacao documental via Swagger, que fica na proxima historia

## Traducao Entre Tecnica e Negocio

- `INVALID_REQUEST_DATA`
  Impacto no dominio: separa claramente erro de entrada de erro de regra de negocio
  Beneficio para o negocio: melhora entendimento para quem integra com a API

- `Handler global de excecoes`
  Impacto no dominio: centraliza a traducao das falhas em respostas HTTP consistentes
  Beneficio para o negocio: reduz ambiguidade e facilita suporte

- `Cenarios funcionais de erro`
  Impacto no dominio: provam que a API responde corretamente em falhas reais de consumo
  Beneficio para o negocio: aumenta confianca na integracao e evita regressao silenciosa

## Regras de Negocio Atendidas

- falhas de regra de negocio continuam respondendo com `key` e `value`
- payload com campo obrigatorio ausente responde com `INVALID_REQUEST_DATA`
- corpo de requisicao invalido responde com `INVALID_REQUEST_DATA`
- parametro com formato invalido responde com `INVALID_REQUEST_DATA`

## Endpoints ou Comportamentos Disponibilizados

- `POST /transfers`
  Objetivo: manter contrato de erro consistente para falhas de negocio e falhas de entrada
  Resultado esperado: retornar `status`, `key` e `value` coerentes para cada cenario invalido

## Estrategia Tecnica Aplicada

O `GlobalApiExceptionHandler` foi consolidado como ponto unico de traducao de falhas. Para erros de payload ausente, a validacao obrigatoria foi reforcada no fluxo de conversao com `InvalidRequestDataException`. Para JSON malformado, o tratamento foi movido para o `ResponseEntityExceptionHandler`, que intercepta corretamente o comportamento padrao do Spring Boot.

Na camada funcional, os cenarios de falha de transferencia foram ampliados para validar mensagem padronizada sem alterar o estado final das contas.

## Evidencias de Validacao

- `make unit-test` com `BUILD SUCCESS`
- `make functional-test` com `16` testes passando
- validacao funcional de campo obrigatorio ausente
- validacao funcional de corpo da requisicao invalido
- manutencao do estado final da base nos cenarios de falha

## Arquivos ou Modulos Mais Relevantes

- `src/main/java/com/cwi/digitalbankapi/api/controller/GlobalApiExceptionHandler.java`
- `src/main/java/com/cwi/digitalbankapi/application/converter/TransferRequestConverter.java`
- `src/main/java/com/cwi/digitalbankapi/shared/exception/InvalidRequestDataException.java`
- `src/test/java/com/cwi/digitalbankapi/api/controller/GlobalApiExceptionHandlerTest.java`
- `__functional_tests__/src/endpoints/transfers/POST/transfersPostFailure.spec.js`

## Riscos, Limitacoes ou Pendencias

- novas historias ainda podem ampliar o catalogo de mensagens
- o Swagger ainda precisa refletir explicitamente esse contrato de erro

## Relacao com a Spec Principal

Esta entrega atende a exigencia de mensagens claras e testaveis para as falhas da API, fortalecendo o comportamento funcional e nao funcional do desafio.

## Pronto Para Fechamento de Versao

- esta entrega esta documentada em ordem cronologica
- a documentacao reflete o que realmente foi implementado
- a classificacao semantica proposta esta coerente
- o documento pode acompanhar o commit de fechamento da versao
- a entrega esta pronta para ser associada a uma tag semantica

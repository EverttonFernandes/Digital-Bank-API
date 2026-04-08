# Entrega 016 — Alinhamento de Padrão com padrao-de-referencia-em-camadas

## Resumo Executivo

Esta entrega refatorou a organização interna do back-end para aproximar o projeto do padrão estrutural observado no
`padrao-de-referencia-em-camadas`, sem alterar o comportamento externo da API.

O foco não foi criar novas regras de negócio. O foco foi redistribuir responsabilidades para deixar a solução mais
coerente, previsível e próxima de um padrão maduro já conhecido pelo autor.

## O Que Mudou

- a fatia de conta passou a ter um único `AccountService` como ponto principal de orquestração
- o controller de conta ficou mais fino e mais centrado em HTTP
- os converters passaram a montar o domínio de forma mais explícita
- as specifications passaram a validar o agregado de conta em vez de um comando intermediário
- a transferência passou a concentrar mais comportamento dentro do próprio domínio `Transfer`
- classes redundantes que perderam utilidade após a convergência estrutural foram removidas

## Tradução Entre Técnica e Negócio

Do ponto de vista técnico, a entrega reduziu a fragmentação da lógica entre camadas.

Do ponto de vista de negócio, isso significa que a API fica mais sustentável para evoluções futuras, com menor risco de
espalhar regra em lugares errados e maior clareza na defesa arquitetural da solução.

## Comportamentos Preservados

Nada do contrato funcional da API foi alterado.

Continuam funcionando como antes:

- criação de conta
- consulta de contas
- transferência entre contas
- consulta de movimentações
- consulta de notificações
- tratamento padronizado de erros

## Evidências de Validação

Validação final executada nesta entrega:

- `make unit-test`
- `make functional-test`

Resultado observado:

- suíte unitária verde
- suíte funcional verde com `23/23` testes passando

## Decisão Arquitetural Relevante

A principal decisão desta entrega foi evitar “copiar” o `padrao-de-referencia-em-camadas` literalmente.

Em vez disso, o projeto absorveu o padrão que fazia sentido:

- service principal por fatia
- converter transformando DTO em domínio
- specification no domínio
- persistência limpa via interfaces e implementações concretas com JPA/Hibernate

## Impacto Final

Esta história deixou o back-end mais coerente com o padrão de código esperado pelo autor, preservando a API pública e
usando os testes funcionais como guardião real de regressão.

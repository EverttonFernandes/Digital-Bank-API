# HISTORIA-022 - CI/CD COM GITHUB ACTIONS

## Objetivo de Negócio

Automatizar a validacao tecnica do projeto em ambiente remoto para reduzir risco manual antes da entrega e deixar o
repositorio pronto para demonstrar maturidade de engenharia em `CI/CD`.

## Objetivo Técnico

Adicionar workflows de `GitHub Actions` para:

- integrar o fluxo de testes unitarios e funcionais a cada `push` e `pull request`
- entregar artefatos de build e cobertura em tags semanticas
- documentar claramente como o pipeline funciona e quais gates ele aplica

## Problema Atual

Hoje a validacao do projeto depende exclusivamente de execucao local. Isso gera algumas fragilidades:

- ausencia de validacao automatica remota a cada mudanca
- ausencia de trilha de execucao para banca tecnica no proprio GitHub
- ausencia de entrega automatizada de artefatos em tags semanticas

## Resultado Esperado

Ao final da historia, o repositorio deve possuir:

- workflow de `CI` rodando testes unitarios e funcionais
- workflow de `CD` reagindo a tags semanticas
- entrega de artefatos relevantes da versao gerada
- documentacao no `README` explicando o pipeline

## Critérios de Aceite

- deve existir um workflow de `CI` em `.github/workflows`
- o workflow de `CI` deve rodar em `push` e `pull_request`
- o workflow de `CI` deve validar a suite unitaria com `Maven`
- o workflow de `CI` deve validar a suite funcional com `Jest`
- o workflow de `CI` deve subir a API contra `PostgreSQL` no runner
- deve existir um workflow de `CD` em `.github/workflows`
- o workflow de `CD` deve reagir a tags semanticas `v*.*.*`
- o workflow de `CD` deve gerar o `jar` da aplicacao
- o workflow de `CD` deve publicar artefatos da entrega
- o `README` deve explicar o fluxo de `GitHub Actions`
- `make unit-test` deve permanecer verde
- `make functional-test` deve permanecer verde

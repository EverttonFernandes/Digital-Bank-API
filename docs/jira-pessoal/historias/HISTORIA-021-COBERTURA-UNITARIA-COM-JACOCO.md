# HISTORIA-021 - COBERTURA UNITARIA COM JACOCO

## Objetivo de Negócio

Dar visibilidade objetiva da qualidade da suíte unitária para facilitar apresentação técnica, manutenção e evolução
segura do projeto.

## Objetivo Técnico

Adicionar cobertura automatizada com `JaCoCo` ao fluxo Maven e expor o percentual consolidado ao executar `make unit-test`.

## Problema Atual

Hoje a suíte unitária informa apenas sucesso ou falha dos testes. O projeto ainda não mostra:

- percentual total de cobertura atingida
- relatório navegável de cobertura
- caminho claro para evidenciar cobertura na apresentação da entrega

## Resultado Esperado

Ao executar `make unit-test`, o projeto deve:

- rodar os testes unitários
- gerar relatório `JaCoCo`
- expor no terminal um resumo de cobertura consolidada
- manter um relatório HTML navegável para inspeção detalhada

## Critérios de Aceite

- `JaCoCo` deve estar configurado no Maven
- `make unit-test` deve gerar o relatório de cobertura automaticamente
- o comando deve exibir um resumo consolidado de cobertura no terminal
- o relatório HTML deve ficar acessível em `target/site/jacoco/index.html`
- o `README` deve explicar como gerar e consultar a cobertura
- `make unit-test` deve ficar verde
- `make functional-test` deve permanecer verde

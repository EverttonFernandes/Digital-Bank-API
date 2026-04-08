# IMPLEMENTATION — HISTORIA-021

## Contexto da História

A suíte unitária já está estável, mas o projeto ainda não oferece evidência objetiva da cobertura atingida ao executar o
fluxo padrão de testes.

## Objetivo da Entrega Atual

Adicionar cobertura unitária automatizada com `JaCoCo`, integrada ao Maven e ao `Makefile`, com resumo visível no
terminal e relatório HTML navegável.

## Referência

- [HISTORIA-021-COBERTURA-UNITARIA-COM-JACOCO.md](docs/jira-pessoal/historias/HISTORIA-021-COBERTURA-UNITARIA-COM-JACOCO.md)

## Estratégia Técnica

1. registrar a nova história no kanban
2. configurar `JaCoCo` no `pom.xml`
3. ajustar `make unit-test` para executar testes e gerar relatório
4. imprimir um resumo de cobertura consolidada no terminal
5. atualizar o `README` com instruções de uso e leitura do relatório
6. validar `make unit-test`
7. validar `make functional-test`

## Checklist de Conclusão

- [x] história criada e backlog atualizado
- [x] `JaCoCo` configurado no Maven
- [x] `make unit-test` gera relatório de cobertura
- [x] resumo de cobertura aparece no terminal
- [x] `README` atualizado
- [x] testes unitários verdes
- [x] testes funcionais verdes

## Resultado Final da História

O fluxo padrão de testes unitários passou a evidenciar cobertura de forma objetiva e reutilizável.

Convergências principais:

- `JaCoCo` integrado ao ciclo `mvn test`
- `make unit-test` passou a imprimir resumo consolidado de cobertura
- relatório HTML gerado em `target/site/jacoco/index.html`
- `README` atualizado com instruções de execução e leitura do relatório
- execução alinhada ao `Maven` local para evitar fragilidade operacional com container e volume somente leitura

Validação final:

- `make unit-test`
- `make functional-test`

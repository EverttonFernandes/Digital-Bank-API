# IMPLEMENTATION — HISTORIA-022

## Contexto da História

O projeto ja possui boa maturidade local de build, cobertura e testes. Falta agora transformar isso em validacao
automatizada no proprio GitHub, com esteiras claras de integracao e entrega de artefatos.

## Objetivo da Entrega Atual

Implementar `CI/CD` via `GitHub Actions`, mantendo o projeto aderente ao fluxo real existente: `Maven`, `PostgreSQL`,
subida da API em background e suite funcional `Jest`.

## Referência

- [HISTORIA-022-CICD-COM-GITHUB-ACTIONS.md](docs/jira-pessoal/historias/HISTORIA-022-CICD-COM-GITHUB-ACTIONS.md)

## Estratégia Técnica

1. criar a `HISTORIA-022` e registrar a fatia no kanban
2. mapear o fluxo atual de build e testes do projeto
3. criar workflow de `CI` com:
   - `checkout`
   - `Java 17`
   - `Node 20`
   - service de `PostgreSQL`
   - `mvn test`
   - subida da aplicacao
   - `npm test` em `__functional_tests__`
4. criar workflow de `CD` disparado por tags semanticas para:
   - empacotar o `jar`
   - expor artefatos de build e cobertura
   - criar release automatizada
5. atualizar o `README` com a explicacao do pipeline
6. validar localmente `make unit-test`
7. validar localmente `make functional-test`

## Checklist de Conclusão

- [x] história criada e backlog atualizado
- [x] workflow de `CI` criado
- [x] workflow de `CD` criado
- [x] pipeline alinhado a `Maven + PostgreSQL + Jest`
- [x] `README` atualizado com a esteira
- [x] testes unitários verdes
- [x] testes funcionais verdes

## Resultado Final da História

O repositorio passou a ter uma esteira explicita de `GitHub Actions`, cobrindo:

- integracao continua em `push` e `pull request`
- execucao remota das suites unitaria e funcional
- entrega automatizada de artefatos em tags semanticas
- documentacao clara do fluxo para quem avaliar o repositorio

Validação final:

- `make unit-test`
- `make functional-test`

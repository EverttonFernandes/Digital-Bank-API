# ENTREGA — HISTORIA-022

## O que foi entregue

Foi adicionada uma esteira de `CI/CD` com `GitHub Actions`, cobrindo integracao continua e entrega automatizada de
artefatos por tag semantica.

## Ganho prático

Agora o repositorio passa a demonstrar no proprio GitHub:

- validacao remota das suites de teste
- uso de `PostgreSQL` no runner para simular o ambiente real
- esteira de release por versao semantica
- artefatos entregues automaticamente junto da release

## Resultado técnico

- workflow de `CI` em `.github/workflows/ci.yml`
- workflow de `Release` em `.github/workflows/release.yml`
- pipeline alinhado a `Maven`, `Node`, `PostgreSQL` e suite funcional `Jest`
- `README` atualizado com a explicacao do fluxo

## Validação final

- `make unit-test` verde
- `make functional-test` verde

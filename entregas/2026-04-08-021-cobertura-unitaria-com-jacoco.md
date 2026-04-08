# ENTREGA — HISTORIA-021

## O que foi entregue

Foi adicionada a medição automatizada de cobertura unitária com `JaCoCo`, integrada ao `Maven` e ao fluxo padrão do
projeto via `make unit-test`.

## Ganho prático

Agora o projeto não informa apenas se a suíte unitária passou ou falhou. Ele também mostra:

- percentual consolidado de cobertura no terminal
- relatório HTML navegável para análise detalhada
- evidência objetiva de qualidade para apresentação técnica

## Resultado técnico

- `JaCoCo` configurado no `pom.xml`
- `make unit-test` executando `mvn test`
- resumo de cobertura calculado a partir do `jacoco.csv`
- relatório HTML disponível em `target/site/jacoco/index.html`

## Validação final

- `make unit-test` verde
- `make functional-test` verde
- cobertura resumida exibida no terminal

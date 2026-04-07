# Digital Bank API

## Ambiente local com Docker

Esta primeira entrega disponibiliza um ambiente mínimo para subir:

- a aplicação Spring Boot
- o PostgreSQL
- o pgAdmin
- o Swagger da API

## Stack de build

- Java 17
- Gradle
- Spring Boot
- Docker Compose

## Portas do ambiente

- aplicação: `8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- PostgreSQL: `5433`
- pgAdmin: `http://localhost:5050`

## Configuração local

As credenciais e portas do ambiente local ficam em um arquivo `.env`.

Arquivo versionado de referência:

```bash
.env.example
```

Se você ainda não tiver o `.env`, crie assim:

```bash
cp .env.example .env
```

## Comandos essenciais com Makefile

```bash
make up
```

```bash
make down
```

```bash
make logs
```

```bash
make unit-test
```

```bash
make functional-test
```

## O que cada comando faz

- `make up`
  Sobe aplicação, PostgreSQL e pgAdmin em background

- `make down`
  Derruba toda a stack do projeto

- `make logs`
  Exibe os logs agregados dos containers

- `make unit-test`
  Executa a suíte unitária completa com Gradle em container

- `make functional-test`
  Fica preparado para a suíte funcional completa com Gradle em container; neste momento ainda depende da criação da task funcional no Gradle

## Alternativa sem Makefile

Subir ambiente completo:

```bash
docker compose up --build -d
```

Derrubar ambiente:

```bash
docker compose down
```

Ver logs:

```bash
docker compose logs -f
```

Executar testes unitários:

```bash
docker run --rm -e GRADLE_USER_HOME=/tmp/gradle-home -v "$(pwd)":/workspace:ro gradle:8.14.3-jdk17 bash -lc 'cp -R /workspace /tmp/project && cd /tmp/project && gradle --no-daemon --project-cache-dir /tmp/gradle-project-cache test'
```

## Como validar rapidamente

Depois da subida:

- status da aplicação: `http://localhost:8080/api/status`
- health check: `http://localhost:8080/actuator/health`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- pgAdmin: `http://localhost:5050`

## Como ver o banco pela interface

Depois de abrir o `pgAdmin`, o servidor do projeto deve aparecer como:

- `Digital Bank PostgreSQL`

Se precisar cadastrar manualmente:

- host: `postgres` dentro do `pgAdmin`
- port: `5432`
- database: valor de `DATABASE_NAME` no `.env`
- username: valor de `DATABASE_USERNAME` no `.env`
- password: valor de `DATABASE_PASSWORD` no `.env`

## Variáveis locais usadas no ambiente

- `APPLICATION_PORT`
- `POSTGRES_PORT`
- `PGADMIN_PORT`
- `DATABASE_HOST`
- `DATABASE_PORT`
- `DATABASE_NAME`
- `DATABASE_USERNAME`
- `DATABASE_PASSWORD`
- `PGADMIN_DEFAULT_EMAIL`
- `PGADMIN_DEFAULT_PASSWORD`

## Observação

Esta etapa prepara apenas o ambiente base e o bootstrap mínimo da aplicação para suportar as próximas histórias.

# Digital Bank API

API REST simplificada para um banco digital, construída em `Java 17` com `Spring Boot`, `Gradle`, `PostgreSQL`,
`Docker Compose` e documentação `OpenAPI`.

O projeto cobre:

- criacao de contas bancarias
- consulta de contas
- transferencia entre contas
- consulta de movimentacoes financeiras
- notificacoes apos transferencia concluida
- tratamento padronizado de erros com `key` e `value`
- testes unitarios
- testes funcionais end-to-end com `Jest`

## Stack Principal

- `Java 17`
- `Spring Boot 3`
- `Gradle`
- `Spring Data JPA`
- `Flyway`
- `PostgreSQL`
- `Docker Compose`
- `springdoc-openapi`
- `JUnit 5`
- `Mockito / BDDMockito`
- `Jest`
- `supertest`
- `Sequelize`

## Como Subir o Ambiente Completo

O projeto disponibiliza um ambiente local com:

- aplicacao
- PostgreSQL
- pgAdmin
- Swagger UI

Antes da primeira subida:

```bash
cp .env.example .env
```

Subida completa:

```bash
make up
```

Derrubar tudo:

```bash
make down
```

Ver logs:

```bash
make logs
```

## Portas do Ambiente

- aplicacao: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- PostgreSQL: `localhost:5433`
- pgAdmin: `http://localhost:5050`

## Como Validar Rapidamente

Status da aplicacao:

```bash
curl -i http://localhost:8080/api/status
```

Health check:

```bash
curl -i http://localhost:8080/actuator/health
```

Swagger:

```text
http://localhost:8080/swagger-ui.html
```

## Como Executar os Testes

Suite unitária completa:

```bash
make unit-test
```

Suite funcional completa:

```bash
make functional-test
```

O fluxo funcional:

1. prepara a massa com `seeders + fixtures`
2. executa toda a suite `Jest`
3. valida endpoints e estado final da API
4. executa rollback total ao fim

## Endpoints Disponiveis

- `GET /api/status`
  Objetivo: verificar disponibilidade basica da API

- `GET /accounts`
  Objetivo: listar contas carregadas na base

- `POST /accounts`
  Objetivo: criar uma nova conta bancaria com `201 Created`, `Location` e body em `HAL`

- `GET /accounts/{accountId}`
  Objetivo: consultar uma conta especifica

- `POST /transfers`
  Objetivo: transferir saldo entre duas contas

- `GET /accounts/{accountId}/movements`
  Objetivo: consultar movimentacoes financeiras da conta

- `GET /accounts/{accountId}/notifications`
  Objetivo: consultar notificacoes registradas para a conta

## Estrutura Arquitetural

O projeto segue uma separacao pragmatica inspirada em DDD:

- `api`
  Controllers e contratos HTTP

- `application`
  Services e converters que transformam entrada em objetos de dominio

- `domain`
  Modelo central do negocio, regras e repositorios do dominio

- `infrastructure`
  Persistencia, configuracoes e adaptadores tecnicos

- `shared`
  erros padronizados e objetos compartilhados entre camadas

## Principais Decisoes Tecnicas

- `Converter antes da regra de negocio`
  A entrada HTTP e convertida antes da orquestracao principal. Isso ajuda a validar formato e a montar o objeto correto
  antes de aplicar regra de dominio.

- `Regras de negocio explicitas`
  As validacoes centrais de transferencia foram mantidas de forma clara para preservar legibilidade e previsibilidade do
  fluxo.

- `Transacao com lock pessimista`
  A transferencia usa controle transacional e lock pessimista nas contas para reduzir risco de inconsistencia em
  concorrencia.

- `Observer para notificacao`
  A notificacao pos-transferencia foi desacoplada da operacao principal para abrir caminho para futuras evolucoes como
  fila, e-mail ou SMS.

- `Erro padronizado com key e value`
  As falhas de negocio e de entrada retornam um contrato uniforme para facilitar teste, leitura e manutencao.

- `Testes funcionais com seeders e fixtures`
  A suite funcional usa massa deterministica e validacao end-to-end via API, inclusive confirmando o estado final por
  `GET` quando aplicavel.

- `Criacao de conta com semantica REST`
  A abertura de conta usa `POST /accounts`, persiste o recurso, devolve `201 Created`, header `Location` e response
  `HAL` com links navegaveis.

- `Mapeamento relacional explicito na persistencia`
  A camada JPA/Hibernate explicita as relacoes entre conta, movimentacao e notificacao sem misturar persistencia com o
  dominio.

## Estrategia de Testes

### Testes Unitarios

- usam `DisplayName` em portugues
- seguem `GIVEN / WHEN / THEN`
- usam `BDDMockito.given`
- usam `Assertions` do `JUnit`

### Testes Funcionais

- usam `Jest`
- seguem `GIVEN / WHEN / THEN`
- separam sucesso e falha em arquivos diferentes
- usam `seeders + fixtures`
- validam `status code`, payload, `key/value` e estado final

## Banco e Interface de Consulta

O banco local roda em `localhost:5433`.

Para consulta visual:

- `pgAdmin`: `http://localhost:5050`

Dentro do `pgAdmin`, o host do banco e:

- `postgres`

As credenciais do ambiente local ficam no `.env`.

## Variaveis Locais do Ambiente

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

## Observacoes Finais

Esta entrega foi organizada cronologicamente por historias versionadas com commit e tag semantica por fatia concluida.

Os documentos em `docs/tasks/` e `entregas/` registram:

- o plano de cada historia
- o progresso operacional
- o que foi entregue em linguagem tecnica e de negocio

## Evolucao Arquitetural Implementada

O projeto recebeu uma evolucao arquitetural adicional na `HISTORIA-013`.

Essa entrega introduziu:

- `Spring HATEOAS`
- responses em `HAL`
- links navegaveis entre recursos relacionados
- Swagger refletindo os DTOs e responses HATEOAS

Na pratica, a API deixou de apenas expor recursos com bons verbos HTTP e passou a guiar melhor o consumidor por meio dos
links retornados nas respostas.

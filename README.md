# Digital Bank API

API REST para operacoes centrais de um banco digital, construida com `Java 17`, `Spring Boot`, `Maven`, `PostgreSQL`, `Docker Compose` e documentacao `OpenAPI`.

O projeto foi entregue de forma incremental, com versionamento semantico por historia, foco em qualidade de codigo, testes automatizados, design de API, semantica HTTP, `HAL/HATEOAS` e resiliencia de concorrencia na transferencia entre contas.

Arquiteturalmente, a solucao segue uma leitura pragmatica inspirada em `DDD`, separando borda HTTP, orquestracao de caso de uso, dominio, persistencia e componentes compartilhados.

## Visao Geral

A API cobre:

- criacao de conta bancaria
- consulta de contas
- transferencia entre contas
- consulta de movimentacoes financeiras
- consulta de notificacoes geradas por transferencia
- tratamento padronizado de erros com `key` e `value`
- documentacao Swagger/OpenAPI ensinando como consumir a API
- testes unitarios
- testes funcionais end-to-end
- controle de concorrencia com `lock pessimista`

## Stack Principal

- `Java 17`
- `Spring Boot 3`
- `Maven`
- `Spring Data JPA`
- `Hibernate`
- `Flyway`
- `PostgreSQL`
- `Docker Compose`
- `springdoc-openapi`
- `Spring HATEOAS`
- `JUnit 5`
- `Mockito / BDDMockito`
- `Jest`
- `supertest`
- `Sequelize`

## Como Subir o Ambiente

Antes da primeira subida:

```bash
cp .env.example .env
```

Subir ambiente completo:

```bash
make up
```

Derrubar ambiente:

```bash
make down
```

Ver logs:

```bash
make logs
```

## Enderecos e Portas

- aplicacao: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- PostgreSQL: `localhost:5433`
- pgAdmin: `http://localhost:5050`

## Validacao Rapida

Status da aplicacao:

```bash
curl -i http://localhost:8080/api/status
```

Health check:

```bash
curl -i http://localhost:8080/actuator/health
```

Listar contas:

```bash
curl -i http://localhost:8080/accounts
```

Swagger:

```text
http://localhost:8080/swagger-ui.html
```

## Como Executar os Testes

Suite unitaria:

```bash
make unit-test
```

Suite funcional:

```bash
make functional-test
```

O fluxo funcional:

1. prepara a massa com `fixtures + seeders`
2. executa toda a suite `Jest`
3. valida payload, status code, contrato de erro e estado final por API
4. executa rollback total da massa de teste

## Endpoints Disponiveis

- `GET /api/status`
  Endpoint tecnico de disponibilidade da API.

- `GET /accounts`
  Lista contas bancarias disponiveis.

- `POST /accounts`
  Cria uma conta bancaria com `201 Created`, header `Location` e body em `HAL`.

- `GET /accounts/{accountId}`
  Consulta uma conta especifica.

- `GET /accounts/{accountId}/movements`
  Consulta movimentacoes financeiras da conta.

- `GET /accounts/{accountId}/notifications`
  Consulta notificacoes registradas para a conta.

- `POST /transfers`
  Executa transferencia entre contas com protecao transacional e resiliencia de concorrencia.

## Contrato da API

### Estilo de resposta

- recursos principais expostos com semantica HTTP coerente
- responses navegaveis com `HAL`
- links HATEOAS para guiar o proximo passo do consumidor
- erros padronizados com `key` e `value`

### Exemplo de jornada de uso

1. criar conta em `POST /accounts`
2. consultar a conta criada em `GET /accounts/{accountId}`
3. transferir saldo via `POST /transfers`
4. consultar movimentacoes em `GET /accounts/{accountId}/movements`
5. consultar notificacoes em `GET /accounts/{accountId}/notifications`

## Estrutura Arquitetural

O projeto segue uma separacao pragmatica em camadas:

- `api`
  contratos HTTP, controllers, assemblers e representations

- `application`
  services, DTOs e converters que transformam entrada em objetos de dominio

- `domain`
  entidades centrais do negocio, regras, especificacoes e contratos de repositorio

- `infrastructure`
  persistencia, JPA/Hibernate, configuracoes, Flyway e OpenAPI

- `shared`
  contratos de erro, respostas compartilhadas e excecoes reutilizaveis

## Entregas Por Historia

Cada entrega relevante ficou registrada em `entregas/`:

- [HISTORIA-001 - Ambiente Docker e PostgreSQL](entregas/2026-04-06-001-ambiente-docker-e-postgresql.md)
- [HISTORIA-002 - Estrutura base da aplicacao](entregas/2026-04-07-002-estrutura-base-da-aplicacao.md)
- [HISTORIA-003 - Gestao basica de contas](entregas/2026-04-07-003-gestao-basica-de-contas.md)
- [HISTORIA-004 - Transferencia entre contas](entregas/2026-04-07-004-transferencia-entre-contas.md)
- [HISTORIA-005 - Movimentacoes financeiras](entregas/2026-04-07-005-movimentacoes-financeiras.md)
- [HISTORIA-006 - Notificacao pos-transferencia](entregas/2026-04-07-006-notificacao-pos-transferencia.md)
- [HISTORIA-007 - Tratamento de erros e mensagens](entregas/2026-04-07-007-tratamento-de-erros-e-mensagens.md)
- [HISTORIA-008 - Swagger / OpenAPI](entregas/2026-04-07-008-swagger-openapi.md)
- [HISTORIA-009 - Testes unitarios](entregas/2026-04-07-009-testes-unitarios.md)
- [HISTORIA-010 - Testes funcionais](entregas/2026-04-07-010-testes-funcionais.md)
- [HISTORIA-011 - README e decisoes tecnicas](entregas/2026-04-07-011-readme-e-decisoes-tecnicas.md)
- [HISTORIA-012 - Fechamento da entrega e versao](entregas/2026-04-07-012-fechamento-da-entrega-e-versao.md)
- [HISTORIA-013 - Maturidade de Richardson e semantica REST](entregas/2026-04-07-013-maturidade-de-richardson-e-semantica-rest.md)
- [HISTORIA-014 - Criacao de conta bancaria](entregas/2026-04-08-014-criacao-de-conta-bancaria.md)
- [HISTORIA-015 - Mapeamento relacional JPA/Hibernate](entregas/2026-04-08-015-mapeamento-relacional-jpa-hibernate.md)
- [HISTORIA-016 - Alinhamento de padrao de codigo em camadas](entregas/2026-04-07-016-alinhamento-de-padrao-de-codigo-em-camadas.md)
- [HISTORIA-017 - Unificacao de dominio e persistencia JPA](entregas/2026-04-07-017-unificacao-de-dominio-e-persistencia-jpa.md)
- [HISTORIA-018 - Padronizacao de nomenclatura arquitetural](entregas/2026-04-07-018-padronizacao-de-nomenclatura-arquitetural.md)
- [HISTORIA-019 - Experiencia de uso do Swagger e contrato OpenAPI](entregas/2026-04-08-019-experiencia-de-uso-do-swagger-e-contrato-openapi.md)
- [HISTORIA-020 - Resiliencia de concorrencia na transferencia](entregas/2026-04-08-020-resiliencia-de-concorrencia-na-transferencia.md)

## Decisoes Tecnicas Relevantes

### Arquitetura e Engenharia de Software

- inspiracao pragmatica em `DDD`, com separacao clara entre camadas e responsabilidades
- separacao clara entre `api`, `application`, `domain`, `infrastructure` e `shared`
- services como orquestradores de caso de uso, mantendo controllers finos
- converters para transformar `DTO -> dominio` antes da aplicacao das regras
- dominio com regras explicitas e objetos centrados no negocio
- repositorios com contrato de dominio e implementacao de persistencia desacoplada

### Persistencia e Banco

- `Spring Data JPA + Hibernate` como base de persistencia relacional
- `Flyway` para versionamento de schema e carga inicial estavel
- `PostgreSQL` como banco relacional por maturidade, confiabilidade transacional, suporte forte a locking e boa aderencia a cenarios de consistencia financeira
- mapeamento relacional explicito entre conta, movimentacao e notificacao
- repositorios preparados para consultas de leitura e gravacao com foco no caso de uso

### Concorrencia, Consistencia e Resiliencia

- transferencia executada dentro de transacao
- uso de `lock pessimista` nas contas envolvidas
- ordenacao deterministica das contas bloqueadas para reduzir risco de deadlock
- timeout de lock configurado para evitar espera indefinida
- tratamento semantico de contencao com `409 Conflict`
- mensagem intuitiva ao cliente em caso de recurso temporariamente ocupado
- uso de `Observer` no fluxo de notificacao para desacoplar a acao principal da transferencia e manter a arquitetura pronta para evolucoes como `SQS`, `SMS`, e-mail ou outros canais assincronos

### Design de API

- design orientado a recurso
- semantica correta de `GET` e `POST`
- responses em `HAL` com `Spring HATEOAS`
- discoverability via links navegaveis
- Swagger refletindo request DTOs, response models e erros esperados
- foco em UX da API: a propria resposta ajuda o consumidor a saber o proximo passo

### Boas Praticas de Codigo

- padronizacao de nomenclatura arquitetural
- contratos HTTP, DTOs, converters e repositorios com responsabilidade clara
- tratamento global de excecao para padronizar a borda HTTP
- eliminacao de redundancias estruturais quando possivel
- refatoracoes guiadas por comportamento preservado

### Testes Unitarios

- `JUnit 5`
- `Mockito / BDDMockito`
- estrutura `GIVEN / WHEN / THEN`
- foco em services, converters, specifications, handlers e repositorios
- cobertura de cenarios positivos e de falha

### Testes Funcionais

- `Jest + supertest + Sequelize`
- seed controlado no inicio da suite
- rollback total ao final da suite
- massa de dados deterministica baseada em `fixtures` e `seeders`, permitindo reproduzir cenarios de sucesso, falha, consulta e concorrencia com previsibilidade
- cobertura de sucesso e falha por endpoint
- validacao de payload, status code, regras de negocio e estado final da base
- testes especificos para concorrencia na transferencia

## Banco de Dados e Consulta Visual

O banco local roda em:

- host: `localhost`
- porta: `5433`

Interface de consulta visual:

- `pgAdmin`: `http://localhost:5050`

Dentro do `pgAdmin`, o host do banco e:

- `postgres`

As credenciais locais ficam em `.env`.

## Variaveis de Ambiente

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

## Operacao Local

Arquivos principais de operacao ficam na raiz do projeto:

- `Dockerfile`
- `docker-compose.yml`
- `pom.xml`
- `Makefile`
- `README.md`
- `.env.example`

Arquivos auxiliares do ecossistema Docker ficam em `docker/`.

## Fechamento

Este projeto foi construido com foco em:

- qualidade tecnica
- clareza arquitetural
- design de API
- resiliencia de concorrencia
- testabilidade
- rastreabilidade de entregas

O resultado final nao e apenas uma API que funciona. E uma API com contrato bem documentado, fluxo de uso ensinavel pelo Swagger, comportamento validado por testes automatizados e decisoes tecnicas defensaveis em uma apresentacao tecnica.

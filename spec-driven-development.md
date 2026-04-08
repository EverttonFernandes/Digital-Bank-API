# Spec — Desafio Técnico Sabi

## 1. Visão Geral

Construir uma API REST simplificada para um banco digital, com foco principal em:

* gestão básica de contas
* transferência de valores entre contas
* consulta de movimentações financeiras
* consistência de saldo
* resiliência em cenário de concorrência
* documentação clara
* ambiente fácil de subir e validar

A solução será implementada em **Java + Spring Boot**, com **PostgreSQL**, documentação via **Swagger/OpenAPI**, ambiente via **Docker Compose**, testes unitários em **Java**, e testes funcionais/BDD em **JavaScript com Jest**, por decisão pragmática de velocidade e familiaridade.

---

## 2. Objetivo do Desafio

Entregar uma API simples de banco digital que demonstre:

* clareza arquitetural
* boas práticas de engenharia de software
* separação de responsabilidades
* consistência transacional
* tratamento de concorrência
* documentação técnica madura
* facilidade de execução pelo avaliador

---

## 3. Escopo Funcional

### 3.1 Gestão de contas

O sistema deve possuir uma base pré-carregada de contas para facilitar validação e testes.

Opcionalmente, pode permitir cadastro básico de conta.

Cada conta deve possuir:

* `id`
* `ownerName`
* `balance`
* `createdAt`
* `updatedAt`

### 3.2 Transferência de fundos

O sistema deve permitir a transferência de dinheiro entre duas contas.

Regras mínimas:

* conta de origem deve existir
* conta de destino deve existir
* origem e destino não podem ser a mesma conta
* valor deve ser maior que zero
* saldo da conta de origem deve ser suficiente

### 3.3 Consulta de movimentações

O sistema deve permitir consultar movimentações financeiras associadas a uma conta.

### 3.4 Notificação

Após uma transferência concluída com sucesso, o sistema deve registrar/envio de notificação ao cliente.

Para o escopo do desafio, a notificação poderá ser simulada por:

* log estruturado
* persistência em tabela de notificação
* evento interno da aplicação com listener

---

## 4. Escopo Não Funcional

### Obrigatórios

* testes unitários
* Swagger/OpenAPI
* README explicando execução e decisões de arquitetura

### Diferenciais que serão implementados

* Docker Compose para ambiente completo
* seed de dados inicial
* tratamento global de exceções
* controle de concorrência com lock pessimista
* testes funcionais em Jest
* separação por camadas inspirada em DDD
* ADRs documentando decisões técnicas

### Evolucao arquitetural planejada apos a entrega principal

* revisao dos endpoints e contratos para aderencia mais forte ao modelo de maturidade de Richardson
* avaliacao de links de hipermidia nas respostas para aproximar a API do nivel 3
* uso de `Spring HATEOAS` para transformar a resposta da API em mecanismo real de navegacao entre recursos
* refinamento da documentacao Swagger para refletir fielmente DTOs, responses e estruturas HATEOAS
* criacao de conta bancaria por `POST /accounts` para completar o ciclo basico de vida da conta no dominio
* refinamento do mapeamento relacional JPA/Hibernate na camada de persistencia, preservando a separacao entre dominio e infraestrutura e mantendo todos os testes funcionais verdes

---

## 5. Stack Tecnológica

### Aplicação principal

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Validation
* Springdoc OpenAPI
* Maven

### Persistência

* PostgreSQL
* Flyway para migrations e seed inicial

### Testes Java

* JUnit 5
* Mockito
* Spring Boot Test
* MockMvc

### Testes funcionais / BDD

* Node.js
* Jest
* Supertest ou Axios para chamadas HTTP
* fixtures em JSON/JS
* seeders auxiliares em JavaScript para setup/cleanup de cenários de teste

### Infra

* Docker
* Docker Compose
* Makefile opcional

---

## 6. Estratégia Arquitetural

A solução seguirá uma arquitetura em camadas inspirada em DDD tático, sem overengineering.

### Camadas

#### `api`

Responsável por:

* controllers REST
* request DTOs
* response DTOs
* documentação OpenAPI
* validação de entrada
* exception handlers

#### `application`

Responsável por:

* casos de uso
* orquestração de fluxo
* coordenação entre domínio e infraestrutura

#### `domain`

Responsável por:

* entidades de negócio
* regras de negócio
* contratos de repositório
* exceções de domínio
* serviços de domínio, se necessário

#### `infrastructure`

Responsável por:

* implementação de repositórios JPA
* entidades de persistência, se houver separação
* integração com banco
* seeders / bootstrap
* listener de notificação
* configurações gerais

---

## 7. Decisões Arquiteturais (ADRs)

### ADR-001 — Banco relacional PostgreSQL

**Decisão**: usar PostgreSQL.

**Motivo**:

* aderência natural a cenário financeiro
* suporte robusto a transações
* integridade e consistência
* fácil uso com Docker

### ADR-002 — Controle de concorrência com lock pessimista

**Decisão**: usar lock pessimista no carregamento das contas envolvidas na transferência.

**Motivo**:

* reduzir risco de race condition
* evitar saldo inconsistente em cenário concorrente
* simplificar defesa técnica da solução

### ADR-003 — Organização em camadas inspiradas em DDD

**Decisão**: separar API, aplicação, domínio e infraestrutura.

**Motivo**:

* clareza de responsabilidade
* melhor testabilidade
* manutenção mais simples
* estrutura profissional sem complexidade excessiva

### ADR-004 — Notificação desacoplada do fluxo principal

**Decisão**: disparar notificação após sucesso da transferência por meio de evento interno ou serviço desacoplado.

**Motivo**:

* reduzir acoplamento
* manter caso de uso mais limpo
* permitir futura evolução sem impacto direto no core

### ADR-005 — Testes funcionais em Jest

**Decisão**: utilizar Jest para testes funcionais/BDD e seeders auxiliares de cenários.

**Motivo**:

* ganho de velocidade de implementação
* familiaridade prática do autor
* foco em cenários HTTP black-box
* manutenção dos testes unitários principais no ecossistema Java, preservando aderência à stack base

### ADR-006 — Ambiente completo via Docker Compose

**Decisão**: subir aplicação e banco com Docker Compose.

**Motivo**:

* simplificar validação pelo avaliador
* evitar instalação manual de banco
* padronizar ambiente de execução

### ADR-007 — Evolucao para maior maturidade REST

**Decisao**: registrar uma historia especifica para revisar a API sob a lente do modelo de maturidade de Richardson.

**Motivo**:

* tornar explicito o nivel de maturidade REST atingido hoje
* abrir caminho para contratos mais orientados a recurso
* permitir evolucao futura com hipermidia e navegacao por links
* usar `Spring HATEOAS` para representar essa navegacao de forma padronizada e aderente ao ecossistema Spring

---

## 8. Modelo de Domínio

### 8.1 Account

Representa a conta bancária.

**Atributos**:

* `id: Long`
* `ownerName: String`
* `balance: BigDecimal`
* `createdAt: OffsetDateTime`
* `updatedAt: OffsetDateTime`

**Regras**:

* saldo não pode ser negativo ao final de uma operação válida
* nome do titular não pode ser vazio

### 8.2 Transfer

Representa a transferência entre contas.

**Atributos**:

* `id: Long`
* `sourceAccountId: Long`
* `targetAccountId: Long`
* `amount: BigDecimal`
* `status: TransferStatus`
* `createdAt: OffsetDateTime`

**Status possíveis**:

* `COMPLETED`
* `FAILED` (opcional)

### 8.3 Movement

Representa um lançamento financeiro ligado a uma conta.

**Atributos**:

* `id: Long`
* `accountId: Long`
* `transferId: Long`
* `type: MovementType` (`DEBIT`, `CREDIT`)
* `amount: BigDecimal`
* `description: String`
* `createdAt: OffsetDateTime`

### 8.4 NotificationLog

Representa o registro de uma notificação gerada após transferência.

**Atributos**:

* `id: Long`
* `transferId: Long`
* `recipient: String`
* `message: String`
* `status: String`
* `sentAt: OffsetDateTime`

---

## 9. Regras de Negócio

### 9.1 Regras da transferência

1. conta de origem deve existir
2. conta de destino deve existir
3. origem e destino devem ser diferentes
4. valor deve ser maior que zero
5. saldo da origem deve ser suficiente
6. operação deve ser transacional
7. operação deve gerar movimentação de débito para origem
8. operação deve gerar movimentação de crédito para destino
9. operação bem-sucedida deve gerar notificação

### 9.2 Regras de consulta de movimentação

1. conta deve existir
2. retornos devem refletir transferências já concluídas
3. idealmente suportar ordenação por data decrescente

---

## 10. Estratégia de Consistência e Concorrência

Esse é o ponto mais importante da solução.

### Estratégia adotada

Durante a transferência:

1. abrir transação
2. carregar contas de origem e destino com lock pessimista
3. validar regras de negócio
4. debitar origem
5. creditar destino
6. persistir transferência
7. persistir movimentações
8. finalizar transação
9. disparar notificação após sucesso

### Justificativa

Com isso, evita-se que duas transferências simultâneas usem o mesmo saldo sem coordenação, reduzindo risco de inconsistência.

### Observação

Para evitar deadlock em cenários mais elaborados, a implementação pode carregar as contas em ordem determinística por ID antes de aplicar a lógica.

---

## 11. Contratos da API

### 11.1 Listar contas

**GET** `/accounts`

**Resposta 200**

```json
[
  {
    "id": 1,
    "ownerName": "João Silva",
    "balance": 1000.00
  }
]
```

### 11.2 Buscar conta por ID

**GET** `/accounts/{id}`

**Resposta 200**

```json
{
  "id": 1,
  "ownerName": "João Silva",
  "balance": 1000.00
}
```

### 11.3 Criar conta (opcional)

**POST** `/accounts`

**Request**

```json
{
  "ownerName": "Carlos Souza",
  "initialBalance": 500.00
}
```

**Resposta 201**

```json
{
  "id": 4,
  "ownerName": "Carlos Souza",
  "balance": 500.00
}
```

### 11.4 Transferir fundos

**POST** `/transfers`

**Request**

```json
{
  "sourceAccountId": 1,
  "targetAccountId": 2,
  "amount": 150.00
}
```

**Resposta 201**

```json
{
  "transferId": 10,
  "status": "COMPLETED",
  "sourceAccountId": 1,
  "targetAccountId": 2,
  "amount": 150.00,
  "createdAt": "2026-04-06T20:00:00Z"
}
```

### 11.5 Consultar movimentações

**GET** `/accounts/{id}/movements`

**Resposta 200**

```json
[
  {
    "id": 100,
    "transferId": 10,
    "type": "DEBIT",
    "amount": 150.00,
    "description": "Transferência enviada",
    "createdAt": "2026-04-06T20:00:00Z"
  },
  {
    "id": 101,
    "transferId": 10,
    "type": "CREDIT",
    "amount": 150.00,
    "description": "Transferência recebida",
    "createdAt": "2026-04-06T20:00:00Z"
  }
]
```

---

## 12. DTOs

### 12.1 CreateAccountRequest

```json
{
  "ownerName": "Carlos Souza",
  "initialBalance": 500.00
}
```

Campos:

* `ownerName`: nome do titular da conta
* `initialBalance`: saldo inicial da conta

### 12.2 TransferRequest

```json
{
  "sourceAccountId": 1,
  "targetAccountId": 2,
  "amount": 150.00
}
```

Campos:

* `sourceAccountId`: ID da conta de origem
* `targetAccountId`: ID da conta de destino
* `amount`: valor da transferência

### 12.3 ErrorResponse

```json
{
  "timestamp": "2026-04-06T20:00:00Z",
  "status": 409,
  "error": "Business rule violation",
  "message": "Insufficient balance",
  "path": "/transfers"
}
```

---

## 13. Estratégia de Resposta HTTP

### Sucesso

* `200 OK` para consultas
* `201 Created` para criação de conta e transferência

### Erros

* `400 Bad Request` para payload inválido ou campos obrigatórios ausentes
* `404 Not Found` para conta inexistente
* `409 Conflict` para regra de negócio violada, como saldo insuficiente ou conta origem igual à destino

---

## 14. Tratamento de Exceções

Será implementado um `GlobalExceptionHandler` para centralizar respostas de erro.

### Exceções previstas

* `AccountNotFoundException`
* `InsufficientBalanceException`
* `InvalidTransferException`
* `DuplicateAccountException` (se aplicável)
* `MethodArgumentNotValidException`
* exceções genéricas não tratadas

Objetivo:

* respostas padronizadas
* mensagens claras
* boa experiência de consumo da API

---

## 15. Persistência e Migrations

### Estratégia

* usar Flyway para versionar schema
* criar scripts para tabelas principais
* popular contas iniciais via migration SQL ou bootstrap controlado

### Tabelas previstas

* `accounts`
* `transfers`
* `movements`
* `notification_logs`

### Seed inicial sugerido

* Conta 1 — João Silva — saldo 1000.00
* Conta 2 — Maria Souza — saldo 500.00
* Conta 3 — Ana Lima — saldo 2000.00

Esses dados permitem testes rápidos e previsíveis.

---

## 16. Estratégia de Testes

## 16.1 Testes unitários em Java

Cobrir:

* transferência com sucesso
* saldo insuficiente
* valor inválido
* origem igual ao destino
* conta inexistente
* geração de movimentações
* disparo de notificação

Ferramentas:

* JUnit 5
* Mockito

## 16.2 Testes de integração em Java

Cobrir:

* endpoint de transferência com persistência real
* atualização correta de saldo
* criação de movimentações
* mapeamento de status HTTP

Ferramentas:

* Spring Boot Test
* MockMvc
* banco de teste / perfil específico

## 16.3 Testes funcionais / BDD em Jest

Cobrir:

* listagem de contas pré-carregadas
* transferência com sucesso
* falha por saldo insuficiente
* falha por conta inexistente
* falha por valor inválido
* consulta de movimentações após transferência

Características:

* testes black-box por HTTP
* fixtures e seeders auxiliares em JavaScript
* setup e teardown independentes
* foco em comportamento e legibilidade

## 16.4 Teste de concorrência

Implementar ao menos um teste controlado simulando múltiplas transferências simultâneas contra a mesma conta de origem.

Objetivo:

* provar consistência de saldo
* demonstrar maturidade técnica

---

## 17. Estratégia de Seeders e Fixtures

### Seed inicial da aplicação

Responsável por disponibilizar massa base para uso imediato do avaliador.

### Seeders auxiliares de testes funcionais

Serão implementados em JavaScript para:

* preparar cenários específicos
* resetar estado entre execuções
* facilitar validação dos cenários BDD

### Fixtures

Arquivos ou objetos reutilizáveis com:

* payloads válidos
* payloads inválidos
* massa para contas
* massas para transferências e cenários de erro

---

## 18. Estratégia de Notificação

### Abordagem inicial

Após transferência concluída com sucesso, a aplicação deve registrar uma notificação.

### Implementação sugerida

Opção preferencial:

* publicar evento interno `TransferCompletedEvent`
* listener registrar `NotificationLog`
* opcionalmente emitir log estruturado

### Justificativa

* mantém caso de uso limpo
* permite desacoplamento
* atende requisito sem exagerar em infraestrutura

---

## 19. Swagger / OpenAPI

A aplicação terá documentação automática com Springdoc.

### Requisitos da documentação

* endpoints descritos
* campos dos DTOs documentados
* exemplos de request e response
* respostas de erro principais
* títulos e descrições didáticas

### Caminho sugerido

* `/swagger-ui/index.html`
* `/v3/api-docs`

Obs.: Swagger ficará na mesma porta da API para manter simplicidade operacional.

---

## 20. Containerização

## 20.1 Objetivo

Permitir que o avaliador suba todo o ambiente com o menor atrito possível.

## 20.2 Componentes

* `app`
* `postgres`

## 20.3 Execução

Comando principal:

```bash
Docker compose up --build
```

Opcional com Makefile:

```bash
make up
make down
make test
```

## 20.4 Resultado esperado

Ao subir o ambiente:

* banco estará disponível
* aplicação estará disponível
* migrations terão sido executadas
* seed inicial estará carregado
* Swagger estará acessível

---

## 21. Observabilidade mínima

Não haverá stack completa de observabilidade, mas a aplicação deve possuir:

* logs claros de startup
* logs claros de transferência bem-sucedida
* logs claros de falha de regra de negócio
* logs claros de notificação gerada

Opcional:

* Spring Boot Actuator com healthcheck

---

## 22. Estrutura de Pacotes Sugerida

```text
com.sabi.digitalbank
  api
    controller
    dto
    handler
    documentation
  application
    usecase
    service
  domain
    model
    exception
    repository
    service
  infrastructure
    persistence
      entity
      repository
    notification
    config
    seeder
```

---

## 23. Plano de Implementação em Etapas

## Etapa 1 — Planejamento e especificação

### Objetivo

Definir toda a base funcional e técnica antes da codificação.

### Tarefas

* [ ] validar escopo final do desafio
* [ ] definir entidades principais
* [ ] definir regras de negócio
* [ ] definir contratos REST
* [ ] definir payloads
* [ ] definir estratégia de concorrência
* [ ] definir ADRs principais
* [ ] definir backlog técnico

### Critério de saída

* spec.md concluído
* endpoints claros
* regras claras
* decisões arquiteturais documentadas

---

## Etapa 2 — Bootstrap do projeto

### Objetivo

Subir a base técnica da aplicação.

### Tarefas

* [ ] criar projeto Spring Boot
* [ ] configurar Maven
* [ ] configurar Java 17
* [ ] adicionar dependências principais
* [ ] configurar profiles da aplicação
* [ ] configurar PostgreSQL
* [ ] configurar Docker Compose
* [ ] validar subida local

### Critério de saída

* aplicação sobe localmente
* banco conecta corretamente

---

## Etapa 3 — Migrations e modelo de dados

### Objetivo

Estruturar persistência com versionamento.

### Tarefas

* [ ] criar migration inicial de `accounts`
* [ ] criar migration de `transfers`
* [ ] criar migration de `movements`
* [ ] criar migration de `notification_logs`
* [ ] criar seed inicial de contas
* [ ] validar schema no banco

### Critério de saída

* schema criado automaticamente
* seed inicial carregado com sucesso

---

## Etapa 4 — Modelagem do domínio

### Objetivo

Implementar entidades e regras centrais.

### Tarefas

* [ ] modelar `Account`
* [ ] modelar `Transfer`
* [ ] modelar `Movement`
* [ ] modelar exceções de domínio
* [ ] definir contratos de repositório
* [ ] validar regras básicas de domínio

### Critério de saída

* domínio modelado e coeso
* invariantes definidas

---

## Etapa 5 — Caso de uso de transferência

### Objetivo

Implementar fluxo principal da aplicação.

### Tarefas

* [ ] implementar `TransferFundsUseCase`
* [ ] buscar contas com lock pessimista
* [ ] validar saldo e regras
* [ ] realizar débito e crédito
* [ ] persistir transferência
* [ ] persistir movimentações
* [ ] publicar notificação após sucesso

### Critério de saída

* transferência funcional de ponta a ponta
* saldo consistente
* movimentações geradas

---

## Etapa 6 — APIs REST

### Objetivo

Expor contratos HTTP.

### Tarefas

* [ ] implementar `AccountController`
* [ ] implementar `TransferController`
* [ ] implementar `MovementController`
* [ ] criar DTOs de request/response
* [ ] adicionar validações
* [ ] mapear status HTTP
* [ ] implementar handler global de exceções

### Critério de saída

* endpoints acessíveis
* respostas consistentes
* erros padronizados

---

## Etapa 7 — Swagger / documentação da API

### Objetivo

Documentar consumo da API.

### Tarefas

* [ ] configurar Springdoc
* [ ] documentar endpoints
* [ ] documentar DTOs
* [ ] adicionar exemplos de payload
* [ ] documentar respostas de erro

### Critério de saída

* Swagger acessível e didático

---

## Etapa 8 — Testes unitários em Java

### Objetivo

Cobrir regras de negócio críticas.

### Tarefas

* [ ] testar transferência com sucesso
* [ ] testar saldo insuficiente
* [ ] testar conta inexistente
* [ ] testar origem igual destino
* [ ] testar valor inválido
* [ ] testar geração de notificação

### Critério de saída

* regras críticas cobertas
* feedback rápido de regressão

---

## Etapa 9 — Testes de integração em Java

### Objetivo

Validar integração da aplicação com persistência e HTTP.

### Tarefas

* [ ] testar endpoint de listagem de contas
* [ ] testar endpoint de transferência
* [ ] testar persistência de movimentações
* [ ] testar respostas HTTP em casos de erro

### Critério de saída

* fluxos principais validados com stack Java

---

## Etapa 10 — Testes funcionais / BDD em Jest

### Objetivo

Validar comportamento fim a fim como consumidor externo da API.

### Tarefas

* [ ] estruturar projeto de testes Node/Jest
* [ ] criar fixtures reutilizáveis
* [ ] criar seeders auxiliares
* [ ] implementar cenário de listagem de contas
* [ ] implementar cenário de transferência com sucesso
* [ ] implementar cenário de saldo insuficiente
* [ ] implementar cenário de conta inexistente
* [ ] implementar cenário de consulta de movimentações
* [ ] implementar setup e teardown independentes

### Critério de saída

* suíte funcional automatizada
* cenários legíveis e estáveis

---

## Etapa 11 — Teste de concorrência

### Objetivo

Demonstrar consistência em cenário simultâneo.

### Tarefas

* [ ] criar cenário com transferências paralelas
* [ ] validar comportamento final do saldo
* [ ] documentar abordagem no README

### Critério de saída

* evidência de proteção contra inconsistência de saldo

---

## Etapa 12 — Docker e experiência do avaliador

### Objetivo

Facilitar execução e validação.

### Tarefas

* [ ] finalizar Dockerfile da aplicação
* [ ] finalizar docker-compose
* [ ] testar subida limpa do ambiente
* [ ] validar Swagger via container
* [ ] validar seed inicial via container
* [ ] validar testes com ambiente dockerizado, se aplicável

### Critério de saída

* ambiente sobe com comando único

---

## Etapa 13 — README final

### Objetivo

Explicar claramente como executar e por que a solução foi desenhada assim.

### Tarefas

* [ ] explicar contexto do projeto
* [ ] explicar stack utilizada
* [ ] explicar como subir localmente
* [ ] explicar como rodar com Docker
* [ ] explicar endpoints principais
* [ ] explicar Swagger
* [ ] explicar decisões de arquitetura
* [ ] explicar concorrência e consistência
* [ ] explicar estratégia de testes
* [ ] explicar trade-offs e próximos passos

### Critério de saída

* README claro, profissional e suficiente para avaliação autônoma

---

## 24. Backlog Técnico Prioritário

### Must have

* [ ] contas pré-carregadas
* [ ] transferência entre contas
* [ ] movimentações
* [ ] notificação simulada
* [ ] testes unitários
* [ ] Swagger
* [ ] README
* [ ] Docker Compose

### Should have

* [ ] tratamento global de exceções
* [ ] seed previsível
* [ ] testes funcionais em Jest
* [ ] teste de concorrência

### Nice to have

* [ ] Makefile
* [ ] Actuator healthcheck
* [ ] paginação de movimentações
* [ ] collection Postman

---

## 25. Cenários BDD de Referência

### Cenário 1 — Transferência com sucesso

**Dado** que existe uma conta de origem com saldo suficiente
**E** existe uma conta de destino válida
**Quando** uma transferência válida for solicitada
**Então** o saldo da origem deve ser debitado
**E** o saldo da conta de destino deve ser creditado
**E** a transferência deve ser registrada
**E** as movimentações devem ser geradas
**E** a notificação deve ser registrada

### Cenário 2 — Saldo insuficiente

**Dado** que existe uma conta de origem com saldo insuficiente
**Quando** uma transferência acima do saldo for solicitada
**Então** a API deve responder com conflito de regra de negócio
**E** nenhum saldo deve ser alterado

### Cenário 3 — Conta inexistente

**Dado** que a conta de destino não existe
**Quando** uma transferência for solicitada
**Então** a API deve responder com recurso não encontrado

### Cenário 4 — Valor inválido

**Dado** que o valor da transferência é zero ou negativo
**Quando** a requisição for enviada
**Então** a API deve responder com erro de validação

### Cenário 5 — Consulta de movimentações

**Dado** que já ocorreu ao menos uma transferência com sucesso
**Quando** as movimentações da conta forem consultadas
**Então** a API deve retornar os lançamentos correspondentes

### Cenário 6 — Concorrência

**Dado** múltiplas requisições simultâneas usando a mesma conta de origem
**Quando** as transferências forem processadas em paralelo
**Então** o saldo final não deve ficar inconsistente

---

## 26. Trade-offs Assumidos

* não será implementado um sistema real de autenticação, pois não foi exigido
* não será utilizado broker externo de mensageria, para manter simplicidade
* notificação será simulada de forma interna e auditável
* Swagger ficará na mesma porta da API por simplicidade operacional
* Jest será utilizado apenas para testes funcionais/BDD e seeders auxiliares, não para a aplicação principal

---

## 27. Próximos Passos Possíveis Pós-Desafio

Se houvesse evolução futura do projeto, os próximos passos naturais seriam:

* autenticação/autorização
* idempotência em transferência
* limites por conta
* paginação e filtros de movimentação
* auditoria mais detalhada
* fila real para notificação
* observabilidade com métricas e tracing

---

## 28. Definição de Pronto

A implementação será considerada pronta quando:

* API estiver funcional com os endpoints principais
* transferência ocorrer com consistência
* movimentações forem registradas
* notificação for gerada após sucesso
* Swagger estiver acessível e claro
* ambiente subir com Docker Compose
* testes unitários estiverem implementados
* testes funcionais em Jest estiverem executando cenários principais
* README explicar execução e decisões técnicas

---

## 29. Estratégia de Execução com Ralph-Loop

Este spec deverá ser usado como base para implementação incremental em ciclos curtos.

### Modo sugerido de execução

1. ler o spec completo
2. atacar uma etapa por vez
3. implementar apenas o escopo da etapa atual
4. validar testes da etapa
5. documentar o que foi concluído
6. seguir para próxima etapa

### Regra operacional

Cada tarefa do backlog deve gerar evidência concreta:

* código
* teste
* documentação
* decisão técnica registrada

### Resultado esperado

Menor ambiguidade possível, maior previsibilidade possível e defesa técnica clara de cada decisão adotada.

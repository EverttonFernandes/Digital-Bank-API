# Kanban da Entrega

## Objetivo

Este arquivo funciona como o quadro principal de acompanhamento do projeto.

Ele organiza, em ordem cronológica, as histórias necessárias para transformar a spec do desafio em entregas reais, controladas e rastreáveis.

O guardião desta organização é o subagente `ralph-loop/product-manager`.

## Regras do Kanban

- a ordem das histórias deve refletir a sequência natural de entrega
- toda história deve ter objetivo de negócio e objetivo técnico
- histórias funcionais, técnicas e não funcionais devem coexistir no mesmo fluxo
- uma história só avança quando seu `IMPLEMENTATION.md` existir
- uma história só é considerada entregue quando existir documentação em `entregas/`
- nenhuma história principal pode começar fora da ordem cronológica
- a próxima história oficial é sempre a primeira ainda não concluída
- uma história só pode entrar em `Done` se estiver comprovadamente funcionando

## Épico Principal

- `EPICO-001` Construção da API REST de banco digital com foco em consistência, concorrência, documentação e testabilidade

## Backlog Cronológico

### To Do

- nenhuma história pendente no momento

### Doing

- nenhuma história em andamento

### Review

- nenhuma história em revisão

### Done

- `HISTORIA-001` Subir ambiente isolado com Docker Compose, aplicação, Swagger e PostgreSQL em portas distintas
- `HISTORIA-002` Estruturar projeto Spring Boot, camadas arquiteturais e configurações base
- `HISTORIA-003` Criar modelo de contas com seed inicial e consultas de listagem/busca
- `HISTORIA-004` Implementar transferência entre contas com consistência transacional, lock pessimista e setup inicial dos testes funcionais em Jest
- `HISTORIA-005` Gerar movimentações financeiras associadas à transferência e expor histórico por conta
- `HISTORIA-006` Registrar notificações pós-transferência com observer explícito e consulta por conta
- `HISTORIA-007` Padronizar tratamento de erros e mensagens key/value para regras de negócio e payload inválido
- `HISTORIA-008` Publicar Swagger/OpenAPI com contratos claros dos endpoints e respostas principais
- `HISTORIA-009` Consolidar suíte de testes unitários com cobertura de converters, services, specifications e domínio
- `HISTORIA-010` Consolidar e ampliar a suite de testes funcionais com seeders, fixtures e heuristica VADER
- `HISTORIA-011` Consolidar README com execucao, arquitetura e decisoes tecnicas
- `HISTORIA-012` Revisar entrega final, documentacao cronologica e fechamento semantico da versao
- `HISTORIA-013` Refatorar contratos e respostas para elevar a API ao topo do modelo de maturidade de Richardson
- `HISTORIA-014` Expor `POST /accounts` para criacao de conta bancaria com validacao, HATEOAS e persistencia real
- `HISTORIA-015` Refatorar o mapeamento relacional JPA/Hibernate das entidades persistentes sem quebrar a suite funcional
- `HISTORIA-016` Alinhar controllers, services, converters, domínio e persistência ao padrão estrutural observado no `umovme-team`

## Backlog de Evolucao Funcional

- `HISTORIA-014` Implementar criacao de conta bancaria via `POST /accounts` com TDD, BDD e semantica REST coerente

## Backlog de Evolucao Arquitetural

- nenhuma evolucao arquitetural pendente no momento

## Backlog de Refatoracao Estrutural

- nenhuma refatoracao estrutural pendente no momento

## Critérios de Movimentação

### To Do -> Doing

Pode mover quando:

- a história estiver descrita
- houver objetivo claro
- existir `docs/tasks/<KEY>/IMPLEMENTATION.md`
- todas as histórias anteriores já estiverem em `Done`

### Doing -> Review

Pode mover quando:

- implementação estiver concluída
- testes obrigatórios da história existirem
- critérios de aceite estiverem marcados no `IMPLEMENTATION.md`

### Review -> Done

Pode mover quando:

- QA aprovar
- revisão final aprovar
- QA liberar continuidade para a próxima história
- revisão final liberar continuidade para a próxima história
- documentação em `entregas/` existir
- tipo de versão semântica estiver sugerido

## Regra de Fluxo Contínuo

Quando o projeto estiver sendo executado em modo contínuo com o `ralph-loop`, o fluxo oficial é:

1. selecionar a próxima história elegível
2. mover `To Do -> Doing`
3. executar a história até aprovação completa
4. documentar a entrega em `entregas/`
5. mover para `Done`
6. iniciar automaticamente a próxima história

Esse fluxo continua ate a `HISTORIA-012` no escopo original do desafio.

A `HISTORIA-013` foi concluida como backlog evolutivo adicional e nao fazia parte obrigatoria do enunciado base.

## Observação

Este kanban é propositalmente textual e simples.

Ele existe para servir como um Jira pessoal totalmente versionado no repositório, sem depender de ferramenta externa.

# EPICO-001 — API de Banco Digital

## Visão do Épico

Construir uma API REST de banco digital que permita:

- consultar contas
- transferir valores entre contas
- consultar movimentações financeiras
- registrar notificação após transferência

Tudo isso com foco em:

- consistência transacional
- concorrência segura
- boa documentação
- ambiente simples de subir
- alta testabilidade

## Origem

Este épico foi derivado diretamente de `spec-driven-development.md`, que é a fonte principal da demanda.

## Valor de Negócio

Este épico materializa uma solução simples, mas profissional, para demonstrar:

- domínio de backend
- clareza arquitetural
- disciplina de engenharia
- capacidade de traduzir regra de negócio em software confiável

## Resultado Esperado

Ao final do épico, o projeto deve permitir:

- subir o ambiente local com Docker
- acessar a API e o Swagger
- consultar contas disponíveis
- realizar transferências entre contas válidas
- consultar movimentações geradas
- verificar que uma notificação foi registrada após sucesso

## Histórias Vinculadas

- `HISTORIA-001` Ambiente Docker e PostgreSQL isolado
- `HISTORIA-002` Estrutura base da aplicação
- `HISTORIA-003` Gestão básica de contas
- `HISTORIA-004` Transferência entre contas
- `HISTORIA-005` Movimentações financeiras
- `HISTORIA-006` Notificação pós-transferência
- `HISTORIA-007` Tratamento de erros e mensagens padronizadas
- `HISTORIA-008` Swagger/OpenAPI
- `HISTORIA-009` Testes unitários
- `HISTORIA-010` Testes funcionais
- `HISTORIA-011` README final
- `HISTORIA-012` Encerramento da entrega e fechamento semântico

## Backlog de Evolucao Pos-Entrega

- `HISTORIA-013` Elevacao da API para o topo do modelo de maturidade de Richardson com revisao de semantica REST e
  hipermidia
- `HISTORIA-014` Criacao de conta bancaria via `POST /accounts` com persistencia real, TDD, BDD e HATEOAS
- `HISTORIA-015` Refatoracao do mapeamento relacional JPA/Hibernate da camada de persistencia com `make functional-test`
  como gate obrigatorio

Essas historias nao alteram o fato de o desafio principal estar concluido. Elas existem para sofisticar a apresentacao
tecnica da API e explicitar um caminho de evolucao arquitetural e funcional do dominio.

## Guardião da Organização

O guardião desta organização é o subagente `ralph-loop/product-manager`.

Ele é responsável por:

- abrir o `IMPLEMENTATION.md` de cada história
- manter a coerência entre história, spec e entrega
- garantir documentação cronológica em `entregas/`
- apoiar o fechamento de versão com versionamento semântico

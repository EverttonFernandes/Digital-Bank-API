# Entrega 015 — Mapeamento Relacional JPA Hibernate

## Identificacao da Entrega

- Data da entrega: 2026-04-08
- Ordem cronologica da entrega: 015
- Nome da entrega: Mapeamento relacional JPA Hibernate
- Historia, fatia ou objetivo atendido: `HISTORIA-015`
- Tipo de versao sugerida: `PATCH`

## Resumo Executivo

Esta entrega amadureceu a camada de persistencia ao explicitar os relacionamentos JPA/Hibernate entre conta,
movimentacao e notificacao, sem alterar o contrato HTTP da API e sem misturar persistencia com o dominio.

Na pratica, a infraestrutura ficou mais fiel ao modelo relacional do banco, e a suite funcional completa permaneceu
verde ao final da refatoracao.

## Linguagem Ubiqua da Entrega

- `AccountEntity`: entidade persistente da conta
- `AccountMovementEntity`: entidade persistente da movimentacao financeira
- `AccountNotificationEntity`: entidade persistente da notificacao
- `Relacionamento persistente`: associacao JPA/Hibernate entre entidades da camada de banco

## Problema de Negocio

Antes desta entrega, o projeto ja usava JPA/Hibernate, mas parte das relacoes persistentes ainda aparecia apenas como
identificadores escalares, o que deixava a camada relacional menos expressiva do que o banco realmente era.

## O Que Foi Entregue

- `@ManyToOne` de movimentacao para conta
- `@ManyToOne` de notificacao para conta
- revisao de `AccountEntity` para expor colecoes persistentes lazy
- ajuste dos repositórios persistentes para trabalhar com referencias relacionais
- ajuste das consultas Spring Data para navegar por `accountEntity.id`
- testes unitarios de repositório para a nova modelagem
- revalidacao completa da suite funcional

## O Que Nao Foi Entregue

- anotacoes JPA na camada `domain`
- nova entidade de transferencia
- reescrita da API publica

## Traducao Entre Tecnica e Negocio

- `Relacionamento explicito entre entidades persistentes`
  Impacto no dominio: torna a persistencia mais clara e aderente ao banco
  Beneficio para o negocio: aumenta maturidade tecnica sem alterar comportamento funcional

- `Repositorio coerente com o modelo relacional`
  Impacto no dominio: reduz ambiguidade no caminho persistencia -> dominio
  Beneficio para o negocio: melhora manutencao e leitura tecnica da solucao

## Regras de Negocio Atendidas

- nenhum comportamento funcional foi alterado
- consultas de contas, movimentacoes, notificacoes e transferencias permaneceram funcionando
- a separacao entre `domain` e `infrastructure` foi preservada

## Estrategia Tecnica Aplicada

O corte adotado foi pragmatico:

- manter o dominio puro
- concentrar a refatoracao na infraestrutura JPA/Hibernate
- explicitar apenas as relacoes que ja existem no banco
- evitar `fetch` agressivo e serializacao acidental de entidades persistentes

## Evidencias de Validacao

- `make unit-test` com `BUILD SUCCESS`
- `make functional-test` com `23` testes passando
- nenhum endpoint deixou de funcionar apos a refatoracao

## Arquivos ou Modulos Mais Relevantes

- `src/main/java/com/avaliadora/digitalbankapi/infrastructure/persistence/entity/AccountEntity.java`
- `src/main/java/com/avaliadora/digitalbankapi/infrastructure/persistence/entity/AccountMovementEntity.java`
- `src/main/java/com/avaliadora/digitalbankapi/infrastructure/persistence/entity/AccountNotificationEntity.java`
- `src/main/java/com/avaliadora/digitalbankapi/infrastructure/persistence/repository/PostgreSqlAccountMovementRepository.java`
-
`src/main/java/com/avaliadora/digitalbankapi/infrastructure/persistence/repository/PostgreSqlAccountNotificationRepository.java`
-
`src/test/java/com/avaliadora/digitalbankapi/infrastructure/persistence/repository/PostgreSqlAccountMovementRepositoryTest.java`
-
`src/test/java/com/avaliadora/digitalbankapi/infrastructure/persistence/repository/PostgreSqlAccountNotificationRepositoryTest.java`

## Riscos, Limitacoes ou Pendencias

- ainda nao existe entidade persistente dedicada para transferencia
- novas relacoes devem continuar sendo introduzidas com parcimonia para nao inflar a camada ORM

## Relacao com a Spec Principal

Esta entrega nao muda o escopo funcional principal do desafio. Ela fortalece a camada de persistencia e melhora a defesa
tecnica da arquitetura adotada.

## Pronto Para Fechamento de Versao

- esta entrega esta documentada em ordem cronologica
- a documentacao reflete a implementacao real
- os testes obrigatorios passaram
- a entrega esta pronta para commit e tag semantica

# IMPLEMENTATION — HISTORIA-017

## Contexto da História

Esta história continua a simplificação estrutural iniciada nas histórias anteriores e ataca uma redundância objetiva do
projeto: a duplicação entre domínio e entidades JPA.

## Objetivo da Entrega Atual

Refatorar o back-end para que os modelos de domínio persistentes passem a refletir diretamente o mapeamento
JPA/Hibernate, começando pela fatia de conta, sem regressão funcional.

## Referências

- [HISTORIA-017-UNIFICACAO-DE-DOMINIO-E-PERSISTENCIA-JPA.md](docs/jira-pessoal/historias/HISTORIA-017-UNIFICACAO-DE-DOMINIO-E-PERSISTENCIA-JPA.md)
- [padrao-padrao-de-referencia-em-camadas.md](docs/padrao-referencia-backend.md)

## Escopo Incluído

- unificação progressiva entre domínio e mapeamento relacional
- simplificação de repositórios concretos
- primeira fatia com `Account`
- revalidação unitária e funcional

## Escopo Não Incluído

- mudar contratos HTTP
- alterar regras de negócio

## Critérios de Aceite

- `Account` deve ser persistido diretamente por JPA
- `AccountEntity` deve deixar de existir
- repositório de conta deve parar de converter `entity -> domain`
- entidades relacionais remanescentes devem continuar funcionando
- `make functional-test` deve passar
- `make unit-test` deve passar

## Primeira Fatia de Refatoração

- mover anotações JPA para `domain/account/model/Account`
- remover `infrastructure/persistence/entity/AccountEntity`
- adaptar `SpringDataAccountJpaRepository`
- adaptar `PostgreSqlAccountRepository`
- adaptar `AccountMovementEntity` e `AccountNotificationEntity` para referenciar `Account`

## Resultado Final da História

Esta história foi concluída por completo.

Primeira convergência:

- `Account` passou a ser o próprio modelo persistido por JPA
- `AccountEntity` foi removida
- o repositório de conta deixou de converter `entity -> domain`
- as entidades relacionais remanescentes passaram a referenciar `Account`

Convergência final:

- `AccountMovement` passou a ser o próprio modelo persistido por JPA
- `AccountNotification` passou a ser o próprio modelo persistido por JPA
- `AccountMovementEntity` e `AccountNotificationEntity` foram removidas
- os `SpringData*JpaRepository` passaram a trabalhar diretamente com os modelos de domínio persistentes
- os `PostgreSql*Repository` deixaram de fazer mapeamentos redundantes `entity -> domain`
- a observação de notificação foi adaptada para trabalhar com o domínio persistido já carregado

Validação final:

- `make unit-test`
- `make functional-test`

## Estratégia de Testes

- rodar `make unit-test`
- rodar `make functional-test`
- tratar a suíte funcional como hard gate principal

## Checklist de Conclusão

- [x] história aberta no kanban
- [x] `Account` persistido diretamente
- [x] `AccountEntity` removido
- [x] repositório de conta simplificado
- [x] `AccountMovement` persistido diretamente
- [x] `AccountNotification` persistido diretamente
- [x] `AccountMovementEntity` removida
- [x] `AccountNotificationEntity` removida
- [x] testes unitários verdes
- [x] testes funcionais verdes

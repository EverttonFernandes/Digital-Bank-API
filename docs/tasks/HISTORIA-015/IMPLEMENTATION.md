# IMPLEMENTATION — HISTORIA-015

## Contexto da História

Esta historia nasce da percepcao de que a API ja usa `Spring Data JPA` e `Hibernate`, mas ainda nao explicita plenamente os relacionamentos entre as entidades persistentes que representam tabelas reais do banco.

O objetivo nao e mover JPA para o dominio. O objetivo e amadurecer a camada de persistencia para que ela reflita melhor o modelo relacional.

## Objetivo da Entrega Atual

Refatorar o mapeamento relacional da camada de persistencia para que:

- `AccountMovementEntity` se relacione corretamente com `AccountEntity`
- `AccountNotificationEntity` se relacione corretamente com `AccountEntity`
- o acesso persistente continue pragmatico
- a API nao sofra regressao funcional
- a suite funcional completa siga verde ao final

## Escopo Incluído

Esta entrega deve incluir:

- revisao das entidades persistentes existentes
- introducao de relacionamentos JPA/Hibernate necessarios
- revisao de repositorios impactados
- revisao do mapeamento persistencia -> dominio
- ajuste de testes unitarios impactados
- revalidacao completa da suite funcional

## Escopo Explicitamente Não Incluído

Esta historia nao precisa incluir:

- mover anotacoes JPA para a camada `domain`
- criar camada ORM supercomplexa
- introduzir entidade de transferencia sem necessidade real
- reescrever a API publica

## Critérios de Aceite

- `AccountMovementEntity` deve ter relacionamento explicito com `AccountEntity`
- `AccountNotificationEntity` deve ter relacionamento explicito com `AccountEntity`
- as consultas da aplicacao devem continuar funcionando
- a separacao entre dominio e persistencia deve ser preservada
- testes unitarios impactados devem ser atualizados
- `make functional-test` deve continuar passando ao final
- nao pode haver regressao em contas, transferencias, movimentacoes ou notificacoes

## Tradução Entre Técnica e Negócio

- `Mapeamento relacional explicito`
  Impacto no dominio: melhora a clareza do modelo persistente e da relacao entre conta, movimentacao e notificacao
  Beneficio para o negocio: deixa a solucao mais profissional e mais facil de defender tecnicamente

- `Persistencia coerente com o banco`
  Impacto no dominio: reduz ambiguidade entre tabela e entidade persistente
  Beneficio para o negocio: melhora manutencao e reduz risco de leitura tecnica confusa

## Estratégia Técnica

### Direcao Arquitetural

Manter:

- `domain` como modelo de negocio puro
- `infrastructure.persistence.entity` como camada JPA/Hibernate

Refatorar:

- `AccountMovementEntity`
- `AccountNotificationEntity`
- possivelmente `AccountEntity` para expor relacoes persistentes com parcimonia

### Mapeamentos Esperados

- `AccountMovementEntity -> AccountEntity`
  usar `@ManyToOne`
  usar `@JoinColumn(name = "account_id")`

- `AccountNotificationEntity -> AccountEntity`
  usar `@ManyToOne`
  usar `@JoinColumn(name = "account_id")`

- `AccountEntity`
  avaliar `@OneToMany(mappedBy = ...)` apenas se agregar clareza sem efeito colateral desnecessario

### Regras de Implementacao

- evitar `EAGER` por padrao
- preferir `LAZY` quando aplicavel
- evitar problemas de serializacao mantendo entidades fora da API
- manter mapeadores explicitos de entidade para dominio

## Estratégia de Testes

### Unitários

- ajustar testes de repositorio ou converter impactados
- adicionar testes que comprovem o uso correto do novo mapeamento onde fizer sentido

### Funcionais

- reexecutar a suite funcional completa
- tratar `make functional-test` como hard gate desta historia
- se algum endpoint quebrar, a historia nao pode ser fechada

## Riscos e Pontos de Atenção

- cuidado com regressao em `seeders` e rollback funcional
- cuidado com `fetch` indevido
- cuidado com referencias ciclicas em entidades
- cuidado para nao misturar persistencia e dominio

## Restrições Pragmáticas e Padrões

- seguir a arquitetura ja adotada
- aplicar SOLID sem exagero
- manter nomes didaticos
- manter JPA/Hibernate restritos a `infrastructure`
- nao fechar a historia com testes funcionais quebrados

## Arquivos Provavelmente Afetados

- `src/main/java/com/cwi/digitalbankapi/infrastructure/persistence/entity/AccountEntity.java`
- `src/main/java/com/cwi/digitalbankapi/infrastructure/persistence/entity/AccountMovementEntity.java`
- `src/main/java/com/cwi/digitalbankapi/infrastructure/persistence/entity/AccountNotificationEntity.java`
- repositorios Spring Data impactados
- mapeadores persistencia -> dominio impactados
- testes unitarios relacionados
- talvez queries auxiliares dos testes funcionais, se o mapeamento mudar algum detalhe de persistencia

## Checklist de Conclusão

- [x] relacionamento JPA entre movimentacao e conta implementado
- [x] relacionamento JPA entre notificacao e conta implementado
- [x] `AccountEntity` revisada quanto a colecoes persistentes
- [x] repositorios ajustados quando necessario
- [x] mapeamento persistencia -> dominio revisado
- [x] testes unitarios ajustados
- [x] `make unit-test` passando
- [x] `make functional-test` passando
- [x] regressao ausente nos endpoints existentes

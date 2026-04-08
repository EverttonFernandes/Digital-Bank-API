# IMPLEMENTATION — HISTORIA-018

## Contexto da História

O projeto já convergiu em arquitetura e persistência, mas ainda está com nomenclatura heterogênea entre camadas.

## Objetivo da Entrega Atual

Renomear de ponta a ponta as classes que divergem do padrão adotado como referência, mantendo o comportamento da aplicação intacto.

## Referência

- [HISTORIA-018-PADRONIZACAO-DE-NOMENCLATURA-ARQUITETURAL.md](/home/umov/Documents/ProjetosPessoais/DigitalBankAPI/docs/jira-pessoal/historias/HISTORIA-018-PADRONIZACAO-DE-NOMENCLATURA-ARQUITETURAL.md)

## Escopo Incluído

- classes `Api`
- classes `DTO`
- classes `DTOConverter`
- `JpaRepository`
- `RepositoryImpl`
- testes correspondentes

## Mapa Inicial de Renomeação

- `AccountController` -> `AccountApi`
- `TransferController` -> `TransferApi`
- `ApplicationStatusController` -> `ApplicationStatusApi`
- `CreateAccountRequest` -> `AccountCreateDTO`
- `TransferRequest` -> `TransferDTO`
- `TransferResponse` -> `TransferResponseDTO`
- `AccountResponse` -> `AccountDTO`
- `AccountMovementResponse` -> `AccountMovementDTO`
- `AccountNotificationResponse` -> `AccountNotificationDTO`
- `CreateAccountRequestConverter` -> `AccountCreateDTOConverter`
- `TransferRequestConverter` -> `TransferDTOConverter`
- `AccountResponseConverter` -> `AccountDTOConverter`
- `SpringDataAccountJpaRepository` -> `AccountJpaRepository`
- `SpringDataAccountMovementJpaRepository` -> `AccountMovementJpaRepository`
- `SpringDataAccountNotificationJpaRepository` -> `AccountNotificationJpaRepository`
- `PostgreSqlAccountRepository` -> `AccountRepositoryImpl`
- `PostgreSqlAccountMovementRepository` -> `AccountMovementRepositoryImpl`
- `PostgreSqlAccountNotificationRepository` -> `AccountNotificationRepositoryImpl`

## Estratégia Técnica

1. criar a história e registrar o plano
2. renomear arquivos e classes principais
3. ajustar imports, wiring e testes
4. rodar `make unit-test`
5. rodar `make functional-test`
6. fechar a história somente com ambos verdes

## Checklist de Conclusão

- [x] história aberta no kanban
- [x] classes `Api` padronizadas
- [x] classes `DTO` padronizadas
- [x] converters padronizados
- [x] `JpaRepository` padronizados
- [x] `RepositoryImpl` padronizados
- [x] testes unitários verdes
- [x] testes funcionais verdes

## Resultado Final da História

A história convergiu por completo, com renomeação ponta a ponta das famílias principais de classes:

- `*Controller` -> `*Api`
- `*Request` e `*Response` -> `*DTO`
- `*RequestConverter` e `*ResponseConverter` -> `*DTOConverter`
- `SpringData*JpaRepository` -> `*JpaRepository`
- `PostgreSql*Repository` -> `*RepositoryImpl`

As mudanças preservaram o comportamento público da API e mantiveram a suíte funcional verde.

Validação final:

- `make unit-test`
- `make functional-test`

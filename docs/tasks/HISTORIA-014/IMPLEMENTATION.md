# IMPLEMENTATION — HISTORIA-014

## Contexto da História

Esta historia nasce da necessidade de completar o ciclo de vida basico da conta bancaria dentro do dominio da API.

Hoje o sistema:

- consulta contas
- transfere valores
- consulta movimentacoes
- consulta notificacoes

Mas ainda depende exclusivamente de contas pre-carregadas para existir.

Com esta historia, a API passa a permitir criacao real de conta via contrato HTTP coerente com o dominio.

## Objetivo da Entrega Atual

Implementar `POST /accounts` com:

- validacao de entrada
- persistencia real
- semantica REST correta
- response em `HAL`
- Swagger coerente
- TDD nos testes unitarios
- BDD nos testes funcionais
- cobertura forte dos cenarios criticos da fatia

## Escopo Incluído

Esta entrega deve incluir:

- request DTO para criacao de conta
- converter de request para dominio
- regras de negocio para criacao de conta
- caso de uso / service de criacao
- persistencia da nova conta
- endpoint `POST /accounts`
- response `201 Created` com `Location`
- response body `HAL`
- atualizacao do Swagger/OpenAPI
- testes unitarios da fatia
- testes funcionais de sucesso e falha

## Escopo Explicitamente Não Incluído

Esta historia nao precisa incluir:

- atualizacao de conta por `PUT` ou `PATCH`
- remocao de conta por `DELETE`
- regra de negocio de limite, bloqueio ou status de conta
- movimentacao inicial artificial apenas pela criacao da conta

## Critérios de Aceite

- deve existir `POST /accounts`
- deve aceitar `ownerName` e `initialBalance`
- deve rejeitar payload invalido com contrato de erro padronizado
- deve persistir a conta criada no banco
- deve retornar `201 Created`
- deve retornar `Location` da conta criada
- deve retornar a conta criada em formato `HAL`
- o Swagger deve descrever o request DTO, o response DTO e os responses esperados
- deve existir teste funcional de sucesso
- deve existir teste funcional de falha
- os testes devem validar estado final real da base
- nao deve haver regressao nos endpoints ja existentes

## Tradução Entre Técnica e Negócio

- `POST /accounts`
  Impacto no dominio: permite abertura de conta por contrato HTTP oficial
  Beneficio para o negocio: deixa a API mais completa e menos dependente de massa seedada

- `201 Created + Location`
  Impacto no dominio: reforca semantica correta de criacao de recurso
  Beneficio para o negocio: mostra maturidade de design de API

- `HAL com links`
  Impacto no dominio: a conta criada ja expõe como consultar seu estado e seus subrecursos
  Beneficio para o negocio: melhora a UX da API e a navegabilidade para integradores

## Estratégia Técnica

### Design do Recurso

Recurso alvo:

- `POST /accounts`

Request inicial sugerido:

```json
{
  "ownerName": "Maria Silva",
  "initialBalance": 350.00
}
```

Response de sucesso sugerido:

```json
{
  "id": 4,
  "ownerName": "Maria Silva",
  "balance": 350.00,
  "_links": {
    "self": {
      "href": "http://localhost:8080/accounts/4"
    },
    "accounts": {
      "href": "http://localhost:8080/accounts"
    },
    "movements": {
      "href": "http://localhost:8080/accounts/4/movements"
    },
    "notifications": {
      "href": "http://localhost:8080/accounts/4/notifications"
    }
  }
}
```

### Regras de Entrada

- `ownerName` deve ser obrigatorio
- `ownerName` nao pode ser branco
- `initialBalance` deve ser obrigatorio
- `initialBalance` deve ser maior ou igual a zero

### Regras de Domínio

- a conta criada nasce com saldo igual ao `initialBalance`
- a conta criada nao gera movimentacao automatica
- a conta criada nao gera notificacao automatica

### Camadas Afetadas

- `api`
- `application`
- `domain`
- `infrastructure`
- documentacao OpenAPI
- testes unitarios
- testes funcionais

## Estratégia de Testes

### TDD Obrigatório

Implementacao guiada por teste:

1. criar primeiro os testes unitarios do converter / specification / service / assembler impactados
2. fazer os testes falharem
3. implementar o minimo necessario
4. refatorar sem quebrar cobertura

### BDD Obrigatório

Criar cenarios funcionais em `Jest` com separacao clara:

- `accountsPostSuccess.spec.js`
- `accountsPostFailure.spec.js`

Os cenarios devem seguir:

- `GIVEN`
- `WHEN`
- `THEN`

### THEN End-to-End Obrigatório

Nos testes funcionais:

- o `THEN` deve validar `status code`
- o `THEN` deve validar body
- o `THEN` deve confirmar se a conta foi realmente criada
- o `THEN` deve usar `GET /accounts/{id}` para confirmar o recurso criado
- quando necessario, pode complementar com consulta auxiliar na base

### Cenários Funcionais Obrigatórios

#### Sucesso

- criar conta com `ownerName` valido e `initialBalance` igual a zero
- criar conta com `ownerName` valido e `initialBalance` positivo

#### Falha

- falhar quando `ownerName` nao for informado
- falhar quando `ownerName` for branco
- falhar quando `initialBalance` nao for informado
- falhar quando `initialBalance` for negativo
- falhar quando o body JSON for invalido

## Swagger e Contrato

O Swagger deve:

- documentar o DTO de criacao de conta
- explicar cada atributo do request
- explicar cada atributo do response
- documentar `201`, `400` e demais responses esperados
- refletir a estrutura `HAL`

## Riscos e Pontos de Atenção

- evitar acoplamento acidental entre seed inicial e contas criadas em testes
- garantir que a criacao da conta nao quebre a listagem atual
- garantir que o response HATEOAS seja coerente com a `HISTORIA-013`
- evitar relaxamento indevido dos testes existentes

## Restrições Pragmáticas e Padrões

- seguir a arquitetura ja adotada
- manter SOLID com interfaces pequenas se necessario
- evitar overengineering
- usar nomes totalmente didaticos
- manter padrao de erro `key/value`
- preservar padrao de escrita dos testes do projeto

## Arquivos Provavelmente Afetados

- controller de contas
- request DTO de criacao de conta
- converter de criacao de conta
- service de criacao de conta
- specification de criacao de conta
- repositorio de contas
- assembler / representation de conta
- Swagger / anotacoes OpenAPI
- testes unitarios relacionados
- testes funcionais de contas

## Checklist de Conclusão

- [x] request DTO de criacao implementado
- [x] converter implementado
- [x] specification implementada
- [x] service implementado
- [x] `POST /accounts` implementado
- [x] `201 Created` com `Location` implementado
- [x] response `HAL` implementado
- [x] Swagger atualizado
- [x] testes unitarios criados e passando
- [x] testes funcionais de sucesso criados e passando
- [x] testes funcionais de falha criados e passando
- [x] regressao validada nos endpoints existentes

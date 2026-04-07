# HISTORIA-013 — Maturidade de Richardson e Semantica REST

## Tipo

- evolucao arquitetural

## Objetivo da Historia

Refatorar os endpoints e os contratos de resposta para elevar a API ao topo do modelo de maturidade de Richardson, aproximando a solucao de uma API explicitamente RESTful, e nao apenas uma API que usa HTTP.

Essa evolucao deve usar `Spring HATEOAS` como base tecnica para expor links de navegacao coerentes entre recursos relacionados.

## Valor de Negocio

Esta historia fortalece a qualidade arquitetural da API e melhora a defesa tecnica do projeto durante a apresentacao.

## Critérios de Aceite

- deve existir revisao formal dos endpoints atuais sob o modelo de Richardson
- a API deve manter recursos nomeados de forma consistente
- respostas devem usar `Spring HATEOAS` para suporte a hipermidia quando fizer sentido
- a API deve respeitar a semantica correta de `GET`, `POST`, `PUT`, `PATCH` e `DELETE`
- nomes de endpoints devem refletir recursos de negocio e nao acoes tecnicas
- deve existir proposta clara de contratos de navegacao entre recursos relacionados
- documentacao Swagger e README devem refletir a nova semantica da API quando a refatoracao for implementada
- o Swagger deve explicar claramente o que cada atributo dos DTOs representa
- cada endpoint deve expor no Swagger os responses esperados segundo o novo padrao adotado
- o Swagger deve refletir os links e estruturas de resposta do modelo com `Spring HATEOAS` quando aplicavel
- respostas de transferencia devem apontar para recursos relacionados, como conta de origem, conta de destino, movimentacoes e notificacoes quando aplicavel
- respostas de notificacao devem apontar para a consulta das notificacoes da conta relacionada

## Mapa Inicial de Refatoracao da API

| Endpoint atual | Leitura de design atual | Endpoint alvo | Verbo correto | Evolucao de HATEOAS esperada |
| --- | --- | --- | --- | --- |
| `GET /accounts` | Ja e recurso bem nomeado | `GET /accounts` | `GET` | cada conta pode expor links para `self`, `movements`, `notifications` |
| `GET /accounts/{accountId}` | Ja e recurso bem nomeado | `GET /accounts/{accountId}` | `GET` | conta pode expor links para `self`, `movements` e `notifications` |
| `POST /transfers` | funcional, mas precisa response mais orientado a recurso | `POST /transfers` ou `POST /accounts/{accountId}/transfers` conforme decisao final de modelagem | `POST` | response deve apontar para conta de origem, conta de destino, movimentacoes e notificacoes |
| `GET /accounts/{accountId}/movements` | bom recurso subordinado | `GET /accounts/{accountId}/movements` | `GET` | cada movimentacao pode apontar para conta associada e colecao da conta |
| `GET /accounts/{accountId}/notifications` | bom recurso subordinado | `GET /accounts/{accountId}/notifications` | `GET` | cada notificacao pode apontar para conta associada e colecao da conta |
| contrato atual sem atualizacao de conta | recurso ainda incompleto para maturidade plena | `PUT /accounts/{accountId}` e/ou `PATCH /accounts/{accountId}` | `PUT` / `PATCH` | response deve apontar para `self` e recursos subordinados |
| contrato atual sem remocao de conta | recurso ainda incompleto para semantica completa | `DELETE /accounts/{accountId}` se o dominio permitir | `DELETE` | resposta deve manter semantica coerente com exclusao ou desativacao |

## Observacao Importante

Nem todo verbo precisa obrigatoriamente ser implementado se o dominio do desafio nao exigir isso.

Mas a historia deve deixar explicito:

- quando um verbo faz sentido para o recurso
- quando nao faz sentido
- e por que a API escolheu expor ou nao expor aquele comportamento

## Diagnostico Tecnico dos Endpoints Reais do Projeto

### Endpoints que eu manteria

- `GET /accounts`
  Motivo: colecao de recurso bem nomeada e semanticamente correta

- `GET /accounts/{accountId}`
  Motivo: consulta de recurso individual bem modelada

- `GET /accounts/{accountId}/movements`
  Motivo: subrecurso coerente com a conta

- `GET /accounts/{accountId}/notifications`
  Motivo: subrecurso coerente com a conta

- `POST /transfers`
  Motivo: a criacao de uma transferencia como recurso independente e defensavel e semanticamente aceitavel

### Endpoints que eu ajustaria

- `GET /api/status`
  Ajuste sugerido: manter apenas como endpoint tecnico auxiliar ou substituir sua importancia funcional por `GET /actuator/health`
  Motivo: ele nao representa recurso de negocio e nao agrega para a maturidade REST do dominio principal

- responses atuais de `GET /accounts`, `GET /accounts/{accountId}`, `POST /transfers`, `GET /accounts/{accountId}/movements` e `GET /accounts/{accountId}/notifications`
  Ajuste sugerido: incorporar `Spring HATEOAS`
  Motivo: hoje os endpoints usam bem HTTP e recursos, mas ainda nao guiam o consumidor por links navegaveis

### Evolucoes que eu trataria como opcionais e dependentes do dominio

- `PUT /accounts/{accountId}`
  So implementaria se o desafio passar a exigir substituicao completa de dados de conta

- `PATCH /accounts/{accountId}`
  So implementaria se o desafio passar a exigir alteracao parcial de conta, como nome do titular ou status

- `DELETE /accounts/{accountId}`
  So implementaria se o dominio permitir exclusao ou inativacao formal de conta

## Conclusao Arquitetural Atual

Hoje a API ja esta em um nivel bom de design para o desafio:

- recursos nomeados corretamente
- verbos coerentes no fluxo principal
- subrecursos bem definidos

O principal salto da `HISTORIA-013` nao e “consertar uma API totalmente errada”, e sim:

- explicitar ainda melhor a semantica REST
- padronizar a navegacao entre recursos
- elevar a experiencia de consumo com `Spring HATEOAS`

## Referencias Externas Consideradas

- `Richardson Maturity Model`
  Referencia: Martin Fowler
  Uso nesta historia: justificar que o nivel 3 introduz discoverability e auto-documentacao pela propria interface

- `Spring HATEOAS Reference Documentation`
  Referencia: documentacao oficial Spring
  Uso nesta historia: usar `RepresentationModel`, `EntityModel`, `CollectionModel` e links padronizados

- `RFC 9110 - HTTP Semantics`
  Referencia: IETF
  Uso nesta historia: fundamentar a semantica correta de `GET`, `POST`, `PUT` e `DELETE`

- `RFC 5789 - PATCH Method for HTTP`
  Referencia: IETF
  Uso nesta historia: fundamentar quando `PATCH` faz sentido como modificacao parcial de recurso

## Exemplos de Response Alvo Adaptados a Este Projeto

### `GET /accounts/{accountId}`

```json
{
  "id": 1,
  "ownerName": "Ana Souza",
  "balance": 1250.00,
  "_links": {
    "self": {
      "href": "http://localhost:8080/accounts/1"
    },
    "movements": {
      "href": "http://localhost:8080/accounts/1/movements"
    },
    "notifications": {
      "href": "http://localhost:8080/accounts/1/notifications"
    }
  }
}
```

### `POST /transfers`

```json
{
  "sourceAccountId": 1,
  "targetAccountId": 2,
  "transferReference": "4d2f91fb-daf5-4ea7-8db2-757ca1b89c30",
  "transferredAmount": 200.00,
  "sourceAccountBalance": 1050.00,
  "targetAccountBalance": 1180.50,
  "_links": {
    "transfers": {
      "href": "http://localhost:8080/transfers"
    },
    "sourceAccount": {
      "href": "http://localhost:8080/accounts/1"
    },
    "targetAccount": {
      "href": "http://localhost:8080/accounts/2"
    },
    "sourceAccountMovements": {
      "href": "http://localhost:8080/accounts/1/movements"
    },
    "targetAccountMovements": {
      "href": "http://localhost:8080/accounts/2/movements"
    },
    "sourceAccountNotifications": {
      "href": "http://localhost:8080/accounts/1/notifications"
    },
    "targetAccountNotifications": {
      "href": "http://localhost:8080/accounts/2/notifications"
    }
  }
}
```

### `GET /accounts/{accountId}/notifications`

```json
{
  "_embedded": {
    "notifications": [
      {
        "accountId": 1,
        "transferReference": "4d2f91fb-daf5-4ea7-8db2-757ca1b89c30",
        "notificationStatus": "REGISTERED",
        "message": "Transferencia enviada com sucesso para a conta 2.",
        "createdAt": "2026-04-07T10:15:30Z",
        "_links": {
          "account": {
            "href": "http://localhost:8080/accounts/1"
          },
          "collection": {
            "href": "http://localhost:8080/accounts/1/notifications"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/accounts/1/notifications"
    },
    "account": {
      "href": "http://localhost:8080/accounts/1"
    }
  }
}
```

## Dependências

- `HISTORIA-003`
- `HISTORIA-004`
- `HISTORIA-005`
- `HISTORIA-006`
- `HISTORIA-008`
- `HISTORIA-011`

## Próxima História Natural

- refinamento futuro do backlog arquitetural

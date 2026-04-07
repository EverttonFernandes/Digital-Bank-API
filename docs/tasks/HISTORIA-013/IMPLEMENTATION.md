# IMPLEMENTATION — HISTORIA-013

## Contexto da História

Esta historia nasce da necessidade de fortalecer a semantica REST da API sob a lente do modelo de maturidade de Richardson.

Ela nao existe para adicionar regra de negocio nova, e sim para refinar a forma como a API expoe recursos, respostas e navegacao entre estados.

Ela nasce diretamente de:

- `spec-driven-development.md`
- `docs/jira-pessoal/KANBAN.md`
- `docs/jira-pessoal/historias/HISTORIA-013-MATURIDADE-DE-RICHARDSON-E-SEMANTICA-REST.md`

## Objetivo da Entrega Atual

Refatorar a API para aumentar aderencia ao modelo de maturidade de Richardson e tornar a interface mais explicitamente RESTful.

Essa refatoracao deve usar `Spring HATEOAS` como mecanismo oficial para representar links, navegacao entre recursos e descoberta de proximos passos pela propria resposta da API.

## Escopo Incluído

Esta entrega deve incluir:

- revisao dos endpoints existentes sob o modelo de Richardson
- identificacao do nivel atual da API
- definicao do contrato alvo de recursos e respostas
- introducao de hipermidia com `Spring HATEOAS`
- refatoracao dos endpoints e payloads necessarios
- atualizacao de Swagger e README conforme a nova interface
- atualizacao dos testes unitarios e funcionais impactados

## Escopo Explicitamente Não Incluído

Esta historia nao precisa incluir:

- nova regra de negocio
- novo modulo de produto fora da semantica REST
- overengineering de hipermidia em pontos onde nao haja ganho real de navegacao

## Critérios de Aceite

- deve existir diagnostico do nivel atual da API no modelo de Richardson
- a API refatorada deve manter coerencia semantica de recursos
- endpoints nao devem expor acoes de forma impropria quando houver alternativa orientada a recurso
- respostas principais devem expor links de navegacao com `Spring HATEOAS` quando fizer sentido
- Swagger deve refletir a nova interface
- DTOs documentados no Swagger devem explicar de forma breve e clara cada atributo
- cada endpoint deve possuir responses esperados coerentes com o novo padrao da API
- testes existentes devem ser ajustados para a nova semantica
- a resposta de transferencia deve expor links para recursos relacionados
- a resposta de notificacao deve expor links coerentes de consulta do estado associado

## Tradução Entre Técnica e Negócio

- `Modelo de maturidade de Richardson`
  Impacto no dominio: organiza a interface da API em torno de recursos, verbos HTTP e navegacao coerente
  Beneficio para o negocio: melhora legibilidade, previsibilidade e profissionalismo da API

- `Hipermidia`
  Impacto no dominio: permite que a resposta aponte proximos recursos ou proximas acoes possiveis
  Beneficio para o negocio: aproxima a API de um desenho REST mais maduro e reduz acoplamento de clientes a conhecimento externo

- `Spring HATEOAS`
  Impacto no dominio: padroniza a representacao de links e a navegacao entre recursos do proprio dominio
  Beneficio para o negocio: transforma a API em uma interface mais guiada, mais didatica e mais profissional para integracao e apresentacao

- `Semantica de recursos`
  Impacto no dominio: faz a interface conversar na linguagem dos objetos do negocio
  Beneficio para o negocio: melhora comunicacao tecnica e facilita manutencao

## Estratégia Técnica

### Diagnostico

Mapear os endpoints atuais e classificar o nivel de maturidade REST alcancado hoje.

Diagnostico inicial baseado no estado atual do projeto:

- `GET /accounts`
  Manter
  Justificativa: colecao bem nomeada e semanticamente correta

- `GET /accounts/{accountId}`
  Manter
  Justificativa: recurso individual claro e coerente

- `POST /transfers`
  Manter como base
  Justificativa: criacao de transferencia como recurso independente e aceitavel
  Evolucao: response deve passar a ser mais navegavel com `Spring HATEOAS`

- `GET /accounts/{accountId}/movements`
  Manter
  Justificativa: subrecurso aderente ao dominio
  Evolucao: incluir links entre item, conta e transferencia relacionada

- `GET /accounts/{accountId}/notifications`
  Manter
  Justificativa: subrecurso aderente ao dominio
  Evolucao: incluir links entre item, conta e colecao

- `GET /api/status`
  Reavaliar
  Justificativa: endpoint tecnico e util para operacao, mas nao representa recurso de negocio do dominio principal

### Referencias de Mercado Adotadas

- `Spring HATEOAS Reference Documentation`
  Direciona o uso de `RepresentationModel`, `EntityModel`, `CollectionModel`, `PagedModel` e `RepresentationModelAssembler`

- `Richardson Maturity Model`
  Direciona a meta da historia: sair de uma API boa em recursos e verbos para uma interface mais discoverable

- `RFC 9110`
  Direciona a semantica correta de `GET`, `POST`, `PUT` e `DELETE`

- `RFC 5789`
  Direciona a semantica correta de `PATCH` como modificacao parcial

### Matriz de Refatoracao Esperada

| Endpoint atual | Problema ou limite de design | Endpoint alvo | Verbo HTTP esperado | Ajuste de resposta |
| --- | --- | --- | --- | --- |
| `GET /accounts` | baixo nivel de navegacao no payload | `GET /accounts` | `GET` | adicionar links `self`, `account`, `movements`, `notifications` |
| `GET /accounts/{accountId}` | payload ainda pouco navegavel | `GET /accounts/{accountId}` | `GET` | adicionar links relacionados ao estado da conta |
| `POST /transfers` | response ainda pode evoluir para guiar melhor o consumidor | `POST /transfers` ou variante subordinada por conta, conforme decisao final | `POST` | adicionar links para origem, destino, movimentacoes e notificacoes |
| `GET /accounts/{accountId}/movements` | falta navegacao entre movimento, conta e transferencia | `GET /accounts/{accountId}/movements` | `GET` | adicionar links de navegacao por item e colecao |
| `GET /accounts/{accountId}/notifications` | falta navegacao entre notificacao e conta | `GET /accounts/{accountId}/notifications` | `GET` | adicionar links por item e colecao |
| ausencia de atualizacao explicita de conta | sem decisao formal de `PUT` ou `PATCH` | `PUT /accounts/{accountId}` e/ou `PATCH /accounts/{accountId}` se o dominio justificar | `PUT` / `PATCH` | resposta deve voltar com `self` e links relacionados |
| ausencia de remocao explicita de conta | sem decisao formal de `DELETE` | `DELETE /accounts/{accountId}` se o dominio justificar | `DELETE` | manter semantica correta para exclusao ou inativacao |

### Refatoracao

Revisar nomes de rotas, padroes de resposta e possibilidade de links relacionados.

Definir formalmente:

- quais recursos devem permanecer como estao
- quais recursos devem ser renomeados
- quais verbos devem ser introduzidos
- quais verbos devem continuar ausentes por decisao de dominio

### Swagger e Contrato da API

O Swagger deve ser tratado como parte da definicao do contrato, nao apenas como documentacao superficial.

Diretrizes obrigatorias:

- cada DTO de request e response deve ter descricao clara de cada atributo
- cada endpoint deve ter descricao objetiva do comportamento
- cada endpoint deve documentar os `responses` esperados no novo padrao adotado
- responses de sucesso devem refletir a estrutura com `Spring HATEOAS` quando aplicavel
- responses de falha devem refletir o contrato padronizado de erro
- exemplos no Swagger devem ser coerentes com o payload real retornado pela API

Decisao inicial sugerida:

- nao adicionar `PUT`, `PATCH` ou `DELETE` apenas para “cumprir tabela”
- introduzir esses verbos somente se houver caso de uso real no dominio
- focar primeiro no refinamento dos recursos ja existentes e no ganho real de navegacao com `Spring HATEOAS`

### Hipermidia

Aplicar com parcimonia usando `Spring HATEOAS`. O objetivo e aumentar clareza arquitetural e melhorar a experiencia de consumo da API, nao inflar a interface artificialmente.

Exemplos esperados:

- resposta de transferencia apontando para:
  - conta de origem
  - conta de destino
  - movimentacoes da conta de origem
  - movimentacoes da conta de destino
  - notificacoes associadas

- resposta de notificacao apontando para:
  - conta relacionada
  - colecao de notificacoes daquela conta

### Modelagem de Response Recomendada

Usar `HAL` como representacao inicial por ser um formato diretamente suportado no ecossistema de `Spring HATEOAS`.

Aplicacao pratica sugerida:

- `AccountResponseModel extends RepresentationModel<AccountResponseModel>`
- `TransferResponseModel extends RepresentationModel<TransferResponseModel>`
- `AccountNotificationResponseModel extends RepresentationModel<AccountNotificationResponseModel>`
- `AccountMovementResponseModel extends RepresentationModel<AccountMovementResponseModel>`

Assemblers sugeridos:

- `AccountRepresentationModelAssembler`
- `TransferRepresentationModelAssembler`
- `AccountNotificationRepresentationModelAssembler`
- `AccountMovementRepresentationModelAssembler`

Exemplos concretos de payload alvo:

#### Conta individual

```json
{
  "id": 1,
  "ownerName": "Ana Souza",
  "balance": 1250.00,
  "_links": {
    "self": { "href": "http://localhost:8080/accounts/1" },
    "movements": { "href": "http://localhost:8080/accounts/1/movements" },
    "notifications": { "href": "http://localhost:8080/accounts/1/notifications" }
  }
}
```

#### Transferencia concluida

```json
{
  "sourceAccountId": 1,
  "targetAccountId": 2,
  "transferReference": "4d2f91fb-daf5-4ea7-8db2-757ca1b89c30",
  "transferredAmount": 200.00,
  "sourceAccountBalance": 1050.00,
  "targetAccountBalance": 1180.50,
  "_links": {
    "transfers": { "href": "http://localhost:8080/transfers" },
    "sourceAccount": { "href": "http://localhost:8080/accounts/1" },
    "targetAccount": { "href": "http://localhost:8080/accounts/2" },
    "sourceAccountMovements": { "href": "http://localhost:8080/accounts/1/movements" },
    "targetAccountMovements": { "href": "http://localhost:8080/accounts/2/movements" },
    "sourceAccountNotifications": { "href": "http://localhost:8080/accounts/1/notifications" },
    "targetAccountNotifications": { "href": "http://localhost:8080/accounts/2/notifications" }
  }
}
```

#### Colecao de notificacoes

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
          "self": { "href": "http://localhost:8080/accounts/1/notifications/4d2f91fb-daf5-4ea7-8db2-757ca1b89c30" },
          "account": { "href": "http://localhost:8080/accounts/1" },
          "collection": { "href": "http://localhost:8080/accounts/1/notifications" }
        }
      }
    ]
  },
  "_links": {
    "self": { "href": "http://localhost:8080/accounts/1/notifications" },
    "account": { "href": "http://localhost:8080/accounts/1" }
  }
}
```

## Camadas Afetadas

- `api`
- `application`
- `shared`
- `infrastructure`
- documentacao OpenAPI
- testes funcionais e unitarios

## Documentos Normativos que Devem Ser Respeitados

- `arquitetura.md`
- `padroes-de-testes.md`
- `codigo-limpo.md`
- `padrões-de-projeto-e-design-de-codigo.md`

## Estratégia de Testes

### Unitários

- ajustar converters e responses impactados
- validar montagem de links de navegacao quando existirem
- validar assemblers ou representacoes `HATEOAS`
- validar decisao semantica de verbo quando a interface expor `PUT`, `PATCH` ou `DELETE`
- validar consistencia entre response model e documentacao do Swagger quando houver componente dedicado para isso

### Funcionais

- validar novos endpoints ou rotas refatoradas
- validar compatibilidade dos recursos principais
- validar estrutura das respostas e links expostos
- validar a navegacao por links retornados nos bodies principais
- validar que o verbo HTTP utilizado corresponde ao comportamento real do recurso
- validar que os responses documentados no Swagger correspondem ao comportamento real da API

## Arquivos Provavelmente Afetados

- controllers
- DTOs de resposta
- assemblers / representacoes `Spring HATEOAS`
- documentacao OpenAPI
- testes funcionais
- README
- definicao de contratos de recurso
- anotacoes `@Schema`, `@Operation`, `@ApiResponse` e correlatas

## Riscos e Pontos de Atenção

- alterar a interface publica exige atualizacao cuidadosa dos testes
- hipermidia excessiva pode complicar a API sem ganho proporcional
- o objetivo e maturidade REST, nao reescrita desnecessaria da solucao
- os links devem ter valor real de navegacao, nao apenas existir por formalidade
- nem todo recurso precisara necessariamente expor `PUT`, `PATCH` ou `DELETE`; isso deve ser decidido pelo dominio e documentado com clareza
- Swagger desatualizado em relacao ao DTO ou ao response real enfraquece diretamente a qualidade da entrega

## Restrições Pragmáticas e Padrões

- preservar clareza do dominio
- evitar exagero de design pattern
- usar linguagem ubiqua nos recursos expostos
- manter a API didatica para avaliacao tecnica
- usar `Spring HATEOAS` como padrao oficial de hipermidia
- tratar o Swagger como contrato vivo da API

## Checklist de Conclusão

- [x] nivel atual da API no modelo de Richardson diagnosticado
- [x] contrato alvo de recursos definido
- [x] endpoints refatorados quando necessario
- [x] respostas com navegacao revisadas
- [x] `Spring HATEOAS` incorporado nas respostas principais
- [x] Swagger atualizado
- [x] atributos dos DTOs documentados com descricao clara no Swagger
- [x] responses esperados de cada endpoint atualizados no Swagger
- [x] README atualizado
- [x] testes unitarios ajustados
- [x] testes funcionais ajustados

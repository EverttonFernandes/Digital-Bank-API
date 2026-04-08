# HISTORIA-014 — Criacao de Conta Bancaria

## Tipo

- evolucao funcional

## Objetivo da Historia

Implementar o endpoint `POST /accounts` para permitir a criacao de uma nova conta bancaria no dominio, cobrindo persistencia, validacao de entrada, semantica REST correta, contrato HATEOAS e documentacao Swagger coerente com o comportamento real.

## Valor de Negocio

Esta historia fecha uma lacuna natural do dominio: hoje a API permite consultar contas, transferir valores, consultar movimentacoes e notificacoes, mas ainda nao permite abrir uma nova conta. Com isso, o fluxo de vida da conta deixa de depender apenas de seed inicial.

## Comportamento Esperado

O endpoint deve:

- receber um payload de criacao de conta
- validar os dados obrigatorios
- criar a conta no banco de dados
- retornar `201 Created`
- retornar `Location` apontando para o recurso criado
- retornar body em formato `HAL` com links para:
  - `self`
  - `accounts`
  - `movements`
  - `notifications`

## Contrato Inicial Proposto

### Endpoint

- `POST /accounts`

### Request DTO

- `ownerName`
  descricao: nome do titular da conta
  regra: obrigatorio, nao pode ser vazio

- `initialBalance`
  descricao: saldo inicial da conta
  regra: obrigatorio, deve ser maior ou igual a zero

### Response DTO

- `id`
- `ownerName`
- `balance`
- `_links`

### Response HTTP Esperado

- `201 Created`
- header `Location` com a URL da conta criada
- body representando a conta criada com links HATEOAS

## Critérios de Aceite

- deve existir `POST /accounts`
- a criacao de conta deve persistir no banco
- o endpoint deve retornar `201 Created`
- o endpoint deve retornar `Location` para `GET /accounts/{accountId}`
- o body de sucesso deve refletir o recurso criado em `HAL`
- o Swagger deve documentar claramente request, response e responses esperados
- o DTO deve ter descricoes curtas e didaticas por atributo
- deve haver testes unitarios cobrindo converter, service, specification e assembler envolvidos
- deve haver testes funcionais BDD cobrindo sucesso e falha
- o `THEN` funcional deve validar o estado final do banco via API e, quando necessario, por consulta auxiliar
- a historia nao pode introduzir regressao nos endpoints ja existentes
- a cobertura da fatia implementada deve buscar `100%` dos cenarios criticos desta historia

## Regras de Negocio Iniciais

- o nome do titular e obrigatorio
- o saldo inicial nao pode ser negativo
- a conta criada deve nascer sem movimentacoes
- a conta criada deve nascer sem notificacoes
- a criacao da conta nao deve interferir no seed base ja existente

## Semantica REST e Design de API

- usar `POST /accounts` por se tratar de criacao de recurso na colecao
- retornar `201 Created` e `Location`
- nao aceitar verbo improprio para criacao
- manter nomenclatura orientada a recurso
- manter a resposta em linha com o padrao HATEOAS ja adotado pela `HISTORIA-013`

## HATEOAS Esperado

Na resposta da conta criada, expor pelo menos:

- `self`
- `accounts`
- `movements`
- `notifications`

## Dependencias

- `HISTORIA-003`
- `HISTORIA-008`
- `HISTORIA-010`
- `HISTORIA-013`

## Proxima Historia Natural

- refinamentos futuros de manutencao do ciclo de vida da conta, como atualizacao parcial ou desativacao, apenas se o dominio justificar

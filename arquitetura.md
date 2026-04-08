# Arquitetura Proposta

## Objetivo

Estruturar a API do banco digital com um desenho inspirado em DDD, separando claramente:

- validação e conversão de entrada
- regras de negócio
- modelo de domínio
- persistência e integrações

O foco dessa abordagem é manter o domínio limpo, facilitar testes e deixar explícita a diferença entre:

- dado inválido de entrada
- regra de negócio não satisfeita

## Visão em Camadas

```text
Controller
  -> DTO
  -> Converter
  -> Command / Domain
  -> Domain Service / Aggregate
  -> Composite Specification
  -> Repository
  -> Notification Gateway
```

## Estrutura Sugerida

```text
src/main/java/.../bank
  application
    controller
    dto
    converter
    service
  domain
    account
      model
      repository
      specification
    transfer
      model
      repository
      specification
      service
    statement
      model
    notification
      gateway
  infrastructure
    persistence
      entity
      repository
    notification
    config
  shared
    exception
    specification
    response
```

## Responsabilidade de Cada Parte

### `controller`

Recebe a requisição HTTP, delega o processamento e devolve a resposta da API.

Não deve conter regra de negócio.

### `dto`

Representa os contratos de entrada e saída da API.

Exemplos:

- `CreateAccountRequest`
- `TransferRequest`
- `TransferResponse`
- `StatementResponse`

### `converter`

Converte DTO para objeto de domínio ou comando de aplicação.

Aqui entram validações de entrada e consistência estrutural, por exemplo:

- campos obrigatórios
- formato de valores monetários
- origem e destino informados
- impedir transferência para a mesma conta já na entrada

Essa camada existe para barrar input inválido antes de chegar no domínio.

### `domain`

Contém o núcleo da regra de negócio.

Exemplos de agregados e entidades:

- `Account`
- `Transfer`
- `Transaction`

O domínio deve expressar comportamento, não apenas dados.

Exemplos:

- debitar saldo
- creditar saldo
- registrar movimentação
- representar uma transferência concluída

### `specification`

Contém regras de negócio isoladas e reutilizáveis, aplicadas sobre o domínio já montado.

Exemplos:

- `ShouldHaveSufficientBalance`
- `ShouldNotTransferToSameAccount`
- `ShouldAllowTransferOnlyWhenAccountsAreActive`

Cada specification valida uma regra específica.

### `CompositeSpecification`

Agrupa várias specifications e executa tudo de forma encadeada.

Exemplo:

```text
ShouldAccountsExist
  AND ShouldNotTransferToSameAccount
  AND ShouldHaveSufficientBalance
  AND ShouldAllowTransferOnlyWhenAccountsAreActive
```

O resultado pode retornar:

- sucesso
- lista de mensagens de erro de negócio

### `domain service`

Orquestra casos de uso que envolvem mais de um agregado ou mais de um repositório.

No desafio, a transferência é o melhor candidato.

Exemplo de responsabilidade:

- carregar conta origem
- carregar conta destino
- montar objeto de transferência
- aplicar composite specification
- efetivar débito e crédito
- gerar movimentações
- persistir atomicamente
- acionar notificação após sucesso

### `repository`

Define contratos do domínio para acesso a dados.

Exemplos:

- `AccountRepository`
- `TransferRepository`
- `TransactionRepository`

O domínio conhece a interface, mas não a implementação.

### `infrastructure`

Implementa detalhes técnicos:

- JPA
- banco de dados
- Swagger/OpenAPI
- gateway de notificação
- tratamento global de exceções

## Fluxo Principal de Transferência

```text
POST /transfers
  -> TransferController
  -> TransferRequestDTO
  -> TransferRequestConverter
  -> TransferService
  -> carregar contas
  -> montar Transfer / aplicar specifications
  -> debitar origem
  -> creditar destino
  -> registrar movimentações
  -> salvar em transação
  -> notificar cliente
  -> retornar resposta
```

## Separação Entre Validação de Entrada e Regra de Negócio

### Validação de entrada

Fica no DTO/converter.

Exemplos:

- valor nulo
- valor menor ou igual a zero
- campos ausentes
- tipo inválido

### Regra de negócio

Fica no domínio/specification.

Exemplos:

- saldo insuficiente
- conta inativa
- transferência entre mesma conta
- conta inexistente

Essa separação deixa a modelagem mais clara e melhora a manutenção.

## Concorrência e Consistência

Como o desafio menciona alta concorrência, a transferência precisa ser tratada como operação transacional.

Diretrizes:

- usar transação no caso de uso de transferência
- garantir atualização consistente das duas contas
- proteger o saldo contra race condition
- registrar movimentações no mesmo contexto transacional

Dependendo da implementação, isso pode ser resolvido com:

- lock pessimista no carregamento das contas
- ou controle otimista com versionamento

Para esse teste, lock pessimista costuma ser a solução mais simples e previsível.

## Notificação

A notificação deve acontecer apenas após a transferência ter sido concluída com sucesso.

Sugestão:

- criar uma porta `NotificationGateway` no domínio
- implementar um adaptador simples na infraestrutura
- inicialmente pode ser log, mock ou persistência de evento

Assim, a regra de negócio não fica acoplada ao meio de envio.

## Benefícios Desse Desenho

- domínio mais limpo e explícito
- regras de negócio testáveis de forma isolada
- converters testáveis separadamente
- menor acoplamento entre API, domínio e infraestrutura
- facilidade para evoluir a solução

## Maturidade REST e Evolucao Arquitetural

No estado atual, a API ja trabalha com:

- recursos nomeados
- verbos HTTP coerentes
- codigos de status adequados

Isso a coloca em um nivel maduro de uso de HTTP e recursos.

Como evolucao planejada, existe a `HISTORIA-013`, focada em revisar a API sob o modelo de maturidade de Richardson, com
atencao especial a:

- semantica dos endpoints
- uniformidade dos contratos de resposta
- navegacao por hipermidia
- links relacionados entre recursos

Essa evolucao sera implementada com `Spring HATEOAS`, para que os responses da API passem a carregar links navegaveis e
coerentes com o estado atual do recurso.

Exemplos desejados:

- ao concluir uma transferencia, a resposta pode apontar para:
    - a conta de origem
    - a conta de destino
    - as movimentacoes da origem
    - as movimentacoes do destino
    - as notificacoes relacionadas

- ao consultar uma notificacao, a resposta pode apontar para:
    - a conta associada
    - a colecao de notificacoes daquela conta
    - o recurso relacionado que originou a notificacao

Isso transforma a API em uma interface mais guiada, quase como uma experiencia de uso da propria API.

Essa evolucao e importante para a apresentacao tecnica porque deixa explicito que a API atual atende bem ao desafio, mas
ainda pode ser refinada para se aproximar do topo do modelo de Richardson.

## Resumo

O desenho segue esta ideia:

1. A API recebe um DTO.
2. O converter valida e transforma a entrada.
3. O domínio representa o problema de negócio.
4. As specifications aplicam as regras de negócio.
5. O service orquestra a transação.
6. A infraestrutura persiste e integra com recursos externos.

Esse modelo é aderente ao estilo arquitetural que você descreveu e combina bem com o desafio da empresa avaliadora.

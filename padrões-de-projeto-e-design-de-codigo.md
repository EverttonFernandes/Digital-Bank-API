# Padrões de Projeto e Design de Código

## Objetivo

Definir as diretrizes obrigatórias de design de código deste projeto, com foco em:

- aplicação rigorosa de SOLID
- segregação de interfaces
- baixo acoplamento
- alta coesão
- uso criterioso de design patterns

Este projeto deve priorizar clareza arquitetural e manutenibilidade acima de sofisticação desnecessária.

## SOLID é obrigatório

Os princípios SOLID devem ser aplicados à risca.

Eles não são opcionais neste projeto.

Toda modelagem, classe, interface e fluxo de dependência deve ser pensada com base em:

- `S` de Single Responsibility Principle
- `O` de Open/Closed Principle
- `L` de Liskov Substitution Principle
- `I` de Interface Segregation Principle
- `D` de Dependency Inversion Principle

## Single Responsibility Principle

Cada classe deve ter uma responsabilidade clara e única.

Exemplos:

- converter converte e valida entrada
- specification valida regra de negócio específica
- service orquestra caso de uso
- repository acessa persistência
- observer reage a evento de notificação

Sinais de violação:

- classe que valida, persiste e notifica ao mesmo tempo
- service que conhece detalhe de banco, HTTP e regra de domínio
- classe com muitos motivos para mudar

## Open/Closed Principle

O código deve estar aberto para extensão e fechado para modificação sempre que isso fizer sentido.

Exemplo:

- novas estratégias de notificação devem poder ser adicionadas sem reescrever o núcleo da transferência
- novas validações devem ser adicionadas por novas specifications ou novos componentes

Evitar:

- grandes blocos de `if/else` para cada novo comportamento
- classes centrais que precisam ser reescritas a cada nova regra

## Liskov Substitution Principle

Qualquer implementação concreta deve poder substituir sua abstração sem comportamento inesperado.

Se uma implementação de interface exige tratamento especial, há um problema de modelagem.

Exemplo:

- qualquer implementação de `NotificationChannel` deve poder ser usada sem quebrar o fluxo
- qualquer implementação de `AccountRepository` deve respeitar o mesmo contrato de negócio

## Interface Segregation Principle

Este é um princípio prioritário neste projeto.

Preferimos:

- 10 interfaces pequenas e segregadas

do que:

- 2 interfaces grandes com muitos métodos

### Regra explícita

É melhor ter interfaces pequenas, específicas e especializadas do que interfaces genéricas e inchadas.

Se uma interface começa a agrupar responsabilidades demais, ela deve ser quebrada.

Exemplos preferíveis:

- `LoadAccountByIdRepository`
- `SaveAccountRepository`
- `LoadTransactionsByAccountIdRepository`
- `SendNotificationChannel`
- `PublishTransferCompletedEvent`

Evitar:

- `AccountRepository` com métodos demais sem critério
- `NotificationService` responsável por tudo
- interfaces genéricas com muitos métodos não relacionados

### Sinal de violação

Se uma classe implementa uma interface e precisa deixar métodos vazios, lançar `UnsupportedOperationException` ou ignorar parte do contrato, a interface está errada.

## Dependency Inversion Principle

Dependências devem apontar para abstrações, não para detalhes concretos.

O domínio e a aplicação não devem depender diretamente de:

- JPA
- cliente de e-mail
- cliente de SMS
- fila
- logger específico

Devem depender de portas e contratos.

Exemplos:

- `NotificationPublisher`
- `SendNotificationChannel`
- `LoadAccountRepository`
- `SaveTransferRepository`

## Diretriz geral de design

Este projeto deve preferir:

- composição
- interfaces pequenas
- contratos explícitos
- fluxos simples
- objetos com responsabilidade bem delimitada

Este projeto deve evitar:

- abstrações prematuras
- hierarquias desnecessárias
- patterns aplicados só por estética
- interfaces grandes e genéricas

## Design Patterns com parcimônia

Design patterns criacionais, comportamentais e estruturais devem ser usados com parcimônia.

A prioridade não é "usar patterns".

A prioridade é:

- resolver o problema
- manter clareza
- não dificultar leitura
- não exagerar na abstração

Se o SOLID estiver muito bem aplicado, boa parte do design já ficará naturalmente saudável sem necessidade de muitos patterns formais.

### Regra prática

Não aplicar design pattern:

- por vaidade
- por currículo
- por excesso de zelo
- só porque "fica bonito"

Aplicar design pattern apenas quando:

- resolver um problema real
- reduzir acoplamento
- facilitar evolução concreta do código
- melhorar a legibilidade em vez de piorá-la

## Pattern permitido e recomendado: Observer para notificação

A parte de notificação é o principal ponto deste projeto em que um design pattern faz sentido claro.

O pattern recomendado para isso é:

- `Observer`

### Motivo

A notificação abre caminho para múltiplos comportamentos reativos após uma transferência concluída com sucesso.

Exemplos futuros:

- envio de e-mail
- envio de SMS
- publicação em fila
- processamento assíncrono
- geração de log
- auditoria
- webhook

O `Observer` permite evoluir esse fluxo sem acoplar o caso de uso principal de transferência a todos os canais possíveis.

### Aplicação sugerida

Fluxo sugerido:

1. a transferência é concluída com sucesso
2. um evento de domínio ou evento de aplicação é publicado
3. observers reagem a esse evento
4. cada observer executa sua responsabilidade

Exemplos:

- `SendEmailNotificationObserver`
- `SendSmsNotificationObserver`
- `PublishTransferLogObserver`
- `PublishTransferToQueueObserver`

### Benefícios

- reduz acoplamento do fluxo principal
- facilita extensão futura
- favorece Open/Closed Principle
- mantém o caso de uso principal mais limpo

## Patterns que não devem ser forçados

Não devemos introduzir sem necessidade:

- `Factory` complexa para objetos simples
- `Strategy` quando uma simples specification ou serviço já resolve
- `Facade` sem contexto real
- `Builder` desnecessário para objetos triviais
- `Chain of Responsibility` quando a composição simples de specifications já basta
- `Abstract Factory` sem múltiplas famílias reais de objetos

Esses patterns só devem aparecer se houver necessidade real.

## Specifications e composição

Como o projeto já adota domínio + specification, isso por si só já cobre boa parte do design de validação de negócio.

Portanto:

- regras devem preferencialmente viver em specifications pequenas
- composições devem ocorrer por composite specification
- não devemos introduzir patterns adicionais para resolver o que já está bem resolvido por esse modelo

## Critérios de design aceitável

Um design será considerado bom quando:

- cada classe tiver responsabilidade clara
- as interfaces forem pequenas e especializadas
- o domínio não depender de detalhes concretos
- novas regras puderem ser adicionadas sem reescrever o núcleo
- a notificação puder evoluir por observers
- o código continuar simples de ler

## Anti-padrões proibidos

Não será aceito:

- interface grande com responsabilidades múltiplas
- service com comportamento de controller, repository e gateway ao mesmo tempo
- classe utilitária genérica para esconder design ruim
- acoplamento direto entre domínio e infraestrutura
- pattern aplicado sem problema real
- abstração excessiva que dificulte entendimento
- observer espalhado onde não há necessidade

## Checklist

- SOLID foi aplicado com rigor
- cada classe tem responsabilidade única
- interfaces estão segregadas
- dependências apontam para abstrações
- não há pattern desnecessário
- o uso de `Observer` está concentrado na parte de notificação
- o código continua simples, explícito e sustentável

## Resumo

Neste projeto:

- SOLID deve ser aplicado à risca
- preferimos várias interfaces pequenas a poucas interfaces grandes
- design patterns devem ser usados com parcimônia
- o principal pattern recomendado é `Observer` para notificação
- a meta é manter o código forte em design sem torná-lo excessivamente sofisticado

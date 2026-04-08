# IMPLEMENTATION — HISTORIA-016

## Contexto da História

Esta historia nasce da decisao de alinhar o back-end deste projeto ao padrão de código observado no contexto `umovme-team` do `umovme-business`.

Nao se trata de copiar cegamente a estrutura da referência. Trata-se de trazer para este projeto um nível maior de consistência estrutural entre:

- `api`
- `application`
- `domain`
- `infrastructure`

## Objetivo da Entrega Atual

Refatorar todo o back-end para que o padrão de organização das camadas fique mais próximo do `umovme-team`, preservando comportamento funcional e tratando a suíte funcional como o principal guardião de regressão.

## Fonte de Verdade da Referência

- [padrao-umovme-team.md](/home/umov/Documents/ProjetosPessoais/DigitalBankAPI/docs/padrao-umovme-team.md)

## Escopo Incluído

Esta entrega deve incluir:

- leitura e aplicação disciplinada do padrão de referência
- revisão de controllers
- revisão de services
- revisão de converters
- revisão das specifications
- revisão de agregados e comandos do domínio
- revisão dos adaptadores de infraestrutura
- ajuste dos testes unitários impactados
- revalidação completa da suíte funcional

## Escopo Explicitamente Não Incluído

Esta história não precisa incluir:

- mudança de regra de negócio
- criação de novos endpoints apenas por oportunismo
- mudança artificial de contratos HTTP sem ganho real
- cópia literal de abstrações base do outro projeto

## Critérios de Aceite

- controllers devem ficar mais finos
- services devem ficar mais orquestradores
- converters devem ficar mais fortes na montagem do fluxo de domínio
- specifications devem ficar mais evidentes e compostas onde necessário
- infraestrutura deve ficar mais consistente como adaptadora do domínio
- o padrão final deve ser coerente com o documento de referência
- `make functional-test` deve passar ao final
- `make unit-test` deve passar ao final
- não pode haver regressão funcional na API

## Tradução Entre Técnica e Negócio

- `Alinhamento estrutural`
  Impacto no domínio: melhora a distribuição de responsabilidades no código
  Benefício para o negócio: torna a solução mais profissional, legível e sustentável

- `Suíte funcional como hard gate`
  Impacto no domínio: garante que a refatoração não altere os comportamentos públicos
  Benefício para o negócio: permite evolução estrutural com segurança

## Estratégia Técnica

### Pilares do Padrão Alvo

- controllers finos
- services orquestradores
- converters fortes
- agregado mais central
- specifications compostas
- infraestrutura como adaptador claro

### Primeira Fatia de Refatoração

A primeira iteração desta história deve atacar a fatia mais segura e mais representativa do padrão alvo:

- `CreateAccountService`
- `TransferService`
- converters relacionados
- consistência entre controller fino, service orquestrador e montagem mais forte do fluxo antes da persistência

Leitura atual do projeto:

- `AccountController` já está relativamente fino
- `CreateAccountService` ainda monta parte do agregado diretamente
- `TransferService` ainda concentra montagem operacional demais para o padrão de referência
- o primeiro objetivo não é mudar contrato HTTP nem regra de negócio
- o primeiro objetivo é redistribuir responsabilidades com preservação total do comportamento observável

### Sequência Recomendada

1. mapear responsabilidades atuais por camada
2. escolher a primeira fatia de refatoração mais segura
3. refatorar sem alterar comportamento observável
4. rodar `make functional-test`
5. ajustar unitários quebrados
6. repetir por fatia até convergir

### Ordem Sugerida de Refatoração

- conta
- transferência
- movimentação
- notificação
- infraestrutura e consistência transversal

## Estratégia de Testes

### Hard Gate Inicial

- `make functional-test`

Sem esse gate verde, a história não pode avançar.

### Hard Gate Final

- `make functional-test`
- `make unit-test`

### Regra Operacional

Durante a refatoração:

- primeiro garantir comportamento pela suíte funcional
- depois ajustar os testes unitários quebrados
- fechar a história apenas com ambos verdes

## Riscos e Pontos de Atenção

- overengineering por querer copiar demais a referência
- quebrar clareza já existente no projeto
- alterar contratos HTTP sem necessidade
- mover regras para camadas erradas
- perder simplicidade em nome de “parecer com o outro projeto”

## Restrições Pragmáticas e Padrões

- seguir a referência com senso crítico
- manter nomes didáticos
- preservar DDD tático pragmático
- preservar SOLID
- preservar os padrões de testes do projeto
- não fechar a história com funcionais quebrados

## Arquivos Provavelmente Afetados

- controllers de conta e transferência
- services de conta e transferência
- converters associados
- specifications do domínio
- repositórios concretos da infraestrutura
- documentação arquitetural e README
- testes unitários relacionados

## Checklist de Conclusão

- [x] padrão de referência aplicado às principais camadas
- [x] controllers afinados
- [x] services afinados
- [x] converters afinados
- [x] specifications afinadas
- [x] infraestrutura afinada
- [x] `make functional-test` passando
- [x] `make unit-test` passando
- [x] regressão ausente

## Resultado da Execução

Esta história convergiu sem alterar o comportamento público da API.

Principais movimentos executados:

- consolidação da superfície de conta em um único `AccountService`
- `AccountController` afinado para depender somente do service principal da fatia
- `CreateAccountRequestConverter` passou a transformar o DTO diretamente em agregado de domínio
- as specifications de criação passaram a validar o agregado `Account`
- `TransferRequestConverter` passou a montar o agregado `Transfer` a partir do DTO e das contas carregadas
- responsabilidades de aplicação, geração de movimentações e evento de conclusão foram puxadas para o domínio `Transfer`
- remoção de services e comandos de domínio que ficaram redundantes após a convergência estrutural

Validação final executada:

- `make unit-test`
- `make functional-test`

# IMPLEMENTATION — HISTORIA-004

## Contexto da História

Esta história é o núcleo funcional do projeto.

É nela que o banco digital passa a cumprir sua principal responsabilidade: transferir valor entre contas de forma
segura, consistente e aderente às regras de negócio.

Ela nasce diretamente de:

- `spec-driven-development.md`
- `docs/jira-pessoal/KANBAN.md`
- `docs/jira-pessoal/historias/HISTORIA-004-TRANSFERENCIA-ENTRE-CONTAS.md`

## Objetivo da Entrega Atual

Implementar a transferência entre contas com foco em:

- consistência transacional
- proteção contra concorrência
- regras de negócio explícitas
- setup inicial dos testes funcionais em Jest
- base sólida para movimentações e notificações

## Escopo Incluído

Esta entrega deve incluir:

- modelagem da transferência
- endpoint para transferir valor entre contas
- validação da requisição de transferência
- aplicação das regras de negócio da transferência
- transação envolvendo origem e destino
- uso de lock pessimista para proteger concorrência
- resposta da API refletindo o resultado da transferência
- setup inicial da suíte funcional com Jest
- estrutura inicial de `fixtures`, `seeders`, `success` e `failure` para a transferência

## Escopo Explicitamente Não Incluído

Esta entrega ainda não precisa incluir de forma completa:

- documentação cronológica final da entrega
- camada completa de notificação funcional
- refinamentos avançados de observabilidade

Observação:

Movimentações financeiras e notificação possuem histórias próprias, mas esta entrega já deve ser preparada para
integrá-las corretamente.

## Critérios de Aceite

- conta de origem deve existir
- conta de destino deve existir
- origem e destino devem ser diferentes
- valor deve ser maior que zero
- saldo da origem deve ser suficiente
- operação deve ser transacional
- lock pessimista deve ser usado para proteger concorrência
- resposta da API deve refletir a transferência concluída
- projeto deve possuir setup funcional inicial em Jest pronto para validar a transferência

## Tradução Entre Técnica e Negócio

- `Transação envolvendo origem e destino`
  Impacto no domínio: garante que débito e crédito façam parte de uma única operação consistente
  Benefício para o negócio: evita que o dinheiro saia de uma conta sem entrar corretamente na outra

- `Lock pessimista nas contas`
  Impacto no domínio: impede concorrência indevida sobre o mesmo saldo no momento da transferência
  Benefício para o negócio: reduz risco de inconsistência financeira em cenários simultâneos

- `Specifications para regras de negócio`
  Impacto no domínio: centraliza as regras que tornam uma transferência válida ou inválida
  Benefício para o negócio: aumenta previsibilidade da operação e clareza da manutenção futura

- `Setup inicial de testes funcionais com Jest`
  Impacto no domínio: permite validar a transferência e seus efeitos reais desde a primeira história crítica
  Benefício para o negócio: aumenta confiança na principal entrega do banco digital e reduz risco de regressão

## Estratégia Técnica

Esta entrega deve seguir rigorosamente a arquitetura documentada.

### Entrada e Conversão

A requisição de transferência deve ser recebida via DTO e convertida de forma estruturada.

Validações de entrada devem ficar na camada apropriada de converter ou validação estrutural, sem misturar isso com regra
de negócio profunda.

### Domínio

As regras de negócio da transferência devem ser expressas sobre o domínio já montado.

### Specifications esperadas

Devem existir regras equivalentes a:

- conta de origem existente
- conta de destino existente
- contas diferentes
- valor positivo
- saldo suficiente

### Persistência e Concorrência

A operação deve:

1. abrir transação
2. carregar contas com lock pessimista
3. validar regras
4. aplicar débito e crédito
5. persistir o resultado

### Estratégia de confiança da entrega

Como a transferência é o centro do desafio, esta história deve antecipar a infraestrutura mínima de testes funcionais.

Isso significa incluir já nesta etapa:

- setup de `Jest`
- estrutura inicial de testes funcionais
- massa com `seeders` e `fixtures`
- separação entre cenários de sucesso e falha
- base de execução que possa ser expandida nas próximas histórias

### Observação de ordem determinística

Se necessário para reduzir risco de deadlock, a leitura das contas deve considerar ordem determinística por
identificador.

## Camadas Afetadas

Esta história afeta:

- `api`
- `application`
- `domain`
- `infrastructure`

## Documentos Normativos que Devem Ser Respeitados

- `arquitetura.md`
- `padroes-de-testes.md`
- `codigo-limpo.md`
- `padrões-de-projeto-e-design-de-codigo.md`

## Estratégia de Testes

Esta história exige validação forte porque é o centro da solução.

### Testes unitários esperados

- converter de transferência
- specifications da transferência
- service ou caso de uso de transferência
- comportamento de débito e crédito no domínio, se aplicável

### Testes funcionais esperados

Além dos cenários da transferência, esta história deve deixar pronta a infraestrutura funcional do projeto em `Jest`.

#### Cenários de sucesso

- transferir valor com sucesso entre contas válidas

#### Cenários de falha

- conta de origem inexistente
- conta de destino inexistente
- origem e destino iguais
- valor zero
- valor negativo
- saldo insuficiente

### Regras obrigatórias dos testes funcionais

- seguir `GIVEN / WHEN / THEN`
- validar status code
- validar mensagens `key` e `value` nas falhas de negócio
- validar estado final via `GET` das contas sempre que possível
- comprovar que o saldo da origem e do destino refletem corretamente o resultado esperado
- preparar a base funcional reutilizável para as histórias seguintes

## Arquivos Provavelmente Afetados

Arquivos esperados nesta entrega:

- DTOs de transferência
- converter de transferência
- entidade ou agregado de transferência
- specifications da transferência
- service ou caso de uso de transferência
- controller de transferência
- repositórios de conta e transferência
- setup do Jest
- estrutura funcional de seeders e fixtures
- testes unitários e funcionais relacionados

## Riscos e Pontos de Atenção

- lógica de saldo mal posicionada pode quebrar o domínio
- ausência de lock adequado pode permitir inconsistência em concorrência
- mistura entre validação estrutural e regra de negócio pode enfraquecer a arquitetura
- mensagens inconsistentes podem gerar retrabalho em testes funcionais
- adiar o setup funcional para muito tarde reduz a confiança na história mais crítica do projeto

## Restrições Pragmáticas e Padrões

- manter o fluxo `DTO -> converter -> domínio -> specification`
- não antecipar notificações completas nesta história
- não exagerar em patterns além do necessário
- usar nomenclatura totalmente didática
- manter a transferência simples de entender, mesmo sendo o ponto mais crítico do projeto
- antecipar apenas a base funcional necessária, sem tentar esgotar toda a suíte final nesta etapa

## Checklist de Conclusão

- [x] endpoint de transferência implementado
- [x] regras de negócio da transferência implementadas
- [x] transação configurada corretamente
- [x] lock pessimista aplicado
- [x] resposta da API da transferência implementada
- [x] setup inicial de testes funcionais em Jest criado
- [x] estrutura inicial de seeders e fixtures criada
- [x] testes unitários da história criados
- [x] testes funcionais de sucesso criados
- [x] testes funcionais de falha criados
- [x] história pronta para integrar movimentações e notificação

## Progresso Atual

### Iteração 1

- foi criada a estrutura `__functional_tests__` inspirada nos exemplos de `wishlist-api` e `projeto-referencia-backend`
- foi configurado `Jest` com `supertest`, `pg`, `fixtures`, `seed.js` e `rollback.js`
- foi atualizado o alvo `make functional-test` para executar a suíte funcional em Jest
- foram criados dois cenários BDD iniciais para contas:
    - sucesso em `GET /accounts`
    - falha em `GET /accounts/{id}` para conta inexistente
- o `make functional-test` foi validado com sucesso contra a aplicação real e o PostgreSQL do ambiente Docker

### Estado Atual da História

O setup funcional da história já está pronto e validado.

O próximo passo desta mesma história é usar essa base para implementar a transferência entre contas com TDD e expandir
os cenários funcionais para o fluxo principal de negócio.

### Iteração 2

- foi implementado o endpoint `POST /transfers`
- foi criado o fluxo `DTO -> converter -> domínio -> specification -> repository`
- foi adicionada proteção com lock pessimista na carga das contas envolvidas
- as regras de contas diferentes, valor positivo e saldo suficiente passaram a ser validadas de forma explícita
- a transferência passou a atualizar os saldos de origem e destino em uma única transação
- foram criados testes unitários para converter, specifications e serviço de transferência
- foram criados testes funcionais BDD de sucesso e falha para `POST /transfers`
- o `runFunctionalTests.js` passou a esperar o `health` da aplicação antes de iniciar a suíte funcional

## Evidências de Validação

- `make unit-test`
  Resultado: `BUILD SUCCESS`

- `make up`
  Resultado: aplicação reconstruída com sucesso após a implementação da transferência

- `make functional-test`
  Resultado: `9` testes passando, cobrindo contas e transferência

- `POST /transfers`
  Resultado: transferência bem-sucedida atualiza os saldos de origem e destino

- `POST /transfers` em cenários inválidos
  Resultado: retornos `404` e `400` com `key/value` coerentes e sem alteração indevida dos saldos

# IMPLEMENTATION — HISTORIA-003

## Contexto da História

Esta história é a primeira entrega funcional do domínio do banco digital.

Ela sucede a preparação do ambiente e da estrutura base da aplicação, e passa a materializar o conceito central de conta
dentro do sistema.

Ela nasce diretamente de:

- `spec-driven-development.md`
- `docs/jira-pessoal/KANBAN.md`
- `docs/jira-pessoal/historias/HISTORIA-003-GESTAO-BASICA-DE-CONTAS.md`

## Objetivo da Entrega Atual

Disponibilizar a gestão básica de contas para que o sistema possua uma base consultável e coerente de contas bancárias.

Nesta etapa, a prioridade é:

- possuir contas pré-carregadas
- permitir listagem de contas
- permitir busca de conta por identificador
- garantir que o modelo de conta esteja coerente com o domínio

## Escopo Incluído

Esta entrega deve incluir:

- modelagem inicial de conta
- persistência da conta
- seed inicial de contas
- endpoint para listar contas
- endpoint para buscar conta por identificador
- resposta da API refletindo corretamente titular e saldo

## Escopo Explicitamente Não Incluído

Esta entrega ainda não inclui:

- transferência entre contas
- movimentações financeiras
- notificação
- criação opcional de conta, caso isso complique o escopo inicial

## Critérios de Aceite

- deve existir base inicial de contas para validação do sistema
- deve ser possível listar contas pela API
- deve ser possível buscar uma conta por identificador
- saldo e nome do titular devem ser retornados corretamente
- conta inexistente deve ser tratada de forma consistente

## Tradução Entre Técnica e Negócio

- `Seed inicial de contas`
  Impacto no domínio: garante que o sistema já possua contas utilizáveis desde o início
  Benefício para o negócio: facilita demonstração, testes e validação sem depender de criação manual prévia

- `Endpoint de listagem de contas`
  Impacto no domínio: expõe as contas disponíveis para consulta
  Benefício para o negócio: torna o estado inicial do banco visível para quem avalia o sistema

- `Endpoint de busca por conta`
  Impacto no domínio: permite localizar uma conta específica e consultar seu saldo
  Benefício para o negócio: prepara o caminho para a futura transferência entre contas

## Estratégia Técnica

Esta entrega deve seguir a arquitetura documentada com separação clara de responsabilidades.

### Domínio

Criar a representação da conta respeitando as regras mínimas já definidas na spec.

### Persistência

Preparar a persistência da conta em PostgreSQL com suporte a carga inicial de dados.

### API

Disponibilizar endpoints de leitura simples:

- `GET /accounts`
- `GET /accounts/{id}`

### Tratamento de Erro

Buscar resposta consistente para conta inexistente, sem ainda expandir demais o tratamento global de mensagens além do
necessário para esta etapa.

## Camadas Afetadas

Esta história afeta:

- `domain`
- `application`
- `api`
- `infrastructure`

## Documentos Normativos que Devem Ser Respeitados

- `arquitetura.md`
- `padroes-de-testes.md`
- `codigo-limpo.md`
- `padrões-de-projeto-e-design-de-codigo.md`

## Estratégia de Testes

Esta é a primeira história funcional do projeto e já deve sair com validação real.

### Testes unitários esperados

- modelagem ou comportamento relevante da conta
- conversão de request/response se aplicável
- service ou caso de uso de consulta

### Testes funcionais esperados

- listar contas com sucesso
- buscar conta existente com sucesso
- tentar buscar conta inexistente e validar o erro esperado

### Validação end-to-end

Nos testes funcionais, o estado final pode ser comprovado por:

- resposta do próprio endpoint de listagem
- resposta do endpoint de busca por identificador

Se houver mensagem de erro para conta inexistente, ela deve refletir corretamente o que aconteceu.

## Arquivos Provavelmente Afetados

Arquivos esperados nesta entrega:

- classes de domínio de conta
- repositório de conta
- caso de uso de consulta de contas
- controller de contas
- DTOs de resposta
- scripts de seed ou migration inicial
- testes unitários e funcionais de contas

## Riscos e Pontos de Atenção

- modelar conta de forma pobre pode dificultar as próximas histórias
- acoplar a API diretamente na persistência quebra a arquitetura cedo demais
- não preparar bem o seed pode atrapalhar testes e demonstração
- mensagens inconsistentes nesta etapa podem gerar retrabalho na história de tratamento de erros

## Restrições Pragmáticas e Padrões

- manter foco em leitura e base inicial de contas
- não antecipar complexidades desnecessárias de criação/edição se não forem essenciais agora
- usar nomenclatura didática e aderente ao domínio
- manter a conta como conceito central e claro para o restante do projeto

## Checklist de Conclusão

- [x] modelo de conta criado
- [x] persistência de conta preparada
- [x] seed inicial de contas criado
- [x] `GET /accounts` implementado
- [x] `GET /accounts/{id}` implementado
- [x] testes unitários da história criados
- [x] ausência de testes funcionais nesta etapa registrada como `N/A`
- [x] história pronta para habilitar a transferência entre contas

## Evidências de Validação

- `make unit-test`
  Resultado: `BUILD SUCCESS`

- `make up`
  Resultado: aplicação reconstruída com sucesso e containers ativos

- `GET /accounts`
  Resultado: `200 OK` com três contas pré-carregadas retornadas pela API

- `GET /accounts/1`
  Resultado: `200 OK` com nome do titular e saldo corretos

- `GET /accounts/99`
  Resultado: `404 Not Found` com resposta `key/value` coerente

- `GET /actuator/health`
  Resultado: `200 OK`

## Log de Iterações

### Iteração 1

- Foi criado o modelo de domínio `Account` com contrato de repositório próprio.
- A persistência passou a usar migration com seed inicial de contas no PostgreSQL.
- Foi disponibilizada a leitura de contas por meio de `GET /accounts` e `GET /accounts/{id}`.
- Foi adicionado tratamento mínimo de erro para conta inexistente com resposta `key/value`.
- Foram criados testes unitários para conversão de resposta e consulta de contas.
- Não foram detectados testes funcionais implementados no projeto nesta etapa, portanto o gate `func_tests` permaneceu
  `N/A` de forma documentada.

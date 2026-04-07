# IMPLEMENTATION — HISTORIA-002

## Contexto da História

Esta história sucede a preparação do ambiente Docker e representa o primeiro passo real de estruturação da aplicação.

Ela nasce diretamente de:

- `spec-driven-development.md`
- `docs/jira-pessoal/KANBAN.md`
- `docs/jira-pessoal/historias/HISTORIA-002-ESTRUTURA-BASE-DA-APLICACAO.md`

Seu objetivo é deixar o projeto pronto para evoluir com consistência técnica, respeitando a arquitetura já documentada.

## Objetivo da Entrega Atual

Estruturar a aplicação Spring Boot com base arquitetural clara e sustentável, preparando o terreno para:

- contas
- transferências
- movimentações
- notificações
- testes

## Escopo Incluído

Esta entrega deve incluir:

- criação da base do projeto Spring Boot
- definição da estrutura de pacotes e camadas
- configuração inicial de build
- configuração inicial da aplicação
- preparação da integração com persistência
- organização coerente entre `api`, `application`, `domain` e `infrastructure`

## Escopo Explicitamente Não Incluído

Esta entrega ainda não inclui:

- regra de negócio de contas
- transferência entre contas
- movimentações financeiras
- notificação funcional
- cobertura completa de testes do domínio

## Critérios de Aceite

- projeto Spring Boot deve existir e estar estruturado
- camadas devem refletir a arquitetura documentada
- configuração inicial de persistência deve existir
- projeto deve compilar corretamente
- organização do código deve permitir evolução sem retrabalho estrutural imediato

## Tradução Entre Técnica e Negócio

- `Estrutura em camadas`
  Impacto no domínio: separa responsabilidades entre entrada, aplicação, regra de negócio e infraestrutura
  Benefício para o negócio: facilita manutenção, reduz acoplamento e torna a solução mais profissional

- `Base Spring Boot organizada`
  Impacto no domínio: cria um ponto de partida estável para as próximas histórias
  Benefício para o negócio: acelera evolução segura das funcionalidades principais

- `Preparação de persistência`
  Impacto no domínio: deixa a aplicação pronta para armazenar contas, transferências e movimentações
  Benefício para o negócio: evita reestruturações custosas nas próximas entregas

## Estratégia Técnica

Esta entrega deve priorizar clareza e sustentabilidade estrutural.

### Estrutura da Aplicação

Organizar o código base em camadas coerentes com `arquitetura.md`:

- `api`
- `application`
- `domain`
- `infrastructure`

### Build e Configuração

- preparar `build.gradle`
- configurar aplicação Spring Boot
- preparar configuração inicial para banco

### Preparação da Persistência

- preparar integração base com PostgreSQL
- deixar o projeto apto a evoluir para migrations e entidades persistidas

## Camadas Afetadas

Esta história afeta:

- bootstrap da aplicação
- organização estrutural de pacotes
- infraestrutura de persistência
- configuração base

## Documentos Normativos que Devem Ser Respeitados

- `arquitetura.md`
- `padroes-de-testes.md`
- `codigo-limpo.md`
- `padrões-de-projeto-e-design-de-codigo.md`

## Estratégia de Testes

Mesmo sendo uma história de base estrutural, a entrega deve ter validação objetiva.

### Validação mínima obrigatória

- projeto deve subir sem erro
- projeto deve compilar
- estrutura de pacotes deve refletir a arquitetura planejada
- configuração principal deve estar íntegra

### Observação

Nesta etapa, o foco principal não é ainda a profundidade de testes de domínio, mas a validação de que a base do projeto está correta e pronta para receber as histórias funcionais.

## Arquivos Provavelmente Afetados

Arquivos esperados nesta entrega:

- `build.gradle`
- classe principal da aplicação
- arquivos de configuração Spring
- estrutura de pacotes da aplicação
- arquivos iniciais de persistência ou configuração associada

## Riscos e Pontos de Atenção

- estrutura ruim nesta etapa gera retrabalho em todas as histórias seguintes
- mistura indevida entre camadas compromete a arquitetura
- antecipar abstrações demais pode gerar overengineering
- deixar a estrutura vaga demais pode gerar desorganização nas próximas implementações

## Restrições Pragmáticas e Padrões

- seguir rigorosamente `arquitetura.md`
- aplicar SOLID desde a base, mas sem exagerar em abstrações prematuras
- evitar patterns desnecessários nesta etapa
- manter nomes totalmente didáticos
- não criar complexidade além do necessário para habilitar as próximas histórias

## Checklist de Conclusão

- [x] projeto Spring Boot criado
- [x] build configurado
- [x] estrutura em camadas criada
- [x] integração base com persistência preparada
- [x] projeto compila
- [x] base pronta para a história de contas

## Evidências de Validação

- `make unit-test`
  Resultado: `BUILD SUCCESS`

- `make up`
  Resultado: aplicação, PostgreSQL e pgAdmin iniciados com sucesso após a reorganização estrutural

- `docker compose ps`
  Resultado: aplicação em `8080`, PostgreSQL em `5433` e pgAdmin em `5050`

- `GET /api/status`
  Resultado: `200 OK`

- `GET /actuator/health`
  Resultado: `200 OK`

## Log de Iterações

### Iteração 1

- A base antes desta história ainda estava concentrada em `bootstrap`, o que não refletia a arquitetura documentada.
- O endpoint de status foi movido para `api/controller` e passou a depender de um serviço em `application/service`.
- Foi criada a configuração de persistência em `infrastructure/config`.
- Foram materializados os pacotes estruturais da arquitetura por meio de `package-info.java`.
- Houve uma regressão temporária de `404` em `/api/status`, corrigida movendo a classe principal para o pacote raiz `com.cwi.digitalbankapi`.
- A validação final confirmou build íntegro e ambiente funcional.

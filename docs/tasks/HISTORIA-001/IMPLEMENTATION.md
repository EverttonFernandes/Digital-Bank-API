# IMPLEMENTATION — HISTORIA-001

## Contexto da História

Esta história é a primeira entrega cronológica do projeto e prepara o ambiente base para todas as próximas etapas.

Ela nasce diretamente de:

- `spec-driven-development.md`
- `docs/jira-pessoal/KANBAN.md`
- `docs/jira-pessoal/historias/HISTORIA-001-AMBIENTE-DOCKER-E-POSTGRESQL.md`

Sem esta entrega, o projeto ainda não possui um ambiente isolado, reproduzível e simples de validar.

## Objetivo da Entrega Atual

Disponibilizar um ambiente local padronizado com:

- aplicação executando isoladamente
- PostgreSQL executando isoladamente
- portas separadas para aplicação e banco
- acesso claro ao Swagger
- caminho simples de subida para qualquer avaliador ou desenvolvedor

## Escopo Incluído

Esta entrega deve incluir:

- definição do `docker-compose`
- serviço da aplicação
- serviço do PostgreSQL
- configuração de portas distintas entre aplicação e banco
- configuração mínima para a aplicação se conectar ao banco no ambiente Docker
- documentação objetiva de como subir o ambiente

## Escopo Explicitamente Não Incluído

Esta entrega ainda não inclui:

- implementação da lógica de contas
- transferência entre contas
- movimentações financeiras
- notificação
- suíte completa de testes do domínio
- documentação final completa do projeto

## Critérios de Aceite

- deve existir configuração de `Docker Compose` para subir aplicação e PostgreSQL
- PostgreSQL deve usar porta diferente da aplicação
- Swagger deve ficar acessível pela aplicação em rota própria
- o ambiente deve ser simples de subir localmente
- deve existir documentação mínima de execução do ambiente

## Tradução Entre Técnica e Negócio

- `Docker Compose`
  Impacto no domínio: padroniza como os componentes do sistema sobem juntos
  Benefício para o negócio: qualquer pessoa consegue validar o projeto com menos fricção

- `PostgreSQL em porta separada`
  Impacto no domínio: separa persistência da aplicação de forma explícita
  Benefício para o negócio: facilita entendimento operacional e inspeção dos dados

- `Swagger acessível`
  Impacto no domínio: expõe os contratos da API de forma navegável
  Benefício para o negócio: acelera validação manual e demonstração do projeto

## Estratégia Técnica

Esta entrega deve seguir uma abordagem simples e direta.

### Aplicação

- preparar a aplicação Spring Boot para executar em container
- externalizar configuração de conexão com banco para ambiente Docker

### Banco

- subir PostgreSQL em container próprio
- expor porta própria, distinta da aplicação
- preparar credenciais e nome do banco do ambiente local

### Documentação

- registrar no `README.md` como subir e acessar:
  - aplicação
  - Swagger
  - banco

## Camadas Afetadas

Esta história afeta principalmente:

- infraestrutura
- configuração da aplicação
- documentação de execução

Camadas de domínio ainda não serão exploradas de forma relevante nesta etapa.

## Documentos Normativos que Devem Ser Respeitados

- `arquitetura.md`
- `padroes-de-testes.md`
- `codigo-limpo.md`
- `padrões-de-projeto-e-design-de-codigo.md`

## Estratégia de Testes

Mesmo sendo uma história de infraestrutura, a entrega deve prever validação objetiva.

### Validação mínima obrigatória

- subir os containers com sucesso
- confirmar que a aplicação responde na porta definida
- confirmar que o PostgreSQL responde na porta definida
- confirmar que o Swagger está acessível

### Observação

Se nesta etapa ainda não houver suíte automatizada suficiente para validar tudo, isso deve ser registrado explicitamente como limitação temporária da história, sem fingir cobertura inexistente.

## Arquivos Provavelmente Afetados

Arquivos esperados nesta entrega:

- `docker-compose.yml`
- `Dockerfile`
- arquivos de configuração da aplicação
- `README.md`

## Riscos e Pontos de Atenção

- configuração errada de portas pode gerar conflito local
- configuração errada de conexão pode impedir a aplicação de subir
- documentação insuficiente pode comprometer a experiência do avaliador
- excesso de complexidade nesta etapa seria desnecessário

## Restrições Pragmáticas e Padrões

- manter a solução simples
- não criar automações desnecessárias nesta primeira história
- não antecipar infraestrutura que ainda não é exigida pela spec
- preparar apenas o necessário para viabilizar as próximas histórias

## Checklist de Conclusão

- [x] `docker-compose` criado
- [x] aplicação sobe em container
- [x] PostgreSQL sobe em container
- [x] portas estão separadas e claras
- [x] Swagger está acessível
- [x] documentação mínima de execução foi registrada
- [x] história pronta para avançar no kanban

## Evidências de Validação

- `make unit-test`
  Resultado: `BUILD SUCCESS`

- `docker compose up --build -d`
  Resultado: aplicação, PostgreSQL e pgAdmin iniciados com sucesso

- `docker compose ps`
  Resultado: aplicação exposta em `8080`, PostgreSQL exposto em `5433` e pgAdmin exposto em `5050`

- `GET /api/status`
  Resultado: `200 OK`

- `GET /actuator/health`
  Resultado: `200 OK`

- `GET /swagger-ui.html`
  Resultado: redirecionamento `302` para `/swagger-ui/index.html`

- `GET /v3/api-docs`
  Resultado: `200 OK`

- `GET http://localhost:5050`
  Resultado: redirecionamento `302` para `/browser/`

- `pg_isready`
  Resultado: PostgreSQL aceitando conexões

## Log de Iterações

### Iteração 1

- Foi criado um bootstrap mínimo em Spring Boot para suportar a validação da infraestrutura sem invadir o escopo arquitetural da `HISTORIA-002`.
- Foi criado `docker-compose.yml` com serviços separados para aplicação e PostgreSQL.
- Foi criado `Dockerfile` multi-stage para build e execução da aplicação.
- Foi registrada documentação mínima no `README.md`.
- A validação operacional confirmou aplicação em `8080`, Swagger acessível pela rota própria e PostgreSQL disponível em `5433`.

### Iteração 2

- O caminho de build foi endurecido para Gradle em container via `Makefile`, eliminando dependência do Gradle local.
- O alvo `make unit-test` foi ajustado para executar em workspace temporário dentro do container, evitando falhas de permissão no repositório local.
- O `pgAdmin` foi corrigido com e-mail válido e passou a subir corretamente na porta `5050`.
- Foi adicionado `spring-boot-starter-validation` e removida a configuração explícita do dialeto PostgreSQL para eliminar warnings desnecessários do bootstrap.

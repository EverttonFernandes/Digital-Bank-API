# Entrega 001

## Identificação da Entrega

- Data da entrega: `2026-04-06`
- Ordem cronológica da entrega: `001`
- Nome da entrega: `Ambiente Docker e PostgreSQL`
- História, fatia ou objetivo atendido: `HISTORIA-001`
- Tipo de versão sugerida: `MINOR`

## Resumo Executivo

Foi entregue a primeira base executável do projeto. A API agora sobe em container próprio, o PostgreSQL sobe em
container separado e o Swagger fica acessível para inspeção manual dos contratos. Isso reduz a fricção de validação e
cria um ambiente previsível para as próximas histórias.

## Linguagem Ubíqua da Entrega

- `Ambiente isolado`: aplicação e banco executando de forma separada, mas coordenada
- `Base PostgreSQL`: banco principal usado pela API
- `Swagger`: interface para visualizar e testar os contratos HTTP da aplicação
- `Bootstrap da aplicação`: estrutura mínima para a API iniciar corretamente

## Problema de Negócio

Antes desta entrega, o projeto não tinha uma forma simples e reproduzível de ser iniciado. Isso dificultava validação,
demonstração e continuidade técnica. Depois desta entrega, qualquer pessoa com Docker consegue subir a aplicação e o
banco com portas claras e comportamento previsível.

## O Que Foi Entregue

- foi criada a base Gradle da API com Spring Boot
- foi criado um bootstrap mínimo da aplicação para responder HTTP
- foi configurado PostgreSQL como banco principal da stack local
- foi configurado pgAdmin para inspecao web do banco
- foi criado `docker-compose.yml` com aplicação e banco em serviços separados
- foi criado `Dockerfile` multi-stage para build e execução da aplicação
- foi disponibilizado Swagger pela própria aplicação
- foi criado `Makefile` com comandos operacionais essenciais
- foi atualizado o `README.md` com instruções objetivas para subir e validar o ambiente

## O Que Não Foi Entregue

- regras de domínio de contas
- transferência entre contas
- movimentações financeiras
- notificação
- testes unitários de domínio
- tratamento padronizado de erros de negócio

## Tradução Entre Técnica e Negócio

- `Docker Compose com aplicação e PostgreSQL`
  Impacto no domínio: cria uma base estável para que o sistema rode sempre do mesmo jeito
  Benefício para o negócio: reduz falhas de ambiente e acelera a validação da entrega

- `PostgreSQL exposto em porta 5433 e aplicação em 8080`
  Impacto no domínio: separa claramente a responsabilidade de persistência da responsabilidade HTTP
  Benefício para o negócio: facilita inspeção operacional e entendimento da solução

- `Swagger publicado pela aplicação`
  Impacto no domínio: torna os contratos da API navegáveis desde o início
  Benefício para o negócio: simplifica demonstração, teste manual e avaliação técnica

## Regras de Negócio Atendidas

- o projeto pode ser iniciado em ambiente isolado
- a aplicação e o banco usam portas diferentes
- a API expõe uma interface navegável para consulta de contratos

## Endpoints ou Comportamentos Disponibilizados

- `GET /api/status`
  Objetivo: confirmar que a aplicação subiu corretamente
  Resultado esperado: retorna `200 OK` com status da aplicação

- `GET /actuator/health`
  Objetivo: verificar saúde operacional da aplicação
  Resultado esperado: retorna `200 OK` com status `UP`

- `GET /swagger-ui.html`
  Objetivo: acessar a interface Swagger
  Resultado esperado: redireciona para a interface web do Swagger

- `GET /v3/api-docs`
  Objetivo: acessar a especificação OpenAPI em JSON
  Resultado esperado: retorna `200 OK` com os contratos disponíveis

- `GET http://localhost:5050`
  Objetivo: acessar o pgAdmin
  Resultado esperado: redireciona para a interface web do pgAdmin

## Estratégia Técnica Aplicada

Foi adotada uma solução mínima e pragmática: um bootstrap em Spring Boot com configuração externa de banco via variáveis
de ambiente, Dockerfile multi-stage para empacotamento da aplicação, `docker-compose.yml` orquestrando aplicação,
PostgreSQL e pgAdmin, e `Makefile` para simplificar a operação do ambiente.

## Evidências de Validação

- `make unit-test` executado com sucesso via Gradle em container
- `docker compose up --build -d` executado com sucesso
- `docker compose ps` confirmando aplicação em `8080`, PostgreSQL em `5433` e pgAdmin em `5050`
- `GET /api/status` retornando `200 OK`
- `GET /actuator/health` retornando `200 OK`
- `GET /swagger-ui.html` respondendo com redirecionamento válido
- `GET /v3/api-docs` retornando `200 OK`
- `GET http://localhost:5050` respondendo com redirecionamento válido
- `pg_isready` confirmando PostgreSQL aceitando conexões

## Arquivos ou Módulos Mais Relevantes

- `build.gradle`
- `settings.gradle`
- `Makefile`
- `Dockerfile`
- `docker-compose.yml`
- `src/main/java/com/avaliadora/digitalbankapi/bootstrap/`
- `src/main/resources/application.properties`
- `README.md`

## Riscos, Limitações ou Pendências

- a aplicação ainda está em bootstrap mínimo e sem camadas de domínio
- a estrutura arquitetural completa fica para a `HISTORIA-002`
- ainda não existem testes unitários reais além da validação de bootstrap

## Relação com a Spec Principal

Esta entrega concretiza a base operacional do desafio: ambiente local simples, previsível, com banco isolado e
documentação HTTP acessível para início das validações.

## Pronto Para Fechamento de Versão

- esta entrega está documentada em ordem cronológica
- a documentação reflete o que realmente foi implementado
- a classificação semântica proposta está coerente
- o documento pode acompanhar o commit de fechamento da versão
- a entrega está pronta para ser associada a uma tag semântica

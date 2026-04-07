# Entrega 002

## Identificação da Entrega

- Data da entrega: `2026-04-07`
- Ordem cronológica da entrega: `002`
- Nome da entrega: `Estrutura Base da Aplicação`
- História, fatia ou objetivo atendido: `HISTORIA-002`
- Tipo de versão sugerida: `MINOR`

## Resumo Executivo

Foi criada a base estrutural da aplicação para sustentar as próximas histórias funcionais. A API agora possui separação explícita entre entrada HTTP, aplicação, domínio, infraestrutura e componentes compartilhados, reduzindo retrabalho estrutural e preparando a evolução para contas, transferências e movimentações.

## Linguagem Ubíqua da Entrega

- `Camada API`: ponto de entrada HTTP da aplicação
- `Camada Application`: orquestração dos casos de uso
- `Camada Domain`: núcleo conceitual das regras de negócio
- `Camada Infrastructure`: detalhes técnicos de persistência e integração
- `Persistência preparada`: configuração inicial que permite evoluir para entidades e repositórios reais

## Problema de Negócio

Antes desta entrega, a aplicação subia, mas a estrutura interna ainda não refletia a arquitetura do projeto. Isso criava risco de crescimento desorganizado nas próximas histórias. Depois desta entrega, a base já está separada em camadas claras e preparada para evolução disciplinada.

## O Que Foi Entregue

- foi criada a organização de pacotes em `api`, `application`, `domain`, `infrastructure` e `shared`
- o endpoint de status foi movido para a camada de API
- foi criado um serviço de aplicação para responder o status da API
- a classe principal da aplicação foi movida para o pacote raiz para habilitar o component scan correto
- foi criada a configuração base de persistência em `infrastructure/config`
- foram materializados os pacotes estruturais previstos na arquitetura

## O Que Não Foi Entregue

- regras funcionais de contas
- entidades persistentes reais
- repositórios JPA concretos
- regras de transferência
- movimentações financeiras
- notificações

## Tradução Entre Técnica e Negócio

- `Separação em camadas da aplicação`
  Impacto no domínio: organiza onde cada responsabilidade deve viver
  Benefício para o negócio: reduz retrabalho e facilita crescimento da solução sem desordem

- `Classe principal no pacote raiz`
  Impacto no domínio: garante que a aplicação reconheça corretamente as camadas criadas
  Benefício para o negócio: evita falhas de inicialização e regressões de roteamento

- `Configuração base de persistência`
  Impacto no domínio: prepara o caminho para armazenar contas e movimentações nas próximas histórias
  Benefício para o negócio: acelera a evolução futura sem reestruturação técnica cara

## Regras de Negócio Atendidas

- a base estrutural da aplicação deve refletir a arquitetura documentada
- a aplicação deve continuar compilando e subindo após a reorganização
- a persistência deve estar preparada para evolução posterior

## Endpoints ou Comportamentos Disponibilizados

- `GET /api/status`
  Objetivo: confirmar que a aplicação segue funcional após a reorganização estrutural
  Resultado esperado: retorna `200 OK`

- `GET /actuator/health`
  Objetivo: verificar saúde operacional da aplicação
  Resultado esperado: retorna `200 OK`

## Estratégia Técnica Aplicada

Foi adotada uma materialização mínima e explícita da arquitetura: o endpoint existente foi reposicionado entre `api` e `application`, a classe principal foi corrigida para o pacote raiz e os demais pacotes estruturais foram criados com foco em clareza e preparação para as próximas histórias, sem antecipar regra de negócio.

## Evidências de Validação

- `make unit-test` executado com sucesso
- `make up` executado com sucesso
- `docker compose ps` confirmando a stack íntegra
- `GET /api/status` retornando `200 OK`
- `GET /actuator/health` retornando `200 OK`

## Arquivos ou Módulos Mais Relevantes

- `src/main/java/com/cwi/digitalbankapi/DigitalBankApiApplication.java`
- `src/main/java/com/cwi/digitalbankapi/api/controller/`
- `src/main/java/com/cwi/digitalbankapi/application/service/`
- `src/main/java/com/cwi/digitalbankapi/infrastructure/config/PersistenceConfiguration.java`
- `src/main/java/com/cwi/digitalbankapi/**/package-info.java`

## Riscos, Limitações ou Pendências

- ainda não existem entidades, repositories e specifications reais
- a próxima história precisa materializar o primeiro recorte funcional do domínio

## Relação com a Spec Principal

Esta entrega concretiza a preparação estrutural da aplicação para que as próximas histórias funcionais sejam implementadas sobre uma base coerente com a arquitetura do projeto.

## Pronto Para Fechamento de Versão

- esta entrega está documentada em ordem cronológica
- a documentação reflete o que realmente foi implementado
- a classificação semântica proposta está coerente
- o documento pode acompanhar o commit de fechamento da versão
- a entrega está pronta para ser associada a uma tag semântica

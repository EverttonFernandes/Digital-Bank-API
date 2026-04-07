# HISTORIA-001 — Ambiente Docker e PostgreSQL Isolado

## Tipo

- não funcional

## Objetivo da História

Disponibilizar um ambiente local simples e previsível em que:

- a aplicação rode isoladamente
- o PostgreSQL rode isoladamente
- a documentação Swagger fique acessível
- cada componente use sua própria porta

## Valor de Negócio

Esta história reduz o esforço de validação do avaliador e aumenta a previsibilidade do projeto.

Em linguagem simples:

- qualquer pessoa deve conseguir subir o sistema sem instalar manualmente um banco
- o ambiente precisa ser reproduzível
- o acesso à API e ao banco precisa ser claro

## Linguagem Ubíqua

- `Ambiente isolado`: execução desacoplada de aplicação e banco
- `Swagger`: interface para consultar e testar os contratos da API
- `Base PostgreSQL`: banco principal da aplicação

## Critérios de Aceite

- deve existir `docker-compose` para subir a aplicação e o PostgreSQL
- PostgreSQL deve usar porta diferente da aplicação
- Swagger deve ficar acessível em rota própria da aplicação
- configuração local deve ser simples de executar
- deve ficar claro no README como subir o ambiente

## Tradução Entre Técnica e Negócio

- `Docker Compose`
  Impacto no domínio: padroniza como os componentes sobem juntos
  Benefício para o negócio: facilita validação e evita divergência de ambiente

- `PostgreSQL em porta separada`
  Impacto no domínio: separa claramente persistência e aplicação
  Benefício para o negócio: reduz confusão operacional e facilita inspeção dos dados

## Dependências

- nenhuma

## Próxima História Natural

- `HISTORIA-002` Estrutura base da aplicação

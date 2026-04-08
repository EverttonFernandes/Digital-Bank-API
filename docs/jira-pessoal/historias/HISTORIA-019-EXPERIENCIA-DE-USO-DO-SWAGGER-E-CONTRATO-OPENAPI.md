# HISTORIA-019 - EXPERIENCIA DE USO DO SWAGGER E CONTRATO OPENAPI

## Objetivo de Negócio

Transformar o Swagger/OpenAPI em um guia real de uso da API, permitindo que qualquer pessoa consiga:

- criar uma conta
- realizar uma transferência
- consultar movimentações
- consultar notificações

sem depender de leitura prévia do código-fonte.

## Objetivo Técnico

Refatorar a documentação OpenAPI para que ela reflita com precisão:

- propósito de cada endpoint
- semântica de cada DTO
- exemplos reais de request e response
- respostas de sucesso e de falha
- fluxo recomendado de uso da API

## Problema Atual

O Swagger atual já documenta endpoints, mas ainda não ensina o consumidor da API a percorrer a jornada completa do
produto. Isso reduz a usabilidade da documentação e enfraquece a apresentação da entrega.

## Resultado Esperado

Ao abrir o Swagger, o consumidor deve entender:

1. como criar uma conta
2. como transferir valores entre contas
3. como consultar o histórico financeiro gerado
4. como consultar as notificações registradas
5. quais erros esperar em cada operação

## Critérios de Aceite

- cada endpoint principal deve ter `summary` e `description` orientados a uso
- cada request DTO deve ter descrição clara em todos os atributos
- cada response principal deve ter descrição clara em todos os atributos
- `POST /accounts` deve mostrar exemplos reais de criação de conta
- `POST /transfers` deve mostrar exemplos reais de transferência
- `GET /accounts/{id}/movements` deve deixar claro o que a coleção representa
- `GET /accounts/{id}/notifications` deve deixar claro o que a coleção representa
- respostas de erro devem estar explicitamente documentadas com exemplos
- a descrição geral do OpenAPI deve orientar a jornada principal de uso da API
- `make unit-test` deve permanecer verde
- `make functional-test` deve permanecer verde

## Fora de Escopo

- criação de novos endpoints
- mudança de comportamento funcional da API
- alteração do domínio de negócio

## Valor para Apresentação

Esta história melhora a percepção de qualidade da API porque transforma a documentação em uma experiência guiada de
consumo, e não apenas em um inventário técnico de rotas.

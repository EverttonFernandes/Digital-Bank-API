# Entrega 019 — Experiência de Uso do Swagger e Contrato OpenAPI

## O que foi entregue

Esta entrega transformou o Swagger em uma experiência guiada de consumo da API.

Agora a documentação ensina de forma mais clara:

- como criar uma conta
- como realizar uma transferência
- como consultar as movimentações geradas
- como consultar as notificações registradas

## Tradução entre Técnica e Negócio

Antes desta entrega, o contrato OpenAPI já existia, mas ainda era mais técnico do que didático.

Depois desta entrega, a API passou a se explicar melhor sozinha. Isso reduz a curva de aprendizado para quem vai
avaliar, integrar ou demonstrar o projeto.

## Principais Melhorias

- descrição geral da API orientada a uma jornada de uso real
- exemplos de request e response para criação de conta e transferência
- exemplos explícitos de erro para cenários principais
- descrições mais claras dos atributos dos DTOs e dos resources HAL
- descrições mais pedagógicas para os endpoints de movimentos e notificações

## Ajuste Estrutural Encontrado na Validação

Durante o fechamento da história foi identificado um problema de runtime que os testes anteriores não tinham revelado:
os métodos derivados dos repositórios JPA de movimentações e notificações ainda apontavam para `accountId`, quando o
mapeamento persistido passou a depender da navegação por `account.id`.

Esse ponto foi corrigido na mesma entrega para manter a aplicação realmente funcional após o rebuild do ambiente.

## Como validar

- `make unit-test`
- `make functional-test`
- `make up`
- acessar `http://localhost:8080/v3/api-docs`
- acessar `http://localhost:8080/swagger-ui.html`

## Resultado esperado

Ao abrir o Swagger, o consumidor da API deve conseguir entender a jornada principal do produto e visualizar exemplos
concretos de uso sem depender de leitura prévia do código-fonte.

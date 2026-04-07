# HISTORIA-010 — Testes Funcionais

## Tipo

- não funcional

## Objetivo da História

Consolidar e ampliar o comportamento end-to-end da API com seeders, fixtures e validação do estado final, partindo do setup inicial introduzido na `HISTORIA-004`.

## Valor de Negócio

Esta história amplia e consolida a prova de que a solução funciona de ponta a ponta no ambiente real do projeto.

## Critérios de Aceite

- testes devem usar `fixtures + seeders`
- testes devem seguir `GIVEN / WHEN / THEN`
- cenários de sucesso e falha devem ficar separados
- deve existir validação por `GET` do estado final quando possível
- mensagens `key` e `value` devem ser validadas em falhas de negócio
- estratégia deve respeitar heurística VADER
- suíte funcional iniciada na `HISTORIA-004` deve ser expandida para cobrir o restante do domínio

## Dependências

- `HISTORIA-001`
- `HISTORIA-003`
- `HISTORIA-004`
- `HISTORIA-005`
- `HISTORIA-006`
- `HISTORIA-007`

## Próxima História Natural

- `HISTORIA-011` README final

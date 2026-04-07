# IMPLEMENTATION — HISTORIA-009

## Contexto da História

Esta história consolida a cobertura unitária da solução.

Ela garante que comportamento interno, regras de negócio e componentes centrais estejam protegidos contra regressão.

Ela nasce diretamente de:

- `spec-driven-development.md`
- `docs/jira-pessoal/KANBAN.md`
- `docs/jira-pessoal/historias/HISTORIA-009-TESTES-UNITARIOS.md`

## Objetivo da Entrega Atual

Construir a suíte de testes unitários aderente ao padrão do projeto.

## Escopo Incluído

Esta entrega deve incluir:

- testes unitários para converters relevantes
- testes unitários para specifications relevantes
- testes unitários para services ou casos de uso relevantes
- testes unitários para comportamentos importantes do domínio

## Escopo Explicitamente Não Incluído

Esta história não substitui:

- testes funcionais
- documentação de entrega final

## Critérios de Aceite

- converters devem ter testes
- specifications devem ter testes
- services devem ter testes
- domínio relevante deve ter testes
- `DisplayName` deve estar em português
- `GIVEN / WHEN / THEN` deve ser respeitado

## Tradução Entre Técnica e Negócio

- `Testes unitários`
  Impacto no domínio: validam regras e comportamentos de forma isolada
  Benefício para o negócio: reduzem regressão e aumentam confiança na evolução da solução

## Estratégia Técnica

Organizar a cobertura unitária de forma coerente com a arquitetura:

- converter testa entrada e transformação
- specification testa regra de negócio
- service testa orquestração
- domínio testa comportamento interno

## Camadas Afetadas

- testes das camadas `application`, `domain` e eventualmente `api`

## Documentos Normativos que Devem Ser Respeitados

- `padroes-de-testes.md`
- `codigo-limpo.md`

## Estratégia de Testes

Esta própria história é focada em testes.

### Regras obrigatórias

- `@DisplayName` em português
- `GIVEN / WHEN / THEN`
- `BDDMockito.given`
- `Assertions.*`

## Arquivos Provavelmente Afetados

- testes unitários em Java
- fixtures auxiliares de testes unitários

## Riscos e Pontos de Atenção

- testes pouco didáticos reduzem legibilidade
- cobrir só o caminho feliz enfraquece a suíte
- misturar responsabilidade demais num teste dificulta manutenção

## Restrições Pragmáticas e Padrões

- não criar testes genéricos demais
- manter nomes totalmente didáticos
- cobrir comportamento relevante de verdade

## Checklist de Conclusão

- [x] converters relevantes cobertos
- [x] specifications relevantes cobertas
- [x] services relevantes cobertos
- [x] domínio relevante coberto
- [x] padrão do projeto respeitado integralmente

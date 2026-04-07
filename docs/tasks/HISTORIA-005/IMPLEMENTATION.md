# IMPLEMENTATION — HISTORIA-005

## Contexto da História

Esta história amplia a transferência entre contas ao registrar seus efeitos no histórico financeiro do sistema.

Ela transforma uma simples alteração de saldo em um fluxo auditável, rastreável e consultável.

Ela nasce diretamente de:

- `spec-driven-development.md`
- `docs/jira-pessoal/KANBAN.md`
- `docs/jira-pessoal/historias/HISTORIA-005-MOVIMENTACOES-FINANCEIRAS.md`

## Objetivo da Entrega Atual

Registrar e expor as movimentações financeiras produzidas pela transferência entre contas.

Nesta etapa, o sistema deve deixar claro:

- qual conta foi debitada
- qual conta foi creditada
- qual valor foi movimentado
- qual transferência originou a movimentação

## Escopo Incluído

Esta entrega deve incluir:

- modelagem da movimentação financeira
- geração de débito para conta de origem
- geração de crédito para conta de destino
- vínculo entre movimentação e transferência
- endpoint para consulta de movimentações por conta
- ordenação coerente do histórico

## Escopo Explicitamente Não Incluído

Esta entrega ainda não precisa incluir:

- notificações completas pós-transferência
- observabilidade avançada
- filtros complexos de histórico além do necessário para o desafio

## Critérios de Aceite

- transferência concluída deve gerar movimentação de débito para origem
- transferência concluída deve gerar movimentação de crédito para destino
- movimentações devem estar associadas à transferência correta
- deve ser possível consultar movimentações por conta
- retornos devem refletir operações concluídas
- ordenação por data deve ser coerente e preferencialmente decrescente

## Tradução Entre Técnica e Negócio

- `Movimentação de débito`
  Impacto no domínio: representa a saída de valor da conta de origem
  Benefício para o negócio: permite rastrear exatamente quando e por que o saldo foi reduzido

- `Movimentação de crédito`
  Impacto no domínio: representa a entrada de valor na conta de destino
  Benefício para o negócio: permite rastrear exatamente quando e por que o saldo foi aumentado

- `Vínculo entre movimentação e transferência`
  Impacto no domínio: conecta o histórico financeiro à operação que o gerou
  Benefício para o negócio: melhora auditabilidade e clareza operacional

## Estratégia Técnica

Esta entrega deve complementar a transferência sem violar a arquitetura definida.

### Domínio

Criar a representação da movimentação financeira com os campos necessários para rastrear:

- conta
- transferência de origem
- tipo da movimentação
- valor
- descrição
- data de criação

### Integração com a Transferência

Durante a operação de transferência:

- gerar débito para origem
- gerar crédito para destino
- persistir ambas as movimentações dentro do mesmo contexto transacional

### Consulta

Disponibilizar endpoint para consulta de movimentações por conta:

- `GET /accounts/{id}/movements`

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

Esta história precisa comprovar que o histórico financeiro espelha corretamente a transferência.

### Testes unitários esperados

- comportamento de criação de movimentação
- service ou caso de uso que associa movimentações à transferência
- consulta de movimentações por conta

### Testes funcionais esperados

#### Cenários de sucesso

- transferência deve gerar débito na conta de origem
- transferência deve gerar crédito na conta de destino
- consulta de movimentações deve retornar histórico coerente

#### Cenários de falha

- consulta de movimentações para conta inexistente

### Regras obrigatórias dos testes funcionais

- seguir `GIVEN / WHEN / THEN`
- validar o resultado da transferência
- validar o histórico retornado por `GET /accounts/{id}/movements`
- validar que as movimentações possuem vínculo correto com a transferência

## Arquivos Provavelmente Afetados

Arquivos esperados nesta entrega:

- entidade ou agregado de movimentação
- repositório de movimentação
- service ou caso de uso de consulta de movimentações
- ajustes na história de transferência para persistir débito e crédito
- controller de movimentações
- DTOs de resposta de movimentações
- testes unitários e funcionais relacionados

## Riscos e Pontos de Atenção

- gerar movimentações fora da transação pode comprometer consistência
- descrição pouco clara da movimentação pode prejudicar rastreabilidade
- consulta mal modelada pode dificultar leitura do histórico
- acoplamento excessivo entre transferência e consulta pode enfraquecer o desenho

## Restrições Pragmáticas e Padrões

- gerar apenas as movimentações necessárias para o desafio
- não criar modelos excessivamente genéricos de extrato além do que a spec pede
- manter nomenclatura de débito e crédito totalmente clara
- preservar a rastreabilidade entre conta, transferência e movimentação

## Checklist de Conclusão

- [x] modelo de movimentação criado
- [x] débito para origem implementado
- [x] crédito para destino implementado
- [x] vínculo entre movimentação e transferência implementado
- [x] `GET /accounts/{id}/movements` implementado
- [x] testes unitários da história criados
- [x] testes funcionais da história criados
- [x] histórico financeiro refletindo corretamente as transferências concluídas

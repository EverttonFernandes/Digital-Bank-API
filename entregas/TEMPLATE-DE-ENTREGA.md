# Template de Entrega

## Identificação da Entrega

- Data da entrega:
- Ordem cronológica da entrega:
- Nome da entrega:
- História, fatia ou objetivo atendido:
- Tipo de versão sugerida: `MAJOR` | `MINOR` | `PATCH`

## Resumo Executivo

Descreva em linguagem simples:

- o que foi entregue
- qual problema isso resolve
- quem é impactado por essa entrega
- qual valor prático ela gera

Este resumo deve ser compreensível para pessoas técnicas e não técnicas.

## Linguagem Ubíqua da Entrega

Liste os principais termos de negócio usados nesta entrega e o que eles significam no contexto do domínio.

Exemplo:

- `Conta`: representação de uma conta bancária que possui titular e saldo
- `Transferência`: operação que move um valor de uma conta de origem para uma conta de destino
- `Movimentação`: registro histórico de débito ou crédito gerado por uma operação financeira
- `Notificação`: comunicação gerada após uma transferência concluída com sucesso

## Problema de Negócio

Explique com clareza:

- qual era a necessidade do negócio
- qual limitação existia antes
- o que precisava passar a acontecer depois desta entrega

Evite jargão técnico desnecessário.

## O Que Foi Entregue

Liste de forma didática o que foi efetivamente implementado.

Cada item deve ser descrito de forma que alguém de produto, negócio ou engenharia consiga entender.

Exemplos:

- foi criada a operação de transferência entre contas
- foi adicionada a validação para impedir transferência com saldo insuficiente
- foi disponibilizada a consulta de movimentações por conta
- foi registrado o envio de notificação após transferência concluída

## O Que Não Foi Entregue

Descreva explicitamente o que ficou fora desta entrega.

Isso evita falsa expectativa e ajuda a separar:

- o que já está disponível
- o que ainda depende de próximas etapas

## Tradução Entre Técnica e Negócio

Esta seção é obrigatória.

Ela deve traduzir decisões técnicas para linguagem de negócio usando linguagem ubíqua.

Use a estrutura:

- decisão técnica
- impacto no domínio
- benefício para o negócio

Exemplo:

- `Lock pessimista nas contas durante a transferência`
  Impacto no domínio: garante que duas operações concorrentes não corrompam o saldo da conta
  Benefício para o negócio: evita inconsistência financeira e aumenta a confiabilidade da operação

- `Specification para saldo insuficiente`
  Impacto no domínio: centraliza a regra que impede transferências inválidas
  Benefício para o negócio: garante que a conta nunca transfira um valor maior do que possui

- `Observer para notificação`
  Impacto no domínio: desacopla a transferência da etapa de comunicação
  Benefício para o negócio: facilita evoluir para e-mail, SMS, fila ou auditoria sem reescrever a operação principal

## Regras de Negócio Atendidas

Liste as regras de negócio atendidas nesta entrega em linguagem clara.

Exemplo:

- a conta de origem deve existir
- a conta de destino deve existir
- origem e destino não podem ser a mesma conta
- o valor transferido deve ser maior que zero
- a conta de origem deve ter saldo suficiente
- após a transferência concluída, uma notificação deve ser registrada

## Endpoints ou Comportamentos Disponibilizados

Liste os endpoints ou comportamentos entregues.

Use um formato didático:

- método HTTP e rota
- objetivo do endpoint
- resultado esperado

Exemplo:

- `POST /transfers`
  Objetivo: realizar uma transferência entre contas
  Resultado esperado: transfere o valor, gera movimentações e registra notificação

- `GET /accounts/{accountId}/transactions`
  Objetivo: consultar o histórico financeiro da conta
  Resultado esperado: retorna as movimentações da conta informada

## Estratégia Técnica Aplicada

Explique resumidamente como a solução foi construída.

Descreva apenas o que for importante para entendimento da entrega.

Exemplos:

- separação entre controller, converter, domínio, specification e persistência
- uso de transação para garantir consistência
- uso de observer para notificação
- uso de testes unitários e funcionais para comprovar comportamento

## Evidências de Validação

Descreva como a entrega foi validada.

Cobrir:

- testes unitários criados
- testes funcionais criados
- cenários de sucesso validados
- cenários de falha validados
- validação end-to-end do estado final

Exemplo:

- foram criados testes unitários para converter, specification e service de transferência
- foram criados testes funcionais de sucesso e falha para a API de transferência
- os testes funcionais validaram status code, mensagens `key/value` e estado final por `GET`

## Arquivos ou Módulos Mais Relevantes

Liste os principais arquivos, módulos ou áreas alteradas.

Não transforme isso em changelog técnico excessivo.

O objetivo é orientar leitura futura.

## Riscos, Limitações ou Pendências

Descreva:

- riscos conhecidos
- limitações intencionais
- melhorias futuras
- pontos que dependerão de próximas entregas

## Relação com a Spec Principal

Explique qual parte de `spec-driven-development.md` esta entrega concretiza.

Exemplo:

- esta entrega cobre a primeira fatia de transferência entre contas
- esta entrega cobre a consulta de movimentações
- esta entrega cobre a infraestrutura inicial de notificação

## Pronto Para Fechamento de Versão

Confirmar:

- esta entrega está documentada em ordem cronológica
- a documentação reflete o que realmente foi implementado
- a classificação semântica proposta está coerente
- o documento pode acompanhar o commit de fechamento da versão
- a entrega está pronta para ser associada a uma tag semântica

## Instruções de Preenchimento

Este template deve sempre ser preenchido:

- em português
- com linguagem ubíqua
- sem abreviações desnecessárias
- com tradução entre detalhe técnico e valor de negócio
- de forma que pessoas técnicas e não técnicas entendam o que foi feito

Se houver conflito entre linguagem técnica e clareza de negócio, prefira explicar o termo técnico e traduzir seu impacto
no domínio.

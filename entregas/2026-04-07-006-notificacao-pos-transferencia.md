# Entrega 006 — Notificacao Pos-Transferencia

## Identificacao da Entrega

- Data da entrega: 2026-04-07
- Ordem cronologica da entrega: 006
- Nome da entrega: Notificacao pos-transferencia
- Historia, fatia ou objetivo atendido: `HISTORIA-006`
- Tipo de versao sugerida: `MINOR`

## Resumo Executivo

Esta entrega adicionou comunicacao rastreavel ao fluxo principal do banco digital. Depois dela, uma transferencia concluida com sucesso nao altera apenas saldo e historico financeiro: ela tambem registra notificacoes consultaveis para as contas envolvidas.

O resultado e um fluxo mais coerente com o dominio financeiro e mais preparado para evoluir depois para e-mail, SMS, fila ou outros canais.

## Linguagem Ubiqua da Entrega

- `Notificacao`: registro de comunicacao gerado pela conclusao da transferencia
- `Observer`: componente que reage ao evento de transferencia concluida
- `Conta de origem`: conta que envia o valor e recebe confirmacao de envio
- `Conta de destino`: conta que recebe o valor e recebe confirmacao de recebimento
- `Status da notificacao`: estado do registro criado para aquela comunicacao

## Problema de Negocio

Antes desta entrega, o sistema transferia saldo e registrava movimentacoes, mas ainda nao mostrava nenhuma consequencia de comunicacao apos o sucesso da operacao. Isso limitava a auditabilidade do processo e fechava pouco o fluxo do ponto de vista do cliente.

Era necessario deixar evidente que a transferencia concluida pode disparar comportamentos adicionais sem acoplar tudo ao caso de uso principal.

## O Que Foi Entregue

- foi criada a tabela `account_notification`
- foi criado o modelo de dominio de notificacao
- foi criado um publisher de evento de transferencia concluida
- foi criado um observer que registra notificacoes para origem e destino
- foi criado o endpoint `GET /accounts/{id}/notifications`
- foram criados testes unitarios da consulta de notificacoes
- foram criados testes unitarios do observer de notificacao
- foram criados testes funcionais de sucesso e falha para notificacoes
- o runner funcional foi ajustado para esperar a aplicacao ficar pronta antes do seed

## O Que Nao Foi Entregue

- envio real de e-mail
- envio real de SMS
- integracao com fila externa
- padronizacao final de todos os contratos de erro da API

## Traducao Entre Tecnica e Negocio

- `Observer explicito para transferencia concluida`
  Impacto no dominio: a transferencia deixa de conhecer diretamente a implementacao concreta da notificacao
  Beneficio para o negocio: prepara o sistema para evoluir canais futuros sem reescrever o nucleo da operacao

- `Registro consultavel por conta`
  Impacto no dominio: cada conta passa a ter um historico minimo de comunicacoes relevantes
  Beneficio para o negocio: melhora transparência e rastreabilidade da operacao

- `Notificacao persistida junto do fluxo bem-sucedido`
  Impacto no dominio: a comunicacao fica coerente com a transferencia efetivamente realizada
  Beneficio para o negocio: evita notificacoes fantasmas em cenarios de falha

## Regras de Negocio Atendidas

- transferencia bem-sucedida deve gerar notificacao para a conta de origem
- transferencia bem-sucedida deve gerar notificacao para a conta de destino
- transferencia com falha nao deve gerar notificacao indevida
- consulta de notificacoes deve falhar com `ACCOUNT_NOT_FOUND` quando a conta nao existir

## Endpoints ou Comportamentos Disponibilizados

- `GET /accounts/{id}/notifications`
  Objetivo: consultar as notificacoes registradas para a conta
  Resultado esperado: retornar a lista ordenada das notificacoes da conta ou erro coerente para conta inexistente

## Estrategia Tecnica Aplicada

Foi aplicado `Observer` de forma parcimoniosa no ponto exato em que ele agrega valor: a consequencia da transferencia concluida. O `TransferService` publica um evento de transferencia concluida e o observer `RegisterTransferNotificationObserver` materializa as notificacoes sem acoplar o fluxo principal ao detalhe da persistencia.

Para manter confianca na automacao, a suite funcional passou a esperar a disponibilidade da aplicacao antes de preparar a massa. Os cenarios de notificacao validam o efeito end-to-end via `GET` e confirmam que falhas de transferencia nao criam registros indevidos.

## Evidencias de Validacao

- `make unit-test` com `BUILD SUCCESS`
- `make functional-test` com `14` testes passando
- cenario funcional de sucesso para `GET /accounts/{id}/notifications`
- cenario funcional de falha para conta inexistente em `GET /accounts/{id}/notifications`
- cenario funcional garantindo ausencia de notificacao quando a transferencia falha

## Arquivos ou Modulos Mais Relevantes

- `src/main/java/com/cwi/digitalbankapi/infrastructure/notification/SpringTransferCompletedEventPublisher.java`
- `src/main/java/com/cwi/digitalbankapi/application/service/RegisterTransferNotificationObserver.java`
- `src/main/java/com/cwi/digitalbankapi/api/controller/AccountNotificationController.java`
- `src/main/java/com/cwi/digitalbankapi/domain/notification`
- `src/main/resources/db/migration/V3__create_account_notification_table.sql`
- `__functional_tests__/src/endpoints/accounts/GET/accountNotificationsGetSuccess.spec.js`

## Riscos, Limitacoes ou Pendencias

- notificacao ainda e apenas registro persistido, nao canal externo real
- a API ainda sera reforcada com padronizacao global de mensagens na historia seguinte
- swagger ainda precisa refletir todos os contratos finais

## Relacao com a Spec Principal

Esta entrega cobre a exigencia de notificacao apos transferencia concluida com uma solucao evolutiva e coerente com a arquitetura proposta.

## Pronto Para Fechamento de Versao

- esta entrega esta documentada em ordem cronologica
- a documentacao reflete o que realmente foi implementado
- a classificacao semantica proposta esta coerente
- o documento pode acompanhar o commit de fechamento da versao
- a entrega esta pronta para ser associada a uma tag semantica

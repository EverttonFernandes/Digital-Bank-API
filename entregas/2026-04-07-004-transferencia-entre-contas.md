# Entrega 004 — Transferencia Entre Contas

## Identificacao da Entrega

- Data da entrega: 2026-04-07
- Ordem cronologica da entrega: 004
- Nome da entrega: Transferencia entre contas
- Historia, fatia ou objetivo atendido: `HISTORIA-004`
- Tipo de versao sugerida: `MINOR`

## Resumo Executivo

Esta entrega colocou a principal capacidade do banco digital em funcionamento: transferir dinheiro entre duas contas com
validacoes claras, consistencia transacional e protecao contra concorrencia.

Ao mesmo tempo, a historia deixou pronta a base funcional em Jest para validar o fluxo principal com BDD. Isso aumenta a
confianca da entrega porque a transferencia agora foi comprovada tanto por testes unitarios quanto por testes funcionais
de ponta a ponta.

## Linguagem Ubiqua da Entrega

- `Conta de origem`: conta de onde o valor sai
- `Conta de destino`: conta para onde o valor vai
- `Transferencia`: operacao que move um valor entre duas contas
- `Saldo suficiente`: condicao necessaria para permitir o debito da conta de origem
- `Lock pessimista`: protecao tecnica para impedir conflito sobre o mesmo saldo em concorrencia

## Problema de Negocio

Antes desta entrega, o sistema conseguia apenas consultar contas. Ainda nao existia a operacao central de um banco
digital: mover saldo entre contas com seguranca.

Isso impedia que o desafio demonstrasse seu principal valor. Era necessario que a API passasse a validar regras reais de
transferencia, impedisse operacoes invalidas e mostrasse efeito concreto no saldo das contas.

## O Que Foi Entregue

- foi criado o endpoint `POST /transfers`
- foi implementado o fluxo de transferencia entre conta de origem e conta de destino
- foi adicionada validacao de valor positivo
- foi adicionada validacao para impedir transferencia entre a mesma conta
- foi adicionada validacao para saldo insuficiente
- foi adicionada validacao para conta inexistente
- foi aplicado lock pessimista na leitura das contas envolvidas
- foi criada a base inicial de testes funcionais em Jest para contas e transferencias
- foram criados testes unitarios e funcionais da transferencia

## O Que Nao Foi Entregue

- movimentacoes financeiras detalhadas
- notificacao apos transferencia
- enriquecimento de observabilidade
- tratamento global completo de erros de toda a API

## Traducao Entre Tecnica e Negocio

- `Lock pessimista nas contas da transferencia`
  Impacto no dominio: protege o saldo das contas durante a operacao critica
  Beneficio para o negocio: reduz risco de inconsistencias em acessos concorrentes

- `Specifications para transferencia`
  Impacto no dominio: centraliza regras como saldo suficiente e contas diferentes
  Beneficio para o negocio: torna a operacao previsivel e mais facil de manter

- `Setup funcional em Jest dentro da historia critica`
  Impacto no dominio: valida a principal operacao do banco digital com estado real do sistema
  Beneficio para o negocio: aumenta a confianca na entrega e reduz risco de regressao

## Regras de Negocio Atendidas

- conta de origem deve existir
- conta de destino deve existir
- origem e destino devem ser diferentes
- valor da transferencia deve ser maior que zero
- conta de origem deve possuir saldo suficiente
- operacao deve atualizar corretamente os saldos das duas contas

## Endpoints ou Comportamentos Disponibilizados

- `POST /transfers`
  Objetivo: realizar uma transferencia entre duas contas
  Resultado esperado: debita a conta de origem, credita a conta de destino e retorna o saldo final das duas contas

## Estrategia Tecnica Aplicada

A entrega foi estruturada com `TransferRequest`, `TransferRequestConverter`, modelo de dominio `Transfer`,
specifications de negocio e `TransferService` transacional. A persistencia das contas passou a oferecer leitura com lock
pessimista e salvamento consolidado dos saldos atualizados.

Na camada funcional, a estrutura `__functional_tests__` foi consolidada com seeders, fixtures, queries de apoio e
cenarios BDD para a transferencia.

## Evidencias de Validacao

- `make unit-test` com `BUILD SUCCESS`
- `make functional-test` com `9` testes passando
- cenario funcional de sucesso para `POST /transfers`
- cenarios funcionais de falha para conta inexistente, mesma conta, valor zero, valor negativo e saldo insuficiente
- validacao do estado final via `GET /accounts/{id}` apos a transferencia

## Arquivos ou Modulos Mais Relevantes

- `src/main/java/com/avaliadora/digitalbankapi/api/controller/TransferController.java`
- `src/main/java/com/avaliadora/digitalbankapi/application/service/TransferService.java`
- `src/main/java/com/avaliadora/digitalbankapi/domain/transfer`
- `src/main/java/com/avaliadora/digitalbankapi/infrastructure/persistence/repository`
- `__functional_tests__/src/endpoints/transfers/POST`

## Riscos, Limitacoes ou Pendencias

- movimentacoes e notificacao ainda dependem das historias seguintes
- o tratamento global de erros ainda sera expandido em historia especifica
- a base funcional criada aqui sera ampliada nas proximas entregas

## Relacao com a Spec Principal

Esta entrega concretiza a parte central da spec: transferencia de fundos entre contas com consistencia, regras de
negocio e base de validacao robusta.

## Pronto Para Fechamento de Versao

- esta entrega esta documentada em ordem cronologica
- a documentacao reflete o que realmente foi implementado
- a classificacao semantica proposta esta coerente
- o documento pode acompanhar o commit de fechamento da versao
- a entrega esta pronta para ser associada a uma tag semantica

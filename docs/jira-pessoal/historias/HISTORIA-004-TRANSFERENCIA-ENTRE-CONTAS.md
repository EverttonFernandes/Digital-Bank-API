# HISTORIA-004 — Transferência Entre Contas

## Tipo

- funcional

## Objetivo da História

Permitir a transferência de valor entre conta de origem e conta de destino com segurança transacional, já preparando a
confiança da entrega por meio do setup inicial dos testes funcionais em Jest.

## Valor de Negócio

Esta é a história central do projeto.

Ela demonstra a principal capacidade do banco digital: mover dinheiro entre contas de forma consistente e segura.

## Critérios de Aceite

- conta de origem deve existir
- conta de destino deve existir
- origem e destino devem ser diferentes
- valor deve ser maior que zero
- saldo da origem deve ser suficiente
- operação deve ser transacional
- lock pessimista deve proteger a concorrência
- setup base de testes funcionais com Jest deve existir
- estrutura inicial de seeders, fixtures e separação entre sucesso e falha deve estar pronta para a transferência

## Dependências

- `HISTORIA-003`

## Próxima História Natural

- `HISTORIA-005` Movimentações financeiras

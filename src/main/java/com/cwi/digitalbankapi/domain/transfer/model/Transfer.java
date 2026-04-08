package com.cwi.digitalbankapi.domain.transfer.model;

import com.cwi.digitalbankapi.domain.account.model.Account;
import com.cwi.digitalbankapi.domain.notification.model.TransferCompletedEvent;
import com.cwi.digitalbankapi.domain.statement.model.AccountMovement;
import com.cwi.digitalbankapi.domain.statement.model.AccountMovementType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record Transfer(
        Account sourceAccount,
        Account targetAccount,
        BigDecimal amount
) {
    public void apply() {
        sourceAccount.debit(amount);
        targetAccount.credit(amount);
    }

    public List<AccountMovement> createAccountMovements(String transferReference, OffsetDateTime movementCreatedAt) {
        return List.of(
                new AccountMovement(
                        null,
                        sourceAccount,
                        transferReference,
                        AccountMovementType.DEBIT,
                        amount,
                        "Debito gerado pela transferencia para a conta " + targetAccount.getId() + ".",
                        movementCreatedAt
                ),
                new AccountMovement(
                        null,
                        targetAccount,
                        transferReference,
                        AccountMovementType.CREDIT,
                        amount,
                        "Credito recebido da transferencia da conta " + sourceAccount.getId() + ".",
                        movementCreatedAt
                )
        );
    }

    public TransferCompletedEvent createTransferCompletedEvent(String transferReference, OffsetDateTime movementCreatedAt) {
        return new TransferCompletedEvent(
                sourceAccount.getId(),
                sourceAccount.getOwnerName(),
                targetAccount.getId(),
                targetAccount.getOwnerName(),
                transferReference,
                amount,
                movementCreatedAt
        );
    }
}

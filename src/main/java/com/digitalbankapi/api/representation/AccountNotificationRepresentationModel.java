package com.digitalbankapi.api.representation;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.OffsetDateTime;

@Relation(itemRelation = "notification", collectionRelation = "notifications")
@Schema(name = "AccountNotificationRepresentationModel", description = "Representacao HAL de uma notificacao registrada para a conta apos a conclusao de uma transferencia.")
public class AccountNotificationRepresentationModel extends RepresentationModel<AccountNotificationRepresentationModel> {

    @Schema(description = "Conta para a qual a notificacao foi registrada.", example = "1")
    private final Long accountId;

    @Schema(description = "Referencia da transferencia relacionada a esta notificacao.", example = "4d2f91fb-daf5-4ea7-8db2-757ca1b89c30")
    private final String transferReference;

    @Schema(description = "Estado atual da notificacao persistida pela API.", example = "REGISTERED")
    private final String notificationStatus;

    @Schema(description = "Mensagem legivel registrada para o titular da conta.", example = "Transferencia enviada com sucesso para a conta 2.")
    private final String message;

    @Schema(description = "Data e hora de registro da notificacao.", example = "2026-04-07T10:15:30Z")
    private final OffsetDateTime createdAt;

    public AccountNotificationRepresentationModel(
            Long accountId,
            String transferReference,
            String notificationStatus,
            String message,
            OffsetDateTime createdAt
    ) {
        this.accountId = accountId;
        this.transferReference = transferReference;
        this.notificationStatus = notificationStatus;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getTransferReference() {
        return transferReference;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public String getMessage() {
        return message;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}

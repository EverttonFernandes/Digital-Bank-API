package com.digitalbankapi.api.representation;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Relation(itemRelation = "movement", collectionRelation = "movements")
@Schema(name = "AccountMovementRepresentationModel", description = "Representacao HAL de uma movimentacao financeira.")
public class AccountMovementRepresentationModel extends RepresentationModel<AccountMovementRepresentationModel> {

    @Schema(description = "Identificador da conta associada a movimentacao.", example = "1")
    private final Long accountId;

    @Schema(description = "Referencia da transferencia que originou a movimentacao.", example = "4d2f91fb-daf5-4ea7-8db2-757ca1b89c30")
    private final String transferReference;

    @Schema(description = "Tipo da movimentacao financeira.", example = "DEBIT")
    private final String movementType;

    @Schema(description = "Valor movimentado.", example = "200.00")
    private final BigDecimal amount;

    @Schema(description = "Descricao legivel da movimentacao.", example = "Debito gerado pela transferencia para a conta 2.")
    private final String description;

    @Schema(description = "Data e hora de criacao da movimentacao.", example = "2026-04-07T10:15:30Z")
    private final OffsetDateTime createdAt;

    public AccountMovementRepresentationModel(
            Long accountId,
            String transferReference,
            String movementType,
            BigDecimal amount,
            String description,
            OffsetDateTime createdAt
    ) {
        this.accountId = accountId;
        this.transferReference = transferReference;
        this.movementType = movementType;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getTransferReference() {
        return transferReference;
    }

    public String getMovementType() {
        return movementType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}

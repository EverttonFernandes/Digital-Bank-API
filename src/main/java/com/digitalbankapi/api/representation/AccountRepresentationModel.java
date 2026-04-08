package com.digitalbankapi.api.representation;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(itemRelation = "account", collectionRelation = "accounts")
@Schema(name = "AccountRepresentationModel", description = "Representacao HAL de uma conta com links navegaveis para recursos relacionados.")
public class AccountRepresentationModel extends RepresentationModel<AccountRepresentationModel> {

    @Schema(description = "Identificador da conta.", example = "1")
    private final Long id;

    @Schema(description = "Nome do titular da conta.", example = "Ana Souza")
    private final String ownerName;

    @Schema(description = "Saldo atual da conta.", example = "1250.00")
    private final BigDecimal balance;

    public AccountRepresentationModel(Long id, String ownerName, BigDecimal balance) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}

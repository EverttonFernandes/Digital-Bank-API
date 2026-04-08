package com.cwi.digitalbankapi.api.representation;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Schema(name = "TransferRepresentationModel", description = "Representacao HAL da transferencia concluida com links para recursos relacionados.")
public class TransferRepresentationModel extends RepresentationModel<TransferRepresentationModel> {

    @Schema(description = "Identificador da conta de origem.", example = "1")
    private final Long sourceAccountId;

    @Schema(description = "Identificador da conta de destino.", example = "2")
    private final Long targetAccountId;

    @Schema(description = "Referencia unica da transferencia.", example = "4d2f91fb-daf5-4ea7-8db2-757ca1b89c30")
    private final String transferReference;

    @Schema(description = "Valor transferido.", example = "200.00")
    private final BigDecimal transferredAmount;

    @Schema(description = "Saldo final da conta de origem apos a transferencia.", example = "1050.00")
    private final BigDecimal sourceAccountBalance;

    @Schema(description = "Saldo final da conta de destino apos a transferencia.", example = "1180.50")
    private final BigDecimal targetAccountBalance;

    public TransferRepresentationModel(
            Long sourceAccountId,
            Long targetAccountId,
            String transferReference,
            BigDecimal transferredAmount,
            BigDecimal sourceAccountBalance,
            BigDecimal targetAccountBalance
    ) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.transferReference = transferReference;
        this.transferredAmount = transferredAmount;
        this.sourceAccountBalance = sourceAccountBalance;
        this.targetAccountBalance = targetAccountBalance;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public Long getTargetAccountId() {
        return targetAccountId;
    }

    public String getTransferReference() {
        return transferReference;
    }

    public BigDecimal getTransferredAmount() {
        return transferredAmount;
    }

    public BigDecimal getSourceAccountBalance() {
        return sourceAccountBalance;
    }

    public BigDecimal getTargetAccountBalance() {
        return targetAccountBalance;
    }
}

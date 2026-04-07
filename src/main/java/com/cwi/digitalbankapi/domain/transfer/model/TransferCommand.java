package com.cwi.digitalbankapi.domain.transfer.model;

import java.math.BigDecimal;

public record TransferCommand(
    Long sourceAccountId,
    Long targetAccountId,
    BigDecimal amount
) {
}

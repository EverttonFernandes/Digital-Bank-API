package com.cwi.digitalbankapi.application.dto;

import java.math.BigDecimal;

public record TransferResponse(
    Long sourceAccountId,
    Long targetAccountId,
    BigDecimal transferredAmount,
    BigDecimal sourceAccountBalance,
    BigDecimal targetAccountBalance
) {
}

package com.cwi.digitalbankapi.application.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferRequest(
    @NotNull(message = "O campo sourceAccountId e obrigatorio.")
    Long sourceAccountId,
    @NotNull(message = "O campo targetAccountId e obrigatorio.")
    Long targetAccountId,
    @NotNull(message = "O campo amount e obrigatorio.")
    BigDecimal amount
) {
}

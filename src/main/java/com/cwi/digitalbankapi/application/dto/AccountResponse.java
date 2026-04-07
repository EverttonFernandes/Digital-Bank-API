package com.cwi.digitalbankapi.application.dto;

import java.math.BigDecimal;

public record AccountResponse(
    Long id,
    String ownerName,
    BigDecimal balance
) {
}

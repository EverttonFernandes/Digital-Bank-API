package com.cwi.digitalbankapi.application.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record AccountMovementResponse(
    Long accountId,
    String transferReference,
    String movementType,
    BigDecimal amount,
    String description,
    OffsetDateTime createdAt
) {
}

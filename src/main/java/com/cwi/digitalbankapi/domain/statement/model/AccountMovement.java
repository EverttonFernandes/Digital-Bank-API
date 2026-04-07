package com.cwi.digitalbankapi.domain.statement.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record AccountMovement(
    Long id,
    Long accountId,
    String transferReference,
    AccountMovementType movementType,
    BigDecimal amount,
    String description,
    OffsetDateTime createdAt
) {
}

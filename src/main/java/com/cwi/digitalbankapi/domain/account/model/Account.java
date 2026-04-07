package com.cwi.digitalbankapi.domain.account.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record Account(
    Long id,
    String ownerName,
    BigDecimal balance,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}

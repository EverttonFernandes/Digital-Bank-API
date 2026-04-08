package com.digitalbankapi.domain.notification.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransferCompletedEvent(
        Long sourceAccountId,
        String sourceAccountOwnerName,
        Long targetAccountId,
        String targetAccountOwnerName,
        String transferReference,
        BigDecimal transferredAmount,
        OffsetDateTime completedAt
) {
}

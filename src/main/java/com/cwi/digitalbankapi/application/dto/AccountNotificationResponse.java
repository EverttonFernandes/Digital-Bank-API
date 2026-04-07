package com.cwi.digitalbankapi.application.dto;

import java.time.OffsetDateTime;

public record AccountNotificationResponse(
    Long accountId,
    String transferReference,
    String notificationStatus,
    String message,
    OffsetDateTime createdAt
) {
}

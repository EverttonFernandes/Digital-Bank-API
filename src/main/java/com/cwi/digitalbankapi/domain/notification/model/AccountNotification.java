package com.cwi.digitalbankapi.domain.notification.model;

import java.time.OffsetDateTime;

public record AccountNotification(
    Long id,
    Long accountId,
    String transferReference,
    AccountNotificationStatus notificationStatus,
    String message,
    OffsetDateTime createdAt
) {
}

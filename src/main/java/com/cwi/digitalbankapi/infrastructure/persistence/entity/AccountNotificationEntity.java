package com.cwi.digitalbankapi.infrastructure.persistence.entity;

import com.cwi.digitalbankapi.domain.notification.model.AccountNotificationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "account_notification")
public class AccountNotificationEntity {

    protected AccountNotificationEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "transfer_reference", nullable = false)
    private String transferReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_status", nullable = false)
    private AccountNotificationStatus notificationStatus;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public AccountNotificationEntity(
        Long accountId,
        String transferReference,
        AccountNotificationStatus notificationStatus,
        String message,
        OffsetDateTime createdAt
    ) {
        this.accountId = accountId;
        this.transferReference = transferReference;
        this.notificationStatus = notificationStatus;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getTransferReference() {
        return transferReference;
    }

    public AccountNotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public String getMessage() {
        return message;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}

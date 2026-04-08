package com.cwi.digitalbankapi.infrastructure.persistence.entity;

import com.cwi.digitalbankapi.domain.notification.model.AccountNotificationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity accountEntity;

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
        AccountEntity accountEntity,
        String transferReference,
        AccountNotificationStatus notificationStatus,
        String message,
        OffsetDateTime createdAt
    ) {
        this.accountEntity = accountEntity;
        this.transferReference = transferReference;
        this.notificationStatus = notificationStatus;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountEntity.getId();
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
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

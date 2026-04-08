package com.cwi.digitalbankapi.domain.notification.model;

import com.cwi.digitalbankapi.domain.account.model.Account;
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
public class AccountNotification {

    protected AccountNotification() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "transfer_reference", nullable = false)
    private String transferReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_status", nullable = false)
    private AccountNotificationStatus notificationStatus;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public AccountNotification(
            Long id,
            Account account,
            String transferReference,
            AccountNotificationStatus notificationStatus,
            String message,
            OffsetDateTime createdAt
    ) {
        this.id = id;
        this.account = account;
        this.transferReference = transferReference;
        this.notificationStatus = notificationStatus;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Long getAccountId() {
        return account.getId();
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

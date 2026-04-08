package com.cwi.digitalbankapi.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.List;
import java.time.OffsetDateTime;

@Entity
@Table(name = "account")
public class AccountEntity {

    protected AccountEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "accountEntity", fetch = FetchType.LAZY)
    private List<AccountMovementEntity> accountMovementEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "accountEntity", fetch = FetchType.LAZY)
    private List<AccountNotificationEntity> accountNotificationEntityList = new ArrayList<>();

    public AccountEntity(
        Long id,
        String ownerName,
        BigDecimal balance,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
    ) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<AccountMovementEntity> getAccountMovementEntityList() {
        return accountMovementEntityList;
    }

    public List<AccountNotificationEntity> getAccountNotificationEntityList() {
        return accountNotificationEntityList;
    }
}

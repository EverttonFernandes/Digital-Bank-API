package com.cwi.digitalbankapi.infrastructure.persistence.entity;

import com.cwi.digitalbankapi.domain.statement.model.AccountMovementType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "account_movement")
public class AccountMovementEntity {

    protected AccountMovementEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "transfer_reference", nullable = false)
    private String transferReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false)
    private AccountMovementType movementType;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public AccountMovementEntity(
        Long accountId,
        String transferReference,
        AccountMovementType movementType,
        BigDecimal amount,
        String description,
        OffsetDateTime createdAt
    ) {
        this.accountId = accountId;
        this.transferReference = transferReference;
        this.movementType = movementType;
        this.amount = amount;
        this.description = description;
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

    public AccountMovementType getMovementType() {
        return movementType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}

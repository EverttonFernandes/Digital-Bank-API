package com.cwi.digitalbankapi.domain.statement.model;

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

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "account_movement")
public class AccountMovement {

    protected AccountMovement() {
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
    @Column(name = "movement_type", nullable = false)
    private AccountMovementType movementType;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public AccountMovement(
            Long id,
            Account account,
            String transferReference,
            AccountMovementType movementType,
            BigDecimal amount,
            String description,
            OffsetDateTime createdAt
    ) {
        this.id = id;
        this.account = account;
        this.transferReference = transferReference;
        this.movementType = movementType;
        this.amount = amount;
        this.description = description;
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

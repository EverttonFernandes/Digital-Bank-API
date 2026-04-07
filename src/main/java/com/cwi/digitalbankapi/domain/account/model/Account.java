package com.cwi.digitalbankapi.domain.account.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Account {

    private final Long id;
    private final String ownerName;
    private BigDecimal balance;
    private final OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Account(
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

    public void debit(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
        this.updatedAt = OffsetDateTime.now();
    }

    public void credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
        this.updatedAt = OffsetDateTime.now();
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}

package com.cwi.digitalbankapi.domain.account.model;

import java.math.BigDecimal;

public record AccountCreationCommand(
    String ownerName,
    BigDecimal initialBalance
) {
}

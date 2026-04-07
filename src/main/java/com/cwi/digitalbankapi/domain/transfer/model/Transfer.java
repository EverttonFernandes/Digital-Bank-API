package com.cwi.digitalbankapi.domain.transfer.model;

import com.cwi.digitalbankapi.domain.account.model.Account;

import java.math.BigDecimal;

public record Transfer(
    Account sourceAccount,
    Account targetAccount,
    BigDecimal amount
) {
}

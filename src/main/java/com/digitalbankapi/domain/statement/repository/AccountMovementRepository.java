package com.digitalbankapi.domain.statement.repository;

import com.digitalbankapi.domain.statement.model.AccountMovement;

import java.util.List;

public interface AccountMovementRepository {

    void saveAccountMovements(List<AccountMovement> accountMovementList);

    List<AccountMovement> findAccountMovementsByAccountId(Long accountId);
}

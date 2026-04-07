package com.cwi.digitalbankapi.domain.statement.repository;

import com.cwi.digitalbankapi.domain.statement.model.AccountMovement;

import java.util.List;

public interface AccountMovementRepository {

    void saveAccountMovements(List<AccountMovement> accountMovementList);

    List<AccountMovement> findAccountMovementsByAccountId(Long accountId);
}

package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.domain.statement.model.AccountMovement;
import com.cwi.digitalbankapi.domain.statement.repository.AccountMovementRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountMovementRepositoryImpl implements AccountMovementRepository {

    private final AccountMovementJpaRepository springDataAccountMovementJpaRepository;

    public AccountMovementRepositoryImpl(AccountMovementJpaRepository springDataAccountMovementJpaRepository) {
        this.springDataAccountMovementJpaRepository = springDataAccountMovementJpaRepository;
    }

    @Override
    public void saveAccountMovements(List<AccountMovement> accountMovementList) {
        springDataAccountMovementJpaRepository.saveAll(accountMovementList);
    }

    @Override
    public List<AccountMovement> findAccountMovementsByAccountId(Long accountId) {
        return springDataAccountMovementJpaRepository.findByAccountIdOrderByCreatedAtDescIdDesc(accountId);
    }
}

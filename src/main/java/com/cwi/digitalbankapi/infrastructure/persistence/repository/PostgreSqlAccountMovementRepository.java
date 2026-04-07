package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.domain.statement.model.AccountMovement;
import com.cwi.digitalbankapi.domain.statement.repository.AccountMovementRepository;
import com.cwi.digitalbankapi.infrastructure.persistence.entity.AccountMovementEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostgreSqlAccountMovementRepository implements AccountMovementRepository {

    private final SpringDataAccountMovementJpaRepository springDataAccountMovementJpaRepository;

    public PostgreSqlAccountMovementRepository(SpringDataAccountMovementJpaRepository springDataAccountMovementJpaRepository) {
        this.springDataAccountMovementJpaRepository = springDataAccountMovementJpaRepository;
    }

    @Override
    public void saveAccountMovements(List<AccountMovement> accountMovementList) {
        springDataAccountMovementJpaRepository.saveAll(
            accountMovementList.stream()
                .map(accountMovement -> new AccountMovementEntity(
                    accountMovement.accountId(),
                    accountMovement.transferReference(),
                    accountMovement.movementType(),
                    accountMovement.amount(),
                    accountMovement.description(),
                    accountMovement.createdAt()
                ))
                .toList()
        );
    }

    @Override
    public List<AccountMovement> findAccountMovementsByAccountId(Long accountId) {
        return springDataAccountMovementJpaRepository.findByAccountIdOrderByCreatedAtDescIdDesc(accountId)
            .stream()
            .map(accountMovementEntity -> new AccountMovement(
                accountMovementEntity.getId(),
                accountMovementEntity.getAccountId(),
                accountMovementEntity.getTransferReference(),
                accountMovementEntity.getMovementType(),
                accountMovementEntity.getAmount(),
                accountMovementEntity.getDescription(),
                accountMovementEntity.getCreatedAt()
            ))
            .toList();
    }
}

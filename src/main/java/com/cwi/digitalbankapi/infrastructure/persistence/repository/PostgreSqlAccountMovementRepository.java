package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.domain.statement.model.AccountMovement;
import com.cwi.digitalbankapi.domain.statement.repository.AccountMovementRepository;
import com.cwi.digitalbankapi.infrastructure.persistence.entity.AccountMovementEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostgreSqlAccountMovementRepository implements AccountMovementRepository {

    private final SpringDataAccountMovementJpaRepository springDataAccountMovementJpaRepository;
    private final SpringDataAccountJpaRepository springDataAccountJpaRepository;

    public PostgreSqlAccountMovementRepository(
        SpringDataAccountMovementJpaRepository springDataAccountMovementJpaRepository,
        SpringDataAccountJpaRepository springDataAccountJpaRepository
    ) {
        this.springDataAccountMovementJpaRepository = springDataAccountMovementJpaRepository;
        this.springDataAccountJpaRepository = springDataAccountJpaRepository;
    }

    @Override
    public void saveAccountMovements(List<AccountMovement> accountMovementList) {
        springDataAccountMovementJpaRepository.saveAll(
            accountMovementList.stream()
                .map(accountMovement -> new AccountMovementEntity(
                    springDataAccountJpaRepository.getReferenceById(accountMovement.accountId()),
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
        return springDataAccountMovementJpaRepository.findByAccountEntityIdOrderByCreatedAtDescIdDesc(accountId)
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

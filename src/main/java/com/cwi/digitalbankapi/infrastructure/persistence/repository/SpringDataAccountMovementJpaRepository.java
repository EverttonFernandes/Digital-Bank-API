package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.infrastructure.persistence.entity.AccountMovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataAccountMovementJpaRepository extends JpaRepository<AccountMovementEntity, Long> {

    List<AccountMovementEntity> findByAccountEntityIdOrderByCreatedAtDescIdDesc(Long accountId);
}

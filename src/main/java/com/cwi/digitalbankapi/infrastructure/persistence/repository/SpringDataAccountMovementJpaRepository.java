package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.domain.statement.model.AccountMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataAccountMovementJpaRepository extends JpaRepository<AccountMovement, Long> {

    List<AccountMovement> findByAccountIdOrderByCreatedAtDescIdDesc(Long accountId);
}

package com.digitalbankapi.infrastructure.persistence.repository;

import com.digitalbankapi.domain.statement.model.AccountMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountMovementJpaRepository extends JpaRepository<AccountMovement, Long> {

    List<AccountMovement> findByAccountIdOrderByCreatedAtDescIdDesc(Long accountId);
}

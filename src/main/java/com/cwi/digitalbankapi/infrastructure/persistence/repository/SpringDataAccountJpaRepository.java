package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataAccountJpaRepository extends JpaRepository<AccountEntity, Long> {
}

package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.infrastructure.persistence.entity.AccountNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataAccountNotificationJpaRepository extends JpaRepository<AccountNotificationEntity, Long> {

    List<AccountNotificationEntity> findByAccountIdOrderByCreatedAtDescIdDesc(Long accountId);
}

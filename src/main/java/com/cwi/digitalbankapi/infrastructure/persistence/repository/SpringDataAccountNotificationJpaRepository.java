package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.domain.notification.model.AccountNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataAccountNotificationJpaRepository extends JpaRepository<AccountNotification, Long> {

    List<AccountNotification> findByAccountIdOrderByCreatedAtDescIdDesc(Long accountId);
}

package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.domain.notification.model.AccountNotification;
import com.cwi.digitalbankapi.domain.notification.repository.AccountNotificationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostgreSqlAccountNotificationRepository implements AccountNotificationRepository {

    private final SpringDataAccountNotificationJpaRepository springDataAccountNotificationJpaRepository;

    public PostgreSqlAccountNotificationRepository(SpringDataAccountNotificationJpaRepository springDataAccountNotificationJpaRepository) {
        this.springDataAccountNotificationJpaRepository = springDataAccountNotificationJpaRepository;
    }

    @Override
    public void saveAccountNotifications(List<AccountNotification> accountNotificationList) {
        springDataAccountNotificationJpaRepository.saveAll(accountNotificationList);
    }

    @Override
    public List<AccountNotification> findAccountNotificationsByAccountId(Long accountId) {
        return springDataAccountNotificationJpaRepository.findByAccountIdOrderByCreatedAtDescIdDesc(accountId);
    }
}

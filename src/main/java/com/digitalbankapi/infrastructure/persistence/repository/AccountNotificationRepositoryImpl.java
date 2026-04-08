package com.digitalbankapi.infrastructure.persistence.repository;

import com.digitalbankapi.domain.notification.model.AccountNotification;
import com.digitalbankapi.domain.notification.repository.AccountNotificationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountNotificationRepositoryImpl implements AccountNotificationRepository {

    private final AccountNotificationJpaRepository springDataAccountNotificationJpaRepository;

    public AccountNotificationRepositoryImpl(AccountNotificationJpaRepository springDataAccountNotificationJpaRepository) {
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

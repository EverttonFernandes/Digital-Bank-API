package com.cwi.digitalbankapi.infrastructure.persistence.repository;

import com.cwi.digitalbankapi.domain.notification.model.AccountNotification;
import com.cwi.digitalbankapi.domain.notification.repository.AccountNotificationRepository;
import com.cwi.digitalbankapi.infrastructure.persistence.entity.AccountNotificationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostgreSqlAccountNotificationRepository implements AccountNotificationRepository {

    private final SpringDataAccountNotificationJpaRepository springDataAccountNotificationJpaRepository;
    private final SpringDataAccountJpaRepository springDataAccountJpaRepository;

    public PostgreSqlAccountNotificationRepository(
        SpringDataAccountNotificationJpaRepository springDataAccountNotificationJpaRepository,
        SpringDataAccountJpaRepository springDataAccountJpaRepository
    ) {
        this.springDataAccountNotificationJpaRepository = springDataAccountNotificationJpaRepository;
        this.springDataAccountJpaRepository = springDataAccountJpaRepository;
    }

    @Override
    public void saveAccountNotifications(List<AccountNotification> accountNotificationList) {
        springDataAccountNotificationJpaRepository.saveAll(
            accountNotificationList.stream()
                .map(accountNotification -> new AccountNotificationEntity(
                    springDataAccountJpaRepository.getReferenceById(accountNotification.accountId()),
                    accountNotification.transferReference(),
                    accountNotification.notificationStatus(),
                    accountNotification.message(),
                    accountNotification.createdAt()
                ))
                .toList()
        );
    }

    @Override
    public List<AccountNotification> findAccountNotificationsByAccountId(Long accountId) {
        return springDataAccountNotificationJpaRepository.findByAccountEntityIdOrderByCreatedAtDescIdDesc(accountId)
            .stream()
            .map(accountNotificationEntity -> new AccountNotification(
                accountNotificationEntity.getId(),
                accountNotificationEntity.getAccountId(),
                accountNotificationEntity.getTransferReference(),
                accountNotificationEntity.getNotificationStatus(),
                accountNotificationEntity.getMessage(),
                accountNotificationEntity.getCreatedAt()
            ))
            .toList();
    }
}

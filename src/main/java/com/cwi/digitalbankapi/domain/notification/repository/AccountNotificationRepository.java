package com.cwi.digitalbankapi.domain.notification.repository;

import com.cwi.digitalbankapi.domain.notification.model.AccountNotification;

import java.util.List;

public interface AccountNotificationRepository {

    void saveAccountNotifications(List<AccountNotification> accountNotificationList);

    List<AccountNotification> findAccountNotificationsByAccountId(Long accountId);
}

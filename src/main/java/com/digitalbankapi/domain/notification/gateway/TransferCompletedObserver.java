package com.digitalbankapi.domain.notification.gateway;

import com.digitalbankapi.domain.notification.model.TransferCompletedEvent;

public interface TransferCompletedObserver {

    void handle(TransferCompletedEvent transferCompletedEvent);
}

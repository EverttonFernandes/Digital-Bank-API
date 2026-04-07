package com.cwi.digitalbankapi.domain.notification.gateway;

import com.cwi.digitalbankapi.domain.notification.model.TransferCompletedEvent;

public interface TransferCompletedObserver {

    void handle(TransferCompletedEvent transferCompletedEvent);
}

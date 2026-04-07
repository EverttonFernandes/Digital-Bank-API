package com.cwi.digitalbankapi.domain.notification.gateway;

import com.cwi.digitalbankapi.domain.notification.model.TransferCompletedEvent;

public interface TransferCompletedEventPublisher {

    void publish(TransferCompletedEvent transferCompletedEvent);
}

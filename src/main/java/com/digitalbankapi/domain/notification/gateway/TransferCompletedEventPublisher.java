package com.digitalbankapi.domain.notification.gateway;

import com.digitalbankapi.domain.notification.model.TransferCompletedEvent;

public interface TransferCompletedEventPublisher {

    void publish(TransferCompletedEvent transferCompletedEvent);
}

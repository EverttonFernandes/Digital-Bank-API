package com.cwi.digitalbankapi.infrastructure.notification;

import com.cwi.digitalbankapi.domain.notification.gateway.TransferCompletedObserver;
import com.cwi.digitalbankapi.domain.notification.gateway.TransferCompletedEventPublisher;
import com.cwi.digitalbankapi.domain.notification.model.TransferCompletedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringTransferCompletedEventPublisher implements TransferCompletedEventPublisher {

    private final List<TransferCompletedObserver> transferCompletedObserverList;

    public SpringTransferCompletedEventPublisher(List<TransferCompletedObserver> transferCompletedObserverList) {
        this.transferCompletedObserverList = transferCompletedObserverList;
    }

    @Override
    public void publish(TransferCompletedEvent transferCompletedEvent) {
        notifyObservers(transferCompletedEvent);
    }

    private void notifyObservers(TransferCompletedEvent transferCompletedEvent) {
        transferCompletedObserverList.forEach(
                transferCompletedObserver -> transferCompletedObserver.handle(transferCompletedEvent)
        );
    }
}

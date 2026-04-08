package com.digitalbankapi.infrastructure.notification;

import com.digitalbankapi.domain.notification.gateway.TransferCompletedObserver;
import com.digitalbankapi.domain.notification.model.TransferCompletedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.mock;

class SpringTransferCompletedEventPublisherTest {

    @Test
    @DisplayName("Deve notificar todos os observers registrados")
    void shouldNotifyAllRegisteredObservers() {
        TransferCompletedObserver firstObserver = mock(TransferCompletedObserver.class);
        TransferCompletedObserver secondObserver = mock(TransferCompletedObserver.class);
        SpringTransferCompletedEventPublisher publisher = new SpringTransferCompletedEventPublisher(
                List.of(firstObserver, secondObserver)
        );
        TransferCompletedEvent transferCompletedEvent = new TransferCompletedEvent(
                1L,
                "Ana",
                2L,
                "Bruno",
                "ref-1",
                new BigDecimal("20.00"),
                java.time.OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );

        publisher.publish(transferCompletedEvent);

        BDDMockito.then(firstObserver).should().handle(transferCompletedEvent);
        BDDMockito.then(secondObserver).should().handle(transferCompletedEvent);
    }
}

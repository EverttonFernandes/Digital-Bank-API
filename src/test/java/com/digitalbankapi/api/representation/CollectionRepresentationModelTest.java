package com.digitalbankapi.api.representation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

class CollectionRepresentationModelTest {

    @Test
    @DisplayName("Deve expor embedded e links da colecao de contas")
    void shouldExposeEmbeddedAndLinksFromAccountCollectionRepresentationModel() {
        AccountRepresentationModel accountRepresentationModel = new AccountRepresentationModel(1L, "Ana", new BigDecimal("100.00"));
        AccountCollectionRepresentationModel.EmbeddedAccounts embeddedAccounts =
                new AccountCollectionRepresentationModel.EmbeddedAccounts(List.of(accountRepresentationModel));
        Map<String, Link> links = Map.of("self", Link.of("/accounts"));

        AccountCollectionRepresentationModel response = new AccountCollectionRepresentationModel(embeddedAccounts, links);

        Assertions.assertEquals(1, response.getEmbedded().getAccounts().size());
        Assertions.assertEquals(links, response.getLinks());
    }

    @Test
    @DisplayName("Deve expor embedded e links da colecao de movimentacoes")
    void shouldExposeEmbeddedAndLinksFromAccountMovementCollectionRepresentationModel() {
        AccountMovementRepresentationModel movementRepresentationModel = new AccountMovementRepresentationModel(
                1L,
                "ref-1",
                "DEBIT",
                new BigDecimal("10.00"),
                "Debito",
                OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );
        AccountMovementCollectionRepresentationModel.EmbeddedMovements embeddedMovements =
                new AccountMovementCollectionRepresentationModel.EmbeddedMovements(List.of(movementRepresentationModel));
        Map<String, Link> links = Map.of("self", Link.of("/accounts/1/movements"));

        AccountMovementCollectionRepresentationModel response =
                new AccountMovementCollectionRepresentationModel(embeddedMovements, links);

        Assertions.assertEquals(1, response.getEmbedded().getMovements().size());
        Assertions.assertEquals(links, response.getLinks());
    }

    @Test
    @DisplayName("Deve expor embedded e links da colecao de notificacoes")
    void shouldExposeEmbeddedAndLinksFromAccountNotificationCollectionRepresentationModel() {
        AccountNotificationRepresentationModel notificationRepresentationModel = new AccountNotificationRepresentationModel(
                1L,
                "ref-1",
                "REGISTERED",
                "Mensagem",
                OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );
        AccountNotificationCollectionRepresentationModel.EmbeddedNotifications embeddedNotifications =
                new AccountNotificationCollectionRepresentationModel.EmbeddedNotifications(List.of(notificationRepresentationModel));
        Map<String, Link> links = Map.of("self", Link.of("/accounts/1/notifications"));

        AccountNotificationCollectionRepresentationModel response =
                new AccountNotificationCollectionRepresentationModel(embeddedNotifications, links);

        Assertions.assertEquals(1, response.getEmbedded().getNotifications().size());
        Assertions.assertEquals(links, response.getLinks());
    }
}

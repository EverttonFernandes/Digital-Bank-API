package com.digitalbankapi.api.representation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

class RepresentationModelTest {

    @Test
    @DisplayName("Deve expor todos os dados da representacao de movimentacao")
    void shouldExposeAllFieldsFromAccountMovementRepresentationModel() {
        OffsetDateTime createdAt = OffsetDateTime.parse("2026-04-08T00:00:00Z");
        AccountMovementRepresentationModel response = new AccountMovementRepresentationModel(
                1L,
                "ref-1",
                "DEBIT",
                new BigDecimal("15.00"),
                "Debito",
                createdAt
        );

        Assertions.assertEquals(1L, response.getAccountId());
        Assertions.assertEquals("ref-1", response.getTransferReference());
        Assertions.assertEquals("DEBIT", response.getMovementType());
        Assertions.assertEquals(new BigDecimal("15.00"), response.getAmount());
        Assertions.assertEquals("Debito", response.getDescription());
        Assertions.assertEquals(createdAt, response.getCreatedAt());
    }

    @Test
    @DisplayName("Deve expor todos os dados da representacao de notificacao")
    void shouldExposeAllFieldsFromAccountNotificationRepresentationModel() {
        OffsetDateTime createdAt = OffsetDateTime.parse("2026-04-08T00:00:00Z");
        AccountNotificationRepresentationModel response = new AccountNotificationRepresentationModel(
                1L,
                "ref-1",
                "REGISTERED",
                "Mensagem",
                createdAt
        );

        Assertions.assertEquals(1L, response.getAccountId());
        Assertions.assertEquals("ref-1", response.getTransferReference());
        Assertions.assertEquals("REGISTERED", response.getNotificationStatus());
        Assertions.assertEquals("Mensagem", response.getMessage());
        Assertions.assertEquals(createdAt, response.getCreatedAt());
    }

    @Test
    @DisplayName("Deve expor todos os dados da representacao de transferencia")
    void shouldExposeAllFieldsFromTransferRepresentationModel() {
        TransferRepresentationModel response = new TransferRepresentationModel(
                1L,
                2L,
                "ref-1",
                new BigDecimal("20.00"),
                new BigDecimal("80.00"),
                new BigDecimal("120.00")
        );

        Assertions.assertEquals(1L, response.getSourceAccountId());
        Assertions.assertEquals(2L, response.getTargetAccountId());
        Assertions.assertEquals("ref-1", response.getTransferReference());
        Assertions.assertEquals(new BigDecimal("20.00"), response.getTransferredAmount());
        Assertions.assertEquals(new BigDecimal("80.00"), response.getSourceAccountBalance());
        Assertions.assertEquals(new BigDecimal("120.00"), response.getTargetAccountBalance());
    }
}

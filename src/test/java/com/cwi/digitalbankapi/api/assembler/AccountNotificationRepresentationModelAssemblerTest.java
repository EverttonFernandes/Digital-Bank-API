package com.cwi.digitalbankapi.api.assembler;

import com.cwi.digitalbankapi.api.representation.AccountNotificationRepresentationModel;
import com.cwi.digitalbankapi.application.dto.AccountNotificationResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class AccountNotificationRepresentationModelAssemblerTest {

    private final AccountNotificationRepresentationModelAssembler accountNotificationRepresentationModelAssembler =
        new AccountNotificationRepresentationModelAssembler();

    @Test
    @DisplayName("Deve montar representacao HATEOAS da notificacao com links da conta e da colecao")
    void shouldBuildAccountNotificationHateoasRepresentationWithAccountAndCollectionLinks() {
        // GIVEN
        AccountNotificationResponse accountNotificationResponse = new AccountNotificationResponse(
            1L,
            "reference-123",
            "REGISTERED",
            "Transferencia enviada com sucesso para a conta 2.",
            OffsetDateTime.parse("2026-04-07T10:15:30Z")
        );

        // WHEN
        AccountNotificationRepresentationModel accountNotificationRepresentationModel =
            accountNotificationRepresentationModelAssembler.toModel(accountNotificationResponse);

        // THEN
        Assertions.assertEquals(1L, accountNotificationRepresentationModel.getAccountId());
        Assertions.assertEquals("reference-123", accountNotificationRepresentationModel.getTransferReference());
        Assertions.assertEquals("REGISTERED", accountNotificationRepresentationModel.getNotificationStatus());
        Assertions.assertEquals("/accounts/1", accountNotificationRepresentationModel.getRequiredLink("account").getHref());
        Assertions.assertEquals("/accounts/1/notifications", accountNotificationRepresentationModel.getRequiredLink("collection").getHref());
    }
}

package com.cwi.digitalbankapi.api.assembler;

import com.cwi.digitalbankapi.api.representation.AccountMovementRepresentationModel;
import com.cwi.digitalbankapi.application.dto.AccountMovementDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

class AccountMovementRepresentationModelAssemblerTest {

    private final AccountMovementRepresentationModelAssembler accountMovementRepresentationModelAssembler =
        new AccountMovementRepresentationModelAssembler();

    @Test
    @DisplayName("Deve montar representacao HATEOAS da movimentacao com links da conta e da colecao")
    void shouldBuildAccountMovementHateoasRepresentationWithAccountAndCollectionLinks() {
        // GIVEN
        AccountMovementDTO accountMovementResponse = new AccountMovementDTO(
            1L,
            "reference-123",
            "DEBIT",
            new BigDecimal("200.00"),
            "Debito gerado pela transferencia para a conta 2.",
            OffsetDateTime.parse("2026-04-07T10:15:30Z")
        );

        // WHEN
        AccountMovementRepresentationModel accountMovementRepresentationModel =
            accountMovementRepresentationModelAssembler.toModel(accountMovementResponse);

        // THEN
        Assertions.assertEquals(1L, accountMovementRepresentationModel.getAccountId());
        Assertions.assertEquals("reference-123", accountMovementRepresentationModel.getTransferReference());
        Assertions.assertEquals("DEBIT", accountMovementRepresentationModel.getMovementType());
        Assertions.assertEquals("/accounts/1", accountMovementRepresentationModel.getRequiredLink("account").getHref());
        Assertions.assertEquals("/accounts/1/movements", accountMovementRepresentationModel.getRequiredLink("collection").getHref());
    }
}

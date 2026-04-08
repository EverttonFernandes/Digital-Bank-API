package com.digitalbankapi.api.assembler;

import com.digitalbankapi.api.representation.TransferRepresentationModel;
import com.digitalbankapi.application.dto.TransferResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class TransferRepresentationModelAssemblerTest {

    private final TransferRepresentationModelAssembler transferRepresentationModelAssembler = new TransferRepresentationModelAssembler();

    @Test
    @DisplayName("Deve montar representacao HATEOAS da transferencia com links de navegacao relacionados")
    void shouldBuildTransferHateoasRepresentationWithRelatedNavigationLinks() {
        // GIVEN
        TransferResponseDTO transferResponse = new TransferResponseDTO(
                1L,
                2L,
                "reference-123",
                new BigDecimal("200.00"),
                new BigDecimal("1050.00"),
                new BigDecimal("1180.50")
        );

        // WHEN
        TransferRepresentationModel transferRepresentationModel = transferRepresentationModelAssembler.toModel(transferResponse);

        // THEN
        Assertions.assertEquals(1L, transferRepresentationModel.getSourceAccountId());
        Assertions.assertEquals(2L, transferRepresentationModel.getTargetAccountId());
        Assertions.assertEquals("reference-123", transferRepresentationModel.getTransferReference());
        Assertions.assertEquals(new BigDecimal("200.00"), transferRepresentationModel.getTransferredAmount());
        Assertions.assertEquals("/transfers", transferRepresentationModel.getRequiredLink("transfers").getHref());
        Assertions.assertEquals("/accounts/1", transferRepresentationModel.getRequiredLink("sourceAccount").getHref());
        Assertions.assertEquals("/accounts/2", transferRepresentationModel.getRequiredLink("targetAccount").getHref());
        Assertions.assertEquals("/accounts/1/movements", transferRepresentationModel.getRequiredLink("sourceAccountMovements").getHref());
        Assertions.assertEquals("/accounts/2/movements", transferRepresentationModel.getRequiredLink("targetAccountMovements").getHref());
        Assertions.assertEquals("/accounts/1/notifications", transferRepresentationModel.getRequiredLink("sourceAccountNotifications").getHref());
        Assertions.assertEquals("/accounts/2/notifications", transferRepresentationModel.getRequiredLink("targetAccountNotifications").getHref());
    }
}

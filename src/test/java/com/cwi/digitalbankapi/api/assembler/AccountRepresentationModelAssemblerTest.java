package com.cwi.digitalbankapi.api.assembler;

import com.cwi.digitalbankapi.api.representation.AccountRepresentationModel;
import com.cwi.digitalbankapi.application.dto.AccountResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class AccountRepresentationModelAssemblerTest {

    private final AccountRepresentationModelAssembler accountRepresentationModelAssembler = new AccountRepresentationModelAssembler();

    @Test
    @DisplayName("Deve montar representacao HATEOAS da conta com links principais")
    void shouldBuildAccountHateoasRepresentationWithMainLinks() {
        // GIVEN
        AccountResponse accountResponse = new AccountResponse(1L, "Ana Souza", new BigDecimal("1250.00"));

        // WHEN
        AccountRepresentationModel accountRepresentationModel = accountRepresentationModelAssembler.toModel(accountResponse);

        // THEN
        Assertions.assertEquals(1L, accountRepresentationModel.getId());
        Assertions.assertEquals("Ana Souza", accountRepresentationModel.getOwnerName());
        Assertions.assertEquals(new BigDecimal("1250.00"), accountRepresentationModel.getBalance());
        Assertions.assertEquals("/accounts/1", accountRepresentationModel.getRequiredLink("self").getHref());
        Assertions.assertEquals("/accounts/1/movements", accountRepresentationModel.getRequiredLink("movements").getHref());
        Assertions.assertEquals("/accounts/1/notifications", accountRepresentationModel.getRequiredLink("notifications").getHref());
    }
}

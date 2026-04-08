package com.digitalbankapi.api.controller;

import com.digitalbankapi.api.assembler.TransferRepresentationModelAssembler;
import com.digitalbankapi.api.representation.TransferRepresentationModel;
import com.digitalbankapi.application.dto.TransferDTO;
import com.digitalbankapi.application.dto.TransferResponseDTO;
import com.digitalbankapi.application.service.TransferService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;

class TransferApiTest {

    private final TransferService transferService = mock(TransferService.class);
    private final TransferRepresentationModelAssembler transferRepresentationModelAssembler = mock(TransferRepresentationModelAssembler.class);
    private final TransferApi transferApi = new TransferApi(transferService, transferRepresentationModelAssembler);

    @Test
    @DisplayName("Deve transferir saldo retornando representation model montado pelo assembler")
    void shouldTransferAmountReturningRepresentationModelBuiltByAssembler() {
        TransferDTO transferDTO = new TransferDTO(1L, 2L, new BigDecimal("80.00"));
        TransferResponseDTO transferResponseDTO = new TransferResponseDTO(
                1L,
                2L,
                "ref-1",
                new BigDecimal("80.00"),
                new BigDecimal("20.00"),
                new BigDecimal("180.00")
        );
        TransferRepresentationModel transferRepresentationModel = new TransferRepresentationModel(
                1L,
                2L,
                "ref-1",
                new BigDecimal("80.00"),
                new BigDecimal("20.00"),
                new BigDecimal("180.00")
        );

        BDDMockito.given(transferService.transfer(transferDTO)).willReturn(transferResponseDTO);
        BDDMockito.given(transferRepresentationModelAssembler.toModel(transferResponseDTO)).willReturn(transferRepresentationModel);

        TransferRepresentationModel response = transferApi.transfer(transferDTO);

        Assertions.assertSame(transferRepresentationModel, response);
    }
}

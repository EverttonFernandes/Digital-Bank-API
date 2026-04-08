package com.cwi.digitalbankapi.api.assembler;

import com.cwi.digitalbankapi.api.controller.AccountApi;
import com.cwi.digitalbankapi.api.controller.TransferApi;
import com.cwi.digitalbankapi.api.representation.TransferRepresentationModel;
import com.cwi.digitalbankapi.application.dto.TransferResponseDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TransferRepresentationModelAssembler implements RepresentationModelAssembler<TransferResponseDTO, TransferRepresentationModel> {

    @Override
    public TransferRepresentationModel toModel(TransferResponseDTO transferResponse) {
        TransferRepresentationModel transferRepresentationModel = new TransferRepresentationModel(
            transferResponse.sourceAccountId(),
            transferResponse.targetAccountId(),
            transferResponse.transferReference(),
            transferResponse.transferredAmount(),
            transferResponse.sourceAccountBalance(),
            transferResponse.targetAccountBalance()
        );

        transferRepresentationModel.add(linkTo(TransferApi.class).withRel("transfers"));
        transferRepresentationModel.add(linkTo(methodOn(AccountApi.class).findAccountById(transferResponse.sourceAccountId())).withRel("sourceAccount"));
        transferRepresentationModel.add(linkTo(methodOn(AccountApi.class).findAccountById(transferResponse.targetAccountId())).withRel("targetAccount"));
        transferRepresentationModel.add(linkTo(methodOn(AccountApi.class).findAccountMovements(transferResponse.sourceAccountId())).withRel("sourceAccountMovements"));
        transferRepresentationModel.add(linkTo(methodOn(AccountApi.class).findAccountMovements(transferResponse.targetAccountId())).withRel("targetAccountMovements"));
        transferRepresentationModel.add(linkTo(methodOn(AccountApi.class).findAccountNotifications(transferResponse.sourceAccountId())).withRel("sourceAccountNotifications"));
        transferRepresentationModel.add(linkTo(methodOn(AccountApi.class).findAccountNotifications(transferResponse.targetAccountId())).withRel("targetAccountNotifications"));

        return transferRepresentationModel;
    }
}

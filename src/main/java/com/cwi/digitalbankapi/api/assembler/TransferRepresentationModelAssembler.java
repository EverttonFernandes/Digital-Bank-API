package com.cwi.digitalbankapi.api.assembler;

import com.cwi.digitalbankapi.api.controller.AccountController;
import com.cwi.digitalbankapi.api.controller.TransferController;
import com.cwi.digitalbankapi.api.representation.TransferRepresentationModel;
import com.cwi.digitalbankapi.application.dto.TransferResponse;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TransferRepresentationModelAssembler implements RepresentationModelAssembler<TransferResponse, TransferRepresentationModel> {

    @Override
    public TransferRepresentationModel toModel(TransferResponse transferResponse) {
        TransferRepresentationModel transferRepresentationModel = new TransferRepresentationModel(
            transferResponse.sourceAccountId(),
            transferResponse.targetAccountId(),
            transferResponse.transferReference(),
            transferResponse.transferredAmount(),
            transferResponse.sourceAccountBalance(),
            transferResponse.targetAccountBalance()
        );

        transferRepresentationModel.add(linkTo(TransferController.class).withRel("transfers"));
        transferRepresentationModel.add(linkTo(methodOn(AccountController.class).findAccountById(transferResponse.sourceAccountId())).withRel("sourceAccount"));
        transferRepresentationModel.add(linkTo(methodOn(AccountController.class).findAccountById(transferResponse.targetAccountId())).withRel("targetAccount"));
        transferRepresentationModel.add(linkTo(methodOn(AccountController.class).findAccountMovements(transferResponse.sourceAccountId())).withRel("sourceAccountMovements"));
        transferRepresentationModel.add(linkTo(methodOn(AccountController.class).findAccountMovements(transferResponse.targetAccountId())).withRel("targetAccountMovements"));
        transferRepresentationModel.add(linkTo(methodOn(AccountController.class).findAccountNotifications(transferResponse.sourceAccountId())).withRel("sourceAccountNotifications"));
        transferRepresentationModel.add(linkTo(methodOn(AccountController.class).findAccountNotifications(transferResponse.targetAccountId())).withRel("targetAccountNotifications"));

        return transferRepresentationModel;
    }
}

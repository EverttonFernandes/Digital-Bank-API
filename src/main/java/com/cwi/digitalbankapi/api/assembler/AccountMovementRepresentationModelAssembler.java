package com.cwi.digitalbankapi.api.assembler;

import com.cwi.digitalbankapi.api.controller.AccountController;
import com.cwi.digitalbankapi.api.representation.AccountMovementRepresentationModel;
import com.cwi.digitalbankapi.application.dto.AccountMovementResponse;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountMovementRepresentationModelAssembler implements RepresentationModelAssembler<AccountMovementResponse, AccountMovementRepresentationModel> {

    @Override
    public AccountMovementRepresentationModel toModel(AccountMovementResponse accountMovementResponse) {
        AccountMovementRepresentationModel accountMovementRepresentationModel = new AccountMovementRepresentationModel(
            accountMovementResponse.accountId(),
            accountMovementResponse.transferReference(),
            accountMovementResponse.movementType(),
            accountMovementResponse.amount(),
            accountMovementResponse.description(),
            accountMovementResponse.createdAt()
        );

        accountMovementRepresentationModel.add(linkTo(methodOn(AccountController.class).findAccountById(accountMovementResponse.accountId())).withRel("account"));
        accountMovementRepresentationModel.add(linkTo(methodOn(AccountController.class).findAccountMovements(accountMovementResponse.accountId())).withRel("collection"));

        return accountMovementRepresentationModel;
    }
}

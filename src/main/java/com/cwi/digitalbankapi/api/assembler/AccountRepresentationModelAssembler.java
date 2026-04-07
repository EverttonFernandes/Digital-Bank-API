package com.cwi.digitalbankapi.api.assembler;

import com.cwi.digitalbankapi.api.controller.AccountController;
import com.cwi.digitalbankapi.api.representation.AccountRepresentationModel;
import com.cwi.digitalbankapi.application.dto.AccountResponse;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountRepresentationModelAssembler implements RepresentationModelAssembler<AccountResponse, AccountRepresentationModel> {

    @Override
    public AccountRepresentationModel toModel(AccountResponse accountResponse) {
        AccountRepresentationModel accountRepresentationModel = new AccountRepresentationModel(
            accountResponse.id(),
            accountResponse.ownerName(),
            accountResponse.balance()
        );

        accountRepresentationModel.add(linkTo(methodOn(AccountController.class).findAccountById(accountResponse.id())).withSelfRel());
        accountRepresentationModel.add(linkTo(methodOn(AccountController.class).findAccountMovements(accountResponse.id())).withRel("movements"));
        accountRepresentationModel.add(linkTo(methodOn(AccountController.class).findAccountNotifications(accountResponse.id())).withRel("notifications"));

        return accountRepresentationModel;
    }
}

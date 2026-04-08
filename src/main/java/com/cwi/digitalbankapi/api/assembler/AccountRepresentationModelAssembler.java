package com.cwi.digitalbankapi.api.assembler;

import com.cwi.digitalbankapi.api.controller.AccountApi;
import com.cwi.digitalbankapi.api.representation.AccountRepresentationModel;
import com.cwi.digitalbankapi.application.dto.AccountDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountRepresentationModelAssembler implements RepresentationModelAssembler<AccountDTO, AccountRepresentationModel> {

    @Override
    public AccountRepresentationModel toModel(AccountDTO accountResponse) {
        AccountRepresentationModel accountRepresentationModel = new AccountRepresentationModel(
                accountResponse.id(),
                accountResponse.ownerName(),
                accountResponse.balance()
        );

        accountRepresentationModel.add(linkTo(methodOn(AccountApi.class).findAccountById(accountResponse.id())).withSelfRel());
        accountRepresentationModel.add(linkTo(methodOn(AccountApi.class).listAllAccounts()).withRel("accounts"));
        accountRepresentationModel.add(linkTo(methodOn(AccountApi.class).findAccountMovements(accountResponse.id())).withRel("movements"));
        accountRepresentationModel.add(linkTo(methodOn(AccountApi.class).findAccountNotifications(accountResponse.id())).withRel("notifications"));

        return accountRepresentationModel;
    }
}

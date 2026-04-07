package com.cwi.digitalbankapi.api.assembler;

import com.cwi.digitalbankapi.api.controller.AccountController;
import com.cwi.digitalbankapi.api.representation.AccountNotificationRepresentationModel;
import com.cwi.digitalbankapi.application.dto.AccountNotificationResponse;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountNotificationRepresentationModelAssembler implements RepresentationModelAssembler<AccountNotificationResponse, AccountNotificationRepresentationModel> {

    @Override
    public AccountNotificationRepresentationModel toModel(AccountNotificationResponse accountNotificationResponse) {
        AccountNotificationRepresentationModel accountNotificationRepresentationModel = new AccountNotificationRepresentationModel(
            accountNotificationResponse.accountId(),
            accountNotificationResponse.transferReference(),
            accountNotificationResponse.notificationStatus(),
            accountNotificationResponse.message(),
            accountNotificationResponse.createdAt()
        );

        accountNotificationRepresentationModel.add(linkTo(methodOn(AccountController.class).findAccountById(accountNotificationResponse.accountId())).withRel("account"));
        accountNotificationRepresentationModel.add(linkTo(methodOn(AccountController.class).findAccountNotifications(accountNotificationResponse.accountId())).withRel("collection"));

        return accountNotificationRepresentationModel;
    }
}

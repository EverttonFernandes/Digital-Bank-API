package com.digitalbankapi.api.assembler;

import com.digitalbankapi.api.controller.AccountApi;
import com.digitalbankapi.api.representation.AccountNotificationRepresentationModel;
import com.digitalbankapi.application.dto.AccountNotificationDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountNotificationRepresentationModelAssembler implements RepresentationModelAssembler<AccountNotificationDTO, AccountNotificationRepresentationModel> {

    @Override
    public AccountNotificationRepresentationModel toModel(AccountNotificationDTO accountNotificationResponse) {
        AccountNotificationRepresentationModel accountNotificationRepresentationModel = new AccountNotificationRepresentationModel(
                accountNotificationResponse.accountId(),
                accountNotificationResponse.transferReference(),
                accountNotificationResponse.notificationStatus(),
                accountNotificationResponse.message(),
                accountNotificationResponse.createdAt()
        );

        accountNotificationRepresentationModel.add(linkTo(methodOn(AccountApi.class).findAccountById(accountNotificationResponse.accountId())).withRel("account"));
        accountNotificationRepresentationModel.add(linkTo(methodOn(AccountApi.class).findAccountNotifications(accountNotificationResponse.accountId())).withRel("collection"));

        return accountNotificationRepresentationModel;
    }
}

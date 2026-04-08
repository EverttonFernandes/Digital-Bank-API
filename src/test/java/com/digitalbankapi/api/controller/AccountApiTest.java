package com.digitalbankapi.api.controller;

import com.digitalbankapi.api.assembler.AccountMovementRepresentationModelAssembler;
import com.digitalbankapi.api.assembler.AccountNotificationRepresentationModelAssembler;
import com.digitalbankapi.api.assembler.AccountRepresentationModelAssembler;
import com.digitalbankapi.api.representation.AccountMovementRepresentationModel;
import com.digitalbankapi.api.representation.AccountNotificationRepresentationModel;
import com.digitalbankapi.api.representation.AccountRepresentationModel;
import com.digitalbankapi.application.dto.AccountCreateDTO;
import com.digitalbankapi.application.dto.AccountDTO;
import com.digitalbankapi.application.dto.AccountMovementDTO;
import com.digitalbankapi.application.dto.AccountNotificationDTO;
import com.digitalbankapi.application.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.hateoas.CollectionModel;

import java.math.BigDecimal;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;

class AccountApiTest {

    private final AccountService accountService = mock(AccountService.class);
    private final AccountRepresentationModelAssembler accountRepresentationModelAssembler = mock(AccountRepresentationModelAssembler.class);
    private final AccountMovementRepresentationModelAssembler accountMovementRepresentationModelAssembler =
            mock(AccountMovementRepresentationModelAssembler.class);
    private final AccountNotificationRepresentationModelAssembler accountNotificationRepresentationModelAssembler =
            mock(AccountNotificationRepresentationModelAssembler.class);

    private final AccountApi accountApi = new AccountApi(
            accountService,
            accountRepresentationModelAssembler,
            accountMovementRepresentationModelAssembler,
            accountNotificationRepresentationModelAssembler
    );

    @Test
    @DisplayName("Deve criar conta e responder com location do link self")
    void shouldCreateAccountAndRespondWithSelfLocation() {
        AccountCreateDTO accountCreateDTO = new AccountCreateDTO("Maria", new BigDecimal("150.00"));
        AccountDTO accountDTO = new AccountDTO(7L, "Maria", new BigDecimal("150.00"));
        AccountRepresentationModel accountRepresentationModel = new AccountRepresentationModel(7L, "Maria", new BigDecimal("150.00"));
        accountRepresentationModel.add(org.springframework.hateoas.Link.of("http://localhost:8080/accounts/7").withSelfRel());

        BDDMockito.given(accountService.createAccount(accountCreateDTO)).willReturn(accountDTO);
        BDDMockito.given(accountRepresentationModelAssembler.toModel(accountDTO)).willReturn(accountRepresentationModel);

        var response = accountApi.createAccount(accountCreateDTO);

        Assertions.assertEquals(201, response.getStatusCode().value());
        Assertions.assertEquals(URI.create("http://localhost:8080/accounts/7"), response.getHeaders().getLocation());
        Assertions.assertSame(accountRepresentationModel, response.getBody());
    }

    @Test
    @DisplayName("Deve listar contas convertendo a resposta para collection model")
    void shouldListAccountsAsCollectionModel() {
        AccountDTO firstAccount = new AccountDTO(1L, "Ana", new BigDecimal("100.00"));
        AccountDTO secondAccount = new AccountDTO(2L, "Bruno", new BigDecimal("200.00"));
        AccountRepresentationModel firstRepresentation = new AccountRepresentationModel(1L, "Ana", new BigDecimal("100.00"));
        AccountRepresentationModel secondRepresentation = new AccountRepresentationModel(2L, "Bruno", new BigDecimal("200.00"));

        BDDMockito.given(accountService.listAllAccounts()).willReturn(List.of(firstAccount, secondAccount));
        BDDMockito.given(accountRepresentationModelAssembler.toModel(firstAccount)).willReturn(firstRepresentation);
        BDDMockito.given(accountRepresentationModelAssembler.toModel(secondAccount)).willReturn(secondRepresentation);

        CollectionModel<AccountRepresentationModel> response = accountApi.listAllAccounts();

        Assertions.assertEquals(2, response.getContent().size());
        Assertions.assertTrue(response.hasLink("self"));
    }

    @Test
    @DisplayName("Deve buscar conta por id usando service e assembler")
    void shouldFindAccountByIdUsingServiceAndAssembler() {
        AccountDTO accountDTO = new AccountDTO(1L, "Ana", new BigDecimal("100.00"));
        AccountRepresentationModel accountRepresentationModel = new AccountRepresentationModel(1L, "Ana", new BigDecimal("100.00"));

        BDDMockito.given(accountService.findAccountById(1L)).willReturn(accountDTO);
        BDDMockito.given(accountRepresentationModelAssembler.toModel(accountDTO)).willReturn(accountRepresentationModel);

        AccountRepresentationModel response = accountApi.findAccountById(1L);

        Assertions.assertSame(accountRepresentationModel, response);
    }

    @Test
    @DisplayName("Deve listar movimentacoes da conta com links de self e account")
    void shouldListAccountMovementsWithSelfAndAccountLinks() {
        AccountMovementDTO movementDTO = new AccountMovementDTO(
                1L,
                "ref-1",
                "DEBIT",
                new BigDecimal("50.00"),
                "Debito",
                OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );
        AccountMovementRepresentationModel movementRepresentationModel = new AccountMovementRepresentationModel(
                1L,
                "ref-1",
                "DEBIT",
                new BigDecimal("50.00"),
                "Debito",
                OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );

        BDDMockito.given(accountService.findAccountMovementsByAccountId(1L)).willReturn(List.of(movementDTO));
        BDDMockito.given(accountMovementRepresentationModelAssembler.toModel(movementDTO)).willReturn(movementRepresentationModel);

        CollectionModel<AccountMovementRepresentationModel> response = accountApi.findAccountMovements(1L);

        Assertions.assertEquals(1, response.getContent().size());
        Assertions.assertTrue(response.hasLink("self"));
        Assertions.assertTrue(response.hasLink("account"));
    }

    @Test
    @DisplayName("Deve listar notificacoes da conta com links de self e account")
    void shouldListAccountNotificationsWithSelfAndAccountLinks() {
        AccountNotificationDTO notificationDTO = new AccountNotificationDTO(
                1L,
                "ref-1",
                "REGISTERED",
                "Mensagem",
                OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );
        AccountNotificationRepresentationModel notificationRepresentationModel = new AccountNotificationRepresentationModel(
                1L,
                "ref-1",
                "REGISTERED",
                "Mensagem",
                OffsetDateTime.parse("2026-04-08T00:00:00Z")
        );

        BDDMockito.given(accountService.findAccountNotificationsByAccountId(1L)).willReturn(List.of(notificationDTO));
        BDDMockito.given(accountNotificationRepresentationModelAssembler.toModel(notificationDTO)).willReturn(notificationRepresentationModel);

        CollectionModel<AccountNotificationRepresentationModel> response = accountApi.findAccountNotifications(1L);

        Assertions.assertEquals(1, response.getContent().size());
        Assertions.assertTrue(response.hasLink("self"));
        Assertions.assertTrue(response.hasLink("account"));
    }
}

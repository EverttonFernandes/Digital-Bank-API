const {
    createGetRequest,
    createPostRequest,
    fixtures
} = require("../../../functionalTestHelper");

describe("GET /accounts/{id}/notifications deve refletir corretamente sucesso e falha na geracao de notificacoes", () => {
    it("deve retornar 404 com key e value quando a conta consultada nao existir", async () => {
        // GIVEN
        const nonExistingAccountIdentifier = fixtures.nonExistingAccountId;

        // WHEN
        const response = await createGetRequest(`/accounts/${nonExistingAccountIdentifier}/notifications`);

        // THEN
        expect(response.status).toBe(404);
        expect(response.body.key).toBe("ACCOUNT_NOT_FOUND");
        expect(response.body.value).toBe(`Conta nao encontrada para o identificador ${nonExistingAccountIdentifier}.`);
    });

    it("nao deve registrar notificacoes quando a transferencia falhar por saldo insuficiente", async () => {
        // GIVEN
        const insufficientBalanceTransfer = fixtures.transferFixtures.insufficientBalanceTransfer;
        const sourceAccountNotificationListBeforeFailureResponse = await createGetRequest(
            `/accounts/${insufficientBalanceTransfer.sourceAccountId}/notifications`
        );
        const targetAccountNotificationListBeforeFailureResponse = await createGetRequest(
            `/accounts/${insufficientBalanceTransfer.targetAccountId}/notifications`
        );

        // WHEN
        const transferFailureResponse = await createPostRequest("/transfers", insufficientBalanceTransfer);
        const sourceAccountNotificationListAfterFailureResponse = await createGetRequest(
            `/accounts/${insufficientBalanceTransfer.sourceAccountId}/notifications`
        );
        const targetAccountNotificationListAfterFailureResponse = await createGetRequest(
            `/accounts/${insufficientBalanceTransfer.targetAccountId}/notifications`
        );

        // THEN
        expect(transferFailureResponse.status).toBe(400);
        expect(transferFailureResponse.body.key).toBe("INSUFFICIENT_ACCOUNT_BALANCE");
        expect(sourceAccountNotificationListAfterFailureResponse.status).toBe(200);
        expect(targetAccountNotificationListAfterFailureResponse.status).toBe(200);
        expect(sourceAccountNotificationListAfterFailureResponse.body.length).toBe(
            sourceAccountNotificationListBeforeFailureResponse.body.length
        );
        expect(targetAccountNotificationListAfterFailureResponse.body.length).toBe(
            targetAccountNotificationListBeforeFailureResponse.body.length
        );
    });
});

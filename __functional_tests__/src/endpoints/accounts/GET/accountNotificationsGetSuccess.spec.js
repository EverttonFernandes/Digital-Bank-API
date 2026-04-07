const {
    createGetRequest,
    createPostRequest,
    fixtures
} = require("../../../functionalTestHelper");

async function waitForNotificationByTransferReference(resourcePath, transferReference) {
    const maximumAttempts = 10;

    for (let attempt = 1; attempt <= maximumAttempts; attempt += 1) {
        const response = await createGetRequest(resourcePath);
        const accountNotification = response.body._embedded.notifications.find(
            (currentAccountNotification) => currentAccountNotification.transferReference === transferReference
        );

        if (accountNotification) {
            return {
                response,
                accountNotification
            };
        }

        await new Promise((resolve) => setTimeout(resolve, 200));
    }

    return null;
}

describe("GET /accounts/{id}/notifications deve retornar as notificacoes geradas apos transferencia concluida", () => {
    it("deve registrar e expor notificacoes para conta de origem e conta de destino apos uma transferencia valida", async () => {
        // GIVEN
        const successfulTransfer = fixtures.transferFixtures.smallSuccessfulTransfer;
        const sourceAccountNotificationListBeforeTransferResponse = await createGetRequest(
            `/accounts/${successfulTransfer.sourceAccountId}/notifications`
        );
        const targetAccountNotificationListBeforeTransferResponse = await createGetRequest(
            `/accounts/${successfulTransfer.targetAccountId}/notifications`
        );

        // WHEN
        const transferResponse = await createPostRequest("/transfers", successfulTransfer);
        const sourceAccountNotificationResult = await waitForNotificationByTransferReference(
            `/accounts/${successfulTransfer.sourceAccountId}/notifications`,
            transferResponse.body.transferReference
        );
        const targetAccountNotificationResult = await waitForNotificationByTransferReference(
            `/accounts/${successfulTransfer.targetAccountId}/notifications`,
            transferResponse.body.transferReference
        );

        // THEN
        expect(transferResponse.status).toBe(200);
        expect(sourceAccountNotificationListBeforeTransferResponse.status).toBe(200);
        expect(targetAccountNotificationListBeforeTransferResponse.status).toBe(200);
        expect(sourceAccountNotificationResult).toBeDefined();
        expect(targetAccountNotificationResult).toBeDefined();
        expect(sourceAccountNotificationResult.response.status).toBe(200);
        expect(targetAccountNotificationResult.response.status).toBe(200);
        expect(sourceAccountNotificationResult.response.body._embedded.notifications.length).toBe(
            sourceAccountNotificationListBeforeTransferResponse.body._embedded.notifications.length + 1
        );
        expect(targetAccountNotificationResult.response.body._embedded.notifications.length).toBe(
            targetAccountNotificationListBeforeTransferResponse.body._embedded.notifications.length + 1
        );
        expect(sourceAccountNotificationResult.accountNotification.notificationStatus).toBe("REGISTERED");
        expect(targetAccountNotificationResult.accountNotification.notificationStatus).toBe("REGISTERED");
        expect(sourceAccountNotificationResult.accountNotification.message).toBe(
            "Transferencia enviada com sucesso para a conta 2."
        );
        expect(targetAccountNotificationResult.accountNotification.message).toBe(
            "Transferencia recebida com sucesso da conta 1."
        );
        expect(sourceAccountNotificationResult.accountNotification._links.account).toBeDefined();
        expect(sourceAccountNotificationResult.accountNotification._links.collection).toBeDefined();
        expect(targetAccountNotificationResult.accountNotification._links.account).toBeDefined();
        expect(targetAccountNotificationResult.accountNotification._links.collection).toBeDefined();
    });
});

const {
    createGetRequest,
    createPostRequest,
    fixtures
} = require("../../../functionalTestHelper");
const { findAccountByIdQuery } = require("../../../db/account/findAccountByIdQuery");

describe("POST /transfers deve transferir saldo entre contas validas e refletir o resultado no estado final", () => {
    it("deve transferir saldo com sucesso quando a conta de origem possuir saldo suficiente", async () => {
        // GIVEN
        const successfulTransfer = fixtures.transferFixtures.successfulTransfer;
        const sourceAccountBeforeTransfer = await findAccountByIdQuery(successfulTransfer.sourceAccountId);
        const targetAccountBeforeTransfer = await findAccountByIdQuery(successfulTransfer.targetAccountId);

        // WHEN
        const response = await createPostRequest("/transfers", successfulTransfer);
        const sourceAccountAfterTransferResponse = await createGetRequest(`/accounts/${successfulTransfer.sourceAccountId}`);
        const targetAccountAfterTransferResponse = await createGetRequest(`/accounts/${successfulTransfer.targetAccountId}`);

        // THEN
        expect(response.status).toBe(200);
        expect(response.body.sourceAccountId).toBe(successfulTransfer.sourceAccountId);
        expect(response.body.targetAccountId).toBe(successfulTransfer.targetAccountId);
        expect(Number(response.body.transferredAmount)).toBe(Number(successfulTransfer.amount));
        expect(Number(response.body.sourceAccountBalance)).toBe(
            Number(sourceAccountBeforeTransfer.balance) - Number(successfulTransfer.amount)
        );
        expect(Number(response.body.targetAccountBalance)).toBe(
            Number(targetAccountBeforeTransfer.balance) + Number(successfulTransfer.amount)
        );
        expect(response.body._links).toBeDefined();
        expect(response.body._links.transfers).toBeDefined();
        expect(response.body._links.sourceAccount).toBeDefined();
        expect(response.body._links.targetAccount).toBeDefined();
        expect(response.body._links.sourceAccountMovements).toBeDefined();
        expect(response.body._links.targetAccountMovements).toBeDefined();
        expect(response.body._links.sourceAccountNotifications).toBeDefined();
        expect(response.body._links.targetAccountNotifications).toBeDefined();
        expect(sourceAccountAfterTransferResponse.status).toBe(200);
        expect(targetAccountAfterTransferResponse.status).toBe(200);
        expect(Number(sourceAccountAfterTransferResponse.body.balance)).toBe(
            Number(sourceAccountBeforeTransfer.balance) - Number(successfulTransfer.amount)
        );
        expect(Number(targetAccountAfterTransferResponse.body.balance)).toBe(
            Number(targetAccountBeforeTransfer.balance) + Number(successfulTransfer.amount)
        );
        expect(sourceAccountAfterTransferResponse.body._links).toBeDefined();
        expect(targetAccountAfterTransferResponse.body._links).toBeDefined();
    });
});

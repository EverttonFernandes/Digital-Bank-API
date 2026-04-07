const {
    createGetRequest,
    createPostRequest,
    fixtures
} = require("../../../functionalTestHelper");

describe("GET /accounts/{id}/movements deve retornar o historico financeiro gerado pela transferencia", () => {
    it("deve retornar debito e credito vinculados a mesma transferencia apos a operacao ser concluida", async () => {
        // GIVEN
        const successfulTransfer = fixtures.transferFixtures.smallSuccessfulTransfer;
        const sourceAccountMovementListBeforeTransferResponse = await createGetRequest(
            `/accounts/${successfulTransfer.sourceAccountId}/movements`
        );
        const targetAccountMovementListBeforeTransferResponse = await createGetRequest(
            `/accounts/${successfulTransfer.targetAccountId}/movements`
        );

        // WHEN
        const transferResponse = await createPostRequest("/transfers", successfulTransfer);
        const sourceAccountMovementsResponse = await createGetRequest(`/accounts/${successfulTransfer.sourceAccountId}/movements`);
        const targetAccountMovementsResponse = await createGetRequest(`/accounts/${successfulTransfer.targetAccountId}/movements`);

        // THEN
        expect(transferResponse.status).toBe(200);
        expect(sourceAccountMovementListBeforeTransferResponse.status).toBe(200);
        expect(targetAccountMovementListBeforeTransferResponse.status).toBe(200);
        expect(sourceAccountMovementsResponse.status).toBe(200);
        expect(targetAccountMovementsResponse.status).toBe(200);
        expect(Array.isArray(sourceAccountMovementsResponse.body._embedded.movements)).toBe(true);
        expect(Array.isArray(targetAccountMovementsResponse.body._embedded.movements)).toBe(true);
        expect(sourceAccountMovementsResponse.body._embedded.movements.length).toBe(
            sourceAccountMovementListBeforeTransferResponse.body._embedded.movements.length + 1
        );
        expect(targetAccountMovementsResponse.body._embedded.movements.length).toBe(
            targetAccountMovementListBeforeTransferResponse.body._embedded.movements.length + 1
        );

        const sourceAccountMovementCreatedByCurrentTransfer = sourceAccountMovementsResponse.body._embedded.movements
            .find((accountMovement) => accountMovement.transferReference === transferResponse.body.transferReference);
        const targetAccountMovementCreatedByCurrentTransfer = targetAccountMovementsResponse.body._embedded.movements
            .find((accountMovement) => accountMovement.transferReference === transferResponse.body.transferReference);

        expect(sourceAccountMovementCreatedByCurrentTransfer).toBeDefined();
        expect(targetAccountMovementCreatedByCurrentTransfer).toBeDefined();
        expect(sourceAccountMovementCreatedByCurrentTransfer.movementType).toBe("DEBIT");
        expect(targetAccountMovementCreatedByCurrentTransfer.movementType).toBe("CREDIT");
        expect(Number(sourceAccountMovementCreatedByCurrentTransfer.amount)).toBe(Number(successfulTransfer.amount));
        expect(Number(targetAccountMovementCreatedByCurrentTransfer.amount)).toBe(Number(successfulTransfer.amount));
        expect(sourceAccountMovementCreatedByCurrentTransfer._links.account).toBeDefined();
        expect(sourceAccountMovementCreatedByCurrentTransfer._links.collection).toBeDefined();
        expect(targetAccountMovementCreatedByCurrentTransfer._links.account).toBeDefined();
        expect(targetAccountMovementCreatedByCurrentTransfer._links.collection).toBeDefined();
    });
});

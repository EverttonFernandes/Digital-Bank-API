const {
    createGetRequest,
    createPostRequest,
    fixtures
} = require("../../../functionalTestHelper");

describe("GET /accounts/{id}/movements deve retornar o historico financeiro gerado pela transferencia", () => {
    it("deve retornar debito e credito vinculados a mesma transferencia apos a operacao ser concluida", async () => {
        // GIVEN
        const successfulTransfer = fixtures.transferFixtures.successfulTransfer;

        // WHEN
        const transferResponse = await createPostRequest("/transfers", successfulTransfer);
        const sourceAccountMovementsResponse = await createGetRequest(`/accounts/${successfulTransfer.sourceAccountId}/movements`);
        const targetAccountMovementsResponse = await createGetRequest(`/accounts/${successfulTransfer.targetAccountId}/movements`);

        // THEN
        expect(transferResponse.status).toBe(200);
        expect(sourceAccountMovementsResponse.status).toBe(200);
        expect(targetAccountMovementsResponse.status).toBe(200);
        expect(Array.isArray(sourceAccountMovementsResponse.body)).toBe(true);
        expect(Array.isArray(targetAccountMovementsResponse.body)).toBe(true);
        expect(sourceAccountMovementsResponse.body).toHaveLength(1);
        expect(targetAccountMovementsResponse.body).toHaveLength(1);
        expect(sourceAccountMovementsResponse.body[0].movementType).toBe("DEBIT");
        expect(targetAccountMovementsResponse.body[0].movementType).toBe("CREDIT");
        expect(Number(sourceAccountMovementsResponse.body[0].amount)).toBe(Number(successfulTransfer.amount));
        expect(Number(targetAccountMovementsResponse.body[0].amount)).toBe(Number(successfulTransfer.amount));
        expect(sourceAccountMovementsResponse.body[0].transferReference).toBe(targetAccountMovementsResponse.body[0].transferReference);
        expect(sourceAccountMovementsResponse.body[0].transferReference).toBe(transferResponse.body.transferReference);
    });
});

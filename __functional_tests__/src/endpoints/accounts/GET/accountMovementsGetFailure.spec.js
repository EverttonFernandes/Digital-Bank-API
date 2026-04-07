const { createGetRequest, fixtures } = require("../../../functionalTestHelper");

describe("GET /accounts/{id}/movements deve falhar de forma coerente quando a conta consultada nao existir", () => {
    it("deve retornar 404 com key e value quando a conta nao existir", async () => {
        // GIVEN
        const nonExistingAccountIdentifier = fixtures.nonExistingAccountId;

        // WHEN
        const response = await createGetRequest(`/accounts/${nonExistingAccountIdentifier}/movements`);

        // THEN
        expect(response.status).toBe(404);
        expect(response.body.key).toBe("ACCOUNT_NOT_FOUND");
        expect(response.body.value).toBe(`Conta nao encontrada para o identificador ${nonExistingAccountIdentifier}.`);
    });
});

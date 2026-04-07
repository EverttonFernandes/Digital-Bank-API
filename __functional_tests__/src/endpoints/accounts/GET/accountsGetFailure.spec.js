const { createGetRequest, fixtures } = require("../../../functionalTestHelper");
const { findAccountByIdQuery } = require("../../../db/account/findAccountByIdQuery");

describe("GET /accounts/{id} deve retornar falha coerente quando a conta consultada nao existir", () => {
    it("deve retornar 404 com key e value quando o identificador nao existir na base", async () => {
        // GIVEN
        const accountInDatabase = await findAccountByIdQuery(fixtures.nonExistingAccountId);

        // WHEN
        const response = await createGetRequest(`/accounts/${fixtures.nonExistingAccountId}`);

        // THEN
        expect(accountInDatabase).toBeNull();
        expect(response.status).toBe(404);
        expect(response.body.key).toBe("ACCOUNT_NOT_FOUND");
        expect(response.body.value).toBe(
            `Conta nao encontrada para o identificador ${fixtures.nonExistingAccountId}.`
        );
    });
});

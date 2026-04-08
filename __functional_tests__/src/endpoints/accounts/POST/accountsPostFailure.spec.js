const {
    createPostRequest,
    createPostRequestWithRawBody,
    fixtures
} = require("../../../functionalTestHelper");
const { listAccountsQuery } = require("../../../db/account/listAccountsQuery");

describe("POST /accounts deve rejeitar cenarios invalidos e preservar o estado atual da base", () => {
    it("deve retornar falha quando o nome do titular nao for informado", async () => {
        // GIVEN
        const accountListBeforeCreationAttempt = await listAccountsQuery();

        // WHEN
        const response = await createPostRequest("/accounts", {
            initialBalance: "350.00"
        });

        // THEN
        expect(response.status).toBe(400);
        expect(response.body.key).toBe("INVALID_REQUEST_DATA");
        expect(response.body.value).toBe("O campo ownerName e obrigatorio.");
        expect((await listAccountsQuery()).length).toBe(accountListBeforeCreationAttempt.length);
    });

    it("deve retornar falha quando o nome do titular estiver em branco", async () => {
        // GIVEN
        const accountListBeforeCreationAttempt = await listAccountsQuery();

        // WHEN
        const response = await createPostRequest("/accounts", {
            ownerName: "   ",
            initialBalance: "350.00"
        });

        // THEN
        expect(response.status).toBe(400);
        expect(response.body.key).toBe("INVALID_REQUEST_DATA");
        expect(response.body.value).toBe("O campo ownerName e obrigatorio.");
        expect((await listAccountsQuery()).length).toBe(accountListBeforeCreationAttempt.length);
    });

    it("deve retornar falha quando o saldo inicial nao for informado", async () => {
        // GIVEN
        const accountListBeforeCreationAttempt = await listAccountsQuery();

        // WHEN
        const response = await createPostRequest("/accounts", {
            ownerName: "Maria Silva"
        });

        // THEN
        expect(response.status).toBe(400);
        expect(response.body.key).toBe("INVALID_REQUEST_DATA");
        expect(response.body.value).toBe("O campo initialBalance e obrigatorio.");
        expect((await listAccountsQuery()).length).toBe(accountListBeforeCreationAttempt.length);
    });

    it("deve retornar falha quando o saldo inicial for negativo", async () => {
        // GIVEN
        const invalidAccountWithNegativeInitialBalance = fixtures.accountCreationFixtures.invalidAccountWithNegativeInitialBalance;
        const accountListBeforeCreationAttempt = await listAccountsQuery();

        // WHEN
        const response = await createPostRequest("/accounts", invalidAccountWithNegativeInitialBalance);

        // THEN
        expect(response.status).toBe(400);
        expect(response.body.key).toBe("ACCOUNT_INITIAL_BALANCE_MUST_NOT_BE_NEGATIVE");
        expect(response.body.value).toBe("O saldo inicial da conta nao pode ser negativo.");
        expect((await listAccountsQuery()).length).toBe(accountListBeforeCreationAttempt.length);
    });

    it("deve retornar falha quando o corpo da requisicao estiver invalido", async () => {
        // GIVEN
        const accountListBeforeCreationAttempt = await listAccountsQuery();

        // WHEN
        const response = await createPostRequestWithRawBody("/accounts", "{ invalid json }");

        // THEN
        expect(response.status).toBe(400);
        expect(response.body.key).toBe("INVALID_REQUEST_DATA");
        expect(response.body.value).toBe("O corpo da requisicao esta invalido.");
        expect((await listAccountsQuery()).length).toBe(accountListBeforeCreationAttempt.length);
    });
});

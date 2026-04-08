const { createGetRequest, fixtures } = require("../../../functionalTestHelper");
const { listAccountsQuery } = require("../../../db/account/listAccountsQuery");

describe("GET /accounts deve listar as contas pre-carregadas com nome do titular e saldo corretos", () => {
    it("deve retornar as contas pre-carregadas quando a base estiver preparada para o cenario", async () => {
        // GIVEN
        const { sourceAccount, targetAccount, statementAccount } = fixtures.accountFixtures;
        const accountListInDatabase = await listAccountsQuery();
        const accountListByIdentifier = new Map(
            accountListInDatabase.map((account) => [Number(account.id), account])
        );

        // WHEN
        const response = await createGetRequest("/accounts");

        // THEN
        expect(response.status).toBe(200);
        expect(response.body._embedded).toBeDefined();
        expect(Array.isArray(response.body._embedded.accounts)).toBe(true);
        expect(response.body._embedded.accounts.length).toBeGreaterThanOrEqual(Object.keys(fixtures.accountFixtures).length);
        expect(response.body._links).toBeDefined();
        expect(response.body._links.self).toBeDefined();
        expect(accountListInDatabase.length).toBeGreaterThanOrEqual(Object.keys(fixtures.accountFixtures).length);

        const responseAccountByIdentifier = new Map(
            response.body._embedded.accounts.map((account) => [Number(account.id), account])
        );

        expect(responseAccountByIdentifier.get(sourceAccount.id).ownerName).toBe(sourceAccount.ownerName);
        expect(Number(responseAccountByIdentifier.get(sourceAccount.id).balance)).toBe(
            Number(accountListByIdentifier.get(sourceAccount.id).balance)
        );
        expect(responseAccountByIdentifier.get(targetAccount.id).ownerName).toBe(targetAccount.ownerName);
        expect(Number(responseAccountByIdentifier.get(targetAccount.id).balance)).toBe(
            Number(accountListByIdentifier.get(targetAccount.id).balance)
        );
        expect(responseAccountByIdentifier.get(statementAccount.id).ownerName).toBe(statementAccount.ownerName);
        expect(Number(responseAccountByIdentifier.get(statementAccount.id).balance)).toBe(
            Number(accountListByIdentifier.get(statementAccount.id).balance)
        );

        expect(responseAccountByIdentifier.get(sourceAccount.id).ownerName).toBe(
            accountListByIdentifier.get(sourceAccount.id).ownerName
        );
        expect(responseAccountByIdentifier.get(targetAccount.id).ownerName).toBe(
            accountListByIdentifier.get(targetAccount.id).ownerName
        );
        expect(responseAccountByIdentifier.get(statementAccount.id).ownerName).toBe(
            accountListByIdentifier.get(statementAccount.id).ownerName
        );
        expect(responseAccountByIdentifier.get(sourceAccount.id)._links.self).toBeDefined();
        expect(responseAccountByIdentifier.get(sourceAccount.id)._links.movements).toBeDefined();
        expect(responseAccountByIdentifier.get(sourceAccount.id)._links.notifications).toBeDefined();
    });
});

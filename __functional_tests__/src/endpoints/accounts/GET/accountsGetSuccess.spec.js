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
        expect(Array.isArray(response.body)).toBe(true);
        expect(response.body).toHaveLength(Object.keys(fixtures.accountFixtures).length);
        expect(accountListInDatabase).toHaveLength(Object.keys(fixtures.accountFixtures).length);

        const responseAccountByIdentifier = new Map(
            response.body.map((account) => [Number(account.id), account])
        );

        expect(accountListByIdentifier.get(sourceAccount.id).ownerName).toBe(sourceAccount.ownerName);
        expect(accountListByIdentifier.get(targetAccount.id).ownerName).toBe(targetAccount.ownerName);
        expect(accountListByIdentifier.get(statementAccount.id).ownerName).toBe(statementAccount.ownerName);

        expect(responseAccountByIdentifier.get(sourceAccount.id).ownerName).toBe(sourceAccount.ownerName);
        expect(Number(responseAccountByIdentifier.get(sourceAccount.id).balance)).toBe(Number(sourceAccount.balance));
        expect(responseAccountByIdentifier.get(targetAccount.id).ownerName).toBe(targetAccount.ownerName);
        expect(Number(responseAccountByIdentifier.get(targetAccount.id).balance)).toBe(Number(targetAccount.balance));
        expect(responseAccountByIdentifier.get(statementAccount.id).ownerName).toBe(statementAccount.ownerName);
        expect(Number(responseAccountByIdentifier.get(statementAccount.id).balance)).toBe(Number(statementAccount.balance));
    });
});

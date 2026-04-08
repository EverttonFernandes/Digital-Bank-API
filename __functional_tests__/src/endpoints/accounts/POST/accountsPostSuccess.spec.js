const {
    applicationBaseUrl,
    createGetRequest,
    createPostRequest,
    fixtures
} = require("../../../functionalTestHelper");
const { findAccountByIdQuery } = require("../../../db/account/findAccountByIdQuery");
const { listAccountsQuery } = require("../../../db/account/listAccountsQuery");

describe("POST /accounts deve criar conta bancaria valida e refletir o novo recurso no estado final", () => {
    it("deve criar conta com saldo inicial zero quando os dados informados forem validos", async () => {
        // GIVEN
        const validAccountWithZeroInitialBalance = fixtures.accountCreationFixtures.validAccountWithZeroInitialBalance;
        const accountListBeforeCreation = await listAccountsQuery();

        // WHEN
        const response = await createPostRequest("/accounts", validAccountWithZeroInitialBalance);
        const createdAccountIdentifier = response.body.id;
        const createdAccountResponse = await createGetRequest(`/accounts/${createdAccountIdentifier}`);
        const createdAccountInDatabase = await findAccountByIdQuery(createdAccountIdentifier);

        // THEN
        expect(response.status).toBe(201);
        expect(response.headers.location).toBe(`${applicationBaseUrl}/accounts/${createdAccountIdentifier}`);
        expect(response.body.ownerName).toBe(validAccountWithZeroInitialBalance.ownerName);
        expect(Number(response.body.balance)).toBe(Number(validAccountWithZeroInitialBalance.initialBalance));
        expect(response.body._links.self).toBeDefined();
        expect(response.body._links.accounts).toBeDefined();
        expect(response.body._links.movements).toBeDefined();
        expect(response.body._links.notifications).toBeDefined();
        expect(createdAccountResponse.status).toBe(200);
        expect(createdAccountResponse.body.id).toBe(createdAccountIdentifier);
        expect(createdAccountResponse.body.ownerName).toBe(validAccountWithZeroInitialBalance.ownerName);
        expect(createdAccountInDatabase).toBeDefined();
        expect(createdAccountInDatabase.ownerName).toBe(validAccountWithZeroInitialBalance.ownerName);
        expect(Number(createdAccountInDatabase.balance)).toBe(Number(validAccountWithZeroInitialBalance.initialBalance));
        expect((await listAccountsQuery()).length).toBe(accountListBeforeCreation.length + 1);
    });

    it("deve criar conta com saldo inicial positivo quando os dados informados forem validos", async () => {
        // GIVEN
        const validAccountWithPositiveInitialBalance = fixtures.accountCreationFixtures.validAccountWithPositiveInitialBalance;
        const accountListBeforeCreation = await listAccountsQuery();

        // WHEN
        const response = await createPostRequest("/accounts", validAccountWithPositiveInitialBalance);
        const createdAccountIdentifier = response.body.id;
        const createdAccountResponse = await createGetRequest(`/accounts/${createdAccountIdentifier}`);
        const createdAccountInDatabase = await findAccountByIdQuery(createdAccountIdentifier);

        // THEN
        expect(response.status).toBe(201);
        expect(response.headers.location).toBe(`${applicationBaseUrl}/accounts/${createdAccountIdentifier}`);
        expect(response.body.ownerName).toBe(validAccountWithPositiveInitialBalance.ownerName);
        expect(Number(response.body.balance)).toBe(Number(validAccountWithPositiveInitialBalance.initialBalance));
        expect(createdAccountResponse.status).toBe(200);
        expect(Number(createdAccountResponse.body.balance)).toBe(Number(validAccountWithPositiveInitialBalance.initialBalance));
        expect(createdAccountInDatabase).toBeDefined();
        expect(Number(createdAccountInDatabase.balance)).toBe(Number(validAccountWithPositiveInitialBalance.initialBalance));
        expect((await listAccountsQuery()).length).toBe(accountListBeforeCreation.length + 1);
    });
});

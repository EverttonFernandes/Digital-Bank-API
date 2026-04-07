const {
    createGetRequest,
    createPostRequest,
    createPostRequestWithRawBody,
    fixtures
} = require("../../../functionalTestHelper");

describe("POST /transfers deve rejeitar cenarios invalidos e manter os saldos originais das contas", () => {
    it("deve retornar falha quando a conta de origem nao existir", async () => {
        // GIVEN
        const nonExistingSourceAccountTransfer = fixtures.transferFixtures.nonExistingSourceAccountTransfer;
        const targetAccountBeforeTransferResponse = await createGetRequest(`/accounts/${nonExistingSourceAccountTransfer.targetAccountId}`);

        // WHEN
        const response = await createPostRequest("/transfers", nonExistingSourceAccountTransfer);
        const targetAccountAfterTransferResponse = await createGetRequest(`/accounts/${nonExistingSourceAccountTransfer.targetAccountId}`);

        // THEN
        expect(response.status).toBe(404);
        expect(response.body.key).toBe("ACCOUNT_NOT_FOUND");
        expect(response.body.value).toBe(
            `Conta nao encontrada para o identificador ${nonExistingSourceAccountTransfer.sourceAccountId}.`
        );
        expect(Number(targetAccountAfterTransferResponse.body.balance)).toBe(
            Number(targetAccountBeforeTransferResponse.body.balance)
        );
    });

    it("deve retornar falha quando a conta de destino nao existir", async () => {
        // GIVEN
        const nonExistingTargetAccountTransfer = fixtures.transferFixtures.nonExistingTargetAccountTransfer;
        const sourceAccountBeforeTransferResponse = await createGetRequest(`/accounts/${nonExistingTargetAccountTransfer.sourceAccountId}`);

        // WHEN
        const response = await createPostRequest("/transfers", nonExistingTargetAccountTransfer);
        const sourceAccountAfterTransferResponse = await createGetRequest(`/accounts/${nonExistingTargetAccountTransfer.sourceAccountId}`);

        // THEN
        expect(response.status).toBe(404);
        expect(response.body.key).toBe("ACCOUNT_NOT_FOUND");
        expect(response.body.value).toBe(
            `Conta nao encontrada para o identificador ${nonExistingTargetAccountTransfer.targetAccountId}.`
        );
        expect(Number(sourceAccountAfterTransferResponse.body.balance)).toBe(
            Number(sourceAccountBeforeTransferResponse.body.balance)
        );
    });

    it("deve retornar falha quando origem e destino forem a mesma conta", async () => {
        // GIVEN
        const sameAccountTransfer = fixtures.transferFixtures.sameAccountTransfer;
        const sourceAccountBeforeTransferResponse = await createGetRequest(`/accounts/${sameAccountTransfer.sourceAccountId}`);

        // WHEN
        const response = await createPostRequest("/transfers", sameAccountTransfer);
        const sourceAccountAfterTransferResponse = await createGetRequest(`/accounts/${sameAccountTransfer.sourceAccountId}`);

        // THEN
        expect(response.status).toBe(400);
        expect(response.body.key).toBe("SOURCE_AND_TARGET_ACCOUNTS_MUST_BE_DIFFERENT");
        expect(response.body.value).toBe("Conta de origem e conta de destino devem ser diferentes.");
        expect(Number(sourceAccountAfterTransferResponse.body.balance)).toBe(
            Number(sourceAccountBeforeTransferResponse.body.balance)
        );
    });

    it("deve retornar falha quando o valor da transferencia for zero", async () => {
        // GIVEN
        const invalidZeroAmountTransfer = fixtures.transferFixtures.invalidZeroAmountTransfer;
        const sourceAccountBeforeTransferResponse = await createGetRequest(`/accounts/${invalidZeroAmountTransfer.sourceAccountId}`);
        const targetAccountBeforeTransferResponse = await createGetRequest(`/accounts/${invalidZeroAmountTransfer.targetAccountId}`);

        // WHEN
        const response = await createPostRequest("/transfers", invalidZeroAmountTransfer);
        const sourceAccountAfterTransferResponse = await createGetRequest(`/accounts/${invalidZeroAmountTransfer.sourceAccountId}`);
        const targetAccountAfterTransferResponse = await createGetRequest(`/accounts/${invalidZeroAmountTransfer.targetAccountId}`);

        // THEN
        expect(response.status).toBe(400);
        expect(response.body.key).toBe("TRANSFER_AMOUNT_MUST_BE_POSITIVE");
        expect(response.body.value).toBe("O valor da transferencia deve ser maior que zero.");
        expect(Number(sourceAccountAfterTransferResponse.body.balance)).toBe(
            Number(sourceAccountBeforeTransferResponse.body.balance)
        );
        expect(Number(targetAccountAfterTransferResponse.body.balance)).toBe(
            Number(targetAccountBeforeTransferResponse.body.balance)
        );
    });

    it("deve retornar falha quando o valor da transferencia for negativo", async () => {
        // GIVEN
        const invalidNegativeAmountTransfer = fixtures.transferFixtures.invalidNegativeAmountTransfer;
        const sourceAccountBeforeTransferResponse = await createGetRequest(`/accounts/${invalidNegativeAmountTransfer.sourceAccountId}`);
        const targetAccountBeforeTransferResponse = await createGetRequest(`/accounts/${invalidNegativeAmountTransfer.targetAccountId}`);

        // WHEN
        const response = await createPostRequest("/transfers", invalidNegativeAmountTransfer);
        const sourceAccountAfterTransferResponse = await createGetRequest(`/accounts/${invalidNegativeAmountTransfer.sourceAccountId}`);
        const targetAccountAfterTransferResponse = await createGetRequest(`/accounts/${invalidNegativeAmountTransfer.targetAccountId}`);

        // THEN
        expect(response.status).toBe(400);
        expect(response.body.key).toBe("TRANSFER_AMOUNT_MUST_BE_POSITIVE");
        expect(response.body.value).toBe("O valor da transferencia deve ser maior que zero.");
        expect(Number(sourceAccountAfterTransferResponse.body.balance)).toBe(
            Number(sourceAccountBeforeTransferResponse.body.balance)
        );
        expect(Number(targetAccountAfterTransferResponse.body.balance)).toBe(
            Number(targetAccountBeforeTransferResponse.body.balance)
        );
    });

    it("deve retornar falha quando a conta de origem nao possuir saldo suficiente", async () => {
        // GIVEN
        const insufficientBalanceTransfer = fixtures.transferFixtures.insufficientBalanceTransfer;
        const sourceAccountBeforeTransferResponse = await createGetRequest(`/accounts/${insufficientBalanceTransfer.sourceAccountId}`);
        const targetAccountBeforeTransferResponse = await createGetRequest(`/accounts/${insufficientBalanceTransfer.targetAccountId}`);

        // WHEN
        const response = await createPostRequest("/transfers", insufficientBalanceTransfer);
        const sourceAccountAfterTransferResponse = await createGetRequest(`/accounts/${insufficientBalanceTransfer.sourceAccountId}`);
        const targetAccountAfterTransferResponse = await createGetRequest(`/accounts/${insufficientBalanceTransfer.targetAccountId}`);

        // THEN
        expect(response.status).toBe(400);
        expect(response.body.key).toBe("INSUFFICIENT_ACCOUNT_BALANCE");
        expect(response.body.value).toBe("A conta de origem nao possui saldo suficiente para a transferencia.");
        expect(Number(sourceAccountAfterTransferResponse.body.balance)).toBe(
            Number(sourceAccountBeforeTransferResponse.body.balance)
        );
        expect(Number(targetAccountAfterTransferResponse.body.balance)).toBe(
            Number(targetAccountBeforeTransferResponse.body.balance)
        );
    });

    it("deve retornar falha padronizada quando o campo sourceAccountId nao for informado", async () => {
        // GIVEN
        const invalidTransferWithoutSourceAccountId = {
            targetAccountId: fixtures.transferFixtures.successfulTransfer.targetAccountId,
            amount: fixtures.transferFixtures.successfulTransfer.amount
        };
        const targetAccountBeforeTransferResponse = await createGetRequest(
            `/accounts/${fixtures.transferFixtures.successfulTransfer.targetAccountId}`
        );

        // WHEN
        const response = await createPostRequest("/transfers", invalidTransferWithoutSourceAccountId);
        const targetAccountAfterTransferResponse = await createGetRequest(
            `/accounts/${fixtures.transferFixtures.successfulTransfer.targetAccountId}`
        );

        // THEN
        expect(response.status).toBe(400);
        expect(response.body.key).toBe("INVALID_REQUEST_DATA");
        expect(response.body.value).toBe("O campo sourceAccountId e obrigatorio.");
        expect(Number(targetAccountAfterTransferResponse.body.balance)).toBe(
            Number(targetAccountBeforeTransferResponse.body.balance)
        );
    });

    it("deve retornar falha padronizada quando o corpo da requisicao estiver invalido", async () => {
        // GIVEN
        const invalidRawRequestBody = "{\"sourceAccountId\":1,\"targetAccountId\":2,";
        const sourceAccountBeforeTransferResponse = await createGetRequest(`/accounts/${fixtures.transferFixtures.successfulTransfer.sourceAccountId}`);
        const targetAccountBeforeTransferResponse = await createGetRequest(`/accounts/${fixtures.transferFixtures.successfulTransfer.targetAccountId}`);

        // WHEN
        const response = await createPostRequestWithRawBody("/transfers", invalidRawRequestBody);
        const sourceAccountAfterTransferResponse = await createGetRequest(`/accounts/${fixtures.transferFixtures.successfulTransfer.sourceAccountId}`);
        const targetAccountAfterTransferResponse = await createGetRequest(`/accounts/${fixtures.transferFixtures.successfulTransfer.targetAccountId}`);

        // THEN
        expect(response.status).toBe(400);
        expect(response.body.key).toBe("INVALID_REQUEST_DATA");
        expect(response.body.value).toBe("O corpo da requisicao esta invalido.");
        expect(Number(sourceAccountAfterTransferResponse.body.balance)).toBe(
            Number(sourceAccountBeforeTransferResponse.body.balance)
        );
        expect(Number(targetAccountAfterTransferResponse.body.balance)).toBe(
            Number(targetAccountBeforeTransferResponse.body.balance)
        );
    });
});

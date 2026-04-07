const { Account } = require("../../models/account.model");

async function seedAccount(fixtures, transaction) {
    const accountFixtures = Object.values(fixtures.accountFixtures);

    return Account.bulkCreate(
        accountFixtures.map((accountFixture) => ({
            ...accountFixture,
            createdAt: new Date(),
            updatedAt: new Date()
        })),
        {
            updateOnDuplicate: [
                "ownerName",
                "balance",
                "updatedAt"
            ],
            transaction
        }
    );
}

async function rollbackAccount(fixtures, transaction) {
    const accountIdentifiers = Object.values(fixtures.accountFixtures)
        .map((accountFixture) => accountFixture.id);

    return Account.destroy({
        where: {
            id: accountIdentifiers
        },
        transaction
    });
}

module.exports = {
    seedAccount,
    rollbackAccount
};

const { Account } = require("../../models/account.model");

async function findAccountByIdQuery(accountIdentifier) {
    const account = await Account.findByPk(accountIdentifier);

    if (!account) {
        return null;
    }

    return {
        id: Number(account.id),
        ownerName: account.ownerName,
        balance: account.balance.toString()
    };
}

module.exports = {
    findAccountByIdQuery
};

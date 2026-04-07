const { Account } = require("../../models/account.model");

async function listAccountsQuery() {
    const accountList = await Account.findAll({
        order: [["id", "ASC"]]
    });

    return accountList.map((account) => ({
        id: Number(account.id),
        ownerName: account.ownerName,
        balance: account.balance.toString()
    }));
}

module.exports = {
    listAccountsQuery
};

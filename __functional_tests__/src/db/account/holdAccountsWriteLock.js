const { Op } = require("sequelize");
const { sequelizeConnection } = require("../connection");
const { Account } = require("../../models/account.model");

async function holdAccountsWriteLock(accountIdentifiers) {
    const transaction = await sequelizeConnection.transaction();

    await Account.findAll({
        where: {
            id: {
                [Op.in]: accountIdentifiers
            }
        },
        order: [["id", "ASC"]],
        lock: transaction.LOCK.UPDATE,
        transaction
    });

    return transaction;
}

async function releaseAccountsWriteLock(transaction) {
    if (transaction && !transaction.finished) {
        await transaction.rollback();
    }
}

module.exports = {
    holdAccountsWriteLock,
    releaseAccountsWriteLock
};

const { AccountNotification } = require("../../models/accountNotification.model");

async function cleanupAccountNotification(transaction) {
    await AccountNotification.destroy({
        where: {},
        truncate: true,
        restartIdentity: true,
        cascade: true,
        transaction
    });
}

module.exports = {
    cleanupAccountNotification
};

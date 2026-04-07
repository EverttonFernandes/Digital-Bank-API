const { AccountMovement } = require("../../models/accountMovement.model");

async function cleanupAccountMovement(transaction) {
    await AccountMovement.destroy({
        where: {},
        truncate: true,
        restartIdentity: true,
        cascade: true,
        transaction
    });
}

module.exports = {
    cleanupAccountMovement
};

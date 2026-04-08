const { sequelizeConnection } = require("./src/db/connection");
const { cleanupAccount } = require("./src/seeders/account/account.seeder");
const { cleanupAccountMovement } = require("./src/seeders/accountMovement/accountMovement.seeder");
const { cleanupAccountNotification } = require("./src/seeders/accountNotification/accountNotification.seeder");

async function runRollbackers() {
    const transaction = await sequelizeConnection.transaction();

    try {
        await cleanupAccountNotification(transaction);
        await cleanupAccountMovement(transaction);
        await cleanupAccount(transaction);
        await transaction.commit();
        await sequelizeConnection.close();
        console.log("Base funcional restaurada para o estado inicial.");
        process.exit(0);
    } catch (error) {
        await transaction.rollback();
        await sequelizeConnection.close();
        console.error("Falha ao restaurar a base funcional.", error);
        process.exit(1);
    }
}

runRollbackers();

const { sequelizeConnection } = require("./src/db/connection");
const { fixtures } = require("./src/functionalTestHelper");
const { seedAccount } = require("./src/seeders/account/account.seeder");
const { cleanupAccountMovement } = require("./src/seeders/accountMovement/accountMovement.seeder");
const { cleanupAccount } = require("./src/seeders/account/account.seeder");

async function runSeeders() {
    const transaction = await sequelizeConnection.transaction();

    try {
        await cleanupAccountMovement(transaction);
        await cleanupAccount(transaction);
        await seedAccount(fixtures, transaction);
        await transaction.commit();
        await sequelizeConnection.close();
        console.log("Dados funcionais preparados com sucesso.");
        process.exit(0);
    } catch (error) {
        await transaction.rollback();
        await sequelizeConnection.close();
        console.error("Falha ao preparar os dados funcionais.", error);
        process.exit(1);
    }
}

runSeeders();

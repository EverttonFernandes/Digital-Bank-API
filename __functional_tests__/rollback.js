const { sequelizeConnection } = require("./src/db/connection");
const { fixtures } = require("./src/functionalTestHelper");
const { rollbackAccount } = require("./src/seeders/account/account.seeder");

async function runRollbackers() {
    const transaction = await sequelizeConnection.transaction();

    try {
        await rollbackAccount(fixtures, transaction);
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

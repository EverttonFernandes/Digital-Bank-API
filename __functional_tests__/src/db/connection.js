const path = require("node:path");
const dotenv = require("dotenv");
const { Sequelize } = require("sequelize");
const fixtures = require("../fixtures/local.json");

dotenv.config({
    path: path.resolve(__dirname, "../../../.env")
});

const sequelizeConnection = new Sequelize(
    process.env.FUNCTIONAL_TEST_DATABASE_NAME || fixtures.database.databaseName,
    process.env.FUNCTIONAL_TEST_DATABASE_USERNAME || fixtures.database.username,
    process.env.FUNCTIONAL_TEST_DATABASE_PASSWORD || fixtures.database.password,
    {
    host: process.env.FUNCTIONAL_TEST_DATABASE_HOST || fixtures.database.host,
    port: Number(process.env.FUNCTIONAL_TEST_DATABASE_PORT || fixtures.database.port),
        dialect: "postgres",
        logging: false
    }
);

module.exports = {
    sequelizeConnection
};

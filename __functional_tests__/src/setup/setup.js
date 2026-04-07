jest.setTimeout(60000);
jest.retryTimes(2);

const { sequelizeConnection } = require("../db/connection");

afterAll(async () => {
    await sequelizeConnection.close();
});

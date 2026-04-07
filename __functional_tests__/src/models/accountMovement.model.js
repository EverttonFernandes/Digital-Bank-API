const { DataTypes } = require("sequelize");
const { sequelizeConnection } = require("../db/connection");

const AccountMovement = sequelizeConnection.define(
    "AccountMovement",
    {
        id: {
            type: DataTypes.BIGINT,
            primaryKey: true,
            autoIncrement: true,
            allowNull: false
        },
        accountId: {
            type: DataTypes.BIGINT,
            allowNull: false,
            field: "account_id"
        },
        transferReference: {
            type: DataTypes.STRING,
            allowNull: false,
            field: "transfer_reference"
        },
        movementType: {
            type: DataTypes.STRING,
            allowNull: false,
            field: "movement_type"
        },
        amount: {
            type: DataTypes.DECIMAL(19, 2),
            allowNull: false
        },
        description: {
            type: DataTypes.STRING,
            allowNull: false
        },
        createdAt: {
            type: DataTypes.DATE,
            allowNull: false,
            field: "created_at"
        }
    },
    {
        tableName: "account_movement",
        timestamps: false
    }
);

module.exports = {
    AccountMovement
};

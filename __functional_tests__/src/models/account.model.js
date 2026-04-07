const { DataTypes } = require("sequelize");
const { sequelizeConnection } = require("../db/connection");

const Account = sequelizeConnection.define(
    "Account",
    {
        id: {
            type: DataTypes.BIGINT,
            primaryKey: true,
            allowNull: false
        },
        ownerName: {
            type: DataTypes.STRING,
            allowNull: false,
            field: "owner_name"
        },
        balance: {
            type: DataTypes.DECIMAL(19, 2),
            allowNull: false
        },
        createdAt: {
            type: DataTypes.DATE,
            allowNull: false,
            field: "created_at"
        },
        updatedAt: {
            type: DataTypes.DATE,
            allowNull: false,
            field: "updated_at"
        }
    },
    {
        tableName: "account",
        timestamps: false
    }
);

module.exports = {
    Account
};

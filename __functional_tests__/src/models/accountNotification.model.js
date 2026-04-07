const { DataTypes } = require("sequelize");
const { sequelizeConnection } = require("../db/connection");

const AccountNotification = sequelizeConnection.define(
    "AccountNotification",
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
        notificationStatus: {
            type: DataTypes.STRING,
            allowNull: false,
            field: "notification_status"
        },
        message: {
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
        tableName: "account_notification",
        timestamps: false
    }
);

module.exports = {
    AccountNotification
};

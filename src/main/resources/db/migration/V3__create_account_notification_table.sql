CREATE TABLE account_notification
(
    id                  BIGSERIAL PRIMARY KEY,
    account_id          BIGINT                   NOT NULL REFERENCES account (id),
    transfer_reference  VARCHAR(100)             NOT NULL,
    notification_status VARCHAR(50)              NOT NULL,
    message             VARCHAR(255)             NOT NULL,
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL
);

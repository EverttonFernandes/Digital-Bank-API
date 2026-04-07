CREATE TABLE account_movement (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    transfer_reference VARCHAR(255) NOT NULL,
    movement_type VARCHAR(20) NOT NULL,
    amount NUMERIC(19,2) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_account_movement_account
        FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE account
(
    id         BIGINT PRIMARY KEY,
    owner_name VARCHAR(255)             NOT NULL,
    balance    NUMERIC(19, 2)           NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

INSERT INTO account (id,
                     owner_name,
                     balance,
                     created_at,
                     updated_at)
VALUES (1, 'Ana Souza', 1250.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 'Bruno Lima', 980.50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, 'Carla Mendes', 2100.75, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

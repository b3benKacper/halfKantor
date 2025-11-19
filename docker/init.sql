CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE exchange_rates (
    id BIGSERIAL PRIMARY KEY,
    from_currency VARCHAR(255) NOT NULL,
    last_updated TIMESTAMP WITHOUT TIME ZONE,
    rate NUMERIC(10,4) NOT NULL,
    to_currency VARCHAR(255) NOT NULL
);

CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    exchange_rate NUMERIC(38,2) NOT NULL,
    from_amount NUMERIC(38,2) NOT NULL,
    from_currency VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE,
    to_amount NUMERIC(38,2) NOT NULL,
    to_currency VARCHAR(255) NOT NULL,
    user_id BIGINT REFERENCES users(id)
);

CREATE TABLE system_logs (
    id BIGSERIAL PRIMARY KEY,
    action VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    timestamp TIMESTAMP WITHOUT TIME ZONE,
    user_id BIGINT REFERENCES users(id)
);
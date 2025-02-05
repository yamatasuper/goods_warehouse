CREATE TABLE order (
    id UUID PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('CREATED', 'CONFIRMED', 'CANCELLED', 'DONE', 'REJECTED')),
    delivery_address VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(255),
    created_at TIMESTAMP(6),
    description VARCHAR(255),
    last_quantity_update VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    currency VARCHAR(255),
    quantity INT NOT NULL,
    sku VARCHAR(255) NOT NULL
);


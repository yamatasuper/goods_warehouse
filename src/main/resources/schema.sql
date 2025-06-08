CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(255),
    created_at VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    last_quantity_update VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    currency VARCHAR(255),
    quantity INT NOT NULL,
    sku VARCHAR(255) NOT NULL
);
CREATE SEQUENCE IF NOT EXISTS product_id_seq START WITH 1 INCREMENT BY 1;
ALTER TABLE product ALTER COLUMN id SET DEFAULT NEXTVAL('product_id_seq');

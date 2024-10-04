CREATE TABLE product
(
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name                    VARCHAR(255) NOT NULL,
    sku                     VARCHAR(100) UNIQUE NOT NULL,
    description             TEXT,
    category                VARCHAR(100),
    price                   DECIMAL(10, 2) NOT NULL,
    quantity                INTEGER NOT NULL,
    last_quantity_update    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO product (id, name, sku, description, category, price, quantity)
VALUES
    (gen_random_uuid(), 'Товар 1', 'SKU001', 'Описание товара 1', 'Категория A', 100.00, 50),
    (gen_random_uuid(), 'Товар 2', 'SKU002', 'Описание товара 2', 'Категория B', 200.00, 30),
    (gen_random_uuid(), 'Товар 3', 'SKU003', 'Описание товара 3', 'Категория A', 150.00, 20),
    (gen_random_uuid(), 'Товар 4', 'SKU004', 'Описание товара 4', 'Категория C', 250.00, 10),
    (gen_random_uuid(), 'Товар 5', 'SKU005', 'Описание товара 5', 'Категория B', 300.00, 0),
    (gen_random_uuid(), 'Товар 6', 'SKU006', 'Описание товара 6', 'Категория A', 180.00, 25),
    (gen_random_uuid(), 'Товар 7', 'SKU007', 'Описание товара 7', 'Категория C', 120.00, 15),
    (gen_random_uuid(), 'Товар 8', 'SKU008', 'Описание товара 8', 'Категория B', 90.00, 40),
    (gen_random_uuid(), 'Товар 9', 'SKU009', 'Описание товара 9', 'Категория A', 130.00, 5),
    (gen_random_uuid(), 'Товар 11', 'SKU010', 'Описание товара 10', 'Категория C', 220.00, 12);


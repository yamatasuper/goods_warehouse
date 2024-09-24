CREATE TABLE goods
(
    id                      SERIAL PRIMARY KEY,  -- Уникальный идентификатор (или можно использовать UUID)
    name                    VARCHAR(255) NOT NULL,  -- Название товара
    sku                     VARCHAR(100) UNIQUE NOT NULL,  -- Артикул (уникальный)
    description             TEXT,  -- Описание товара
    category                VARCHAR(100),  -- Категория товара
    price                   DECIMAL(10, 2) NOT NULL,  -- Цена товара
    quantity                INTEGER NOT NULL,  -- Количество на складе
    last_quantity_update    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Дата и время последнего изменения количества
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- Дата создания записи
);


--changeset vjsinatra@gmail.com:1 labels:init-table context:development

CREATE TABLE items (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price NUMERIC(10,2) NOT NULL
);

CREATE TABLE orders (
    order_no VARCHAR(10) PRIMARY KEY,
    item_id BIGINT NOT NULL,
    qty INTEGER NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    CONSTRAINT fk_order_item
        FOREIGN KEY (item_id)
        REFERENCES item(id)
);

CREATE TABLE inventories (
    id BIGINT PRIMARY KEY,
    item_id BIGINT NOT NULL,
    qty INTEGER NOT NULL,
    type CHAR(1) NOT NULL,
    CONSTRAINT fk_inventory_item
        FOREIGN KEY (item_id)
        REFERENCES item(id)
);
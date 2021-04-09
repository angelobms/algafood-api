CREATE TABLE order_item (
    id BIGINT NOT NULL AUTO_INCREMENT,
    amount SMALLINT(6) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    observation VARCHAR(255) NULL,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_item_product (order_id, product_id),

    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES order_restaurant (id),
    CONSTRAINT fk_order_item_product FOREIGN KEY (product_id) REFERENCES product (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
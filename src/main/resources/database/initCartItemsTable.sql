CREATE TABLE IF NOT EXISTS cart_items (
                            id SERIAL PRIMARY KEY,
                            cart_id BIGINT,
                            product_id BIGINT,
                            quantity INT NOT NULL,
                            CONSTRAINT fk_cart FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE,
                            CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);
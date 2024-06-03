CREATE TABLE IF NOT EXISTS users
(
    id SERIAL PRIMARY KEY NOT NULL,
    first_name  VARCHAR(30) NOT NULL ,
    last_name  VARCHAR(30) NOT NULL ,
    email VARCHAR(254) NOT NULL UNIQUE,
    password VARCHAR(200)  NOT NULL,
    balance NUMERIC,
    role VARCHAR(20),
    cart_id BIGINT UNIQUE,
    CONSTRAINT fk_cart FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE SET NULL
);
--liquibase formatted sql

--changeset nhatvu:202301091645000000-create-product.sql

CREATE TABLE IF NOT EXISTS product(
    id UUID,
    name VARCHAR(100),
    description VARCHAR(100),
    price NUMERIC,
    created_at             timestamp default current_timestamp,
    updated_at             timestamp default current_timestamp
)
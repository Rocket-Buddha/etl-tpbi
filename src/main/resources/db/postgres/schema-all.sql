DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS sites;
DROP TABLE IF EXISTS business_types;

CREATE TABLE business_types
(
    business_type_id INTEGER PRIMARY KEY,
    initcap          VARCHAR(255) NOT NULL,
    active           BOOLEAN      NOT NULL
);

CREATE TABLE sites
(
    site_code VARCHAR(255) PRIMARY KEY,
    latitude  DOUBLE PRECISION,
    longitude DOUBLE PRECISION
);

CREATE TABLE customers
(
    customer_id       INTEGER PRIMARY KEY,
    email_address     VARCHAR(255),
    name              VARCHAR(255),
    business_type_id  INTEGER,
    default_site_code VARCHAR(255),
    archived          BOOLEAN,
    is_key_account    BOOLEAN,
    date_updated      TIMESTAMP,
    date_created      TIMESTAMP,
    client_value      VARCHAR(255),
    FOREIGN KEY (business_type_id) REFERENCES business_types (business_type_id),
    FOREIGN KEY (default_site_code) REFERENCES sites (site_code)
);

CREATE TABLE orders
(
    id                    SERIAL PRIMARY KEY,
    order_id              INTEGER,
    batch_id              VARCHAR(255),
    created_date          TIMESTAMP,
    updated_date          TIMESTAMP,
    submitted_date        TIMESTAMP,
    delivery_date         TIMESTAMP,
    customer_id           INTEGER,
    site_code             VARCHAR(255),
    total                 DECIMAL,
    total_usd             DECIMAL,
    total_shipping        DECIMAL,
    total_shipping_usd    DECIMAL,
    tracking_code         VARCHAR(255),
    order_status          VARCHAR(255),
    gmv_enabled           BOOLEAN,
    order_number          VARCHAR(255),
    shipping_by_tracking  DECIMAL,
    usd_official_listing  DECIMAL,
    used_parallel_listing DECIMAL,
    FOREIGN KEY (customer_id) REFERENCES customers (customer_id),
    FOREIGN KEY (site_code) REFERENCES sites (site_code)
);

INSERT INTO business_types (business_type_id, initcap, active)
VALUES (9, 'Cnpj Invalido', true);
INSERT INTO sites (site_code, longitude, latitude)
VALUES ('UNDEFINED', 0, 0);
INSERT INTO customers (customer_id, email_address, name, business_type_id, default_site_code, archived, is_key_account,
                       date_updated, date_created)
VALUES (0, 'generic@example.com', 'Undefined Customer', 9, 'UNDEFINED', FALSE, FALSE, NOW(), NOW());

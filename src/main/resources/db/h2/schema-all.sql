CREATE TABLE IF NOT EXISTS historical_data (
       date DATE PRIMARY KEY,
       price DECIMAL(10, 4)
);

CREATE TABLE IF NOT EXISTS historical_parallel_data (
    date DATE PRIMARY KEY,
    price DECIMAL(10, 4)
);

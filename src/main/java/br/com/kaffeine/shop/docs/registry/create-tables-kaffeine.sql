-- V1__create_orders_table.sql
CREATE TABLE kaffeine_orders (
                                 id VARCHAR(36) NOT NULL,
                                 customer_id VARCHAR(36) NOT NULL,
                                 total_amount DECIMAL(10, 2) NOT NULL,
                                 status VARCHAR(50) NOT NULL,
                                 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
                                 PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
-- Видалення таблиць, якщо вони існують
DROP TABLE IF EXISTS statement_info CASCADE;
DROP TABLE IF EXISTS statement CASCADE;
DROP TABLE IF EXISTS file_data CASCADE;

DROP TABLE IF EXISTS file_info CASCADE;
DROP SEQUENCE IF EXISTS hibernate_sequence;

-- Створення послідовності для генерації значень ID
CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE statement (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255),
    year_birthday VARCHAR(4),
    group_name VARCHAR(255),
    phone_number VARCHAR(255),
    faculty VARCHAR(255),
    type_of_statement VARCHAR(255)
);

CREATE TABLE statement_info (
    id BIGINT PRIMARY KEY,
    is_ready BOOLEAN,
    statement_status VARCHAR(50),
    CONSTRAINT fk_statement FOREIGN KEY (id) REFERENCES statement(id) ON DELETE CASCADE
);

CREATE TABLE file_info (
    id SERIAL PRIMARY KEY,
    file_name VARCHAR(255),
    file_type VARCHAR(255),
    statement_id BIGINT UNIQUE,
    CONSTRAINT fk_statement FOREIGN KEY (statement_id) REFERENCES statement(id) ON DELETE SET NULL
);

CREATE TABLE file_data (
    id SERIAL PRIMARY KEY,
    data BYTEA,
    file_info_id BIGINT UNIQUE,
    CONSTRAINT fk_file_info FOREIGN KEY (file_info_id) REFERENCES file_info(id) ON DELETE CASCADE
);




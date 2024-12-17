-- Видалення таблиць, якщо вони існують
DROP TABLE IF EXISTS statement CASCADE;
DROP TABLE IF EXISTS file_data CASCADE;
DROP TABLE IF EXISTS file_info CASCADE;
DROP TABLE IF EXISTS statement_info CASCADE;
DROP SEQUENCE IF EXISTS hibernate_sequence;

-- Створення послідовності для генерації значень ID
CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1;



CREATE TABLE statement (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    year_birthday VARCHAR(4) NOT NULL,
    group_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    faculty VARCHAR(255) NOT NULL,
    type_of_statement VARCHAR(255) NOT NULL
);


CREATE TABLE statement_info (
    id BIGINT PRIMARY KEY,
    is_ready BOOLEAN,
    statement_status VARCHAR(255),
    CONSTRAINT fk_statement FOREIGN KEY (id) REFERENCES statement(id) ON DELETE CASCADE
);


CREATE TABLE file_info (
    id BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(50) NOT NULL,
    statement_id BIGINT UNIQUE,
    CONSTRAINT fk_statement_info FOREIGN KEY (statement_id) REFERENCES statement_info(id) ON DELETE CASCADE
);



CREATE TABLE file_data (
    id BIGSERIAL PRIMARY KEY,
    data BYTEA NOT NULL,
    file_info_id BIGINT UNIQUE,
    CONSTRAINT fk_file_info FOREIGN KEY (file_info_id) REFERENCES file_info(id) ON DELETE CASCADE
);



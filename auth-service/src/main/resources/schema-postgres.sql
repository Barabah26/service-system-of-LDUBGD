DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Таблиця для ролей
CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL UNIQUE
);

-- Таблиця для користувачів
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       name VARCHAR(36) NOT NULL UNIQUE,
                       email VARCHAR(128) NOT NULL,
                       login VARCHAR(128) NOT NULL,
                       password VARCHAR(128) NOT NULL,
                       role_id INT NOT NULL,
                       CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

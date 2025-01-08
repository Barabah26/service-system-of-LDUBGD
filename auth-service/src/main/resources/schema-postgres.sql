DROP TABLE IF EXISTS users CASCADE;

-- Таблиця для користувачів
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       name VARCHAR(36) NOT NULL UNIQUE,
                       email VARCHAR(128) NOT NULL,
                       login VARCHAR(128) NOT NULL,
                       password VARCHAR(128) NOT NULL,
                       role VARCHAR(128) NOT NULL,
                       faculty VARCHAR(128),
                       specialty VARCHAR(128),
                       degree VARCHAR(128),
                       student_group VARCHAR(128)
);

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS admins CASCADE;
DROP TABLE IF EXISTS statement_info CASCADE;
DROP TABLE IF EXISTS statement CASCADE;
DROP TABLE IF EXISTS notification CASCADE;
DROP TABLE IF EXISTS file_data CASCADE;
DROP TABLE IF EXISTS file_info CASCADE;
DROP TABLE IF EXISTS forgot_password CASCADE;
DROP TABLE IF EXISTS forgot_password_info CASCADE;
DROP SEQUENCE IF EXISTS hibernate_sequence;

-- Створення послідовності для генерації значень ID
CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1;




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
                       student_group VARCHAR(128),
                       phone_number VARCHAR(128),
                       date_birth VARCHAR(128)
);

-- Таблиця для адміністраторів
CREATE TABLE admins (
                        admin_id SERIAL PRIMARY KEY,
                        name VARCHAR(36) NOT NULL UNIQUE,
                        login VARCHAR(128) NOT NULL,
                        password VARCHAR(128) NOT NULL,
                        role VARCHAR(128) NOT NULL
);



CREATE TABLE statement (
                           id BIGSERIAL PRIMARY KEY,
                           full_name VARCHAR(255),
                           year_birthday VARCHAR(255),
                           group_name VARCHAR(255),
                           phone_number VARCHAR(255),
                           faculty VARCHAR(255),
                           type_of_statement VARCHAR(255),
                           user_id BIGINT NOT NULL,
                           CONSTRAINT fk_user
                               FOREIGN KEY (user_id)
                                   REFERENCES users (user_id)
                                   ON DELETE CASCADE
);

CREATE TABLE statement_info (
                                id BIGSERIAL PRIMARY KEY,
                                is_ready BOOLEAN,
                                statement_status VARCHAR(50),
                                CONSTRAINT fk_statement
                                    FOREIGN KEY (id)
                                        REFERENCES statement (id)
                                        ON DELETE CASCADE
);

CREATE TABLE forgot_password (
                                 id BIGSERIAL PRIMARY KEY,
                                 type_of_forgot_password VARCHAR(255) NOT NULL,
                                 user_id BIGINT NOT NULL,
                                 login VARCHAR(255),
                                 password VARCHAR(255),
                                 CONSTRAINT fk_user
                                     FOREIGN KEY (user_id)
                                         REFERENCES users (user_id)
                                         ON DELETE CASCADE
);

CREATE TABLE forgot_password_info (
                                      id BIGINT PRIMARY KEY,
                                      is_ready BOOLEAN,
                                      statement_status VARCHAR(255),
                                      CONSTRAINT fk_forgot_password
                                          FOREIGN KEY (id)
                                              REFERENCES forgot_password (id)
                                              ON DELETE CASCADE
);





CREATE TABLE notification (
                              id BIGSERIAL PRIMARY KEY,
                              message VARCHAR(255) NOT NULL,
                              user_id BIGINT NOT NULL,
                              is_read BOOLEAN DEFAULT FALSE,
                              statement_info_id BIGINT,
                              forgot_password_info_id BIGINT,
                              CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
                              CONSTRAINT fk_statement_info FOREIGN KEY (statement_info_id) REFERENCES statement_info(id) ON DELETE CASCADE,
                              CONSTRAINT fk_forgot_password_info FOREIGN KEY (forgot_password_info_id) REFERENCES forgot_password_info(id) ON DELETE CASCADE
);



CREATE TABLE file_info (
    id BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(255) NOT NULL,
    statement_id BIGINT UNIQUE,
    CONSTRAINT fk_statement_info FOREIGN KEY (statement_id) REFERENCES statement_info(id) ON DELETE CASCADE
);



CREATE TABLE file_data (
    id BIGSERIAL PRIMARY KEY,
    data BYTEA NOT NULL,
    file_info_id BIGINT UNIQUE,
    CONSTRAINT fk_file_info FOREIGN KEY (file_info_id) REFERENCES file_info(id) ON DELETE CASCADE
);








DROP TABLE IF EXISTS statement_info CASCADE;
DROP TABLE IF EXISTS statement CASCADE;
DROP TABLE IF EXISTS notification CASCADE;

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

CREATE TABLE notification (
                              id BIGSERIAL PRIMARY KEY,
                              message VARCHAR(255) NOT NULL,
                              user_id BIGINT NOT NULL,
                              is_read BOOLEAN DEFAULT FALSE,
                              CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Видалення таблиць, якщо вони існують
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS statement CASCADE;
DROP TABLE IF EXISTS file_data CASCADE;
DROP TABLE IF EXISTS file_info CASCADE;
DROP TABLE IF EXISTS statement_info CASCADE;
DROP TABLE IF EXISTS telegram_cache CASCADE;
DROP TABLE IF EXISTS statement_cache CASCADE;
DROP SEQUENCE hibernate_sequence;

-- Створення послідовності для генерації значень ID
CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1;

-- Таблиця для зберігання користувачів Telegram
CREATE TABLE telegram_cache (
    id BIGINT PRIMARY KEY,
    massage_id INT,
    user_position VARCHAR(255)
);

-- Таблиця для зберігання кешу заявок
CREATE TABLE statement_cache (
    id BIGINT PRIMARY KEY,
    full_name VARCHAR(255),
    year_birthday VARCHAR(10),
    groupe VARCHAR(255),
    phone_number VARCHAR(20),
    faculty TEXT,
    type_of_statement TEXT,
    telegram_id BIGINT UNIQUE,
    FOREIGN KEY (telegram_id) REFERENCES telegram_cache(id) ON DELETE CASCADE
);

-- Створення таблиці заявок з генерацією ID за допомогою послідовності
CREATE TABLE statement (
    id BIGINT DEFAULT nextval('hibernate_sequence') PRIMARY KEY,
    full_name VARCHAR(255),
    year_birthday VARCHAR(10),
    group_name VARCHAR(255),
    phone_number VARCHAR(20),
    faculty TEXT,
    type_of_statement TEXT,
    telegram_id BIGINT
);

-- Таблиця для зберігання статусу заявки
CREATE TABLE statement_info (
    id BIGINT PRIMARY KEY,              -- PRIMARY KEY, пов'язаний із заявкою
    is_ready BOOLEAN,                   -- Статус готовності
    statement_status VARCHAR(255)       -- Статус заявки
);

-- Таблиця для зберігання користувачів системи
CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,      -- Унікальний ідентифікатор користувача
    user_name VARCHAR(36) NOT NULL,     -- Ім'я користувача
    encrypted_password VARCHAR(128) NOT NULL  -- Зашифроване пароль користувача
);

-- Таблиця для зберігання ролей
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,            -- Унікальний ідентифікатор ролі
    name VARCHAR(50) NOT NULL            -- Назва ролі
);

-- Таблиця для зв'язку між користувачами та ролями
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,             -- Ідентифікатор користувача
    role_id BIGINT NOT NULL,             -- Ідентифікатор ролі
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)       -- Первинний ключ, що складається з user_id і role_id
);

-- Таблиця для зберігання інформації про файли
CREATE TABLE file_info (
    id SERIAL PRIMARY KEY,                -- Унікальний ідентифікатор файлу
    file_name VARCHAR(255) NOT NULL,     -- Назва файлу
    file_type VARCHAR(255) NOT NULL,     -- Тип файлу
    statement_id BIGINT UNIQUE,           -- Ідентифікатор заяви, пов'язаної з файлом
    FOREIGN KEY (statement_id) REFERENCES statement_info(id) ON DELETE CASCADE
);

-- Таблиця для зберігання даних файлів
CREATE TABLE file_data (
    id SERIAL PRIMARY KEY,                -- Унікальний ідентифікатор даних файлу
    data BYTEA NOT NULL,                  -- Дані файлу у бінарному форматі
    file_info_id BIGINT UNIQUE,           -- Ідентифікатор інформації про файл
    FOREIGN KEY (file_info_id) REFERENCES file_info(id) ON DELETE CASCADE
);

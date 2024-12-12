-- Вставка тестових даних у таблицю statement
INSERT INTO statement (full_name, year_birthday, group_name, phone_number, faculty, type_of_statement)
VALUES
    ('John Doe', '1990-01-01', 'Group A', '1234567890', 'Faculty of Science', 'Application'),
    ('Jane Smith', '1992-05-15', 'Group B', '0987654321', 'Faculty of Arts', 'Request');

-- Вставка тестових даних у таблицю statement_info
INSERT INTO statement_info (id, is_ready, statement_status)
VALUES
    (1, false, 'PENDING'),
    (2, true, 'READY');
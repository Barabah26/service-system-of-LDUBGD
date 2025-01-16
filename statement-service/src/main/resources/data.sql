-- Вставка тестових даних у таблицю users
INSERT INTO users (name, email, login, password, role, faculty, specialty, degree, student_group, phone_number, date_birth)
VALUES
    ('John User', 'john.user@example.com', 'johnuser', 'password123', 'Student', 'Faculty of Science', 'Physics', 'Bachelor', 'Group A', '1234567890', '1990-01-01'),
    ('Jane User', 'jane.user@example.com', 'janeuser', 'password456', 'Student', 'Faculty of Arts', 'Literature', 'Master', 'Group B', '0987654321', '1992-05-15');

-- Вставка тестових даних у таблицю statement
INSERT INTO statement (full_name, year_birthday, group_name, phone_number, faculty, type_of_statement, user_id)
VALUES
    ('John Doe', '1990-01-01', 'Group A', '1234567890', 'Faculty of Science', 'Application', 1),
    ('Jane Smith', '1992-05-15', 'Group B', '0987654321', 'Faculty of Arts', 'Request', 2);

-- Вставка тестових даних у таблицю statement_info
INSERT INTO statement_info (id, is_ready, statement_status)
VALUES
    (1, false, 'PENDING'),
    (2, true, 'READY');
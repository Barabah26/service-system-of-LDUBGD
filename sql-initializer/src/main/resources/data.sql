INSERT INTO users (name, email, login, password, role, faculty, specialty, degree, student_group, phone_number, date_birth)
VALUES
    ('Pavlo', 'pavlo@gamil.com', 'student', '$2a$10$BXH1wlAJPIMXvjnJTBoRuea4CvZwSs8/Zqz4bDRZBDJ6hxvXoHlqq', 'STUDENT', 'Факультет цивільного захисту', '122 Комп’ютерні науки', 'бакалавр', 'КН43с', '', ''),
    ('Serhii', 'serhii@gamil.com', 'employee', '$2a$10$BXH1wlAJPIMXvjnJTBoRuea4CvZwSs8/Zqz4bDRZBDJ6hxvXoHlqq', 'STUDENT', 'Факультет інженерії', '122 Електроніка', 'бакалавр', 'КН42с', '', '');

INSERT INTO admins (name, login, password, role)
VALUES
    ('Admin1', 'admin1', '$2a$10$BXH1wlAJPIMXvjnJTBoRuea4CvZwSs8/Zqz4bDRZBDJ6hxvXoHlqq', 'ADMIN'),
    ('Admin2', 'admin2', '$2a$10$BXH1wlAJPIMXvjnJTBoRuea4CvZwSs8/Zqz4bDRZBDJ6hxvXoHlqq', 'SUPER_ADMIN');


-- Тестові дані для таблиці statement
INSERT INTO statement (full_name, year_birthday, group_name, phone_number, faculty, type_of_statement, user_id)
VALUES
    ('John Doe', '1990', 'Group A', '123-456-7890', 'Engineering', 'Type A', 1),
    ('Jane Smith', '1992', 'Group B', '987-654-3210', 'Science', 'Type B', 2),
    ('Test User', '2000', 'Group C', '555-555-5555', 'Arts', 'Type C', 1);

-- Тестові дані для таблиці statement_info
INSERT INTO statement_info (id, is_ready, statement_status)
VALUES
    (1, TRUE, 'READY'),
    (2, FALSE, 'PENDING'),
    (3, FALSE, 'IN_PROGRESS');
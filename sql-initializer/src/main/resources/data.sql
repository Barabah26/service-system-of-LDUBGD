INSERT INTO users (name, email, login, password, role, faculty, specialty, degree, student_group, phone_number, date_birth)
VALUES
    ('Pavlo', 'movie.to.48@gmail.com', 'student', '$2a$10$BXH1wlAJPIMXvjnJTBoRuea4CvZwSs8/Zqz4bDRZBDJ6hxvXoHlqq', 'STUDENT', 'Факультет цивільного захисту', '122 Комп’ютерні науки', 'бакалавр', 'КН43с', '', ''),
    ('Serhii', 'movie.to.48@gmail.com', 'employee', '$2a$10$BXH1wlAJPIMXvjnJTBoRuea4CvZwSs8/Zqz4bDRZBDJ6hxvXoHlqq', 'STUDENT', 'Факультет інженерії', '122 Електроніка', 'бакалавр', 'КН42с', '', '');

INSERT INTO admins (name, login, password, role)
VALUES
    ('Admin1', 'admin1', '$2a$10$BXH1wlAJPIMXvjnJTBoRuea4CvZwSs8/Zqz4bDRZBDJ6hxvXoHlqq', 'ADMIN'),
    ('Admin2', 'admin2', '$2a$10$BXH1wlAJPIMXvjnJTBoRuea4CvZwSs8/Zqz4bDRZBDJ6hxvXoHlqq', 'SUPER_ADMIN');


INSERT INTO statement (full_name, year_birthday, group_name, phone_number, faculty, type_of_statement, user_id)
VALUES
    ('Serhii Kmyta', '1990', 'Group A', '123-456-7890', 'Engineering', 'Type A', 1),
    ('Test User', '2000', 'Group C', '555-555-5555', 'Arts', 'Type C', 1),
    ('Pavlo ', '1992', 'Group B', '987-654-3210', 'Science', 'Type B', 2);


INSERT INTO statement_info (id, is_ready, statement_status)
VALUES
    (1, FALSE, 'READY'),
    (2, FALSE, 'PENDING'),
    (3, FALSE, 'PENDING');
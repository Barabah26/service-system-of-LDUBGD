INSERT INTO users (name, email, login, password, role, faculty, specialty, degree, student_group, phone_number, date_birth)
VALUES
    ('Pavlo', 'pavlo@gamil.com', 'student', '$2a$10$BXH1wlAJPIMXvjnJTBoRuea4CvZwSs8/Zqz4bDRZBDJ6hxvXoHlqq', 'STUDENT', 'Факультет цивільного захисту', '122 Комп’ютерні науки', 'бакалавр', 'КН43с', '', ''),
    ('Serhii', 'serhii@gamil.com', 'employee', '$2a$10$BXH1wlAJPIMXvjnJTBoRuea4CvZwSs8/Zqz4bDRZBDJ6hxvXoHlqq', 'STUDENT', 'Факультет інженерії', '122 Електроніка', 'бакалавр', 'КН42с', '', '');

INSERT INTO admins (name, login, password, role)
VALUES
    ('Admin1', 'admin1', '$2a$10$BXH1wlAJPIMXvjnJTBoRuea4CvZwSs8/Zqz4bDRZBDJ6hxvXoHlqq', 'ADMIN'),
    ('Admin2', 'admin2', '$2a$10$BXH1wlAJPIMXvjnJTBoRuea4CvZwSs8/Zqz4bDRZBDJ6hxvXoHlqq', 'SUPER_ADMIN');

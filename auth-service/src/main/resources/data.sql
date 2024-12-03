INSERT INTO roles (name) VALUES
                             ('STUDENT'),
                             ('EMPLOYEE');

INSERT INTO users (name, email, login, password, role_id)
VALUES
    ('Pavlo', 'pavlo@gamil.com', 'student', '$2a$10$BXH1wlAJPIMXvjnJTBoRuea4CvZwSs8/Zqz4bDRZBDJ6hxvXoHlqq', 1),
    ('Serhii', 'serhii@gamil.com', 'employee', '$2a$10$BXH1wlAJPIMXvjnJTBoRuea4CvZwSs8/Zqz4bDRZBDJ6hxvXoHlqq', 2);


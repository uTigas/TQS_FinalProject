-- Password: password
INSERT INTO role (role) VALUES ('USER') WHERE
    NOT EXISTS (
        SELECT role FROM role WHERE role = 'USER'
    );
INSERT INTO Role (role) VALUES ('ADMIN') WHERE
    NOT EXISTS (
        SELECT role FROM role WHERE role = 'ADMIN'
    );
INSERT INTO USERS (username, password, name, role) VALUES ('admin', '$2a$10$GiseHkdvwOFr7A9KRWbeiOmg/PYPhWVjdm42puLfOzR/gIAQrsAGy', 'Admin User', 'ADMIN');
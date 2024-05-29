CREATE TABLE IF NOT EXISTS Role (
    role VARCHAR(255) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS USERS (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255),
    name VARCHAR(255),
    role_role VARCHAR(255),
    FOREIGN KEY (role_role) REFERENCES Role(role)
);

-- Password: password
INSERT INTO role (role) VALUES ('USER') ON CONFLICT DO NOTHING;
INSERT INTO Role (role) VALUES ('ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO USERS (username, password, name, role_role) VALUES ('admin', '$2a$10$GiseHkdvwOFr7A9KRWbeiOmg/PYPhWVjdm42puLfOzR/gIAQrsAGy', 'Admin User', 'ADMIN') ON CONFLICT DO NOTHING;
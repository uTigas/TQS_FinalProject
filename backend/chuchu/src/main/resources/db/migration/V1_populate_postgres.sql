-- Password: password
INSERT INTO role (role) VALUES ('USER') ON CONFLICT DO NOTHING;
INSERT INTO Role (role) VALUES ('ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO USERS (username, password, name, role_role) VALUES ('admin', '$2a$10$GiseHkdvwOFr7A9KRWbeiOmg/PYPhWVjdm42puLfOzR/gIAQrsAGy', 'Admin User', 'ADMIN') ON CONFLICT DO NOTHING;
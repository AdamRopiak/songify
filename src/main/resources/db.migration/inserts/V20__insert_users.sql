INSERT INTO users (email, password, authorities, enabled)
VALUES
    ('Adam', '12345', '{ROLE_ADMIN, ROLE_USER}', true),
    ('John', '12345', '{ROLE_USER}', true)
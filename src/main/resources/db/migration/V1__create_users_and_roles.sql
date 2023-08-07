CREATE TABLE roles
(
    id      INT AUTO_INCREMENT NOT NULL,
    user_id INT NULL,
    `role`  VARCHAR(255) NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       INT AUTO_INCREMENT NOT NULL,
    username VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    enabled  INT DEFAULT 1 NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE roles
    ADD CONSTRAINT FK_ROLES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

INSERT INTO users (id, username, password)
VALUES (1, 'admin@gmail.com', '$2a$10$ZR1eDc3SRYB7ovZhQQGYDemzDst2hXu1lNqipDJdm0UaJCSQZ/67i');

INSERT INTO roles (id, user_id, role) VALUES (1, 1, 'ROLE_ADMIN');
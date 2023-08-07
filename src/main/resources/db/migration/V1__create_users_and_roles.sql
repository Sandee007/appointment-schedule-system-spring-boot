CREATE TABLE roles
(
    id      INT AUTO_INCREMENT NOT NULL,
    user_id INT                NULL,
    `role`  VARCHAR(255)       NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       INT AUTO_INCREMENT NOT NULL,
    username VARCHAR(255)       NULL,
    password VARCHAR(255)       NULL,
    email    VARCHAR(255)       NULL,
    enabled  INT DEFAULT 1      NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE roles
    ADD CONSTRAINT FK_ROLES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

INSERT INTO users (id, username, password, email)
VALUES (1, 'admin', '$2a$10$.b92Xwm2n.9M63mhXz2DbeJrzfvcdqYf8F3DUrx2x7./UyAcdMKPy', 'admin@gmail.com');

INSERT INTO roles (id, user_id, role) VALUES (1, 1, 'ROLE_ADMIN');
CREATE TABLE consultants
(
    id              INT AUTO_INCREMENT NOT NULL,
    user_id         INT NULL,
    firstname       VARCHAR(255) NULL,
    lastname        VARCHAR(255) NULL,
    birthday        date NULL,
    `description`   LONGTEXT NULL,
    phone           VARCHAR(20) NULL,
    charge_per_hour INT NULL,
    CONSTRAINT pk_consultants PRIMARY KEY (id)
);

ALTER TABLE consultants
    ADD CONSTRAINT FK_CONSULTANTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);
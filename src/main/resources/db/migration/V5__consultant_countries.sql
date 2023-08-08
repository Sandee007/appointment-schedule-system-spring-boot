CREATE TABLE consultant_countries
(
    consultant_id INT NOT NULL,
    country_id    INT NOT NULL
);

CREATE TABLE countries
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_countries PRIMARY KEY (id)
);

ALTER TABLE consultant_countries
    ADD CONSTRAINT fk_concou_on_consultant FOREIGN KEY (consultant_id) REFERENCES consultants (id);

ALTER TABLE consultant_countries
    ADD CONSTRAINT fk_concou_on_country FOREIGN KEY (country_id) REFERENCES countries (id);
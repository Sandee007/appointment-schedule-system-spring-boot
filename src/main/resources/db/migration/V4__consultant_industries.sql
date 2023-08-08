CREATE TABLE consultant_industries
(
    consultant_id INT NOT NULL,
    industry_id   INT NOT NULL
);

CREATE TABLE industries
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_industries PRIMARY KEY (id)
);

ALTER TABLE consultant_industries
    ADD CONSTRAINT fk_conind_on_consultant FOREIGN KEY (consultant_id) REFERENCES consultants (id);

ALTER TABLE consultant_industries
    ADD CONSTRAINT fk_conind_on_industry FOREIGN KEY (industry_id) REFERENCES industries (id);
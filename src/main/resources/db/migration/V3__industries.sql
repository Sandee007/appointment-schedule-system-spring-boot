CREATE TABLE consultant_industries
(
    id            INT AUTO_INCREMENT NOT NULL,
    consultant_id INT NULL,
    industry_id   INT NULL,
    CONSTRAINT pk_consultant_industries PRIMARY KEY (id)
);

CREATE TABLE industries
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_industries PRIMARY KEY (id)
);

ALTER TABLE consultant_industries
    ADD CONSTRAINT FK_CONSULTANT_INDUSTRIES_ON_CONSULTANT FOREIGN KEY (consultant_id) REFERENCES consultants (id);

ALTER TABLE consultant_industries
    ADD CONSTRAINT FK_CONSULTANT_INDUSTRIES_ON_INDUSTRY FOREIGN KEY (industry_id) REFERENCES industries (id);
CREATE TABLE consultant_schedule_dates
(
    id            INT AUTO_INCREMENT NOT NULL,
    date          date NULL,
    consultant_id INT NULL,
    CONSTRAINT pk_consultant_schedule_dates PRIMARY KEY (id)
);

CREATE TABLE consultant_schedule_times
(
    id                          INT AUTO_INCREMENT NOT NULL,
    consultant_schedule_date_id INT NULL,
    time_slot_id                INT NULL,
    CONSTRAINT pk_consultant_schedule_times PRIMARY KEY (id)
);

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

CREATE TABLE time_slots
(
    id         INT AUTO_INCREMENT NOT NULL,
    slot_start time NULL,
    slot_end   time NULL,
    CONSTRAINT pk_time_slots PRIMARY KEY (id)
);

ALTER TABLE consultants
    ADD CONSTRAINT FK_CONSULTANTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE consultant_schedule_dates
    ADD CONSTRAINT FK_CONSULTANT_SCHEDULE_DATES_ON_CONSULTANT FOREIGN KEY (consultant_id) REFERENCES consultants (id);

ALTER TABLE consultant_schedule_times
    ADD CONSTRAINT FK_CONSULTANT_SCHEDULE_TIMES_ON_CONSULTANT_SCHEDULE_DATE FOREIGN KEY (consultant_schedule_date_id) REFERENCES consultant_schedule_dates (id);

ALTER TABLE consultant_schedule_times
    ADD CONSTRAINT FK_CONSULTANT_SCHEDULE_TIMES_ON_TIME_SLOT FOREIGN KEY (time_slot_id) REFERENCES time_slots (id);
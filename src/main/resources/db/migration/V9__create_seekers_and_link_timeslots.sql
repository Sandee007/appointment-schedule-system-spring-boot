CREATE TABLE consultant_schedule_date_timeslots
(
    id                          INT AUTO_INCREMENT NOT NULL,
    consultant_schedule_date_id INT NULL,
    timeslot_id                 INT NULL,
    seeker_id                   INT NULL,
    deleted_at                  datetime NULL,
    CONSTRAINT pk_consultant_schedule_date_timeslots PRIMARY KEY (id)
);

CREATE TABLE consultant_schedule_dates
(
    id            INT AUTO_INCREMENT NOT NULL,
    date          date NULL,
    deleted_at    datetime NULL,
    consultant_id INT NULL,
    CONSTRAINT pk_consultant_schedule_dates PRIMARY KEY (id)
);

CREATE TABLE seekers
(
    id            INT AUTO_INCREMENT NOT NULL,
    firstname     VARCHAR(255) NULL,
    lastname      VARCHAR(255) NULL,
    birthday      date NULL,
    phone         VARCHAR(255) NULL,
    address       LONGTEXT NULL,
    `description` LONGTEXT NULL,
    image         VARCHAR(255) NULL,
    deleted_at    datetime NULL,
    CONSTRAINT pk_seekers PRIMARY KEY (id)
);

CREATE TABLE time_slots
(
    id         INT AUTO_INCREMENT NOT NULL,
    slot_start time NULL,
    slot_end   time NULL,
    deleted_at datetime NULL,
    CONSTRAINT pk_time_slots PRIMARY KEY (id)
);

ALTER TABLE consultant_schedule_date_timeslots
    ADD CONSTRAINT FK_CONSULTANTSCHEDULEDATETIMESLOTS_ON_CONSULTANTSCHEDULEDATE FOREIGN KEY (consultant_schedule_date_id) REFERENCES consultant_schedule_dates (id);

ALTER TABLE consultant_schedule_dates
    ADD CONSTRAINT FK_CONSULTANT_SCHEDULE_DATES_ON_CONSULTANT FOREIGN KEY (consultant_id) REFERENCES consultants (id);

ALTER TABLE consultant_schedule_date_timeslots
    ADD CONSTRAINT FK_CONSULTANT_SCHEDULE_DATE_TIMESLOTS_ON_SEEKER FOREIGN KEY (seeker_id) REFERENCES seekers (id);

ALTER TABLE consultant_schedule_date_timeslots
    ADD CONSTRAINT FK_CONSULTANT_SCHEDULE_DATE_TIMESLOTS_ON_TIMESLOT FOREIGN KEY (timeslot_id) REFERENCES time_slots (id);
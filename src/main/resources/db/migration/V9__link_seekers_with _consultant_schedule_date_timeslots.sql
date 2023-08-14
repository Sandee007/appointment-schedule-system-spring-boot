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

ALTER TABLE consultant_schedule_date_timeslots
    ADD consultant_schedule_date_id INT NULL;

ALTER TABLE consultant_schedule_date_timeslots
    ADD deleted_at datetime NULL;

ALTER TABLE consultant_schedule_date_timeslots
    ADD id INT AUTO_INCREMENT NULL;

ALTER TABLE consultant_schedule_date_timeslots
    ADD seeker_id INT NULL;

ALTER TABLE consultant_schedule_date_timeslots
    ADD PRIMARY KEY (id);

ALTER TABLE consultant_schedule_date_timeslots
    ADD CONSTRAINT FK_CONSULTANTSCHEDULEDATETIMESLOTS_ON_CONSULTANTSCHEDULEDATE FOREIGN KEY (consultant_schedule_date_id) REFERENCES consultant_schedule_dates (id);

ALTER TABLE consultant_schedule_date_timeslots
    ADD CONSTRAINT FK_CONSULTANT_SCHEDULE_DATE_TIMESLOTS_ON_SEEKER FOREIGN KEY (seeker_id) REFERENCES seekers (id);

ALTER TABLE consultant_schedule_date_timeslots
DROP
FOREIGN KEY fk_conschdattim_on_consultant_schedule_date;

ALTER TABLE consultant_schedule_date_timeslots
DROP
COLUMN consultant_schedule_date;

ALTER TABLE consultant_schedule_date_timeslots
    MODIFY timeslot_id INT NULL;
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
    user_id       INT NULL,
    CONSTRAINT pk_seekers PRIMARY KEY (id)
);

CREATE TABLE timeslots
(
    id         INT AUTO_INCREMENT NOT NULL,
    slot_start time NULL,
    slot_end   time NULL,
    deleted_at datetime NULL,
    CONSTRAINT pk_timeslots PRIMARY KEY (id)
);

ALTER TABLE consultant_schedule_date_timeslots
    ADD CONSTRAINT FK_CONSULTANTSCHEDULEDATETIMESLOTS_ON_CONSULTANTSCHEDULEDATE FOREIGN KEY (consultant_schedule_date_id) REFERENCES consultant_schedule_dates (id);

ALTER TABLE consultant_schedule_dates
    ADD CONSTRAINT FK_CONSULTANT_SCHEDULE_DATES_ON_CONSULTANT FOREIGN KEY (consultant_id) REFERENCES consultants (id);

ALTER TABLE consultant_schedule_date_timeslots
    ADD CONSTRAINT FK_CONSULTANT_SCHEDULE_DATE_TIMESLOTS_ON_SEEKER FOREIGN KEY (seeker_id) REFERENCES seekers (id);

ALTER TABLE consultant_schedule_date_timeslots
    ADD CONSTRAINT FK_CONSULTANT_SCHEDULE_DATE_TIMESLOTS_ON_TIMESLOT FOREIGN KEY (timeslot_id) REFERENCES timeslots (id);

ALTER TABLE seekers
    ADD CONSTRAINT FK_SEEKERS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

INSERT INTO timeslots (slot_start, slot_end)
VALUES
    ('00:00', '00:30'),
    ('01:00', '01:30'),
    ('02:00', '02:30'),
    ('03:00', '03:30'),
    ('04:00', '04:30'),
    ('05:00', '05:30'),
    ('06:00', '06:30'),
    ('07:00', '07:30'),
    ('08:00', '08:30'),
    ('09:00', '09:30'),
    ('10:00', '10:30'),
    ('11:00', '11:30'),
    ('12:00', '12:30'),
    ('13:00', '13:30'),
    ('14:00', '14:30'),
    ('15:00', '15:30'),
    ('16:00', '16:30'),
    ('17:00', '17:30'),
    ('18:00', '18:30'),
    ('19:00', '19:30'),
    ('20:00', '20:30'),
    ('21:00', '21:30'),
    ('22:00', '22:30'),
    ('23:00', '23:30');
CREATE TABLE consultant_schedule_date_timeslots
(
    consultant_schedule_date INT NOT NULL,
    timeslot_id              INT NOT NULL
);

CREATE TABLE consultant_schedule_dates
(
    id            INT AUTO_INCREMENT NOT NULL,
    date          date NULL,
    consultant_id INT NULL,
    CONSTRAINT pk_consultant_schedule_dates PRIMARY KEY (id)
);

CREATE TABLE time_slots
(
    id         INT AUTO_INCREMENT NOT NULL,
    slot_start time NULL,
    slot_end   time NULL,
    CONSTRAINT pk_time_slots PRIMARY KEY (id)
);

ALTER TABLE consultant_schedule_dates
    ADD CONSTRAINT FK_CONSULTANT_SCHEDULE_DATES_ON_CONSULTANT FOREIGN KEY (consultant_id) REFERENCES consultants (id);

ALTER TABLE consultant_schedule_date_timeslots
    ADD CONSTRAINT fk_conschdattim_on_consultant_schedule_date FOREIGN KEY (consultant_schedule_date) REFERENCES consultant_schedule_dates (id);

ALTER TABLE consultant_schedule_date_timeslots
    ADD CONSTRAINT fk_conschdattim_on_time_slot FOREIGN KEY (timeslot_id) REFERENCES time_slots (id);
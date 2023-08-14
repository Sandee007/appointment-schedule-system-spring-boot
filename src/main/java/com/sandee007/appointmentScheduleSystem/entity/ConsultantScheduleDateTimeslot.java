package com.sandee007.appointmentScheduleSystem.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "consultant_schedule_date_timeslots")
public class ConsultantScheduleDateTimeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "consultant_schedule_date_id")
    private ConsultantScheduleDate consultantScheduleDate;

    @OneToOne
    @JoinColumn(name = "timeslot_id")
    private TimeSlot timeSlot;

    @OneToOne
    @JoinColumn(name = "seeker_id")
    private Seeker seeker;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    public ConsultantScheduleDateTimeslot() {
    }

    public ConsultantScheduleDateTimeslot(
            ConsultantScheduleDate consultantScheduleDate,
            TimeSlot timeSlot,
            Seeker seeker,
            Date deletedAt
    ) {
        this.consultantScheduleDate = consultantScheduleDate;
        this.timeSlot = timeSlot;
        this.seeker = seeker;
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return "ConsultantScheduleDateTimeslot{" +
                "id=" + id +
                ", deletedAt=" + deletedAt +
                '}';
    }
}

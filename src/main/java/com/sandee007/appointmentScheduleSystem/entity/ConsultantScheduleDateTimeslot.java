package com.sandee007.appointmentScheduleSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@Entity
@Table(name = "consultant_schedule_date_timeslots")
@Getter
@Setter
@SQLDelete(sql = "UPDATE consultant_schedule_date_timeslots SET deleted_at=CURRENT_TIMESTAMP WHERE seeker_id IS NULL AND id=?")
@Where(clause = "deleted_at IS NULL")
public class ConsultantScheduleDateTimeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_schedule_date_id")
    private ConsultantScheduleDate consultantScheduleDate;

//    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "timeslot_id")
    private TimeSlot timeslot;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seeker_id")
    private Seeker seeker;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "status", columnDefinition = "integer default 0")
    private int status;

    public ConsultantScheduleDateTimeslot() {
    }

    public ConsultantScheduleDateTimeslot(
            ConsultantScheduleDate consultantScheduleDate,
            TimeSlot timeSlot,
            Seeker seeker,
            Date deletedAt
    ) {
        this.consultantScheduleDate = consultantScheduleDate;
        this.timeslot = timeSlot;
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

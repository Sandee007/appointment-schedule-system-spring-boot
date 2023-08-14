package com.sandee007.appointmentScheduleSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "consultant_schedule_dates")
@Getter
@Setter
public class ConsultantScheduleDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;

//    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinTable(
//            name = "consultant_schedule_date_timeslots",
//            joinColumns = @JoinColumn(name = "consultant_schedule_date_id"),
//            inverseJoinColumns = @JoinColumn(name = "timeslot_id")
//    )
//    private List<TimeSlot> timeSlots;

    public ConsultantScheduleDate(){}

    public ConsultantScheduleDate(Date date, Consultant consultant) {
        this.date = date;
        this.consultant = consultant;
    }

    @Override
    public String toString() {
        return "ConsultantScheduleDate{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}

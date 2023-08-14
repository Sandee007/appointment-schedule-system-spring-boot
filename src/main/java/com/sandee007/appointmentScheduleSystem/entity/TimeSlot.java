package com.sandee007.appointmentScheduleSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "time_slots")
@Getter
@Setter
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Temporal(TemporalType.TIME)
    @Column(name = "slot_start")
    private Date slotStart;

    @Temporal(TemporalType.TIME)
    @Column(name = "slot_end")
    private Date slotEnd;

//    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JoinTable(
//            name = "consultant_schedule_date_timeslots",
//            joinColumns = @JoinColumn(name = "timeslot_id"),
//            inverseJoinColumns = @JoinColumn(name = "consultant_schedule_date_id")
//    )
//    private List<ConsultantScheduleDate> consultantScheduleDates;

    public TimeSlot(){}

    public TimeSlot(Date slotStart, Date slotEnd) {
        this.slotStart = slotStart;
        this.slotEnd = slotEnd;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", slotStart=" + slotStart +
                ", slotEnd=" + slotEnd +
                '}';
    }
}

package com.sandee007.appointmentScheduleSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

package com.sandee007.appointmentScheduleSystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "consultant_schedule_times")
@Getter
@Setter
public class ConsultantScheduleTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_schedule_date_id")
    private ConsultantScheduleDate consultantScheduleDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_slot_id")
    private TimeSlot timeSlot;

    public ConsultantScheduleTime() {
    }

    public ConsultantScheduleTime(ConsultantScheduleDate consultantScheduleDate, TimeSlot timeSlot) {
        this.consultantScheduleDate = consultantScheduleDate;
        this.timeSlot = timeSlot;
    }

    @Override
    public String toString() {
        return "ConsultantScheduleTime{" +
                "id=" + id +
                '}';
    }
}

package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;

import java.util.List;

public interface TimeslotService{
    List<TimeSlot> findAll();
}

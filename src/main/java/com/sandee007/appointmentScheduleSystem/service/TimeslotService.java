package com.sandee007.appointmentScheduleSystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;

import java.util.List;

public interface TimeslotService{
    List<TimeSlot> findAll();
    List<TimeSlot> findAllById(Iterable<Integer> ids);
    List<Integer> getTimeslotIdsByString(String timeslotString) throws JsonProcessingException;
    List<TimeSlot> getTimeslotsByString(String timeslotString) throws JsonProcessingException;
}

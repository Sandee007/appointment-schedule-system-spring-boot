package com.sandee007.appointmentScheduleSystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandee007.appointmentScheduleSystem.dao.TimeslotRepository;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeslotServiceImplt implements TimeslotService{
    private TimeslotRepository timeslotRepository;

    public TimeslotServiceImplt(TimeslotRepository timeslotRepository) {
        this.timeslotRepository = timeslotRepository;
    }

    @Override
    public List<TimeSlot> findAll() {
        return timeslotRepository.findAll();
    }

    @Override
    public List<TimeSlot> findAllById(Iterable<Integer> ids) {
        return timeslotRepository.findAllById(ids);
    }

    @Override
    public List<Integer> getTimeslotIdsByString(String timeslotString) throws JsonProcessingException {
        List<Integer> ids = new ArrayList<>();

        if (timeslotString != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            ids = objectMapper.readValue(
                    timeslotString,
                    new TypeReference<List<Integer>>() {
                    }
            );
        }

        return ids;
    }

    @Override
    public List<TimeSlot> getTimeslotsByString(String timeslotString) throws JsonProcessingException {
        return this.findAllById(this.getTimeslotIdsByString(timeslotString));
    }
}

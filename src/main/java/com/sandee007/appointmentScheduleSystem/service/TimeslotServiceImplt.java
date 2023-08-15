package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.dao.TimeslotRepository;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;
import org.springframework.stereotype.Service;

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
}

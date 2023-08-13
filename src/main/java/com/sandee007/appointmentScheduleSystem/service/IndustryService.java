package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.entity.Industry;

import java.util.List;
import java.util.Optional;

public interface IndustryService {
    List<Industry> findAll();
    Optional<Industry> findById(int id);
}

package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.entity.Seeker;

import java.util.Optional;

public interface SeekerService {
    Optional<Seeker> findById(int id);
}

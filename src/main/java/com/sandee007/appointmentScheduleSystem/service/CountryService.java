package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.entity.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    List<Country> findAll();
    Optional<Country> findById(int id);
}

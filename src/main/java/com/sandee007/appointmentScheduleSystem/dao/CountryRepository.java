package com.sandee007.appointmentScheduleSystem.dao;

import com.sandee007.appointmentScheduleSystem.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}

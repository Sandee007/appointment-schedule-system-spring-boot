package com.sandee007.appointmentScheduleSystem.dao;

import com.sandee007.appointmentScheduleSystem.entity.Seeker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeekerRepository extends JpaRepository<Seeker, Integer> {
}

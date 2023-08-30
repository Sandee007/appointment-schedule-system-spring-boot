package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.entity.Seeker;

import java.util.List;
import java.util.Optional;

public interface SeekerService {
    Optional<Seeker> findById(int id);
    Seeker findByUser_Id(int user_id);
    void save(Seeker seeker);
    List<Seeker> findAll();
}

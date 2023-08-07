package com.sandee007.appointmentScheduleSystem.auth.dao;

import com.sandee007.appointmentScheduleSystem.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

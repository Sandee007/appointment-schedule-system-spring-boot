package com.sandee007.appointmentScheduleSystem.base.auth.dao;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}

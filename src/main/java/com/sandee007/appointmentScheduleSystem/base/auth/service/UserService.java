package com.sandee007.appointmentScheduleSystem.base.auth.service;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;

import java.util.Optional;

public interface UserService {
    void save(User user);
    Optional<User> findById(int id);
    User findByUsername(String username);
    boolean existsByUsername(String username);
    void disableById(int id);
    void enableById(int id);
}

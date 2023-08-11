package com.sandee007.appointmentScheduleSystem.base.auth.service;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
    boolean existsByUsername(String username);
}

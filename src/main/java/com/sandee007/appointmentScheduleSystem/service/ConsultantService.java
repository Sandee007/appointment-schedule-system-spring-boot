package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface ConsultantService {
    Optional<Consultant> findById(int id);
    Consultant findByUser(User user);
    void save(Consultant consultant);
    Consultant getLoggedInConsultant(Authentication authentication);

}

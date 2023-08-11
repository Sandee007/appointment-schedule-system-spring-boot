package com.sandee007.appointmentScheduleSystem.dao;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultantRepository extends JpaRepository<Consultant, Integer> {
    Consultant findByUser(User user);
}

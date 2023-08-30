package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.Country;
import com.sandee007.appointmentScheduleSystem.entity.Industry;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface ConsultantService {
    Optional<Consultant> findById(int id);

    Consultant findByUser(User user);

    void save(Consultant consultant);

    List<Consultant> findAll();

    //     ! dont , need to keep join tables data
    //    void deleteByIdAndDisabledUser(int id);

    Consultant getLoggedInConsultant(Authentication authentication);

    String generateConsultantEmailDomain();

    List<Consultant> findAllActiveConsultants();

    Optional<Consultant> findActiveConsultantById(int id);

    List<Consultant> filterConsultants(
            List<Integer> countryIds,
            List<Integer> industryIds
    );
}

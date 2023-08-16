package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ConsultantScheduleDateService {
    void save(ConsultantScheduleDate consultantScheduleDate);
    List<ConsultantScheduleDate> findAllByConsultant(Consultant consultant);
    Optional<ConsultantScheduleDate> findById(int id);
    boolean existsByConsultantAndDate(Consultant consultant, Date date);
}

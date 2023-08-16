package com.sandee007.appointmentScheduleSystem.dao;

import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ConsultantScheduleDateRepository extends JpaRepository<ConsultantScheduleDate, Integer> {
    List<ConsultantScheduleDate> findAllByConsultantOrderByDateAsc(Consultant consultant);
    boolean existsByConsultantAndDate(Consultant consultant, Date date);
}

package com.sandee007.appointmentScheduleSystem.dao;

import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDate;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDateTimeslot;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultantScheduleDateTimeslotRepository extends JpaRepository<ConsultantScheduleDateTimeslot, Integer> {

    //    * where in example - working - not used anywhere atm
    List<ConsultantScheduleDateTimeslot> findAllByConsultantScheduleDateAndTimeslotIn(
            ConsultantScheduleDate consultantScheduleDate,
            List<TimeSlot> timeSlots
    );
    void deleteAllByConsultantScheduleDate(ConsultantScheduleDate consultantScheduleDate);
    boolean existsByConsultantScheduleDateAndTimeslot(ConsultantScheduleDate consultantScheduleDate, TimeSlot timeSlot);

}

package com.sandee007.appointmentScheduleSystem.dao;

import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDate;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDateTimeslot;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ConsultantScheduleDateTimeslotRepository extends JpaRepository<ConsultantScheduleDateTimeslot, Integer> {

    //    * where in example - working - not used anywhere atm
    List<ConsultantScheduleDateTimeslot> findAllByConsultantScheduleDateAndTimeslotIn(
            ConsultantScheduleDate consultantScheduleDate,
            List<TimeSlot> timeSlots
    );

    void deleteAllByConsultantScheduleDate(ConsultantScheduleDate consultantScheduleDate);

    boolean existsByConsultantScheduleDateAndTimeslot(ConsultantScheduleDate consultantScheduleDate, TimeSlot timeSlot);

    List<ConsultantScheduleDateTimeslot> findAllByStatusIsAndConsultantScheduleDate_ConsultantAndSeekerNotNullAndConsultantScheduleDate_DateAfter(
            int status, Consultant consultantScheduleDate_consultant, Date consultantScheduleDate_date
    );

    List<ConsultantScheduleDateTimeslot> findAllByStatusIsAndConsultantScheduleDate_ConsultantAndSeekerNotNullAndConsultantScheduleDate_DateOrderByTimeslotAsc(
            int status,
            Consultant consultantScheduleDate_consultant,
            Date consultantScheduleDate_date
    );

    List<ConsultantScheduleDateTimeslot> findAllByStatusIsAndConsultantScheduleDate_ConsultantAndSeekerNotNullAndConsultantScheduleDate_DateAfterOrderByConsultantScheduleDate_Date(
            int status,
            Consultant consultantScheduleDate_consultant,
            Date consultantScheduleDate_date
    );

}

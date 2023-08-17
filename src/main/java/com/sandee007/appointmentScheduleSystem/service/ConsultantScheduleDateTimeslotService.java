package com.sandee007.appointmentScheduleSystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDate;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDateTimeslot;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ConsultantScheduleDateTimeslotService {
    Optional<ConsultantScheduleDateTimeslot> findById(int id);

    void save(ConsultantScheduleDateTimeslot consultantScheduleDateTimeslot);

    List<ConsultantScheduleDateTimeslot> findAllById(Iterable<Integer> ids);

    void deleteAllById(Iterable<Integer> ids);

    void deleteAll(List<ConsultantScheduleDateTimeslot> consultantScheduleDateTimeslots);

    List<ConsultantScheduleDateTimeslot> findAllByConsultantScheduleDateAndTimeslotIn(
            ConsultantScheduleDate consultantScheduleDate,
            List<TimeSlot> timeSlots
    );

    void deleteAllByConsultantScheduleDate(ConsultantScheduleDate consultantScheduleDate);

    boolean existsByConsultantScheduleDateAndTimeslot(ConsultantScheduleDate consultantScheduleDate, TimeSlot timeSlot);

    List<ConsultantScheduleDateTimeslot> generateConsultantScheduleDateTimeslots(
            List<TimeSlot> timeslots,
            ConsultantScheduleDate consultantScheduleDate
    );

    List<ConsultantScheduleDateTimeslot> getAllByIdString(String string) throws JsonProcessingException;

    void saveAll(List<ConsultantScheduleDateTimeslot> consultantScheduleDateTimeslots);

    List<ConsultantScheduleDateTimeslot> getPendingAppointments(
            int status,
            Consultant consultantScheduleDate_consultant
    );

    List<ConsultantScheduleDateTimeslot> getTodayAppointments(
            Consultant consultantScheduleDate_consultant
    );

    List<ConsultantScheduleDateTimeslot> getUpcomingAppointments(
            Consultant consultantScheduleDate_consultant
    );


}

package com.sandee007.appointmentScheduleSystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDate;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDateTimeslot;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;

import java.util.List;

public interface ConsultantScheduleDateTimeslotService {
    List<ConsultantScheduleDateTimeslot> findAllById(Iterable<Integer> ids);
    void deleteAllById(Iterable<Integer> ids);
    void deleteAll(List<ConsultantScheduleDateTimeslot> consultantScheduleDateTimeslots);
    List<ConsultantScheduleDateTimeslot> findAllByConsultantScheduleDateAndTimeslotIn(
            ConsultantScheduleDate consultantScheduleDate,
            List<TimeSlot> timeSlots
    );
    void  deleteAllByConsultantScheduleDate(ConsultantScheduleDate consultantScheduleDate);
    boolean existsByConsultantScheduleDateAndTimeslot(ConsultantScheduleDate consultantScheduleDate, TimeSlot timeSlot);
    List<ConsultantScheduleDateTimeslot> generateConsultantScheduleDateTimeslots(
            List<TimeSlot> timeslots,
            ConsultantScheduleDate consultantScheduleDate
    );
    List<ConsultantScheduleDateTimeslot> getAllByIdString(String string) throws JsonProcessingException;

    void saveAll(List<ConsultantScheduleDateTimeslot> consultantScheduleDateTimeslots);
}

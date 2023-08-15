package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.dao.ConsultantScheduleDateTimeslotRepository;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDate;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDateTimeslot;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConsultantScheduleDateTimeslotServiceImpl implements  ConsultantScheduleDateTimeslotService{
    private ConsultantScheduleDateTimeslotRepository consultantScheduleDateTimeslotRepository;

    public ConsultantScheduleDateTimeslotServiceImpl(ConsultantScheduleDateTimeslotRepository consultantScheduleDateTimeslotRepository) {
        this.consultantScheduleDateTimeslotRepository = consultantScheduleDateTimeslotRepository;
    }

    @Override
    public void deleteAllById(Iterable<Integer> ids) {
        consultantScheduleDateTimeslotRepository.deleteAllById(ids);
    }
    @Override
    public void deleteAll(List<ConsultantScheduleDateTimeslot> consultantScheduleDateTimeslots) {
        consultantScheduleDateTimeslotRepository.deleteAll(consultantScheduleDateTimeslots);
    }

    @Override
    public List<ConsultantScheduleDateTimeslot> findAllByConsultantScheduleDateAndTimeslotIn(
            ConsultantScheduleDate consultantScheduleDate,
            List<TimeSlot> timeSlots
    ) {
        return consultantScheduleDateTimeslotRepository.findAllByConsultantScheduleDateAndTimeslotIn(consultantScheduleDate, timeSlots);
    }

    @Transactional // * doesn't work without it ¯\_(ツ)_/¯
    @Override
    public void deleteAllByConsultantScheduleDate(ConsultantScheduleDate consultantScheduleDate) {
         consultantScheduleDateTimeslotRepository.deleteAllByConsultantScheduleDate(consultantScheduleDate);
    }

    @Override
    public boolean existsByConsultantScheduleDateAndTimeslot(
            ConsultantScheduleDate consultantScheduleDate,
            TimeSlot timeSlot
    ) {
        return consultantScheduleDateTimeslotRepository.existsByConsultantScheduleDateAndTimeslot(consultantScheduleDate, timeSlot);
    }

}

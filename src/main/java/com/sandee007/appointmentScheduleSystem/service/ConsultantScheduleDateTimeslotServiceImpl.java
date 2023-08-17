package com.sandee007.appointmentScheduleSystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandee007.appointmentScheduleSystem.dao.ConsultantScheduleDateTimeslotRepository;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDate;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDateTimeslot;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultantScheduleDateTimeslotServiceImpl implements ConsultantScheduleDateTimeslotService {
    private ConsultantScheduleDateTimeslotRepository consultantScheduleDateTimeslotRepository;

    public ConsultantScheduleDateTimeslotServiceImpl(ConsultantScheduleDateTimeslotRepository consultantScheduleDateTimeslotRepository) {
        this.consultantScheduleDateTimeslotRepository = consultantScheduleDateTimeslotRepository;
    }

    @Override
    public Optional<ConsultantScheduleDateTimeslot> findById(int id) {
        return consultantScheduleDateTimeslotRepository.findById(id);
    }

    @Override
    public void save(ConsultantScheduleDateTimeslot consultantScheduleDateTimeslot) {
        consultantScheduleDateTimeslotRepository.save(consultantScheduleDateTimeslot);
    }

    @Override
    public List<ConsultantScheduleDateTimeslot> findAllById(Iterable<Integer> ids) {
        return consultantScheduleDateTimeslotRepository.findAllById(ids);
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
        return consultantScheduleDateTimeslotRepository.findAllByConsultantScheduleDateAndTimeslotIn(
                consultantScheduleDate,
                timeSlots
        );
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
        return consultantScheduleDateTimeslotRepository.existsByConsultantScheduleDateAndTimeslot(
                consultantScheduleDate,
                timeSlot
        );
    }

    @Override
    public List<ConsultantScheduleDateTimeslot> generateConsultantScheduleDateTimeslots(
            List<TimeSlot> timeslots,
            ConsultantScheduleDate consultantScheduleDate
    ) {
        return timeslots.stream()
                        .map(i -> {
                            var t = new ConsultantScheduleDateTimeslot();
                            t.setConsultantScheduleDate(consultantScheduleDate);
                            t.setTimeslot(i);
                            return t;
                        })
                        .collect(Collectors.toList());
    }

    @Override
    public List<ConsultantScheduleDateTimeslot> getAllByIdString(String string) throws JsonProcessingException {
        List<Integer> ids = new ArrayList<>();

        if (string != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            ids = objectMapper.readValue(
                    string,
                    new TypeReference<List<Integer>>() {
                    }
            );
        }

        return this.findAllById(ids);
    }

    @Override
    public void saveAll(List<ConsultantScheduleDateTimeslot> consultantScheduleDateTimeslots) {
        consultantScheduleDateTimeslotRepository.saveAll(consultantScheduleDateTimeslots);
    }

    @Override
    public List<ConsultantScheduleDateTimeslot> getPendingAppointments(
            int status,
            Consultant consultantScheduleDate_consultant
    ) {
        return consultantScheduleDateTimeslotRepository.findAllByStatusIsAndConsultantScheduleDate_ConsultantAndSeekerNotNull(
                status,
                consultantScheduleDate_consultant
        );
    }

    @Override
    public List<ConsultantScheduleDateTimeslot> getTodayAppointments(
            Consultant consultantScheduleDate_consultant
    ) {
        return consultantScheduleDateTimeslotRepository.findAllByStatusIsAndConsultantScheduleDate_ConsultantAndSeekerNotNullAndConsultantScheduleDate_DateOrderByTimeslotAsc(
                1,
                consultantScheduleDate_consultant,
                new Date()
        );
    }

    @Override
    public List<ConsultantScheduleDateTimeslot> getUpcomingAppointments(Consultant consultantScheduleDate_consultant) {
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + 24 * 60 * 60 * 1000);

        return consultantScheduleDateTimeslotRepository.findAllByStatusIsAndConsultantScheduleDate_ConsultantAndSeekerNotNullAndConsultantScheduleDate_DateAfterOrderByConsultantScheduleDate_Date(
                1,
                consultantScheduleDate_consultant,
                tomorrow
        );
    }


}

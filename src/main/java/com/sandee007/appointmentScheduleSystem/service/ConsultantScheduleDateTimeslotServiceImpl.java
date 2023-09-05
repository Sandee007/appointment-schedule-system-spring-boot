package com.sandee007.appointmentScheduleSystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandee007.appointmentScheduleSystem.dao.ConsultantScheduleDateTimeslotRepository;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDate;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDateTimeslot;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;
import com.sandee007.appointmentScheduleSystem.util.UtilThymeleaf;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
    private UtilThymeleaf utilThymeleaf;
    private EntityManager entityManager;

    public ConsultantScheduleDateTimeslotServiceImpl(
            ConsultantScheduleDateTimeslotRepository consultantScheduleDateTimeslotRepository,
            UtilThymeleaf utilThymeleaf,
            EntityManager entityManager
    ) {
        this.consultantScheduleDateTimeslotRepository = consultantScheduleDateTimeslotRepository;
        this.utilThymeleaf = utilThymeleaf;
        this.entityManager = entityManager;
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
        return consultantScheduleDateTimeslotRepository.findAllByStatusIsAndConsultantScheduleDate_ConsultantAndSeekerNotNullAndConsultantScheduleDate_DateAfter(
                status,
                consultantScheduleDate_consultant,
                utilThymeleaf.getYesterday()
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


        return consultantScheduleDateTimeslotRepository.findAllByStatusIsAndConsultantScheduleDate_ConsultantAndSeekerNotNullAndConsultantScheduleDate_DateAfterOrderByConsultantScheduleDate_Date(
                1,
                consultantScheduleDate_consultant,
                utilThymeleaf.getTomorrow()
        );
    }

    @Override
    public List<ConsultantScheduleDateTimeslot> getAppointmentHistory(Consultant consultantScheduleDate_consultant) {
        return consultantScheduleDateTimeslotRepository.findAllByStatusIsAndConsultantScheduleDate_ConsultantAndSeekerNotNullAndConsultantScheduleDate_DateBeforeOrderByConsultantScheduleDate_Date(
                1,
                consultantScheduleDate_consultant,
                new Date()
        );
    }

    @Override
    public List<ConsultantScheduleDateTimeslot> findAllAppointments() {
        TypedQuery<ConsultantScheduleDateTimeslot> q = entityManager.createQuery(
                " SELECT csdt FROM ConsultantScheduleDateTimeslot csdt" +
                        " JOIN FETCH csdt.consultantScheduleDate " +
                        " JOIN FETCH csdt.consultantScheduleDate.consultant " +
                        " JOIN FETCH csdt.timeslot " +
                        " JOIN FETCH csdt.seeker " +
                        " WHERE csdt.status = 1 " +
                        " ORDER BY csdt.consultantScheduleDate.date DESC "
                , ConsultantScheduleDateTimeslot.class
        );

        return q.getResultList();
    }


}

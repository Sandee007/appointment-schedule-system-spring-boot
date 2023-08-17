package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.dao.ConsultantScheduleDateRepository;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultantScheduleDateServiceImpl implements ConsultantScheduleDateService {
    private ConsultantScheduleDateRepository consultantScheduleDateRepository;

    public ConsultantScheduleDateServiceImpl(ConsultantScheduleDateRepository consultantScheduleDateRepository) {
        this.consultantScheduleDateRepository = consultantScheduleDateRepository;
    }

    @Override
    public void save(ConsultantScheduleDate consultantScheduleDate) {
        consultantScheduleDateRepository.save(consultantScheduleDate);
    }

    @Override
    public List<ConsultantScheduleDate> findAllByConsultant(Consultant consultant) {
        return consultantScheduleDateRepository.findAllByConsultantOrderByDateAsc(consultant);
    }

    @Override
    public Optional<ConsultantScheduleDate> findById(int id) {
        return consultantScheduleDateRepository.findById(id);
    }

    @Override
    public boolean existsByConsultantAndDate(Consultant consultant, Date date) {
        return consultantScheduleDateRepository.existsByConsultantAndDate(consultant, date);
    }

    @Override
    public List<ConsultantScheduleDate> findAllByConsultantAndDateAfterOrderByDateAsc(Consultant consultant, Date date) {
        return consultantScheduleDateRepository.findAllByConsultantAndDateAfterOrderByDateAsc(consultant, date);
    }

}

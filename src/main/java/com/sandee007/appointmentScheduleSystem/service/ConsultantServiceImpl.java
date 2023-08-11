package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.dao.ConsultantRepository;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultantServiceImpl implements ConsultantService{

    private ConsultantRepository consultantRepository;

    public ConsultantServiceImpl(ConsultantRepository consultantRepository) {
        this.consultantRepository = consultantRepository;
    }

    @Override
    public Optional<Consultant> findById(int id) {
        return consultantRepository.findById(id);
    }

    @Override
    public Consultant findByUser(User user) {
        return consultantRepository.findByUser(user);
    }

    @Override
    public void save(Consultant consultant) {
        consultantRepository.save(consultant);
    }
}

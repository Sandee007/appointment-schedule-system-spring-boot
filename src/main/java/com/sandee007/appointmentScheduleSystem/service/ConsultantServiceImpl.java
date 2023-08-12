package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.base.auth.service.EmailService;
import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.dao.ConsultantRepository;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultantServiceImpl implements ConsultantService {

    private ConsultantRepository consultantRepository;
    private UserService userService;
    private EmailService emailService;

    public ConsultantServiceImpl(ConsultantRepository consultantRepository, UserService userService, EmailService emailService) {
        this.consultantRepository = consultantRepository;
        this.userService = userService;
        this.emailService = emailService;
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

    @Override
    public Consultant getLoggedInConsultant(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        return this.findByUser(user);
    }
}

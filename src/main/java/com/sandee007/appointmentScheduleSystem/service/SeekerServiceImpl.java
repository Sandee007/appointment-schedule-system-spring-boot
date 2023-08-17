package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.dao.SeekerRepository;
import com.sandee007.appointmentScheduleSystem.entity.Seeker;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeekerServiceImpl implements SeekerService{

    private SeekerRepository seekerRepository;

    public SeekerServiceImpl(SeekerRepository seekerRepository) {
        this.seekerRepository = seekerRepository;
    }

    @Override
    public Optional<Seeker> findById(int id) {
        return seekerRepository.findById(id);
    }
}

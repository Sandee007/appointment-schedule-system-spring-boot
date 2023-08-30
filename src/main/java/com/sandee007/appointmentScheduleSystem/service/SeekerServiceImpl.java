package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.dao.SeekerRepository;
import com.sandee007.appointmentScheduleSystem.entity.Seeker;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public Seeker findByUser_Id(int user_id) {
        return seekerRepository.findByUser_Id(user_id);
    }

    @Override
    public void save(Seeker seeker) {
        seekerRepository.save(seeker);
    }

    @Override
    public List<Seeker> findAll() {
        return seekerRepository.findAll();
    }

}

package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.dao.IndustryRepository;
import com.sandee007.appointmentScheduleSystem.entity.Industry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IndustryServiceImpl implements  IndustryService{
    private IndustryRepository industryRepository;

    public IndustryServiceImpl(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    @Override
    public List<Industry> findAll() {
        return industryRepository.findAll();
    }

    @Override
    public Optional<Industry> findById(int id) {
        return industryRepository.findById(id);
    }
}

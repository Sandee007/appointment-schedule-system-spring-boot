package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.dao.IndustryRepository;
import com.sandee007.appointmentScheduleSystem.entity.Country;
import com.sandee007.appointmentScheduleSystem.entity.Industry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IndustryServiceImpl implements  IndustryService{
    private IndustryRepository industryRepository;
    private EntityManager entityManager;

    public IndustryServiceImpl(IndustryRepository industryRepository, EntityManager entityManager) {
        this.industryRepository = industryRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Industry> findAll() {
        return industryRepository.findAll();
    }

    @Override
    public Optional<Industry> findById(int id) {
        return industryRepository.findById(id);
    }

    @Override
    public List<Industry> findAllWithActiveConsultants() {
        Query q = entityManager.createNativeQuery(
                " SELECT industries.* FROM industries" +
                        " INNER JOIN consultant_industries ON industries.id = consultant_industries.industry_id " +
                        " INNER JOIN consultants ON consultant_industries.consultant_id = consultants.id " +
                        " INNER JOIN users ON consultants.user_id = users.id " +
                        " WHERE users.enabled = 1 " +
                        " GROUP BY industries.id "
                , Industry.class
        );

        return q.getResultList();
    }
}

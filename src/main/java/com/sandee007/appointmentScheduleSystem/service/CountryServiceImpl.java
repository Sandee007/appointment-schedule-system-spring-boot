package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.dao.CountryRepository;
import com.sandee007.appointmentScheduleSystem.entity.Country;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CountryServiceImpl implements  CountryService{
    private CountryRepository countryRepository;
    private ConsultantService consultantService;
    private EntityManager entityManager;

    public CountryServiceImpl(CountryRepository countryRepository, ConsultantService consultantService,
                              EntityManager entityManager
    ) {
        this.countryRepository = countryRepository;
        this.consultantService = consultantService;
        this.entityManager = entityManager;
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<Country> findById(int id) {
        return countryRepository.findById(id);
    }

    @Override
    public List<Country> findAllWithActiveConsultants() {
        Query q = entityManager.createNativeQuery(
                " SELECT countries.* FROM countries" +
                        " INNER JOIN consultant_countries ON countries.id = consultant_countries.country_id " +
                        " INNER JOIN consultants ON consultant_countries.consultant_id = consultants.id " +
                        " INNER JOIN users ON consultants.user_id = users.id " +
                        " WHERE users.enabled = 1 " +
                        " GROUP BY countries.id "
                , Country.class
        );

        return q.getResultList();
    }
}

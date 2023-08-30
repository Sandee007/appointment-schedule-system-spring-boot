package com.sandee007.appointmentScheduleSystem.service;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.base.auth.service.EmailService;
import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.dao.ConsultantRepository;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.Country;
import com.sandee007.appointmentScheduleSystem.entity.Industry;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConsultantServiceImpl implements ConsultantService {

    private ConsultantRepository consultantRepository;
    private UserService userService;
    private EmailService emailService;
    private Environment environment;
    private EntityManager entityManager;

    public ConsultantServiceImpl(
            ConsultantRepository consultantRepository,
            UserService userService,
            EmailService emailService,
            Environment environment,
            EntityManager entityManager
    ) {
        this.consultantRepository = consultantRepository;
        this.userService = userService;
        this.emailService = emailService;
        this.environment = environment;
        this.entityManager = entityManager;
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
    public List<Consultant> findAll() {
        return consultantRepository.findAll();
    }

    //    @Override
    //    public void deleteByIdAndDisabledUser(int id) {
    //        Optional<Consultant> consultant = this.findById(id);
    //        if (consultant.isPresent()) {
    //            //             ! dont , need to keep join tables data
    //            userService.deleteById(consultant.get().getUser().getId());
    //            consultantRepository.deleteById(id);
    //        }
    //    }


    @Override
    public Consultant getLoggedInConsultant(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        return this.findByUser(user);
    }

    @Override
    public String generateConsultantEmailDomain() {
        return "@"
                + Objects.requireNonNull(environment.getProperty(
                "spring.application.name")).toLowerCase()
                + ".com";
    }

    @Override
    public List<Consultant> findAllActiveConsultants() {
        //        TypedQuery<Consultant> q = entityManager.createQuery(
        //                "SELECT c FROM Consultant c " +
        //                        " JOIN FETCH c.user WHERE c.user.enabled=1 "
        //                , Consultant.class);
        //        return q.getResultList();
        return consultantRepository.findAllByUser_Enabled(1);
    }

    @Override
    public Optional<Consultant> findActiveConsultantById(int id) {
        return consultantRepository.findByIdAndUser_Enabled(id, 1);
    }

    @Override
    public List<Consultant> filterConsultants(
            List<Integer> countryIds,
            List<Integer> industryIds
    ) {

        Query q = entityManager.createNativeQuery(
                "SELECT consultants.* FROM consultants " +
                        " INNER JOIN consultant_countries ON consultants.id = consultant_countries.consultant_id " +
                        " INNER JOIN consultant_industries ON consultants.id = consultant_industries.consultant_id " +
                        " INNER JOIN users ON consultants.user_id = users.id " +
                        " WHERE ( consultant_countries.country_id IN (:countryIds) OR consultant_industries.industry_id IN (:industryIds) ) " +
                        " AND users.enabled = 1 " +
                        " GROUP BY consultants.id " // * remove duplicates
                , Consultant.class
        );

        q.setParameter("countryIds", countryIds);
        q.setParameter("industryIds", industryIds);
        return q.getResultList();
    }

    //    @Override
    //    public List<Consultant> findAllWithDeleted() {
    //        TypedQuery<Consultant> q = entityManager.createQuery(
    //                " SELECT c FROM Consultant c "
    //                , Consultant.class
    //        );
    //
    //        return q.getResultList();
    //    }
}

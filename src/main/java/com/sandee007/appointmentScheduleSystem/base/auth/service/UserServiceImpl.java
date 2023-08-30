package com.sandee007.appointmentScheduleSystem.base.auth.service;

import com.sandee007.appointmentScheduleSystem.base.auth.dao.UserRepository;
import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        userRepository.save(user);

        //        TODO add to roles table
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void disableById(int id) {
        userRepository.deleteById(id);
    }

//    https://stackoverflow.com/questions/32532827/hibernate-gives-me-invalid-update-sql-query
//    https://stackoverflow.com/questions/8307578/what-is-the-best-way-to-update-the-entity-in-jpa
//    @Transactional
    @Override
    public void enableById(int id) {
       User user = this.findById(id).orElse(null);

       if (user != null){
           user.setEnabled(1);
           userRepository.save(user);
       }

//        ! doesn't work
//        Query q = entityManager.createQuery(
//                "FROM User u UPDATE SET u.enabled=1 WHERE u.id=:id "
//                , User.class);
//        q.setParameter("id", id);
//        q.executeUpdate();
    }
}

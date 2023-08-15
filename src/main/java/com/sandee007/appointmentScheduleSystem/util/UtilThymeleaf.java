package com.sandee007.appointmentScheduleSystem.util;

import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UtilThymeleaf {
    private UserService userService;

    public UtilThymeleaf(UserService userService) {
        this.userService = userService;
    }

    public Consultant getConsultant(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userService.findByUsername(username).getConsultant();
    }
}

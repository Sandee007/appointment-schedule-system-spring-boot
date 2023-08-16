package com.sandee007.appointmentScheduleSystem.util;

import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;
import com.sandee007.appointmentScheduleSystem.service.TimeslotService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class UtilThymeleaf {
    private UserService userService;
    private TimeslotService timeslotService;

    public UtilThymeleaf(UserService userService, TimeslotService timeslotService) {
        this.userService = userService;
        this.timeslotService = timeslotService;
    }

    public Consultant getConsultant(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userService.findByUsername(username).getConsultant();
    }

    public List<TimeSlot> getPropTimeslots(){
        return timeslotService.findAll();
    }

    public boolean isPastDate(Date date){
        return !date.after(new Date());
    }
}

package com.sandee007.appointmentScheduleSystem.util;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.entity.*;
import com.sandee007.appointmentScheduleSystem.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class UtilThymeleaf {
    private UserService userService;
    private TimeslotService timeslotService;
    private CountryService countryService;
    private IndustryService industryService;
    private ConsultantService consultantService;
    private SeekerService seekerService;

    public UtilThymeleaf(
            UserService userService, TimeslotService timeslotService, CountryService countryService,
            IndustryService industryService,
            ConsultantService consultantService,
            SeekerService seekerService
    ) {
        this.userService = userService;
        this.timeslotService = timeslotService;
        this.countryService = countryService;
        this.industryService = industryService;
        this.consultantService = consultantService;
        this.seekerService = seekerService;
    }

    public Consultant getConsultant() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userService.findByUsername(username).getConsultant();
    }

    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(authentication.getName());
    }

    public List<TimeSlot> getPropTimeslots() {
        return timeslotService.findAll();
    }

    public boolean isPastDate(Date date) {
        return !date.after(new Date());
    }

    public List<Country> getCountries() {
        return countryService.findAll();
    }

    public List<Industry> getIndustries() {
        return industryService.findAll();
    }

    //    * consultants
    public List<Consultant> getAllConsultants() {
        return consultantService.findAll();
    }

    public String generateInvoiceNo() {
        return String.valueOf(new Date().getTime());
    }

    public String getNow() {
        return LocalDate.now().toString();
    }

    public boolean isAuthSeekerAccount(int id) {
        Seeker seeker = seekerService.findById(id).orElse(null);
        if (seeker == null) return false;

        return this.getAuthUser().getId() == seeker.getUser().getId();
    }

    public Date getYesterday() {
        Date today = new Date();
        return new Date(today.getTime() - 24 * 60 * 60 * 1000);
    }

    public Date getTomorrow() {
        Date today = new Date();
        return  new Date(today.getTime() + 24 * 60 * 60 * 1000);
    }



}

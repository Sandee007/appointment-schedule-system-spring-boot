package com.sandee007.appointmentScheduleSystem.util;

import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.Country;
import com.sandee007.appointmentScheduleSystem.entity.Industry;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;
import com.sandee007.appointmentScheduleSystem.service.ConsultantService;
import com.sandee007.appointmentScheduleSystem.service.CountryService;
import com.sandee007.appointmentScheduleSystem.service.IndustryService;
import com.sandee007.appointmentScheduleSystem.service.TimeslotService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public class UtilThymeleaf {
    private UserService userService;
    private TimeslotService timeslotService;
    private CountryService countryService;
    private IndustryService industryService;
    private ConsultantService consultantService;

    public UtilThymeleaf(
            UserService userService, TimeslotService timeslotService, CountryService countryService,
            IndustryService industryService,
            ConsultantService consultantService
    ) {
        this.userService = userService;
        this.timeslotService = timeslotService;
        this.countryService = countryService;
        this.industryService = industryService;
        this.consultantService = consultantService;
    }

    public Consultant getConsultant() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userService.findByUsername(username).getConsultant();
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

    public String getNow(){
        return LocalDate.now().toString();
    }

}

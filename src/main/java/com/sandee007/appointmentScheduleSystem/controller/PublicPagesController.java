package com.sandee007.appointmentScheduleSystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDateTimeslot;
import com.sandee007.appointmentScheduleSystem.entity.Country;
import com.sandee007.appointmentScheduleSystem.entity.Industry;
import com.sandee007.appointmentScheduleSystem.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class PublicPagesController {
    private ConsultantService consultantService;
    private ConsultantScheduleDateService consultantScheduleDateService;
    private ConsultantScheduleDateTimeslotService consultantScheduleDateTimeslotService;
    private UserService userService;
    private IndustryService industryService;
    private CountryService countryService;

    public PublicPagesController(
            ConsultantService consultantService,
            ConsultantScheduleDateService consultantScheduleDateService,
            ConsultantScheduleDateTimeslotService consultantScheduleDateTimeslotService,
            UserService userService,
            IndustryService industryService,
            CountryService countryService
    ) {
        this.consultantService = consultantService;
        this.consultantScheduleDateService = consultantScheduleDateService;
        this.consultantScheduleDateTimeslotService = consultantScheduleDateTimeslotService;
        this.userService = userService;
        this.industryService = industryService;
        this.countryService = countryService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Industry> industries = industryService.findAll();
        model.addAttribute("industries", industries);

        List<Country> countries = countryService.findAll();
        model.addAttribute("countries", countries);
        return "index";
    }

    @GetMapping("/view/consultant")
    public String viewConsultant(
            @RequestParam("id") int id,
            Model model
    ) {
        Consultant consultant = consultantService.findById(id).orElse(null);
        if (consultant == null) return "redirect:/";

        Date today = new Date();
        Date yesterday = new Date(today.getTime() - 24 * 60 * 60 * 1000);

        model.addAttribute("consultant", consultant);
        model.addAttribute(
                "futureScheduleDates",
                consultantScheduleDateService.findAllByConsultantAndDateAfterOrderByDateAsc(consultant, yesterday)
        );
        return "view/consultant";
    }

    @PostMapping("/payments")
    public String payments(
            @RequestParam("timeslotString") String timeslotString,
            @RequestParam("consultantId") int id,
            Model model
    ) throws JsonProcessingException {

        Consultant consultant = consultantService.findById(id).orElse(null);

        if (consultant == null) return "redirect:/";

        List<ConsultantScheduleDateTimeslot> timeslots = consultantScheduleDateTimeslotService.getAllByIdString(
                timeslotString
        );
        model.addAttribute("timeslotString", timeslotString);
        model.addAttribute("timeslots", timeslots);
        model.addAttribute("consultant", consultant);

        return "authenticated/payments";

    }

    @PostMapping("/complete-payment")
    public String completePayment(
            @RequestParam("timeslotString") String timeslotString,
            @RequestParam("consultantId") int id,
            Model model
    ) throws JsonProcessingException {
        Consultant consultant = consultantService.findById(id).orElse(null);
        if (consultant == null) return "redirect:/";

        List<ConsultantScheduleDateTimeslot> timeslots = consultantScheduleDateTimeslotService.getAllByIdString(
                timeslotString
        );

        for (ConsultantScheduleDateTimeslot timeslot : timeslots) {
            User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            timeslot.setSeeker(user.getSeeker());
        }
        consultantScheduleDateTimeslotService.saveAll(timeslots);


        model.addAttribute("timeslots", timeslots);
        model.addAttribute("consultant", consultant);
        return "authenticated/payment-success";

    }
}

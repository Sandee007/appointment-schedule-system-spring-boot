package com.sandee007.appointmentScheduleSystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.base.auth.service.EmailService;
import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDateTimeslot;
import com.sandee007.appointmentScheduleSystem.entity.Country;
import com.sandee007.appointmentScheduleSystem.entity.Industry;
import com.sandee007.appointmentScheduleSystem.service.*;
import com.sandee007.appointmentScheduleSystem.util.UtilThymeleaf;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PublicPagesController {
    private ConsultantService consultantService;
    private ConsultantScheduleDateService consultantScheduleDateService;
    private ConsultantScheduleDateTimeslotService consultantScheduleDateTimeslotService;
    private UserService userService;
    private IndustryService industryService;
    private CountryService countryService;
    private UtilThymeleaf utilThymeleaf;
    private EmailService emailService;

    public PublicPagesController(
            ConsultantService consultantService,
            ConsultantScheduleDateService consultantScheduleDateService,
            ConsultantScheduleDateTimeslotService consultantScheduleDateTimeslotService,
            UserService userService,
            IndustryService industryService,
            CountryService countryService,
            UtilThymeleaf utilThymeleaf,
            EmailService emailService
    ) {
        this.consultantService = consultantService;
        this.consultantScheduleDateService = consultantScheduleDateService;
        this.consultantScheduleDateTimeslotService = consultantScheduleDateTimeslotService;
        this.userService = userService;
        this.industryService = industryService;
        this.countryService = countryService;
        this.utilThymeleaf = utilThymeleaf;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Industry> industries = industryService.findAll();
        model.addAttribute("industries", industries);

        List<Country> countries = countryService.findAllWithActiveConsultants();
        model.addAttribute("countries", countries);

        List<Consultant> consultants = consultantService.findAllActiveConsultants();
        model.addAttribute("consultants", consultants);
        return "index";
    }

    @GetMapping("/view/consultant")
    public String viewConsultant(
            @RequestParam("id") int id,
            Model model
    ) {
        Consultant consultant = consultantService.findActiveConsultantById(id).orElse(null);
        if (consultant == null) return "redirect:/";

        model.addAttribute("consultant", consultant);
        model.addAttribute(
                "futureScheduleDates",
                consultantScheduleDateService.findAllByConsultantAndDateAfterOrderByDateAsc(
                        consultant,
                        utilThymeleaf.getYesterday()
                )
        );
        return "view/consultant";
    }

    @GetMapping("/view/consultants-filter")
    public String consultantsFilter(
            @RequestParam(value = "countryIds", required = false) List<Integer> countryIds,
            @RequestParam(value = "industryIds", required = false) List<Integer> industryIds,
            Model model
    ) {

        List<Consultant> consultants = consultantService.filterConsultants(countryIds, industryIds);

        model.addAttribute("consultants", consultants);
        model.addAttribute("countries", countryService.findAll());
        model.addAttribute("industries", industryService.findAll());
        return "view/consultants-filter";
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

        //        * auth seeker
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        List<ConsultantScheduleDateTimeslot> timeslots = consultantScheduleDateTimeslotService.getAllByIdString(
                timeslotString
        );
        for (ConsultantScheduleDateTimeslot timeslot : timeslots) {
            timeslot.setSeeker(user.getSeeker());
        }
//        consultantScheduleDateTimeslotService.saveAll(timeslots);
        emailService.sendMailAppointmentsBooked(user, consultant, timeslots);


        model.addAttribute("timeslots", timeslots);
        model.addAttribute("consultant", consultant);
        return "authenticated/payment-success";

    }
}

package com.sandee007.appointmentScheduleSystem.controller.consultant;

import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDateTimeslot;
import com.sandee007.appointmentScheduleSystem.service.ConsultantScheduleDateTimeslotService;
import com.sandee007.appointmentScheduleSystem.service.ConsultantService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("consultant")
public class ConsultantDashboardController {
    private ConsultantService consultantService;
    private ConsultantScheduleDateTimeslotService consultantScheduleDateTimeslotService;

    public ConsultantDashboardController(
            ConsultantService consultantService,
            ConsultantScheduleDateTimeslotService consultantScheduleDateTimeslotService
    ) {
        this.consultantService = consultantService;
        this.consultantScheduleDateTimeslotService = consultantScheduleDateTimeslotService;
    }

    @GetMapping("dashboard")
    public String dashboard(Model model, Authentication authentication) {
        Consultant consultant = consultantService.getLoggedInConsultant(authentication);
        List<ConsultantScheduleDateTimeslot> pendingAppointments = consultantScheduleDateTimeslotService.getPendingAppointments(
                0,
                consultant
        );
        List<ConsultantScheduleDateTimeslot> todayAppointments = consultantScheduleDateTimeslotService.getTodayAppointments(
                consultant
        );
        List<ConsultantScheduleDateTimeslot> upcomingAppointments = consultantScheduleDateTimeslotService.getUpcomingAppointments(
                consultant
        );

        model.addAttribute("consultant", consultant);
        model.addAttribute("pendingAppointments", pendingAppointments);
        model.addAttribute("todayAppointments", todayAppointments);
        model.addAttribute("upcomingAppointments", upcomingAppointments);
        return "consultant/dashboard";
    }

    @GetMapping("toggle-appointment-status")
    public String acceptAppointment(
            @RequestParam("id") int id,
            @RequestParam("status") int status
    ) {
        Optional<ConsultantScheduleDateTimeslot> c = consultantScheduleDateTimeslotService.findById(id);
        c.ifPresent(i -> {
            i.setStatus(status);
            consultantScheduleDateTimeslotService.save(i);
        });

        return "redirect:/consultant/dashboard#pending-appointments";
    }
}

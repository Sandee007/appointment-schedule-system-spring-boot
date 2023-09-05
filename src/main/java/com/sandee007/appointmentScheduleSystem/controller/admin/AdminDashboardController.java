package com.sandee007.appointmentScheduleSystem.controller.admin;

import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDateTimeslot;
import com.sandee007.appointmentScheduleSystem.service.ConsultantScheduleDateTimeslotService;
import com.sandee007.appointmentScheduleSystem.service.ConsultantService;
import com.sandee007.appointmentScheduleSystem.service.SeekerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminDashboardController {
    private ConsultantService consultantService;
    private UserService userService;
    private SeekerService seekerService;
    private ConsultantScheduleDateTimeslotService consultantScheduleDateTimeslotService;

    public AdminDashboardController(ConsultantService consultantService, UserService userService,
                                    SeekerService seekerService,
                                    ConsultantScheduleDateTimeslotService consultantScheduleDateTimeslotService
    ) {
        this.consultantService = consultantService;
        this.userService = userService;
        this.seekerService = seekerService;
        this.consultantScheduleDateTimeslotService = consultantScheduleDateTimeslotService;
    }

    @GetMapping("dashboard")
    public String dashboard(Model model){

        model.addAttribute("seekers", seekerService.findAll());
        model.addAttribute("consultants", consultantService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("toggle-active")
    public String toggleActive(
            @RequestParam("id") int id,
            @RequestParam("toggle") int toggle // * current enabled status
    ) {
        if (toggle == 0) {
            userService.enableById(id);
        } else {
            userService.disableById(id);
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("appointment-history")
    public String appointmentHistory(Model model){
        model.addAttribute("timeslots", consultantScheduleDateTimeslotService.findAllAppointments());
        return "admin/appointment-history";
    }
}

package com.sandee007.appointmentScheduleSystem.controller.admin;

import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.service.ConsultantService;
import com.sandee007.appointmentScheduleSystem.service.SeekerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminDashboardController {
    private ConsultantService consultantService;
    private UserService userService;
    private SeekerService seekerService;

    public AdminDashboardController(ConsultantService consultantService, UserService userService,
                                    SeekerService seekerService
    ) {
        this.consultantService = consultantService;
        this.userService = userService;
        this.seekerService = seekerService;
    }

    @GetMapping("dashboard")
    public String dashboard(Model model){

        model.addAttribute("seekers", seekerService.findAll());
        model.addAttribute("consultants", consultantService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("test")
    public String test(){
        return "admin/test";
    }
}

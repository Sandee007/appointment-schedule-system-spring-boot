package com.sandee007.appointmentScheduleSystem.controller.consultant;

import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.service.ConsultantService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("consultant")
public class ConsultantDashboardController {
    ConsultantService consultantService;

    public ConsultantDashboardController(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    @GetMapping("dashboard")
    public String dashboard(Model model, Authentication authentication){
        Consultant consultant = consultantService.getLoggedInConsultant(authentication);
        model.addAttribute("consultant", consultant);
        return "consultant/dashboard";
    }
}

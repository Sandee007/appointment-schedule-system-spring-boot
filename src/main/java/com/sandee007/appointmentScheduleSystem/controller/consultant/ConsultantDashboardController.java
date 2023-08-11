package com.sandee007.appointmentScheduleSystem.controller.consultant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("consultant")
public class ConsultantDashboardController {
    @GetMapping("dashboard")
    public String dashboard(){
        return "consultant/dashboard";
    }
}

package com.sandee007.appointmentScheduleSystem.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {
    @GetMapping("dashboard")
    public String dashboard(){
        return "admin/dashboard";
    }

    @GetMapping("test")
    public String test(){
        return "admin/test";
    }
}

package com.sandee007.appointmentScheduleSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicPagesController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
}

package com.sandee007.appointmentScheduleSystem.controller.admin;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.service.ConsultantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin")
public class AdminDashboardController {
    private ConsultantService consultantService;
    private UserService userService;

    public AdminDashboardController(ConsultantService consultantService, UserService userService) {
        this.consultantService = consultantService;
        this.userService = userService;
    }

    @GetMapping("dashboard")
    public String dashboard(Model model){

//        READ !!!!!!!!!
//        https://stackoverflow.com/questions/15359306/how-to-fetch-fetchtype-lazy-associations-with-jpa-and-hibernate-in-a-spring-cont

        Optional<Consultant> consultant = consultantService.findById(32);
        if(consultant.isPresent()){
            int id = consultant.get().getUser().getId();
            userService.disableById(id);
        }

        List<Consultant> consultants = consultantService.findAll();
//        List<Consultant> consultants = consultantService.findAllActiveUsers();

        model.addAttribute("consultants", consultants);
        return "admin/dashboard";
    }

    @GetMapping("test")
    public String test(){
        return "admin/test";
    }
}

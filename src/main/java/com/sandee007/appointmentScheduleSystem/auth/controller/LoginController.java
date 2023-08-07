package com.sandee007.appointmentScheduleSystem.auth.controller;

import com.sandee007.appointmentScheduleSystem.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.auth.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    private UserService userService;

    public LoginController(UserService userService){
        this.userService = userService;
    }

//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/register")
    public String register() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("123");
        userService.save(user);

        return "redirect:/";
    }

}

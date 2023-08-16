package com.sandee007.appointmentScheduleSystem.base.auth.controller;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.Role;
import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.base.auth.security.ERole;
import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }



    @PostMapping("/register-handler")
    public String register(HttpServletRequest request) throws ServletException {
        User user = new User();

        //        ! MST HAVE A ROLE WHEN CREATING A USER
        Role role = new Role();
        role.setRole(ERole.ROLE_SEEKER.name());
        role.setUser(user);

        user.setUsername("test4");
        user.setPassword("123");
        user.setRole(role);
        userService.save(user);

        //        * auto log user in
        //        ! NEEDS TO SEND THE PLAIN PASSWORD HERE, NOT THE ENCRYPTED ONE
        request.login(user.getUsername(), "123");
        return "redirect:/";
    }

}

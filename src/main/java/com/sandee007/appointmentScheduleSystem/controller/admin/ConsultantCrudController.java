package com.sandee007.appointmentScheduleSystem.controller.admin;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.Role;
import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.base.auth.security.ERole;
import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.base.auth.validation.ValidationMessages;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.base.auth.service.EmailService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin/consultant")
public class ConsultantCrudController {
    private UserService userService;
    private EmailService emailService;
    private Environment environment;

    public ConsultantCrudController(UserService userService, EmailService emailService, Environment environment) {
        this.userService = userService;
        this.emailService = emailService;
        this.environment = environment;
    }

    //    upgraded as a ControllerAdvice
    //    @InitBinder
    //    public void initBinder(WebDataBinder webDataBinder) {
    //        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
    //        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    //    }

    @GetMapping("create")
    public String create(Model model) {

        Consultant consultant = new Consultant();
        consultant.setFirstname(null);
        consultant.setLastname(null);

        User user = new User();
        user.setConsultant(consultant);
        model.addAttribute("user", user);

        return "admin/crud/consultant/create";
    }

    //    REDIRECT ATTRIBUTES
    //    https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/support/RedirectAttributes.html
    @PostMapping("store")
    public String store(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {

        if (user.getConsultant().getFirstname() == null) {
            bindingResult.rejectValue(
                    "consultant.firstname", "error.consultant.firstname", ValidationMessages.REQUIRED
            );
        }
        if (user.getConsultant().getLastname() == null) {
            bindingResult.rejectValue(
                    "consultant.lastname", "error.consultant.lastname", ValidationMessages.REQUIRED
            );
        }

        if (bindingResult.hasErrors()) {
            return "admin/crud/consultant/create";
        }

        Role role = new Role(ERole.ROLE_CONSULTANT.name());
        role.setUser(user);
        user.setRole(role);

        user.getConsultant().setUser(user);

        String password = RandomStringUtils.randomAlphanumeric(8);
        user.setUsername(user.getUsername() + "@" + environment.getProperty("spring.application.name").toLowerCase() + ".com");
        user.setPassword(password);
        userService.save(user);

        emailService.sendMailConsultantAccountCreated(user, password);

        redirectAttributes.addFlashAttribute("success", "Consultant Created.");
        return "redirect:/admin/dashboard";
    }
}

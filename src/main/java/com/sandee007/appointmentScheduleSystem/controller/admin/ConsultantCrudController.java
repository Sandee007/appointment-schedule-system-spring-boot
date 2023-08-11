package com.sandee007.appointmentScheduleSystem.controller.admin;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.Role;
import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.base.auth.security.ERole;
import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin/consultant")
public class ConsultantCrudController {
    private UserService userService;

    public ConsultantCrudController(UserService userService) {
        this.userService = userService;
    }

//    upgraded as a ControllerAdvice
//    @InitBinder
//    public void initBinder(WebDataBinder webDataBinder) {
//        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
//        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
//    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("user", new User());
        return "admin/crud/consultant/create";
    }

    //    REDIRECT ATTRIBUTES
    //    https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/support/RedirectAttributes.html
    @PostMapping("store")
    public String store(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        //        admin only submits the email

        //  create a user

        //        auto gen password
        //        send email
        //        consultants will come and update profile

        if (bindingResult.hasErrors()) {
            return "admin/crud/consultant/create";
        }

        Role role = new Role(ERole.ROLE_CONSULTANT.name());
        role.setUser(user);
        user.setRole(role);

        Consultant consultant = new Consultant();
        consultant.setUser(user);
        user.setConsultant(consultant);

        user.setPassword("123"); // TODO gen random pass
        userService.save(user);

        redirectAttributes.addFlashAttribute("success", "Consultant Created.");
        return "redirect:/admin/dashboard";
    }
}

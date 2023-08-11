package com.sandee007.appointmentScheduleSystem.controller.consultant;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.service.ConsultantService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("consultant/profile")
public class ConsultantProfileController {
    private ConsultantService consultantService;
    private UserService userService;

    public ConsultantProfileController(ConsultantService consultantService, UserService userService) {
        this.consultantService = consultantService;
        this.userService = userService;
    }

    @GetMapping("edit")
    public String edit(Model model, RedirectAttributes redirectAttributes, Authentication authentication, Principal principal) {
        User user = userService.findByUsername(authentication.getName());
        Consultant consultant = consultantService.findByUser(user);

        //        if(consultant.isEmpty()){
        //            redirectAttributes.addFlashAttribute("error", "Profile Not Found");
        //            return "redirect:/consultant/dashboard";
        //        }

        model.addAttribute("consultant", consultant);
        return "consultant/profile/edit";
    }

    @PostMapping("update")
    public String update(@Valid @ModelAttribute("consultant") Consultant consultant, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws ParseException {

        if(bindingResult.hasErrors())return "consultant/profile/edit";

        consultantService.save(consultant);
        redirectAttributes.addFlashAttribute("success", "Profile Updated");
        return "redirect:/consultant/dashboard";
    }
}

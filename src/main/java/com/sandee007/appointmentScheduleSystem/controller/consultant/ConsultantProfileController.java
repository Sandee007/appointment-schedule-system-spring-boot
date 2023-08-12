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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

@Controller
@RequestMapping("consultant/profile")
public class ConsultantProfileController {
    private ConsultantService consultantService;

    public ConsultantProfileController(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    @GetMapping("edit")
    public String edit(
            Model model,
            RedirectAttributes redirectAttributes,
            Authentication authentication
    ) {
        Consultant consultant = consultantService.getLoggedInConsultant(authentication);

        if (consultant == null) {
            redirectAttributes.addFlashAttribute("error", "Profile Not Found");
            return "redirect:/consultant/dashboard";
        }

        model.addAttribute("consultant", consultant);
        return "consultant/profile/edit";
    }

    @PostMapping("update")
    public String update(
            @Valid @ModelAttribute("consultant") Consultant consultant,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @RequestParam("imageFile") MultipartFile file
    ) throws ParseException, IOException {

        if (bindingResult.hasErrors()) return "consultant/profile/edit";

        //        upload image references
        //        https://www.baeldung.com/spring-boot-thymeleaf-image-upload
        String UPLOAD_DIRECTORY_LOCATION = "/src/main/resources/static";
        String UPLOAD_DIRECTORY = "/uploads/";
        String FILE_NAME = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(
                System.getProperty("user.dir") + UPLOAD_DIRECTORY_LOCATION + UPLOAD_DIRECTORY,
                FILE_NAME
        );
        Files.write(fileNameAndPath, file.getBytes());

        consultant.setImage(UPLOAD_DIRECTORY + FILE_NAME);
        consultantService.save(consultant);
        redirectAttributes.addFlashAttribute("success", "Profile Updated");
        return "redirect:/consultant/dashboard";
    }
}

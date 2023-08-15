package com.sandee007.appointmentScheduleSystem.controller.consultant;

import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;
import com.sandee007.appointmentScheduleSystem.service.ConsultantService;
import com.sandee007.appointmentScheduleSystem.service.TimeslotService;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("consultant/profile")
public class ConsultantProfileController {
    private ConsultantService consultantService;
    private TimeslotService timeslotService;

    public ConsultantProfileController(ConsultantService consultantService, TimeslotService timeslotService) {
        this.consultantService = consultantService;
        this.timeslotService = timeslotService;
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
            @RequestParam(value = "imageFile", required = false) MultipartFile file
    ) throws ParseException, IOException {

        if (bindingResult.hasErrors()) return "consultant/profile/edit";

        //        * upload image references
        //        https://www.baeldung.com/spring-boot-thymeleaf-image-upload
        if (!file.isEmpty()) {
            String UPLOAD_DIRECTORY_LOCATION = "/src/main/resources/static";
            String UPLOAD_DIRECTORY = "/uploads/";
            String FILE_NAME = new Date().getTime() + file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(
                    System.getProperty("user.dir") + UPLOAD_DIRECTORY_LOCATION + UPLOAD_DIRECTORY,
                    FILE_NAME
            );
            Files.write(fileNameAndPath, file.getBytes());
            consultant.setImage(UPLOAD_DIRECTORY + FILE_NAME);
        } else {
//            consultant.setImage();
        }

        consultantService.save(consultant);
        redirectAttributes.addFlashAttribute("success", "Profile Updated");
        return "redirect:/consultant/dashboard";
    }

    @GetMapping("calendar")
    public String calendar(Model model){
        List<TimeSlot> propTimeslots = timeslotService.findAll();
        model.addAttribute("propTimeslots", propTimeslots);
        return "consultant/profile/calendar";
    }
}

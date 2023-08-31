package com.sandee007.appointmentScheduleSystem.controller.consultant;

import com.sandee007.appointmentScheduleSystem.base.auth.dto.ChangePasswordDto;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.service.ConsultantService;
import com.sandee007.appointmentScheduleSystem.service.TimeslotService;
import com.sandee007.appointmentScheduleSystem.util.UtilThymeleaf;
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

@Controller
@RequestMapping("consultant/profile")
public class ConsultantProfileController {
    private ConsultantService consultantService;
    private TimeslotService timeslotService;
    private UtilThymeleaf utilThymeleaf;

    public ConsultantProfileController(ConsultantService consultantService, TimeslotService timeslotService,
                                       UtilThymeleaf utilThymeleaf
    ) {
        this.consultantService = consultantService;
        this.timeslotService = timeslotService;
        this.utilThymeleaf = utilThymeleaf;
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
        }

        consultantService.save(consultant);
        redirectAttributes.addFlashAttribute("success", "Profile Updated");
        return "redirect:/consultant/dashboard";
    }


    @GetMapping("change-password")
    String changePassword(Model model) {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setId(utilThymeleaf.getAuthUser().getId());

        model.addAttribute("changePasswordDto", changePasswordDto);
        return "consultant/profile/change-password";
    }

    @PostMapping("change-password")
    String changePasswordPost(
            @Valid @ModelAttribute("changePasswordDto") ChangePasswordDto changePasswordDto,
            BindingResult bindingResult
    ) {
        System.out.println(utilThymeleaf.getAuthUser().getPassword());
        if (bindingResult.hasErrors()) return "consultant/profile/change-password";

        return "redirect:/consultant/dashboard";


    }
}

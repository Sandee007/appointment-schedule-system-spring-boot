package com.sandee007.appointmentScheduleSystem.controller.seeker;

import com.sandee007.appointmentScheduleSystem.base.auth.dto.ChangePasswordDto;
import com.sandee007.appointmentScheduleSystem.base.auth.validation.ValidationMessages;
import com.sandee007.appointmentScheduleSystem.entity.Seeker;
import com.sandee007.appointmentScheduleSystem.service.SeekerService;
import com.sandee007.appointmentScheduleSystem.util.UtilThymeleaf;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Controller
@RequestMapping("view")
public class SeekerProfileController {
    private SeekerService seekerService;
    private UtilThymeleaf utilThymeleaf;

    public SeekerProfileController(SeekerService seekerService, UtilThymeleaf utilThymeleaf) {
        this.seekerService = seekerService;
        this.utilThymeleaf = utilThymeleaf;
    }

    @GetMapping("seeker")
    public String viewStudent(
            @RequestParam("id") int id,
            Model model
    ) {
        Seeker seeker = seekerService.findById(id).orElse(null);
        if (seeker == null) return "redirect:/";

        model.addAttribute("seeker", seeker);
        model.addAttribute("isAuthSeekerAccount", utilThymeleaf.getAuthUser().getId() == seeker.getUser().getId());
        return "view/seeker";
    }

    @PostMapping("seeker")
    public String updateSeeker(
            @Valid Seeker seeker,
            BindingResult bindingResult,
            @RequestParam(value = "imageFile", required = false) MultipartFile file,
            Model model,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            Seeker s = seekerService.findById(seeker.getId()).orElse(null);
            if (s == null) return "redirect:/";

            model.addAttribute("isAuthSeekerAccount", utilThymeleaf.getAuthUser().getId() == s.getUser().getId());
            return "view/seeker";
        }

        if (!file.isEmpty()) {
            String UPLOAD_DIRECTORY_LOCATION = "/src/main/resources/static";
            String UPLOAD_DIRECTORY = "/uploads/";
            String FILE_NAME = new Date().getTime() + file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(
                    System.getProperty("user.dir") + UPLOAD_DIRECTORY_LOCATION + UPLOAD_DIRECTORY,
                    FILE_NAME
            );
            Files.write(fileNameAndPath, file.getBytes());
            seeker.setImage(UPLOAD_DIRECTORY + FILE_NAME);
        }
        seekerService.save(seeker);

        redirectAttributes.addFlashAttribute(ValidationMessages.SUCCESS, "Profile updated");
        return "redirect:/view/seeker?id=4";
    }

    @GetMapping(value = "seeker/change-password")
    String seekerChangePassword(Model model){

        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setId(utilThymeleaf.getAuthUser().getId());
        model.addAttribute("changePasswordDto", changePasswordDto);

        return "view/seeker/change-password";
    }
}

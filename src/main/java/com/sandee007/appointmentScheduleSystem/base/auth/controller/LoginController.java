package com.sandee007.appointmentScheduleSystem.base.auth.controller;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.Role;
import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.base.auth.security.ERole;
import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import com.sandee007.appointmentScheduleSystem.base.auth.validation.ValidationMessages;
import com.sandee007.appointmentScheduleSystem.dto.RegisterSeekerDto;
import com.sandee007.appointmentScheduleSystem.entity.Seeker;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

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


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("RegisterSeekerDto", new RegisterSeekerDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerHandler(
            @Valid RegisterSeekerDto registerSeekerDto,
            BindingResult bindingResult,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) throws ServletException, IOException {
        final String ERROR_PREFIX = "error_";

        //        ! re-rendering the page doesn't work cuz user is still not logged in ¯\_(ツ)_/¯

        if (bindingResult.hasErrors()) {
            //        * bind all values first
            redirectAttributes.addFlashAttribute("username", registerSeekerDto.getUsername());
            redirectAttributes.addFlashAttribute("password", registerSeekerDto.getPassword());
            redirectAttributes.addFlashAttribute("confirmPassword", registerSeekerDto.getConfirmPassword());
            redirectAttributes.addFlashAttribute("firstname", registerSeekerDto.getFirstname());
            redirectAttributes.addFlashAttribute("lastname", registerSeekerDto.getLastname());
            redirectAttributes.addFlashAttribute("birthday", registerSeekerDto.getBirthday());
            redirectAttributes.addFlashAttribute("phone", registerSeekerDto.getPhone());
            redirectAttributes.addFlashAttribute("address", registerSeekerDto.getAddress());
            redirectAttributes.addFlashAttribute("description", registerSeekerDto.getDescription());

            if (registerSeekerDto.getUsername() == null) {
                redirectAttributes.addFlashAttribute(ERROR_PREFIX + "username", ValidationMessages.REQUIRED);
            }
            if (registerSeekerDto.getPassword() == null) {
                redirectAttributes.addFlashAttribute(ERROR_PREFIX + "password", ValidationMessages.REQUIRED);
            }
            System.out.println(registerSeekerDto.getPassword().length());
            if (registerSeekerDto.getPassword() != null && registerSeekerDto.getPassword().length() < 8) {
                redirectAttributes.addFlashAttribute(
                        ERROR_PREFIX + "password",
                        "Password should be 8 characters minimum"
                );
            }
            if (registerSeekerDto.getConfirmPassword() == null) {
                redirectAttributes.addFlashAttribute(ERROR_PREFIX + "confirmPassword", ValidationMessages.REQUIRED);
            }
            if (registerSeekerDto.getPassword() != null &&
                    registerSeekerDto.getConfirmPassword() != null &&
                    !registerSeekerDto.getPassword().equals(registerSeekerDto.getConfirmPassword())
            ) {
                redirectAttributes.addFlashAttribute(ERROR_PREFIX + "password", "Passwords do not match");
            }
            if (registerSeekerDto.getFirstname() == null) {
                redirectAttributes.addFlashAttribute(ERROR_PREFIX + "firstname", ValidationMessages.REQUIRED);
            }
            if (registerSeekerDto.getLastname() == null) {
                redirectAttributes.addFlashAttribute(ERROR_PREFIX + "lastname", ValidationMessages.REQUIRED);
            }
            if (registerSeekerDto.getBirthday() == null) {
                redirectAttributes.addFlashAttribute(ERROR_PREFIX + "birthday", ValidationMessages.REQUIRED);
            }
            if (registerSeekerDto.getPhone() == null) {
                redirectAttributes.addFlashAttribute(ERROR_PREFIX + "phone", ValidationMessages.REQUIRED);
            }
            if (registerSeekerDto.getAddress() == null) {
                redirectAttributes.addFlashAttribute(ERROR_PREFIX + "address", ValidationMessages.REQUIRED);
            }
            if (registerSeekerDto.getDescription() == null) {
                redirectAttributes.addFlashAttribute(ERROR_PREFIX + "description", ValidationMessages.REQUIRED);
            }
            if (registerSeekerDto.getImageFile().isEmpty()) {
                redirectAttributes.addFlashAttribute(ERROR_PREFIX + "image", ValidationMessages.REQUIRED);
            }

            return "redirect:/register";
        }

        User user = new User();
        user.setUsername(registerSeekerDto.getUsername());
        user.setPassword(registerSeekerDto.getPassword());

        //        ! MST HAVE A ROLE WHEN CREATING A USER
        Role role = new Role();
        role.setRole(ERole.ROLE_SEEKER.name());
        role.setUser(user);
        user.setRole(role);

        Seeker seeker = new Seeker();
        seeker.setFirstname(registerSeekerDto.getFirstname());
        seeker.setLastname(registerSeekerDto.getLastname());
        seeker.setBirthday(registerSeekerDto.getBirthday());
        seeker.setPhone(registerSeekerDto.getPhone());
        seeker.setAddress(registerSeekerDto.getAddress());
        seeker.setDescription(registerSeekerDto.getDescription());
        user.setSeeker(seeker);

        if (!registerSeekerDto.getImageFile().isEmpty()) {
            String UPLOAD_DIRECTORY_LOCATION = "/src/main/resources/static";
            String UPLOAD_DIRECTORY = "/uploads/";
            String FILE_NAME = new Date().getTime() + registerSeekerDto.getImageFile().getOriginalFilename();
            Path fileNameAndPath = Paths.get(
                    System.getProperty("user.dir") + UPLOAD_DIRECTORY_LOCATION + UPLOAD_DIRECTORY,
                    FILE_NAME
            );
            Files.write(fileNameAndPath, registerSeekerDto.getImageFile().getBytes());
            seeker.setImage(UPLOAD_DIRECTORY + FILE_NAME);
        }

        userService.save(user);

        //        * auto log user in
        //        ! NEEDS TO SEND THE PLAIN PASSWORD HERE, NOT THE ENCRYPTED ONE
        request.login(user.getUsername(), registerSeekerDto.getPassword());
        return "redirect:/";
    }

}

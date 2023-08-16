package com.sandee007.appointmentScheduleSystem.dto;

import com.sandee007.appointmentScheduleSystem.base.auth.validation.ValidationMessages;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class RegisterSeekerDto {
    @NotNull(message = ValidationMessages.REQUIRED)
    private String username;

    @NotNull(message = ValidationMessages.REQUIRED)
    private String password;

    @NotNull(message = ValidationMessages.REQUIRED)
    private String confirmPassword;

    @NotNull(message = ValidationMessages.REQUIRED)
    private String firstname;

    @NotNull(message = ValidationMessages.REQUIRED)
    private String lastname;

    @NotNull(message = ValidationMessages.REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @NotNull(message = ValidationMessages.REQUIRED)
    private String phone;

    @NotNull(message = ValidationMessages.REQUIRED)
    private String address;

    @NotNull(message = ValidationMessages.REQUIRED)
    private String description;

    @NotNull(message = ValidationMessages.REQUIRED)
    private MultipartFile imageFile;

}

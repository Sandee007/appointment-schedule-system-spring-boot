package com.sandee007.appointmentScheduleSystem.base.auth.dto;

import com.sandee007.appointmentScheduleSystem.base.auth.validation.MatchesCurrentPassword;
import com.sandee007.appointmentScheduleSystem.base.auth.validation.ValidationMessages;
import com.sandee007.appointmentScheduleSystem.util.UtilThymeleaf;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangePasswordDto {
    private int id;

    @NotNull(message = ValidationMessages.REQUIRED)
    @MatchesCurrentPassword
    private String currentPassword;

    @NotNull(message = ValidationMessages.REQUIRED)
    private String newPassword;

    @NotNull(message = ValidationMessages.REQUIRED)
    private String confirmPassword;
}

package com.sandee007.appointmentScheduleSystem.base.auth.dto;

import com.sandee007.appointmentScheduleSystem.base.auth.validation.FieldsMatch;
import com.sandee007.appointmentScheduleSystem.base.auth.validation.MatchesCurrentPassword;
import com.sandee007.appointmentScheduleSystem.base.auth.validation.ValidationMessages;
import com.sandee007.appointmentScheduleSystem.util.UtilThymeleaf;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@ToString
// * allows to get the whole DTO into the validator - cuz this is annotated on top of ChangePasswordDto
@FieldsMatch.List(
        {
                @FieldsMatch(
                        firstField = "newPassword",
                        secondField = "confirmPassword",
                        message = "New Password and Confirm Password do not match"
                )
                //        @FieldsMatch(firstField = "newPassword", secondField = "confirmPassword", message = "Does not match") // * add another - not implemented yet correctly
                //                // * https://stackoverflow.com/questions/1972933/cross-field-validation-with-hibernate-validator-jsr-303
        }
)
public class ChangePasswordDto {
    private int id;

    @NotNull(message = ValidationMessages.REQUIRED)
    @MatchesCurrentPassword
    private String currentPassword;

    @NotNull(message = ValidationMessages.REQUIRED)
    @Size(min = 8, message = ValidationMessages.PASSWORD_MIN_8)
    private String newPassword;

    @NotNull(message = ValidationMessages.REQUIRED)
    private String confirmPassword;
}

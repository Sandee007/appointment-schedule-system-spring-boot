package com.sandee007.appointmentScheduleSystem.base.auth.validation;

import com.sandee007.appointmentScheduleSystem.base.auth.dto.ChangePasswordDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.codehaus.groovy.ast.tools.BeanUtils;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, ChangePasswordDto> {
    private String firstField;
    private String secondField;
    private String message;

    @Override
    public void initialize(FieldsMatch constraintAnnotation) {
        this.firstField = constraintAnnotation.firstField();
        this.secondField = constraintAnnotation.secondField();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(ChangePasswordDto changePasswordDto, ConstraintValidatorContext constraintValidatorContext) {

        if (
                changePasswordDto.getNewPassword() != null &&
                        changePasswordDto.getConfirmPassword() != null &&
                        changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())
        ) return true;


        //        * https://stackoverflow.com/questions/11890334/cross-field-validation-with-hibernatevalidator-displays-no-error-messages
        //        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message).addPropertyNode(firstField).addConstraintViolation();
        return false;
    }
}

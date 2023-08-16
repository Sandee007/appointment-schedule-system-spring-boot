package com.sandee007.appointmentScheduleSystem.base.auth.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;

public class NotPastDateValidator implements ConstraintValidator<NotPastDate, Date> {
    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null) return false;
        return date.after(new Date());
    }
}

package com.sandee007.appointmentScheduleSystem.base.auth.validation;

import com.sandee007.appointmentScheduleSystem.base.auth.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private UserService userService;

    public UniqueEmailValidator() {
    }

    @Autowired
    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    //    THIS HAD THE CORRECT ANSWER
    //    https://stackoverflow.com/questions/69072406/hv000028-unexpected-exception-during-isvalid-call
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.existsByUsername(s);
    }
}

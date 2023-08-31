package com.sandee007.appointmentScheduleSystem.base.auth.validation;

import com.sandee007.appointmentScheduleSystem.util.UtilThymeleaf;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MatchesCurrentPasswordValidator implements ConstraintValidator<MatchesCurrentPassword, String> {
    private UtilThymeleaf utilThymeleaf;
    private PasswordEncoder passwordEncoder;

    public MatchesCurrentPasswordValidator(UtilThymeleaf utilThymeleaf, PasswordEncoder passwordEncoder) {
        this.utilThymeleaf = utilThymeleaf;
        this.passwordEncoder = passwordEncoder;
    }

    //    @Override
//    public void initialize(MatchesCurrentPassword matchesCurrentPassword) {
//
//    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null) return false;
        return passwordEncoder.matches(s, utilThymeleaf.getAuthUser().getPassword());
    }
}

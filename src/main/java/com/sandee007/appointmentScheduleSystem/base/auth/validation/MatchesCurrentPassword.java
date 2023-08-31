package com.sandee007.appointmentScheduleSystem.base.auth.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MatchesCurrentPasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchesCurrentPassword {
//    String passwordToMatch();
    String message() default "Does not match with Current Password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

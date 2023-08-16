package com.sandee007.appointmentScheduleSystem.base.auth.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueConsultantDateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueConsultantDate {
    String message() default "A Schedule is already available for this date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

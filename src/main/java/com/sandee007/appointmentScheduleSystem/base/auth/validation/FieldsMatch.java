package com.sandee007.appointmentScheduleSystem.base.auth.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// * https://stackoverflow.com/questions/1972933/cross-field-validation-with-hibernate-validator-jsr-303
@Constraint(validatedBy = FieldsMatchValidator.class)
@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Documented
public @interface FieldsMatch {
    String message() default "" ;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String firstField();
    String secondField();

    /**
     * Defines several <code>@FieldMatch</code> annotations on the same element
     *
     * @see FieldsMatch
     */
    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List
    {
        FieldsMatch[] value();
    }
}

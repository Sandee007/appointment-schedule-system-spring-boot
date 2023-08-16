package com.sandee007.appointmentScheduleSystem.base.auth.validation;

import com.sandee007.appointmentScheduleSystem.service.ConsultantScheduleDateService;
import com.sandee007.appointmentScheduleSystem.util.UtilThymeleaf;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class UniqueConsultantDateValidator implements ConstraintValidator<UniqueConsultantDate, Date> {
    private ConsultantScheduleDateService consultantScheduleDateService;
    private UtilThymeleaf utilThymeleaf;

    public UniqueConsultantDateValidator() {
    }

    @Autowired
    public UniqueConsultantDateValidator(
            ConsultantScheduleDateService consultantScheduleDateService,
            UtilThymeleaf utilThymeleaf
    ) {
        this.consultantScheduleDateService = consultantScheduleDateService;
        this.utilThymeleaf = utilThymeleaf;
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        return !consultantScheduleDateService.existsByConsultantAndDate(utilThymeleaf.getConsultant(), date);
    }
}

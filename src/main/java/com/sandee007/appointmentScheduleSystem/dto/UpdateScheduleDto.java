package com.sandee007.appointmentScheduleSystem.dto;

import com.sandee007.appointmentScheduleSystem.base.auth.validation.NotPastDate;
import com.sandee007.appointmentScheduleSystem.base.auth.validation.UniqueConsultantDate;
import com.sandee007.appointmentScheduleSystem.base.auth.validation.ValidationMessages;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class UpdateScheduleDto {
    private int id;

    @NotNull(message = ValidationMessages.REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotPastDate
    @Temporal(TemporalType.DATE)
    private Date selectedDate;

    private String timeslotString;
}

package com.sandee007.appointmentScheduleSystem.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandee007.appointmentScheduleSystem.base.auth.validation.ValidationMessages;
import com.sandee007.appointmentScheduleSystem.entity.TimeSlot;
import com.sandee007.appointmentScheduleSystem.service.TimeslotService;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class CreateScheduleDto {
    private int id;

    @NotNull(message = ValidationMessages.REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date selectedDate;

    private String timeslotString;
}

package com.sandee007.appointmentScheduleSystem.base.config;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class InitBinderConfig {
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
//        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
//                new SimpleDateFormat("yyyy-MM-dd"),
//                true, 10
//        ));
    }
}

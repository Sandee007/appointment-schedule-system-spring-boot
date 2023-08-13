package com.sandee007.appointmentScheduleSystem.base.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@ControllerAdvice
public class InitBinderConfig {
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder, HttpServletRequest request) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
        //        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
        //                new SimpleDateFormat("yyyy-MM-dd"),
        //                true, 10
        //        ));

        //        * how to exclude specific fields from validations
        //        https://stackoverflow.com/questions/31957055/eliminate-some-class-attributes-from-spring-valid-when-editing
        //        https://stackoverflow.com/questions/15031049/using-the-setallowedfields-method-in-spring
        //        ! didn't work
        //        if (Objects.equals(request.getRequestURI(), "/admin/consultant/store")) {
        //            webDataBinder.setDisallowedFields("user");
        //        }
    }
}

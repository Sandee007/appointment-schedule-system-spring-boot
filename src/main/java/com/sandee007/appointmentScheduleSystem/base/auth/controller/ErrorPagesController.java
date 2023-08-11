package com.sandee007.appointmentScheduleSystem.base.auth.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPagesController implements ErrorController {
    final String KEY_STATUS = "status";
    final String KEY_MESSAGE = "message";

    //    must have server.error.path=/error in application.properties
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        model.addAttribute(KEY_MESSAGE, "Something went wrong!");
        model.addAttribute(KEY_STATUS, "ERROR");

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute(KEY_STATUS, statusCode);

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute(KEY_MESSAGE, "Page not found!");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // pass
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute(KEY_MESSAGE, "Access Denied!");
            }
        }

        return "base/error-base";

    }
}

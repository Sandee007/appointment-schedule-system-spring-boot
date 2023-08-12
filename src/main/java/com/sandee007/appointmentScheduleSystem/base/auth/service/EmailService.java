package com.sandee007.appointmentScheduleSystem.base.auth.service;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import jakarta.mail.MessagingException;

import java.util.concurrent.Future;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String body);
    void sendHtmlEmail(String to, String subject, String content) throws MessagingException;
    void sendMailConsultantAccountCreated(User user, String password);
}

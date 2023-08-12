package com.sandee007.appointmentScheduleSystem.base.auth.service;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

//        REFERENCES
//        https://www.baeldung.com/spring-email
//        https://mailtrap.io/blog/spring-send-email/
//http://dolszewski.com/spring/sending-html-mail-with-spring-boot-and-thymeleaf/

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;
    private Environment environment;

    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine, Environment environment) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.environment = environment;
    }

    @Override
    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        javaMailSender.send(mail);
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(new InternetAddress("sender@example.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);
        message.setContent(content, "text/html; charset=utf-8");

        javaMailSender.send(message);
    }

    @Async
    @Override
    public void sendMailConsultantAccountCreated(User user, String password) {
        String subject = environment.getProperty("spring.application.name") + " - Consultant Account Has Been Created";

        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("password", password);
        context.setVariable("subject", subject);
        String content = templateEngine.process("emailTemplates/consultantRegistered", context);

        try {
            this.sendHtmlEmail(
                    user.getUsername(),
                    subject,
                    content
            );
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}

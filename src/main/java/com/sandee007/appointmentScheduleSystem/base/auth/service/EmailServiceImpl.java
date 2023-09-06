package com.sandee007.appointmentScheduleSystem.base.auth.service;

import com.sandee007.appointmentScheduleSystem.base.auth.entity.User;
import com.sandee007.appointmentScheduleSystem.entity.Consultant;
import com.sandee007.appointmentScheduleSystem.entity.ConsultantScheduleDateTimeslot;
import com.sandee007.appointmentScheduleSystem.entity.Seeker;
import com.sandee007.appointmentScheduleSystem.service.ConsultantService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

//        REFERENCES
//        https://www.baeldung.com/spring-email
//        https://mailtrap.io/blog/spring-send-email/
//http://dolszewski.com/spring/sending-html-mail-with-spring-boot-and-thymeleaf/

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;
    private Environment environment;
    private ConsultantService consultantService;

    @Value("${testing.app.url}")
    private String appUrl;
    @Value("${spring.application.name}")
    private String appName;

    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine, Environment environment,
                            ConsultantService consultantService
    ) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.environment = environment;
        this.consultantService = consultantService;
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

    @Override
    public void sendMailAppointmentsBooked(
            User user,
            Consultant consultant,
            List<ConsultantScheduleDateTimeslot> timeslots
    ) {
        Context context = new Context();
        String subject = environment.getProperty("spring.application.name") + " - Appointment Booked";
        context.setVariable("subject", subject);
        context.setVariable("appName", appName);
        context.setVariable("appUrl", appUrl);
        context.setVariable("appContactEmail", "info"+consultantService.generateConsultantEmailDomain());
        appointmentBookedNotifyConsultant(context, subject, consultant, timeslots, user.getSeeker());
        appointmentBookedNotifySeeker(context, subject, consultant, timeslots, user.getSeeker());
    }

    private void appointmentBookedNotifyConsultant(
            Context context,
            String subject,
            Consultant consultant,
            List<ConsultantScheduleDateTimeslot> timeslots,
            Seeker seeker
    ) {
        context.setVariable("firstname", consultant.getFirstname());
        context.setVariable("seeker", seeker);
        context.setVariable("timeslots", timeslots);
        context.setVariable("seekerProfileUrl", appUrl + "/view/seeker?id=" + seeker.getId());
        String content = templateEngine.process("emailTemplates/appointmentBooked/consultant", context);

        try {
            this.sendHtmlEmail(
                    consultant.getUser().getUsername(),
                    subject,
                    content
            );
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void appointmentBookedNotifySeeker(
            Context context,
            String subject,
            Consultant consultant,
            List<ConsultantScheduleDateTimeslot> timeslots,
            Seeker seeker
    ) {
        context.setVariable("firstname", seeker.getFirstname());
        context.setVariable("consultant", consultant);
        context.setVariable("timeslots", timeslots);
        context.setVariable("consultantProfileUrl", appUrl + "/view/consultant?id=" + consultant.getId());
        String content = templateEngine.process("emailTemplates/appointmentBooked/seeker", context);

        try {
            this.sendHtmlEmail(
                    seeker.getUser().getUsername(),
                    subject,
                    content
            );
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}

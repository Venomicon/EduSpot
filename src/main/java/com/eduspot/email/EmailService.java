package com.eduspot.email;

import com.eduspot.domain.User;
import com.eduspot.service.CourseService;
import com.eduspot.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    public static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final static String SUBJECT = "EduSpot: courses reminder";

    private JavaMailSender javaMailSender;
    private MailCreatorService mailCreatorService;
    private UserService userService;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, MailCreatorService mailCreatorService, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.mailCreatorService = mailCreatorService;
        this.userService = userService;
    }

    //@Scheduled
    public void scheduleReminders() throws Exception {
        List<User> users = userService.findAllUsers();
        for (User user : users) {
            if (!user.getCarriedCourses().isEmpty() || !user.getTakenCourses().isEmpty()) {
                sendReminderEmails(new Mail(
                        user.getCredentials().getEmail(),
                        SUBJECT,
                        mailCreatorService.buildCoursesReminderEmail(user)
                ));
            }
        }
    }

    public void sendReminderEmails(final Mail mail) {
        int mailCounter = 0;
        try {
            javaMailSender.send(createReminderMessage(mail));
            mailCounter += 1;
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
        LOGGER.info("Successfully sent " + mailCounter + " reminders.");
    }

    private MimeMessagePreparator createReminderMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getMessage(), true);
        };
    }


}

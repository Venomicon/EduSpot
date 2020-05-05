package com.eduspot.email;

import com.eduspot.domain.Course;
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
    private final static String REMINDER_SUBJECT = "EduSpot: courses reminder";
    private final static String DELETE_SUBJECT = "EduSpot: deleted course";

    private JavaMailSender javaMailSender;
    private MailCreatorService mailCreatorService;
    private UserService userService;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, MailCreatorService mailCreatorService, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.mailCreatorService = mailCreatorService;
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 0 * * MON")
    public void scheduleReminders() {
        List<User> users = userService.findAllUsers();
        for (User user : users) {
            if (!user.getCarriedCourses().isEmpty() || !user.getTakenCourses().isEmpty()) {
                try {
                    sendReminderEmail(new Mail(
                            user.getCredentials().getEmail(),
                            REMINDER_SUBJECT,
                            mailCreatorService.buildCoursesReminderEmail(user)
                    ));
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    public void sendReminderEmail(final Mail mail) {
        int mailCounter = 0;
        try {
            javaMailSender.send(createMimeMessage(mail));
            mailCounter += 1;
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: " + e.getMessage(), e);
        }
        LOGGER.info("Successfully sent " + mailCounter + " reminders.");
    }

    /*public void sendDeleteInformationEmail(User user, Course course) {
        try {
            javaMailSender.send(createMimeMessage(new Mail(
                    user.getCredentials().getEmail(),
                    DELETE_SUBJECT,
                    mailCreatorService.buildDeletedCourseEmail(user, course)
            )));
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: " + e.getMessage(), e);
        }
    }*/

    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getMessage(), true);
        };
    }
}

package com.eduspot.email;

import com.eduspot.domain.Course;
import com.eduspot.domain.User;
import com.eduspot.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailCreatorService {
    private TemplateEngine templateEngine;
    private CourseService courseService;

    @Autowired
    public MailCreatorService(@Qualifier("templateEngine") TemplateEngine templateEngine, CourseService courseService) {
        this.templateEngine = templateEngine;
        this.courseService = courseService;
    }

    public String buildCoursesReminderEmail(User user) throws Exception {
        List<String> courseTakenTitles = courseService.findAllCoursesTakenByUser(user.getUserId()).stream()
                .map(Course::getTitle)
                .collect(Collectors.toList());
        List<String> courseCarriedTitles = courseService.findAllCoursesCarriedByUser(user.getUserId()).stream()
                .map(Course::getTitle)
                .collect(Collectors.toList());
        String messageTaken = "This week you're taking " + courseTakenTitles.size() + " courses:";
        String messageCarried = "This week you're carrying out " + courseCarriedTitles.size() + " courses:";

        Context context = new Context();
        context.setVariable("messageTaken", messageTaken);
        context.setVariable("takenCourses", courseTakenTitles);
        context.setVariable("messageCarried", messageCarried);
        context.setVariable("carriedCourses", courseCarriedTitles);
        context.setVariable("name", user.getFirstName());

        return templateEngine.process("mail/courseReminder", context);
    }

    /*public String buildInformationAfterDeleting(User user) throws Exception {

    }*/
}

package com.eduspot.exception;

import com.sun.deploy.net.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public void handleUserAlreadyExists() {
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleUserNotFound() {
    }

    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleCourseNotFound(CourseNotFoundException e) {
        LOGGER.error("Course not found --- " + e.getMessage());
        return "error/courseNotFound";
    }
}

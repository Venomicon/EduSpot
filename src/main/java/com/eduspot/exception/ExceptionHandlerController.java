package com.eduspot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {
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
    public void handleCourseNotFound() {
    }
}

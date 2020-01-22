package com.eduspot.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;

@ControllerAdvice
public class ExceptionHandlerController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ModelAndView handleUserAlreadyExists(UserAlreadyExistException e) {
        String message = e.getMessage();
        LOGGER.error(message);
        ModelAndView modelAndView = new ModelAndView("error/errorPage");
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @ExceptionHandler({UserNotFoundException.class, UsernameNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleUserNotFound(Exception e) {
        String message = "User not found (" + e.getMessage() + ")";
        LOGGER.error(message);
        ModelAndView modelAndView = new ModelAndView("error/errorPage");
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleCourseNotFound(CourseNotFoundException e) {
        String message = "Course not found (" + e.getMessage() + ")";
        LOGGER.error(message);
        ModelAndView modelAndView = new ModelAndView("error/errorPage");
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @ExceptionHandler(CourseAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ModelAndView handleCourseAlreadyExists(CourseAlreadyExistsException e) {
        String message = e.getMessage();
        LOGGER.error(message);
        ModelAndView modelAndView = new ModelAndView("error/errorPage");
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @ExceptionHandler(AddingCourseException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ModelAndView handleAddingCourse(AddingCourseException e) {
        String message = "Conflict while adding course (" + e.getMessage() + ")";
        LOGGER.error(message);
        ModelAndView modelAndView = new ModelAndView("error/errorPage");
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @ExceptionHandler(ServletException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleServletException(ServletException e) {
        String message = e.getMessage();
        LOGGER.error(message);
        ModelAndView modelAndView = new ModelAndView("error/errorPage");
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handlePostNotFound(PostNotFoundException e) {
        String message = "Post not found (" + e.getMessage() + ")";
        LOGGER.error(message);
        ModelAndView modelAndView = new ModelAndView("error/errorPage");
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @ExceptionHandler(UnableToPostException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ModelAndView handleUnableToPost(UnableToPostException e) {
        String message = "Unable to post message (" + e.getMessage() + ")";
        LOGGER.error(message);
        ModelAndView modelAndView = new ModelAndView("error/errorPage");
        modelAndView.addObject("message", message);
        return modelAndView;
    }
}

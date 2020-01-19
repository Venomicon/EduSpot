package com.eduspot.exception;

public class CourseAlreadyExistsException extends Exception {
    public CourseAlreadyExistsException(String message) {
        super(message);
    }

    public CourseAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

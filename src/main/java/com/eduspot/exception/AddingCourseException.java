package com.eduspot.exception;

public class AddingCourseException extends Exception {
    public AddingCourseException(String message) {
        super(message);
    }

    public AddingCourseException(String message, Throwable cause) {
        super(message, cause);
    }
}

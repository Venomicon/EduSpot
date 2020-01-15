package com.eduspot.exception;

public class CourseNotFoundException extends Exception {
    public CourseNotFoundException(String message) { super(message); }

    public CourseNotFoundException(String message, Throwable cause) { super(message, cause); }
}

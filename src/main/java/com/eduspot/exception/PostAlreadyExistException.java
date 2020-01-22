package com.eduspot.exception;

public class PostAlreadyExistException extends Exception {
    public PostAlreadyExistException(String message) {
        super(message);
    }

    public PostAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}

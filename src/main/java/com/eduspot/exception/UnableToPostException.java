package com.eduspot.exception;

public class UnableToPostException extends Exception {
    public UnableToPostException(String message) {
        super(message);
    }

    public UnableToPostException(String message, Throwable cause) {
        super(message, cause);
    }
}

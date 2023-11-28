package com.assessment.acs.exception;

public class ValidationException extends RuntimeException {

    ValidationException() {

    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable throwable) {
        super (message, throwable);
    }
}

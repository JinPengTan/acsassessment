package com.assessment.acs.exception;

public class ResourceNotFoundException extends RuntimeException {

    ResourceNotFoundException() {}

    public ResourceNotFoundException(String message) {super(message);}

    public ResourceNotFoundException(String message, Throwable throwable) {super(message,throwable);}

}

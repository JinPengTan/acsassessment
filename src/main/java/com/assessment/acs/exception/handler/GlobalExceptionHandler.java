package com.assessment.acs.exception.handler;

import com.assessment.acs.exception.DuplicateResourceException;
import com.assessment.acs.exception.ErrorResponse;
import com.assessment.acs.exception.ResourceNotFoundException;
import com.assessment.acs.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleValidationException(RuntimeException ex, WebRequest request) {

        ErrorResponse errorResponse = buildErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        log.error("Validation Exception: ", ex);

        return handleExceptionInternal(ex, errorResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleResourceNotFoundException(RuntimeException ex, WebRequest request) {

        ErrorResponse errorResponse = buildErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        log.error("Resource Not Found Exception: ", ex);

        return handleExceptionInternal(ex, errorResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({DuplicateResourceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleDuplicateResourceException(RuntimeException ex, WebRequest request) {

        ErrorResponse errorResponse = buildErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        log.error("Duplicate Resource Exception: ", ex);

        return handleExceptionInternal(ex, errorResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleUnexpectedException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected Error, please contact us for assistant");

        log.error("Unexpected Exception", ex);

        return handleExceptionInternal(ex, errorResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessages(errors);
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimeStamp(LocalDateTime.now());

        log.error("Argument Not Valid", ex);

        return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    private ErrorResponse buildErrorResponse(int errorCode, String errorMessage) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(errorCode);
        errorResponse.setErrorMessage(errorMessage);
        errorResponse.setTimeStamp(LocalDateTime.now());
        return errorResponse;
    }

}

package com.assessment.acs.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {

    private int errorCode;
    private String errorMessage;
    private Map<String, String> errorMessages;
    private LocalDateTime timeStamp;

}

package com.java.challenge.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    private Integer errorCode;
    private HttpStatus httpStatus = HttpStatus.OK;
    private Exception ex;
    private String message;

    public ServiceException(Integer errorCode, HttpStatus httpStatus, String message) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.message = message;

    }
}

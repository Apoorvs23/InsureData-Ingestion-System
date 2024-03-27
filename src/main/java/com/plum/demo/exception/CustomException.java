package com.plum.demo.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomException extends Exception {
    private String message;
    private String errorMessage;
    private Integer code;
    private String type;
    private HttpStatus statusCode;

    public CustomException(String message) {
        super(message);
        this.type = "error";
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = message;
    }

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.type = "error";
        this.code = httpStatus.value();
        this.statusCode = httpStatus;
        this.errorMessage = message;
    }

    public CustomException(String type, String message, HttpStatus httpStatus) {
        super(message);
        this.type = type;
        this.code = httpStatus.value();
        this.statusCode = httpStatus;
        this.errorMessage = message;
    }
}

package com.plum.demo.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

// CustomException class represents a custom exception to handle specific cases
@Data
public class CustomException extends Exception {

    // Message to describe the exception
    private String message;

    // Additional error message
    private String errorMessage;

    // Error code associated with the exception
    private Integer code;

    // Type of the exception
    private String type;

    // HTTP status code associated with the exception
    private HttpStatus statusCode;

    // Constructor for creating CustomException with only a message
    public CustomException(String message) {
        super(message);
        this.type = "error"; // Set type as "error" by default
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value(); // Set default code as internal server error
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR; // Set default status code as internal server error
        this.message = message; // Set the message
    }

    // Constructor for creating CustomException with a message and specific HttpStatus
    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.type = "error"; // Set type as "error" by default
        this.code = httpStatus.value(); // Set code as the provided HttpStatus value
        this.statusCode = httpStatus; // Set the status code
        this.errorMessage = message; // Set the error message
    }

    // Constructor for creating CustomException with type, message, and specific HttpStatus
    public CustomException(String type, String message, HttpStatus httpStatus) {
        super(message);
        this.type = type; // Set the type as provided
        this.code = httpStatus.value(); // Set code as the provided HttpStatus value
        this.statusCode = httpStatus; // Set the status code
        this.errorMessage = message; // Set the error message
    }
}
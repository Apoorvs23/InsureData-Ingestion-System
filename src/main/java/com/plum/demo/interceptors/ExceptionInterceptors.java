package com.plum.demo.interceptors;

import com.plum.demo.exception.CustomException;
import com.plum.demo.response.BaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // This annotation allows this class to handle exceptions globally for controllers.
public class ExceptionInterceptors {

    /**
     * Provides handling for exceptions throughout this service.
     */

    @ExceptionHandler({CustomException.class}) // This method handles exceptions of type CustomException.
    public final ResponseEntity<BaseResponse> handleException(Exception ex) { // This method returns a ResponseEntity that includes headers, status, and potentially cookies, other than the body.
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof CustomException) { // Checks if the exception is an instance of CustomException.
            HttpStatus status = ((CustomException) ex).getStatusCode(); // Retrieves the HTTP status code from the CustomException, if available.
            if (status == null) { // If status is not provided in the CustomException, defaults to INTERNAL_SERVER_ERROR.
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            CustomException customOrderException = (CustomException) ex; // Casts the exception to CustomException.

            return handleCustomException(customOrderException, headers, status); // Calls a method to handle the CustomException.
        }

        CustomException customOrderException = new CustomException( // Creates a new CustomException with default error message and status.
                "error", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR
        );
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // Default status for unhandled exceptions.
        return handleCustomException(customOrderException, headers, status); // Calls a method to handle the unhandled exception.
    }

    // This method handles the CustomException and returns a ResponseEntity with appropriate error details.
    private ResponseEntity<BaseResponse> handleCustomException(CustomException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status) {
        return new ResponseEntity<>( // Constructs a ResponseEntity with BaseResponse as the body, headers, and status.
                new BaseResponse(Boolean.FALSE, ex.getCode(), ex.getMessage(), ex.getType()), headers, status
        );
    }
}

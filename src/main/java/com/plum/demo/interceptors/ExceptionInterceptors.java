package com.plum.demo.interceptors;

import com.plum.demo.exception.CustomException;
import com.plum.demo.response.BaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptors {

    /**
     * Provides handling for exceptions throughout this service.
     */

    @ExceptionHandler({CustomException.class})
    public final ResponseEntity<BaseResponse> handleException(Exception ex) {
        HttpHeaders headers = new HttpHeaders();
        if (ex instanceof CustomException) {
            HttpStatus status = ((CustomException) ex).getStatusCode();
            if (status == null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            CustomException customOrderException = (CustomException) ex;

            return handleCustomException(customOrderException, headers, status);
        }

        CustomException customOrderException = new CustomException(
                "error", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR
        );
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handleCustomException(customOrderException, headers, status);
    }

    private ResponseEntity<BaseResponse> handleCustomException(CustomException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status) {
        return new ResponseEntity<>(
                new BaseResponse(Boolean.FALSE, ex.getCode(), ex.getMessage(), ex.getType()), headers, status
        );
    }
}

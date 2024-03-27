package com.plum.demo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@AllArgsConstructor
@SuperBuilder
public class BaseResponse implements Serializable {
    @JsonProperty("success")
    private Boolean success = Boolean.FALSE;

    @JsonProperty("error")
    private CustomError customError;

    @Data
    public static class CustomError {
        private Integer code;
        private String type;
        private String message;

        public CustomError(Integer code, String error, String message) {
            this.code = code;
            this.type = error;
            this.message = message;
        }
    }

    public BaseResponse() {
        super();
    }

    public BaseResponse(Boolean success, Integer code, String message, String error) {
        super();
        CustomError customError = new CustomError(code, error, message);
        this.success = Boolean.FALSE;
        this.customError = customError;
    }
}

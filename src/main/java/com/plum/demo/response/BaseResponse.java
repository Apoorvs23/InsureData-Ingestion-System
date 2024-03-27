package com.plum.demo.response;

import com.fasterxml.jackson.annotation.JsonProperty;  //This annotation is used to specify the JSON property name during serialization and deserialization.
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@AllArgsConstructor
@SuperBuilder //parent class ka (bacho dedeta hai) - good for inheritance
public class BaseResponse implements Serializable { //serizable inherit - object class is parent
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
    } //calling constructor of super class -explicit calling

    public BaseResponse(Boolean success, Integer code, String message, String error) {
        super();
        CustomError customError = new CustomError(code, error, message);
        this.success = Boolean.FALSE;
        this.customError = customError;
    }
}

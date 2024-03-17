package com.plum.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadEmployeesRequest extends BaseRequest {
    @JsonProperty("Employee ID")
    private Integer id;

    @JsonProperty("First Name")
    private String firstName;

    @JsonProperty("Middle Name")
    private String middleName;

    @JsonProperty("Last Name")
    private String lastName;

    @JsonProperty("Date of Birth")
    private String dateOfBirth;

    @JsonProperty("Gender")
    private String gender;

    @JsonProperty("Email ID")
    private String emailID;

}

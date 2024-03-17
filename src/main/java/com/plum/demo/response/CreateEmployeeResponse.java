package com.plum.demo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.plum.demo.entity.EmployeeEntity;
import com.plum.demo.request.UploadEmployeesRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateEmployeeResponse extends BaseResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("ord_id")
    private Integer orgId;

    @JsonProperty("accepted_employees")
    private List<EmployeeEntity> acceptedEmployees;

    @JsonProperty("rejected_employees")
    private List<UploadEmployeesRequest> rejectedEmployees;

}

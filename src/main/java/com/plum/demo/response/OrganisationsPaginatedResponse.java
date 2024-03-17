package com.plum.demo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.plum.demo.entity.EmployeeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganisationsPaginatedResponse extends BaseResponse {
    @JsonProperty("status")
    private String status;

    @JsonProperty("org_wide_employees")
    private List<OrgWiseEmployees> orgWiseEmployees;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrgWiseEmployees {
        @JsonProperty("org_id")
        private Integer orgId;
        @JsonProperty("no_of_employees")
        private Integer noOfEmployees;
        @JsonProperty("employees")
        private List<EmployeeEntity> employeeEntityList;
    }

}

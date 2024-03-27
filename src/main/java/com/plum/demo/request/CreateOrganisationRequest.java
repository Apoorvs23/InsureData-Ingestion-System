package com.plum.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrganisationRequest extends BaseRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("alternate_name")
    private String alternateName;

    @JsonProperty("category")
    private String category;

    @JsonProperty("description")
    private String description;

    @JsonProperty("location_description")
    private String locationDescription;

    @JsonProperty("landmark")
    private String landmark;

    @JsonProperty("country")
    private String country;

    @JsonProperty("pin_code")
    private String pinCode;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_no")
    private String phoneNo;

}

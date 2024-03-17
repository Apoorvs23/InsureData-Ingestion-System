package com.plum.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "organisation")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OrganisationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull
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

    @NotNull
    @JsonProperty("email")
    private String email;

    @NotNull
    @JsonProperty("phone_no")
    private String phoneNo;

}

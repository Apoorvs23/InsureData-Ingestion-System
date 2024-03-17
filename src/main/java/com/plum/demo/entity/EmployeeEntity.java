package com.plum.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.plum.demo.util.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "employee")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class EmployeeEntity extends BaseEntity {
    @Id
    private Integer id;

    @NotNull
    @JsonProperty("first_name")
    @Column(name = "first_name")
    private String firstName;

    @JsonProperty("middle_name")
    @Column(name = "middle_name")
    private String middleName;

    @JsonProperty("last_name")
    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @JsonProperty("date_of_birth")
    @Column(name = "date_of_birth")
    @NotNull
    private String dateOfBirth;

    @NotNull
    @JsonProperty("gender")
    @Column(name = "gender")
    private Gender gender;

    @JsonProperty("email_id")
    @Column(name = "email_id")
    private String emailID;

    @JsonProperty("org_id")
    @Column(name = "org_id")
    private Integer orgId;
}

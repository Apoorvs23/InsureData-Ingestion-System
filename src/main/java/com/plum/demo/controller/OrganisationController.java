package com.plum.demo.controller;

import com.plum.demo.entity.OrganisationEntity;
import com.plum.demo.exception.CustomException;
import com.plum.demo.request.CreateOrganisationRequest;
import com.plum.demo.request.UploadEmployeesRequest;
import com.plum.demo.response.CreateEmployeeResponse;
import com.plum.demo.response.CreateOrganisationResponse;
import com.plum.demo.response.OrganisationsPaginatedResponse;
import com.plum.demo.service.employee.EmployeeService;
import com.plum.demo.service.organisation.OrganisationService;
import com.plum.demo.service.validate.employee.EmployeeValidation;
import com.plum.demo.service.validate.organisation.OrganisationValidation;
import com.plum.demo.util.CSVUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping({"api/organisations", "api/v1/organisations"})
public class OrganisationController extends BaseController {

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private OrganisationValidation organisationValidation;

    @Autowired
    private EmployeeValidation employeeValidation;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE) // what I will give
    public CreateOrganisationResponse createOrganisation(@NotNull @RequestBody CreateOrganisationRequest // object should not be null
                                                                 createOrganisationRequest) throws CustomException { //400, status code - wrong status code
        organisationValidation.validate(createOrganisationRequest);
        CreateOrganisationResponse createOrganisationResponse = null;
        try {
            createOrganisationResponse = organisationService.createOrganisation(createOrganisationRequest); //write into the DB
        } catch (Exception e) {
            throw new CustomException("Error while creating Organisation"); //call the exception handler
        }
        return createOrganisationResponse;
    }

    @PostMapping(value = "/{orgId}/members/upload", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //uploading file of employee of the organizations - You will get file
    public CreateEmployeeResponse uploadEmployees(@PathVariable("orgId") Integer orgId,
                                                  @RequestParam("employee_data") MultipartFile employeeFile) throws CustomException { //? is requstparam
        organisationValidation.isOrganisationIdValid(orgId);
        List<UploadEmployeesRequest> uploadEmployeesRequests = null;
        try {
            uploadEmployeesRequests = CSVUtils.read(UploadEmployeesRequest.class, employeeFile.getInputStream()); // will convert data from csv and generate list of java objects and return it as list of request
        } catch (IOException e) {
            throw new CustomException(e.getMessage());
        }
        employeeValidation.validate(uploadEmployeesRequests);
        CreateEmployeeResponse createEmployeeResponse = null;
        try {
            createEmployeeResponse = employeeService.addEmployees(uploadEmployeesRequests, orgId); //we will validate employees and then we will put them to the database
        } catch (Exception e) {
            throw new CustomException("Error while Adding Employees for org id : " + orgId);
        }
        return createEmployeeResponse;
    }

    @GetMapping(value = "/get_paginated_data")
    public OrganisationsPaginatedResponse getOrganisationsData(@RequestParam("page") Integer page,
                                                               @RequestParam("size") Integer size) {
        return employeeService.getOrganisationsDataInPages(page, size); // 100 - rows (61 to 80 )page number and size - 2,5
    }

    @GetMapping(value = "/{orgId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrganisationEntity getOrganisation(@PathVariable("orgId") Integer orgId) throws CustomException {
        // redis [1,2,3,4]
        organisationValidation.isOrganisationIdValid(orgId);
        return organisationService.getOrganisationEntity(orgId);
    }
}

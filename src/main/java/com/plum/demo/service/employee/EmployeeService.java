package com.plum.demo.service.employee;

import com.plum.demo.entity.EmployeeEntity;
import com.plum.demo.manager.EmployeeManager;
import com.plum.demo.request.UploadEmployeesRequest;
import com.plum.demo.response.CreateEmployeeResponse;
import com.plum.demo.response.OrganisationsPaginatedResponse;
import com.plum.demo.service.BaseService;
import com.plum.demo.service.validate.employee.EmployeeValidation;
import com.plum.demo.util.contants.Constants;
import com.plum.demo.util.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class EmployeeService extends BaseService {
    @Autowired
    private EmployeeManager employeeManager;

    @Autowired
    private EmployeeValidation employeeValidation;

    public CreateEmployeeResponse addEmployees(List<UploadEmployeesRequest> uploadEmployeesRequestList, Integer orgId) {
        List<EmployeeEntity> addedEmployees = new ArrayList<>();
        List<UploadEmployeesRequest> rejectedEmployees = new ArrayList<>();

        uploadEmployeesRequestList.forEach(
                uploadEmployeesRequest -> {
                    if (employeeValidation.validateParticularEmployee(uploadEmployeesRequest)) {
                        addedEmployees.add(addEmployee(uploadEmployeesRequest, orgId));
                    } else {
                        rejectedEmployees.add(uploadEmployeesRequest);
                    }
                }
        );
        return CreateEmployeeResponse.builder()
                .orgId(orgId)
                .status("Success")
                .acceptedEmployees(addedEmployees)
                .rejectedEmployees(rejectedEmployees)
                .success(Boolean.TRUE)
                .build();
    }

    public EmployeeEntity addEmployee(UploadEmployeesRequest uploadEmployeesRequest, Integer orgId) {
        EmployeeEntity employee = createEmployeeEntity(uploadEmployeesRequest, orgId);
        return employeeManager.addEmployee(employee);
    }

    private EmployeeEntity createEmployeeEntity(UploadEmployeesRequest uploadEmployeesRequest, Integer orgId) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setEmailID(uploadEmployeesRequest.getEmailID());
        employeeEntity.setFirstName(uploadEmployeesRequest.getFirstName());
        employeeEntity.setId(uploadEmployeesRequest.getId());
        employeeEntity.setGender(Gender.getGenderByName(uploadEmployeesRequest.getGender()));
        employeeEntity.setLastName(uploadEmployeesRequest.getLastName());
        employeeEntity.setMiddleName(uploadEmployeesRequest.getMiddleName());
        employeeEntity.setOrgId(orgId);
        employeeEntity.setDateOfBirth(uploadEmployeesRequest.getDateOfBirth());
        employeeEntity.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
        return employeeEntity;
    }

    public OrganisationsPaginatedResponse getOrganisationsDataInPages(Integer pageNumber, Integer pageSize) {
        Pageable pageable = getPaginationCriteria(pageNumber, pageSize);
        Page<EmployeeEntity> page = employeeManager.findAll(pageable);
        Map<Integer, List<EmployeeEntity>> orgWiseEmployees = getOrgWiseEmployees(page.getContent());
        OrganisationsPaginatedResponse organisationsPaginatedResponse = getOrganisationsPaginatedResponse(
                orgWiseEmployees
        );
        return organisationsPaginatedResponse;
    }

    private Pageable getPaginationCriteria(Integer pageNumber, Integer pageSize) {
        Sort sort = Sort.by(Constants.ORG_ID).ascending();
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    private Map<Integer, List<EmployeeEntity>> getOrgWiseEmployees(List<EmployeeEntity> employeeEntities) {
        Map<Integer, List<EmployeeEntity>> map = new HashMap<>();
        employeeEntities.stream().forEach(employeeEntity -> {
            List<EmployeeEntity> list = map.get(employeeEntity.getOrgId());
            if (!Objects.isNull(list)) {
                list.add(employeeEntity);
            } else {
                list = new ArrayList<>();
                list.add(employeeEntity);
            }
            map.put(employeeEntity.getOrgId(), list);
        });
        return map;
    }

    private OrganisationsPaginatedResponse getOrganisationsPaginatedResponse(Map<Integer, List<EmployeeEntity>> orgWiseEmployees) {
        OrganisationsPaginatedResponse organisationsPaginatedResponse = new OrganisationsPaginatedResponse();
        organisationsPaginatedResponse.setSuccess(Boolean.TRUE);
        organisationsPaginatedResponse.setStatus("SUCCESS");
        List<OrganisationsPaginatedResponse.OrgWiseEmployees> orgWiseEmployeesList = new ArrayList<>();
        orgWiseEmployees.keySet().forEach(orgId -> {
            OrganisationsPaginatedResponse.OrgWiseEmployees employee = new OrganisationsPaginatedResponse.OrgWiseEmployees();
            List<EmployeeEntity> employeeEntities = orgWiseEmployees.get(orgId);
            employee.setNoOfEmployees(employeeEntities.size());
            employee.setEmployeeEntityList(employeeEntities);
            employee.setOrgId(orgId);
            orgWiseEmployeesList.add(employee);
        });
        organisationsPaginatedResponse.setOrgWiseEmployees(orgWiseEmployeesList);
        return organisationsPaginatedResponse;
    }
}

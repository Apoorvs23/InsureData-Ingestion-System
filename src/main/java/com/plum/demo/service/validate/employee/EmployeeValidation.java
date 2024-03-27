package com.plum.demo.service.validate.employee;

import com.plum.demo.exception.CustomException;
import com.plum.demo.manager.EmployeeManager;
import com.plum.demo.request.UploadEmployeesRequest;
import com.plum.demo.service.validate.ValidationService;
import com.plum.demo.util.contants.ValidationConstants;
import com.plum.demo.util.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeValidation extends ValidationService<List<UploadEmployeesRequest>> {

    @Autowired
    private EmployeeManager employeeManager;

    @Override
    public void validate(List<UploadEmployeesRequest> uploadEmployeesRequests) throws CustomException {
        if (Objects.isNull(uploadEmployeesRequests) || uploadEmployeesRequests.isEmpty()) {
            throw new CustomException("Invalid Request For Employee Addition");
        }
    }

    public boolean validateParticularEmployee(UploadEmployeesRequest uploadEmployeesRequest) {
        if (Objects.isNull(uploadEmployeesRequest.getId()) || Objects.isNull(uploadEmployeesRequest.getFirstName()) ||
                Objects.isNull(uploadEmployeesRequest.getLastName()) || Objects.isNull(uploadEmployeesRequest.getGender()) ||
                Objects.isNull(uploadEmployeesRequest.getDateOfBirth())) {
            return false;
        }

        try {
            validateEmployeeId(uploadEmployeesRequest.getId());
            validateName(uploadEmployeesRequest.getFirstName());
            validateName(uploadEmployeesRequest.getLastName());
            validateEmail(uploadEmployeesRequest.getEmailID());
            validateGender(uploadEmployeesRequest.getGender());
            validateDOB(uploadEmployeesRequest.getDateOfBirth());
        } catch (CustomException e) {
            return false;
        }
        return true;
    }

    private void validateDOB(String dateOfBirth) throws CustomException {
        dateOfBirth = dateOfBirth.replaceAll("\\s", "");
        DateFormat sdf = new SimpleDateFormat(ValidationConstants.DATE_OF_BIRTH_REGEX);
        sdf.setLenient(false);
        try {
            sdf.parse(dateOfBirth);
        } catch (ParseException e) {
            throw new CustomException("Invalid Date of Birth Format");
        }
    }

    private void validateGender(String gender) throws CustomException {
        gender = gender.replaceAll("\\s", "");
        gender = gender.toLowerCase();
        Gender gen = Gender.getGenderByName(gender); //enums are dictionary
        if (Objects.isNull(gen)) {
            throw new CustomException("Invalid Gender Field");
        }
    }

    private void validateEmployeeId(Integer id) throws CustomException {
        boolean isEmployeeExist = employeeManager.getEmployee(id).size() > 0;
        if (isEmployeeExist) {
            throw new CustomException("Employee Id already exists");
        }
    }
}

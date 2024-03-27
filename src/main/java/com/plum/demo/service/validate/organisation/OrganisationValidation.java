package com.plum.demo.service.validate.organisation;

import com.plum.demo.exception.CustomException;
import com.plum.demo.manager.OrganisationManager;
import com.plum.demo.request.CreateOrganisationRequest;
import com.plum.demo.service.validate.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OrganisationValidation extends ValidationService<CreateOrganisationRequest> {

    @Autowired
    private OrganisationManager organisationManager;

    @Override
    public void validate(CreateOrganisationRequest createOrganisationRequest) throws CustomException {
        if (Objects.isNull(createOrganisationRequest.getEmail()) ||
                Objects.isNull(createOrganisationRequest.getPhoneNo()) ||
                Objects.isNull(createOrganisationRequest.getName())) {
            throw new CustomException("Invalid Create Request");
        }
        validatePhone(createOrganisationRequest.getPhoneNo());
        validateEmail(createOrganisationRequest.getEmail());
        validateName(createOrganisationRequest.getName());
        isOrganisationExits(createOrganisationRequest);
    }

    private void isOrganisationExits(CreateOrganisationRequest createOrganisationRequest) throws CustomException {
        boolean isOrgExists = organisationManager.isOrganisationExist(createOrganisationRequest.getEmail(),
                createOrganisationRequest.getPhoneNo());
        if (isOrgExists) {
            throw new CustomException("Organisation already Exists");
        }
    }

    public void isOrganisationIdValid(Integer organisationId) throws CustomException {
        boolean isOrgExists = organisationManager.isOrganisationIdExist(organisationId);
        if (!isOrgExists) {
            throw new CustomException("Organisation does not exists with id : " + organisationId);
        }
    }
}

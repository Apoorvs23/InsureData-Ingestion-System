package com.plum.demo.service.organisation;

import com.plum.demo.entity.OrganisationEntity;
import com.plum.demo.manager.OrganisationManager;
import com.plum.demo.request.CreateOrganisationRequest;
import com.plum.demo.response.CreateOrganisationResponse;
import com.plum.demo.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class OrganisationService extends BaseService {
    @Autowired
    private OrganisationManager organisationManager;

    // Method to create an organisation based on the provided request.
    public CreateOrganisationResponse createOrganisation(CreateOrganisationRequest createOrganisationRequest) {
        // Creating an OrganisationEntity object based on the request data.
        OrganisationEntity organisationEntity = getOrganisationEntity(createOrganisationRequest);
        // Saving the organisationEntity using the organisationManager.
        organisationEntity = organisationManager.createOrganisation(organisationEntity);
        // Building and returning a CreateOrganisationResponse object.
        return CreateOrganisationResponse.builder()
                .name(organisationEntity.getName())
                .orgId(organisationEntity.getId())
                .success(Boolean.TRUE)
                .status("SUCCESS")
                .build(); // New object created and returned.
    }

    // Method to convert CreateOrganisationRequest to OrganisationEntity.
    private OrganisationEntity getOrganisationEntity(CreateOrganisationRequest createOrganisationRequest) {
        OrganisationEntity organisationEntity = new OrganisationEntity();
        // Setting attributes of OrganisationEntity based on the request.
        organisationEntity.setName(createOrganisationRequest.getName());
        organisationEntity.setCategory(createOrganisationRequest.getCategory());
        organisationEntity.setCountry(createOrganisationRequest.getCountry());
        organisationEntity.setDescription(createOrganisationRequest.getDescription());
        organisationEntity.setEmail(createOrganisationRequest.getEmail());
        organisationEntity.setAlternateName(createOrganisationRequest.getAlternateName());
        organisationEntity.setLandmark(createOrganisationRequest.getLandmark());
        organisationEntity.setLocationDescription(createOrganisationRequest.getLocationDescription());
        organisationEntity.setPhoneNo(createOrganisationRequest.getPhoneNo());
        organisationEntity.setPinCode(createOrganisationRequest.getPinCode());
        organisationEntity.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC)); // Setting creation timestamp.
        return organisationEntity;
    }
}

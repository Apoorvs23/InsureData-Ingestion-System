package com.plum.demo.service.organisation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plum.demo.entity.OrganisationEntity;
import com.plum.demo.exception.CustomException;
import com.plum.demo.jedis.CacheManager;
import com.plum.demo.manager.OrganisationManager;
import com.plum.demo.request.CreateOrganisationRequest;
import com.plum.demo.response.CreateOrganisationResponse;
import com.plum.demo.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
@Slf4j
public class OrganisationService extends BaseService {
    @Autowired
    private OrganisationManager organisationManager;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ObjectMapper objectMapper;


    public CreateOrganisationResponse createOrganisation(CreateOrganisationRequest createOrganisationRequest) {
        OrganisationEntity organisationEntity = getOrganisationEntity(createOrganisationRequest);
        organisationEntity = organisationManager.createOrganisation(organisationEntity);
        return CreateOrganisationResponse.builder()
                .name(organisationEntity.getName())
                .orgId(organisationEntity.getId())
                .success(Boolean.TRUE)
                .status("SUCCESS")
                .build();
    }

    private OrganisationEntity getOrganisationEntity(CreateOrganisationRequest createOrganisationRequest) {
        OrganisationEntity organisationEntity = new OrganisationEntity();
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
        organisationEntity.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
        return organisationEntity;
    }

    public OrganisationEntity getOrganisationEntity(Integer orgId) throws CustomException {
        String key = "ORGANISATION_ID_" + orgId + "_DATA";
        // "ORGANISATION_ID_1_DATA"

        Optional<Boolean> exists = cacheManager.exists(key);
        if(exists.isPresent() && exists.get()) {
            Optional<String> organisationData = cacheManager.get(key);
            if(organisationData.isPresent()) {
                try {
                    return objectMapper.readValue(organisationData.get(), OrganisationEntity.class);
                } catch (JsonProcessingException e) {
                    throw new CustomException(e.getMessage());
                }
            }
        }

        Optional<OrganisationEntity> organisationEntity = organisationManager.getOrganisationEntity(orgId);
        String organisationData = null;
        try {
            organisationData = objectMapper.writeValueAsString(organisationEntity);
        } catch (JsonProcessingException e) {
            throw new CustomException(e.getMessage());
        }
        boolean isSet = cacheManager.set(key, organisationData, 1800);
        if(!isSet) {
            log.info("Error while setting key : {} in redis: ", key);
        }
        return organisationEntity.get();
    }
}

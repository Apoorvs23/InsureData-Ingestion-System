package com.plum.demo.manager;

import com.plum.demo.entity.OrganisationEntity;
import com.plum.demo.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrganisationManager {
    @Autowired
    private OrganisationRepository organisationRepository;

    public OrganisationEntity createOrganisation(OrganisationEntity organisationEntity) {
        return organisationRepository.save(organisationEntity);
    }

    public Optional<OrganisationEntity> getOrganisationEntity(Integer orgId) {
        return organisationRepository.findById(orgId);
    }

    public boolean isOrganisationExist(String email, String phone) {
        return organisationRepository.findAllByEmailAndPhoneNo(email, phone).size() > 0;
    }

    public boolean isOrganisationIdExist(Integer id) {
        return organisationRepository.findAllById(id).size() > 0;
    }
}

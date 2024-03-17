package com.plum.demo.manager;

import com.plum.demo.entity.OrganisationEntity;
import com.plum.demo.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// OrganisationManager class manages operations related to organisations
public class OrganisationManager { // (get data - do some operation then give to service layer)

    // Autowired annotation to inject OrganisationRepository dependency
    @Autowired
    private OrganisationRepository organisationRepository;

    // Method to create a new organisation entity

    public OrganisationEntity createOrganisation(OrganisationEntity organisationEntity) {
        return organisationRepository.save(organisationEntity); // Save organisation entity using repository
    }

    // Method to check if an organisation exists based on email and phone number
    public boolean isOrganisationExist(String email, String phone) {
        // Check if any organisations with the given email and phone number exist
        return organisationRepository.findAllByEmailAndPhoneNo(email, phone).size() > 0;
    }

    // Method to check if an organisation ID exists
    public boolean isOrganisationIdExist(Integer id) {
        // Check if any organisations with the given ID exist
        return organisationRepository.findAllById(id).size() > 0;
    }
}
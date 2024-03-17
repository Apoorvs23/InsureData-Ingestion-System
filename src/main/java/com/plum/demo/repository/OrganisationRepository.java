package com.plum.demo.repository;

import com.plum.demo.entity.OrganisationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationRepository extends JpaRepository<OrganisationEntity, Integer> {
    List<OrganisationEntity> findAllByEmailAndPhoneNo(String email, String phone);
    List<OrganisationEntity> findAllById(Integer id);
}

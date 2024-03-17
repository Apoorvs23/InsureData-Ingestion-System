package com.plum.demo.repository;

import com.plum.demo.entity.EmployeeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    @Query(value = "SELECT employee.ord_id, count(*) ", nativeQuery = true)
    List<Object[]> getOrganisationWideEmployees(Pageable pageable);

    List<EmployeeEntity> findAllById(Integer id);
}

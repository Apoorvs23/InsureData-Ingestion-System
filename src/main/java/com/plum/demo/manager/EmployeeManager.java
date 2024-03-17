package com.plum.demo.manager;

import com.plum.demo.entity.EmployeeEntity;
import com.plum.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeManager {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeEntity addEmployee(EmployeeEntity employeeEntity) {
        return employeeRepository.save(employeeEntity);
    }

    public Page<EmployeeEntity> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public List<EmployeeEntity> getEmployee(Integer id) {
        return employeeRepository.findAllById(id);
    }

}

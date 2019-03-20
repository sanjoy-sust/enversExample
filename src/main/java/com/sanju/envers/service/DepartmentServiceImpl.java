package com.sanju.envers.service;


import com.sanju.envers.entity.Department;
import com.sanju.envers.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department update(long id, Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void delete(Department department) {
        departmentRepository.delete(department);
    }

    @Override
    public Department getDepartmentById(long id) {
        return departmentRepository.findById(id).get();
    }

    @Override
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }
}

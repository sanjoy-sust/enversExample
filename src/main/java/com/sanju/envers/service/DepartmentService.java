package com.sanju.envers.service;

import com.sanju.envers.entity.Department;
import com.sanju.envers.entity.Employee;

import java.util.List;

public interface DepartmentService {
    Department save(Department department);
    Department update(long id, Department department);
    void delete(Department department);
    Department getDepartmentById(long id);
    List<Department> getDepartments();
}

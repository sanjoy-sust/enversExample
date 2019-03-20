package com.sanju.envers.service;

import com.sanju.envers.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);
    Employee update(long id, Employee employee);
    void delete(Employee employee);
    Employee getEmployeeById(long id);
    List<Employee> getEmployees();
}

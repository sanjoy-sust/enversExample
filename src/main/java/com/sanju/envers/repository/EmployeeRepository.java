package com.sanju.envers.repository;

import com.sanju.envers.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
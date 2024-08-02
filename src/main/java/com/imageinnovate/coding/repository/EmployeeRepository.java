package com.imageinnovate.coding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imageinnovate.coding.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}

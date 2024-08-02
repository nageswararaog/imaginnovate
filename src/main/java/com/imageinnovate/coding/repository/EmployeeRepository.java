package com.imageinnovate.coding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imageinnovate.coding.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}

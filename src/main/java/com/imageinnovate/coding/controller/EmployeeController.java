package com.imageinnovate.coding.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imageinnovate.coding.model.Employee;
import com.imageinnovate.coding.service.EmployeeService;
import com.imageinnovate.coding.service.TaxCalculation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping("/{id}/tax")
    public ResponseEntity<TaxCalculation> getEmployeeTax(@PathVariable String id) {
        TaxCalculation taxCalculation = employeeService.calculateTax(id);
        return ResponseEntity.ok(taxCalculation);
    }
}

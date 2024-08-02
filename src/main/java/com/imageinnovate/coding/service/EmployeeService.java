package com.imageinnovate.coding.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.imageinnovate.coding.model.Employee;
import com.imageinnovate.coding.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public TaxCalculation calculateTax(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        LocalDate currentFinancialYearStart = LocalDate.of(LocalDate.now().getYear(), 4, 1);
        long monthsWorked = ChronoUnit.MONTHS.between(employee.getDoj().isBefore(currentFinancialYearStart) ? currentFinancialYearStart : employee.getDoj(), LocalDate.now()) + 1;
        
        double yearlySalary = employee.getSalary() * monthsWorked;

        double taxAmount = calculateTaxAmount(yearlySalary);
        double cessAmount = yearlySalary > 2500000 ? (yearlySalary - 2500000) * 0.02 : 0;

        return new TaxCalculation(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), yearlySalary, taxAmount, cessAmount);
    }

    private double calculateTaxAmount(double yearlySalary) {
        double taxAmount = 0;

        if (yearlySalary > 1000000) {
            taxAmount += (yearlySalary - 1000000) * 0.20;
            yearlySalary = 1000000;
        }
        if (yearlySalary > 500000) {
            taxAmount += (yearlySalary - 500000) * 0.10;
            yearlySalary = 500000;
        }
        if (yearlySalary > 250000) {
            taxAmount += (yearlySalary - 250000) * 0.05;
        }

        return taxAmount;
    }
}

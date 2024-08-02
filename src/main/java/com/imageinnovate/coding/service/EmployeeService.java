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
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new RuntimeException("Employee not found"));

		LocalDate fromDate = LocalDate.of(2024, 4, 1); // Last Year Tax calculation start date
		LocalDate toDate = LocalDate.now(); // Last Year Tax calculation end date

		if (employee.getDoj().isAfter(fromDate)) {
			fromDate = employee.getDoj();
		}
		
		long monthsWorked = ChronoUnit.MONTHS.between(fromDate.withDayOfMonth(1), toDate.withDayOfMonth(1)) + 1;

		// Calculate the number of days in the first and last month
		int daysInFirstMonth = fromDate.lengthOfMonth() - fromDate.getDayOfMonth() + 1;
		int daysInLastMonth = toDate.getDayOfMonth();

		// Calculate the daily salary
		double dailySalary = employee.getSalary() / 30;

		// Calculate the total salary
		double yearlySalary = (monthsWorked - 2) * employee.getSalary() + (daysInFirstMonth * dailySalary)
				+ (daysInLastMonth * dailySalary);

		double taxAmount = calculateTaxAmount(yearlySalary);

		double cessAmount = yearlySalary > 2500000 ? (yearlySalary - 2500000) * 0.02 : 0;

		return new TaxCalculation(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(),
				yearlySalary, taxAmount, cessAmount);
	}

	private double calculateTaxAmount(double yearlySalary) {
		double taxAmount = 0;

		if (yearlySalary > 250000) {

			if (yearlySalary <= 500000) {
				taxAmount += (yearlySalary - 250000) * 0.05;
			} else if (yearlySalary <= 1000000) {
				taxAmount += 250000 * 0.05;
				taxAmount += (yearlySalary - 500000) * 0.10;
			} else {
				taxAmount += 250000 * 0.05;
				taxAmount += 500000 * 0.10;
				taxAmount += (yearlySalary - 1000000) * 0.20;
			}

		}

		return taxAmount;
	}
}

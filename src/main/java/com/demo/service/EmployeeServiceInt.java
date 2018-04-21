package com.demo.service;

import java.util.List;
import java.util.Set;

import com.demo.model.Employee;

public interface EmployeeServiceInt {
	String saveEmployee(Employee employee);
	Employee getEmployeeByEmpNumber(String empUsername);
	List<Employee> getAllTechnicians();
	List<Employee> getAllTechnicians(String empUsername);
	List<Employee> getAllEmployees(Integer offset, Integer maxResults);
	List<Employee> getAllEmployees(String email);
	List<Employee> getAllEmployees();
	String updateEmployee (Employee employee);
	String changePassword(String email, String password);
	String changePassword(String email);
	String deactivateEmployee(String email);
	Integer count();
	List<Employee> getAllManagers();
	List<Employee> reassignTechnicianList(String technicianName);

}

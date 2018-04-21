package com.demo.dao;

import java.util.List;
import java.util.Set;

import com.demo.model.Employee;

public interface EmployeeDaoInt {
	
	String saveEmployee(Employee employee);
	Employee getEmployeeByEmpNum(String empUsername);
	List<Employee> getAllTechnicians();
	List<Employee> getAllTechnicians(String empUsername);
	List<Employee>getTechniciansByProvince(String province);
	List<Employee> getAllEmployees(Integer offset, Integer maxResults);
	List<Employee> getAllEmployees();
	List<Employee> getAllEmployees(String email);
	String updateEmployee (Employee employee);
	String changePassword(String email,String password);
	String changePassword(String email);
	String deactivateEmployee(String email);
	Integer count();
	List<Employee> getAllManagers();
	String returnManagerEmail();
	String[] managersEmails();
	List<Employee> reassignTechnicianList(String technicianName);

}

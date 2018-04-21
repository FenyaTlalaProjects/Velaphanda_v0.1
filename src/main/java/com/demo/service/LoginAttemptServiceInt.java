package com.demo.service;

import com.demo.model.Employee;
import com.demo.model.LoginAttempt;

public interface LoginAttemptServiceInt {

	void upsertUserAttempt(LoginAttempt userLoginAttempt);
	LoginAttempt getLoginUser(String userName);
	LoginAttempt getEmployeeDetails(Employee employee);
	void userLoggeIn(Employee employee);
}

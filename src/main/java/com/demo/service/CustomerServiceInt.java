package com.demo.service;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;

import com.demo.bean.CustomerBean;
import com.demo.model.Customer;

public interface CustomerServiceInt {
	
	Customer getClientByClientName(String clientName);
	List<Customer> getClientList();
	List<Customer> getClientList(String customerName);
	String updateCustomer(CustomerBean customerBean);
	CustomerBean contactDetails(String customerName);
	String saveCustomer(CustomerBean customerBean);	
	JRDataSource getCustomerDetailsDataSource(String customerName);

}

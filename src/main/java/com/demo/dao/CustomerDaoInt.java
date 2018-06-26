package com.demo.dao;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;

import com.demo.bean.CustomerBean;
import com.demo.model.Customer;
import com.demo.model.CustomerContactDetails;

public interface CustomerDaoInt {
	
	Customer getClientByClientName(String clientName);
	List<Customer> getClientList();
	List<Customer> getClientList(String customerName);
	CustomerBean contactDetails(String customerName);
	String updateCustomer(CustomerBean customerBean);
	String saveCustomer(CustomerBean customerBean);
	JRDataSource getCustomerListDataSource();
	JRDataSource getCustomerDetailsDataSource(String customerName);	
	List<CustomerContactDetails> contacts();
	
	

}

package com.demo.dao;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;

import com.demo.bean.CustomerBean;
import com.demo.model.Customer;

public interface CustomerDaoInt {
	
	Customer getClientByClientName(String clientName);
	List<Customer> getClientList(Integer offset, Integer maxResults);
	List<Customer> getClientList();
	List<Customer> getClientList(String customerName);
	Integer count();
	CustomerBean contactDetails(String customerName);
	String updateCustomer(CustomerBean customerBean);
	String saveCustomer(CustomerBean customerBean);
	JRDataSource getCustomerListDataSource();
	JRDataSource getCustomerDetailsDataSource(String customerName);
	
	

}

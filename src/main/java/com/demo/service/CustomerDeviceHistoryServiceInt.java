package com.demo.service;

import java.util.List;

import com.demo.bean.CustomerDeviceHistoryBean;
import com.demo.bean.HistoryBean;
import com.demo.model.CustomerDeviceHistory;
import com.demo.model.History;

public interface CustomerDeviceHistoryServiceInt {

	String saveCustomerDeviceHistory(CustomerDeviceHistoryBean history);
	List<CustomerDeviceHistory> getAllHistoryByCustomer();
	List<CustomerDeviceHistory> getHistoryByCustomer(String customerName);
	List<CustomerDeviceHistory> getAllHistoryBySerialNumber();
	List<CustomerDeviceHistory> getHistoryBySerialNumber(String serialNumber);	
}

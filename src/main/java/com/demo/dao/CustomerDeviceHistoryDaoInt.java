package com.demo.dao;

import java.util.List;

import com.demo.bean.CustomerDeviceHistoryBean;
import com.demo.bean.HistoryBean;
import com.demo.model.CustomerDeviceHistory;
import com.demo.model.History;

public interface CustomerDeviceHistoryDaoInt {

	String saveCustomerDeviceHistory(CustomerDeviceHistoryBean history);
	List<CustomerDeviceHistory> getAllHistoryByCustomer();
	List<CustomerDeviceHistory> getHistoryByCustomer(String customerName);
	List<CustomerDeviceHistory> getAllHistoryBySerialNumber();
	List<CustomerDeviceHistory> getHistoryBySerialNumber(String serialNumber);
	
}

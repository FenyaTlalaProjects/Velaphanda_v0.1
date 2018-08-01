package com.demo.service;

import java.util.List;

import com.demo.bean.HistoryBean;
import com.demo.model.History;

public interface HistoryServiceInt {

	String saveHistory(HistoryBean history);
	List<History> getHistoryByCustomer(String customerName);
	List<History> getAllHistoryByCustomer();
	List<History> getHistoryBySerialNumber(String serialNumber);
	List<History> getAllHistoryBySerialNumber();
	List<History> getHistoryByPartNumber(String partNumber);
	List<History> getAllHistoryByPartNumber();
}

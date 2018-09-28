package com.demo.service.impl;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bean.CustomerDeviceHistoryBean;
import com.demo.bean.HistoryBean;
import com.demo.dao.CustomerDeviceHistoryDaoInt;
import com.demo.dao.HistoryDaoInt;
import com.demo.model.CustomerDeviceHistory;
import com.demo.model.Device;
import com.demo.model.Employee;
import com.demo.model.History;
import com.demo.model.TicketHistory;
import com.demo.service.CustomerDeviceHistoryServiceInt;
import com.demo.service.HistoryServiceInt;


@Service("customerDeviceHistoryService")
@Transactional
public class CustomerDeviceHistoryService implements CustomerDeviceHistoryServiceInt {
	
	@Autowired
	private CustomerDeviceHistoryDaoInt historyDAO;
	private String retMessage = null;
		
	public String saveCustomerDeviceHistory(CustomerDeviceHistoryBean history) {
		retMessage =historyDAO.saveCustomerDeviceHistory(history);
		return retMessage;		
	}	
	@Override
	public List<CustomerDeviceHistory> getAllHistoryByCustomer() {		
		return historyDAO.getAllHistoryByCustomer();
	}		
	@Override
	public List<CustomerDeviceHistory> getHistoryByCustomer(String customerName){		
		return historyDAO.getHistoryByCustomer(customerName);
	}	
	@Override
	public List<CustomerDeviceHistory> getAllHistoryBySerialNumber() {		
		return historyDAO.getAllHistoryBySerialNumber();
	}	
	@Override
	public List<CustomerDeviceHistory> getHistoryBySerialNumber(String serialNumber){	
		return historyDAO.getHistoryBySerialNumber(serialNumber);
	}
			
}

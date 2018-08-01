package com.demo.service.impl;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bean.HistoryBean;
import com.demo.dao.HistoryDaoInt;
import com.demo.model.Device;
import com.demo.model.Employee;
import com.demo.model.History;
import com.demo.model.TicketHistory;
import com.demo.service.HistoryServiceInt;


@Service("historyService")
@Transactional
public class HistoryService implements HistoryServiceInt {
	
	@Autowired
	private HistoryDaoInt historyDAO;
	private String retMessage = null;
		
	public String saveHistory(HistoryBean history) {
		retMessage =historyDAO.saveHistory(history);
		return retMessage;		
	}	
	@Override
	public List<History> getAllHistoryByCustomer() {		
		return historyDAO.getAllHistoryByCustomer();
	}		
	@Override
	public List<History> getHistoryByCustomer(String customerName){		
		return historyDAO.getHistoryByCustomer(customerName);
	}	
	@Override
	public List<History> getAllHistoryBySerialNumber() {		
		return historyDAO.getAllHistoryBySerialNumber();
	}	
	@Override
	public List<History> getHistoryBySerialNumber(String serialNumber){	
		return historyDAO.getHistoryBySerialNumber(serialNumber);
	}
	
	@Override
	public List<History> getAllHistoryByPartNumber() {		
		return historyDAO.getAllHistoryByPartNumber();
	}	
	@Override
	public List<History> getHistoryByPartNumber(String partNumber){	
		return historyDAO.getHistoryByPartNumber(partNumber);
	}
		
}

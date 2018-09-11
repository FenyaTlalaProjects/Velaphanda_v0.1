package com.demo.service.impl;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.bean.HistoryBean;
import com.demo.bean.HistoryMovementBean;
import com.demo.dao.HistoryDaoInt;
import com.demo.dao.HistoryMovementDaoInt;
import com.demo.model.Device;
import com.demo.model.Employee;
import com.demo.model.History;
import com.demo.model.HistoryMovement;
import com.demo.model.TicketHistory;
import com.demo.service.HistoryMovementServiceInt;
import com.demo.service.HistoryServiceInt;


@Service("historyMovementService")
@Transactional
public class HistoryMovementService implements HistoryMovementServiceInt {
	
	@Autowired
	private HistoryMovementDaoInt historyMovementDAO;
	private String retMessage = null;
		
	public String saveHistoryMovement(HistoryMovementBean historyMovement) {
		retMessage =historyMovementDAO.saveHistoryMovement(historyMovement);
		return retMessage;		
	}	
	
	@Override
	public List<HistoryMovement> getAllHistoryMovementByPartNumber() {		
		return historyMovementDAO.getAllHistoryMovementByPartNumber();
	}	
	@Override
	public List<HistoryMovement> getHistoryMovementByPartNumber(String partNumber){	
		return historyMovementDAO.getHistoryMovementByPartNumber(partNumber);
	}
	@Override
	public List<HistoryMovement> getSiteStockHistoryMovementByPartNumber(String partNumber) {
		return historyMovementDAO.getSiteStockHistoryMovementByPartNumber(partNumber);
	}
	@Override
	public List<HistoryMovement> getAllSiteStockHistoryMovementByPartNumber() {
		return historyMovementDAO.getAllSiteStockHistoryMovementByPartNumber();
	}
	@Override
	public List<HistoryMovement> getBootStockHistoryMovementByPartNumber(String partNumber) {
		return historyMovementDAO.getBootStockHistoryMovementByPartNumber(partNumber);
	}
	@Override
	public List<HistoryMovement> getAllBootStockHistoryMovementByPartNumber() {
		return historyMovementDAO.getAllBootStockHistoryMovementByPartNumber();
	}
		
}

package com.demo.dao;

import java.util.List;

import com.demo.bean.HistoryBean;
import com.demo.bean.HistoryMovementBean;
import com.demo.model.History;
import com.demo.model.HistoryMovement;

public interface HistoryMovementDaoInt {

	String saveHistoryMovement(HistoryMovementBean historyMovement);
	
	List<HistoryMovement> getHistoryMovementByPartNumber(String partNumber);
	List<HistoryMovement> getAllHistoryMovementByPartNumber();
	List<HistoryMovement> getSiteStockHistoryMovementByPartNumber(String partNumber);
	List<HistoryMovement> getAllSiteStockHistoryMovementByPartNumber();
	List<HistoryMovement> getBootStockHistoryMovementByPartNumber(String partNumber);
	List<HistoryMovement> getAllBootStockHistoryMovementByPartNumber();
}

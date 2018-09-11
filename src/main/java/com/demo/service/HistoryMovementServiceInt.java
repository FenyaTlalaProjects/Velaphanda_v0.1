package com.demo.service;

import java.util.List;

import com.demo.bean.HistoryBean;
import com.demo.model.History;
import com.demo.model.HistoryMovement;

public interface HistoryMovementServiceInt {

	List<HistoryMovement> getHistoryMovementByPartNumber(String partNumber);
	List<HistoryMovement> getAllHistoryMovementByPartNumber();
	List<HistoryMovement> getSiteStockHistoryMovementByPartNumber(String partNumber);
	List<HistoryMovement> getAllSiteStockHistoryMovementByPartNumber();
	List<HistoryMovement> getBootStockHistoryMovementByPartNumber(String partNumber);
	List<HistoryMovement> getAllBootStockHistoryMovementByPartNumber();
}

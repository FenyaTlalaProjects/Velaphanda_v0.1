package com.demo.service;

import java.util.List;

import com.demo.model.BootStock;

public interface BootStockInt {
	
	List<BootStock> getAllOrders();
	List<BootStock> getAllOrders(String technician);
	List<BootStock> getAllOrders(String technician, Long ticketRecordID);
	int countBootStock();
	int countBootStock(String technicianName);
	int countPartsForTechnician(String technicianName);
	List<BootStock> getPartsForTechnician(String technicianName);
	int countTonerForTechnician(String technicianName);
	List<BootStock> getTonerForTechnician(String technicianName);
	List<BootStock> getAllBootStock();
	List<BootStock> getAllBootStockByTechnician(String technicianName);
	

}

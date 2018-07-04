package com.demo.dao;

import java.util.List;

import com.demo.model.BootStock;
import com.demo.model.OrderDetails;

public interface BootStockDaoInt {
	
	void saveBootStock(List<OrderDetails> detailsDaos);
	List<BootStock> getAllOrders();
	List<BootStock> getAllOrders(String technician);
	void updateBootStock(BootStock bootStock);
	List<BootStock> getAllOrders(String technician, Long ticketRecordID);
	BootStock getBootStock(String partNumber);
	BootStock getBootStock(String partNumber, String technicianName);
	int countBootStock();
	int countBootStock(String technicianName);
	int countPartsForTechnician(String technicianName);
	List<BootStock> getPartsForTechnician(String technicianName);
	int countTonerForTechnician(String technicianName);
	List<BootStock> getTonerForTechnician(String technicianName);
	List<BootStock> getAllBootStock();
	List<BootStock> getAllBootStockByTechnician(String technicianName);
	String getTechName(String techEmail);

}

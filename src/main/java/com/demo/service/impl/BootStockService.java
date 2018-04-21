package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.BootStockDaoInt;
import com.demo.model.BootStock;
import com.demo.service.BootStockInt;

@Service("bootStockService")
public class BootStockService implements BootStockInt{
	
	@Autowired
	private BootStockDaoInt bootStockDaoInt;

	@Override
	public List<BootStock> getAllOrders() {
		return bootStockDaoInt.getAllOrders();
		
	}

	@Override
	public List<BootStock> getAllOrders(String technician) {
		
		return bootStockDaoInt.getAllOrders(technician);
	}

	@Override
	public List<BootStock> getAllOrders(String technician, Long ticketRecordID) {
		
		return bootStockDaoInt.getAllOrders(technician, ticketRecordID);
	}

	@Override
	public int countBootStock() {
		// TODO Auto-generated method stub
		return bootStockDaoInt.countBootStock();
	}

	@Override
	public int countPartsForTechnician(String technicianName) {
		// TODO Auto-generated method stub
		return bootStockDaoInt.countPartsForTechnician(technicianName);
	}

	@Override
	public List<BootStock> getPartsForTechnician(String technicianName) {
		// TODO Auto-generated method stub
		return bootStockDaoInt.getPartsForTechnician(technicianName);
	}

	@Override
	public int countTonerForTechnician(String technicianName) {
		// TODO Auto-generated method stub
		return bootStockDaoInt.countTonerForTechnician(technicianName);
	}

	@Override
	public List<BootStock> getTonerForTechnician(String technicianName) {
		// TODO Auto-generated method stub
		return bootStockDaoInt.getTonerForTechnician(technicianName);
	}

	@Override
	public List<BootStock> getAllBootStock() {
		// TODO Auto-generated method stub
		return bootStockDaoInt.getAllBootStock();
	}

	@Override
	public List<BootStock> getAllBootStockByTechnician(String technicianName) {
		// TODO Auto-generated method stub
		return bootStockDaoInt.getAllBootStockByTechnician(technicianName);
	}

	@Override
	public int countBootStock(String technicianName) {
		return bootStockDaoInt.countBootStock(technicianName);
	}

}

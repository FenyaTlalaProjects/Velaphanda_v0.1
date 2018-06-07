package com.demo.service;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;

import com.demo.model.OrderDetails;

public interface OrderDetailsInt {
	List<OrderDetails> getOrderDetailsByOrderNum(Long recordID);
	List<OrderDetails> getAllOrderDetails();
	List<OrderDetails> getOrderDetailsByTechnician(String email);
	List<OrderDetails> getAllAvailableOrderDetails(String technician);
	List<OrderDetails> getOrderDetailsByOrderNum(String key,Long recordID);
	List<OrderDetails> getAllBootStockOrders();
	List<OrderDetails> getAllSiteStockOrders();
	List<OrderDetails> getAllAvailableOrderDetailsForCustomer(String customerName);
	JRDataSource getOrderDetailsDataSource(Long RecordID);
}

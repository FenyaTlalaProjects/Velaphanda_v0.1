package com.demo.dao;

import java.util.List;

import com.demo.model.ApprovedOrderStock;
import com.demo.model.OrderDetails;

public interface ApprovedOrderStockDaoInt {
	
	
	public String approveOrderStock(List<OrderDetails> detailsDaos);
	
	public List<ApprovedOrderStock> getApprovedOrdersByOrderNumber(String orderNumber);

}

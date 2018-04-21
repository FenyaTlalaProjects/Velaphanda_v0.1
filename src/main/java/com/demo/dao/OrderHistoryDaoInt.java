package com.demo.dao;

import java.util.List;

import com.demo.model.OrderHeader;
import com.demo.model.OrderHistory;

public interface OrderHistoryDaoInt {
	
	void insetOrderHistory(OrderHeader order);
	List<OrderHistory> getAllOrderHistoryByOrderNumber(Long recordID);
	List<OrderHistory> getAllOrderHistoryByOrderNumber();
	List<OrderHistory> getAllOrderHistoryTicketNumber(Long ticketNumber);

}

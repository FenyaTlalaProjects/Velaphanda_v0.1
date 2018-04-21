package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.OrderHistoryDaoInt;
import com.demo.model.OrderHistory;
import com.demo.service.OrderHistoryInt;


@Service("orderHistoryService")
@Transactional
public class OrderHistoryService implements OrderHistoryInt{

	@Autowired
	private OrderHistoryDaoInt orderHistory;
	
	@Override
	public List<OrderHistory> getAllOrderHistoryByOrderNumber(Long orderNumber) {
		
		return orderHistory.getAllOrderHistoryByOrderNumber(orderNumber);
	}

	@Override
	public List<OrderHistory> getAllOrderHistoryTicketNumber(Long ticketNumber) {
		
		return orderHistory.getAllOrderHistoryTicketNumber(ticketNumber);
	}

}

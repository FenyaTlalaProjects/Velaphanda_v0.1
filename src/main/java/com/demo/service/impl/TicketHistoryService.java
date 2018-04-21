package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.TicketHistoryDaoInt;
import com.demo.model.TicketHistory;
import com.demo.service.TicketHistoryInt;


@Service("ticketHistoryService")
public class TicketHistoryService implements TicketHistoryInt{
	
	@Autowired
	private TicketHistoryDaoInt historyDaoInt;

	@Override
	public List<TicketHistory> getHistoryByTicketNumber(Long ticketNumber) {
		
		return historyDaoInt.getHistoryByTicketNumber(ticketNumber);
	}

}

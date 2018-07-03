package com.demo.service.impl;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.TicketHistoryDaoInt;
import com.demo.model.TicketHistory;
import com.demo.model.Tickets;
import com.demo.service.TicketHistoryInt;


@Service("ticketHistoryService")
public class TicketHistoryService implements TicketHistoryInt{
	
	@Autowired
	private TicketHistoryDaoInt historyDaoInt;

	@Override
	public List<TicketHistory> getHistoryByTicketNumber(Long ticketNumber) {
		
		return historyDaoInt.getHistoryByTicketNumber(ticketNumber);
	}

	@Override
	public JRDataSource getDeviceHistoryDataSource(Long recordID) {
		// TODO Auto-generated method stub
		return historyDaoInt.getDeviceHistoryDataSource(recordID);
	}

	@Override
	public Tickets getLoggedTicketsByTicketNumber(Long ticketNumber) {
		// TODO Auto-generated method stub
		return historyDaoInt.getLoggedTicketsByTicketNumber(ticketNumber);
	}

	@Override
	public List<TicketHistory> getAllTicketHistoryByTicketNumber(Long recordID) {
		// TODO Auto-generated method stub
		return historyDaoInt.getAllTicketHistoryByTicketNumber(recordID);
	}

}

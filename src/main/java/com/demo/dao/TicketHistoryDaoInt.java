package com.demo.dao;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;

import com.demo.model.TicketHistory;
import com.demo.model.Tickets;

public interface TicketHistoryDaoInt {
	void insertTicketHistory(Tickets ticket);
	List<TicketHistory> getHistoryByTicketNumber(Long ticketNumber);	
	List<TicketHistory> getAllTicketHistoryByTicketNumber();
	JRDataSource getDeviceHistoryDataSource(Long recordID);
	Tickets getLoggedTicketsByTicketNumber(Long recordID);
	List<TicketHistory> getAllTicketHistoryByTicketNumber(Long recordID);
}

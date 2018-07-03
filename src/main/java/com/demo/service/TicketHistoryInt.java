package com.demo.service;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;

import com.demo.model.TicketHistory;
import com.demo.model.Tickets;

public interface TicketHistoryInt {
	List<TicketHistory> getHistoryByTicketNumber(Long ticketNumber);

	JRDataSource getDeviceHistoryDataSource(Long recordID);
	Tickets getLoggedTicketsByTicketNumber(Long ticketNumber);
	List<TicketHistory> getAllTicketHistoryByTicketNumber(Long recordID);
}

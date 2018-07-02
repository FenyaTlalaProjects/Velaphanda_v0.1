package com.demo.service;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;

import com.demo.model.TicketHistory;

public interface TicketHistoryInt {
	List<TicketHistory> getHistoryByTicketNumber(Long ticketNumber);

	JRDataSource getDeviceHistoryDataSource(Long recordID);
	TicketHistory getLoggedTicketsByTicketNumber(Long ticketNumber);
}

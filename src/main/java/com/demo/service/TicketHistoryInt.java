package com.demo.service;

import java.util.List;

import com.demo.model.TicketHistory;

public interface TicketHistoryInt {
	List<TicketHistory> getHistoryByTicketNumber(Long ticketNumber);

}

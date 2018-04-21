package com.demo.dao;

import com.demo.model.Tickets;

public interface ScheduledTickets {

	void calculateSLAHours();
	void resolveToClosedTicketUpdate();
	void updateSLA(Tickets tickets);
}

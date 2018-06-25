package com.demo.dao;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;

import com.demo.bean.EmployeeBean;
import com.demo.bean.PieChart;
import com.demo.bean.TicketsBean;
import com.demo.model.OrderHeader;
import com.demo.model.Tickets;

public interface TicketsDaoInt {
	
	String logTicket(TicketsBean tickets);
	Tickets getLoggedTicketsByTicketNumber(Long ticketNumber);
	List<Tickets> getAllLoggedTickets();
	List<Tickets> getAllLoggedTickets(String startDate);
	List<Tickets> getAllLoggedTickets(String startDate, String endDate);
	List<Tickets> getAllOpenTickets();
	List<Tickets> getAllEscalatedTickets();
	List<Tickets> getAllAwaitingSpares();
    List<Tickets> getAllAcknowledgedTickets();	
	List<Tickets> getAllTakenTickets();	
	
	List<Tickets> getAllClosedTickets();
	List<Tickets> getAllClosedTickets(String startDate,String endDate);
	List<Tickets> getAllClosedTickets(String startDate,String endDate, String technicianEmail);
	List<Tickets> getAllEscalatedTickets(String startDate,String endDate);
	List<Tickets> getAllEscalatedTickets(String startDate,String endDate,String technicianEmail);
	List<Tickets> getAllBridgedTickets(String startDate,String endDate);
	List<Tickets> getAllBridgedTickets(String startDate,String endDate,String technicianEmail);
	List<Tickets> getAllAwaitingSparesTickets(String startDate,String endDate);
	List<Tickets> getAllAwaitingSparesTickets(String startDate,String endDate,String technicianEmail);
	List<Tickets> getAllOpenTickets(String startDate,String endDate);
	List<Tickets> getAllOpenTickets(String startDate,String endDate,String technicianEmail);
	
	List<Tickets> getAllBridgedTickets();
	List<Tickets> getAllResolvedTickets();
	List<Tickets> getAssignedCallsToTechnician();
	List<Tickets> getAssignedCallsToTechnician(String username);
	List<Tickets> getAllEmployees(String searchName);
	List<PieChart> ticketsResults();
	String updateTicket(TicketsBean ticket);
	
    int ticketCountForTechnician(String technicianEmail);
    List<Tickets> getOpenTicketsForTechnician(String technicianEmail);
    int countEscalatedTickets();
    int countClosedTickets();
    int countBridgedTickets();
    int countOpenTickets();
    int countAwaitingSparesTickets();
    int countResolvedTickets();
    int countAcknowledgedTickets();	
	int countTakenTickets();
    
    int countEscalatedTickets(String technicianEmail);
    int countClosedTickets(String technicianEmail);
    int countBridgedTickets(String technicianEmail);
    int countOpenTickets(String technicianEmail);
    int countAwaitingSparesTickets(String technicianEmail);
    int countResolvedTickets(String technicianEmail);
    int countAcknowledgedTickets(String technicianEmail);	
	int countTakenTickets(String technicianEmail);
    
    List<Tickets> getAllOpenTickets(String technicianEmail);
	List<Tickets> getAllEscalatedTickets(String technicianEmail);
	List<Tickets> getAllAwaitingSpares(String technicianEmail);
	List<Tickets> getAllClosedTickets(String technicianEmail);
	List<Tickets> getAllBridgedTickets(String technicianEmail);
	List<Tickets> getAllResolvedTickets(String technicianEmail);
	List<Tickets> getAllAcknowledgedTickets(String technicianEmail);	
	List<Tickets> getAllTakenTickets(String technicianEmail);	
	List<Tickets> getAwaitingSparesTickets();
	List<Tickets> getLastFourteenDaysTickets();
	int getTicketCount(String status, String dateRange, String technicianEmail,
			String customer, Long ticketNumber);
	List<Tickets> getTicketListByStatus(String status, String dateRange,
			String technicianEmail, String customer, Long ticketNumber);
	List<Tickets> getTicketListByCustomerName(String customer);
	List<Tickets> getTicketListByDateRange(String dateRange);
	List<Tickets> getTicketListByTechnicianEmail(String technicianEmail);
	String[] getTicketNumbers();
	List<Tickets> searchTicketByTicketNumber(Long ticketNumber);
	List<Tickets> getFourteenDaysTicketsForTech(String technicianEmail);
	List<Tickets> getTicketListByCustomerNameForTech(String customer,
			String technicianEmail);
	List<Tickets> searchByTicketNumberForTech(Long ticketNumber,
			String technicianEmail);
	String[] getTicketNumbersForTech(String technicianEmail);
	String performTicketAction(TicketsBean ticketsBean);

	List<Tickets> getTicketByDateAndCustomer(String selecteDate,String customerName,String technicianEmai);
	List<Tickets> getTicketByDateAndCustomerForManager(String selecteDate,String customerName,String technicianEmai);
	List<Tickets> getTicketListForManager(String customer, String dateRange,
			String technician, Long ticketNumber);
	int getTicketCountForManager(String customer, String dateRange,
			String technician, Long ticketNumber, String status);
	List<Tickets> getTicketListByStatusForManager(String customer,
			String dateRange, String technician, Long ticketNumber,
			String status);
	List<Tickets> getTicketListForTechnician(String customer, String dateRange,
			String technician, Long ticketNumber);
	int getTicketCountForTechnician(String customer, String dateRange,
			String technician, Long ticketNumber, String status);
	int getTicketCountForTechnicianDashbord(String status, String dateRange,
			String technicianEmail, String customer, Long ticketNumber);
	List<Tickets> getTicketListByStatusForTech(String customer,
			String dateRange, String technician, Long ticketNumber,
			String status);
	long convertTicketToLong(String ticketNo);
	Long logTicketForTonnerReplacement(Tickets ticket);
	List<Tickets> getAllTicketsBySerialNumber(String serialNumber);
	public String acknowledgeTicketsForTech(Long recordID);
	public String takeTicketsForTech(Long recordID);
	JRDataSource getTicketDetailsDataSource(Long recordID);
	
	
}

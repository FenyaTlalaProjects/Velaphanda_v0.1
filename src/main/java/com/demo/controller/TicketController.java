package com.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo.bean.PieChart;
import com.demo.bean.SparePartsBean;
import com.demo.bean.TicketsBean;
import com.demo.dao.EmployeeDaoInt;
import com.demo.model.Customer;
import com.demo.model.Employee;
import com.demo.model.Tickets;
import com.demo.service.BootStockInt;
import com.demo.service.CustomerContactDetailsServiceInt;
import com.demo.service.CustomerServiceInt;
import com.demo.service.EmployeeServiceInt;
import com.demo.service.LeaveInt;
import com.demo.service.OrderHistoryInt;
import com.demo.service.OrdersServiceInt;
import com.demo.service.SiteStockInt;
import com.demo.service.SpareMasterServiceInt;
import com.demo.service.TicketsServiceInt;
import com.demo.service.DeviceServiceInt;
import com.demo.service.TicketHistoryInt;

@Controller
public class TicketController {

	@Autowired
	private TicketsServiceInt logTicketService;
	@Autowired
	private CustomerServiceInt customerServiceInt;
	@Autowired
	private DeviceServiceInt deviceServiceInt;
	@Autowired
	private EmployeeServiceInt employeeServiceInt;
	@Autowired
	private EmployeeDaoInt employeeDaoInt;
	@Autowired
	private TicketHistoryInt ticketHistoryInt;
	@Autowired
	private OrdersServiceInt ordersServiceInt;
	@Autowired
	private TicketsServiceInt ticketsServiceInt;
	@Autowired
	private SpareMasterServiceInt spareMasterServiceInt;
	@Autowired
	private BootStockInt bootStockint;
	@Autowired
	private SiteStockInt siteStock;
	@Autowired
	private LeaveInt leaveInt;
	@Autowired
	private OrderHistoryInt historyInt;
	private List<PieChart> beanList = null;
	Integer count = 1;
	public String[] getSerialNumbers = null;

	@Autowired
	private CustomerContactDetailsServiceInt contactDetailsServiceInt;
	@Autowired
	private HttpSession session = null;

	@SuppressWarnings("unused")
	private Customer customer = null;
	private ModelAndView model = null;
	private Employee userName = null;
	private String retMessage = "";
	public String[] getSerials = null;
	private String customerName, technicianName, technicianEmail,
			selectedDateRange, heading, machineType = null;
	public String[] getOrdersNumbers = null;
	// Retrieve String All technicians if selected on front end
	public String allTechniciansSelected = null;
	public Long ticketNumber;
	private Employee tempEmployee = null;

	// technician dash board
	@RequestMapping("technicianDashboard")
	public ModelAndView bridgedTicketsForTech() {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("shipment",
					ordersServiceInt.shippedOrders(userName.getEmail()));
			model.addObject("ticketList",
					logTicketService.getAllBridgedTickets());
			model.addObject("countOpenTickets", ticketsServiceInt
					.getTicketCount("Open", "Last 14 Days", "", "", 0L));
			model.addObject("countAcknowledgedTickets", ticketsServiceInt
					.getTicketCount("Acknowledged", "Last 14 Days", "", "", 0L));
			model.addObject("countTakenTickets", ticketsServiceInt
					.getTicketCount("Taken", "Last 14 Days", "", "", 0L));
			model.addObject("countEscalatedTickets", ticketsServiceInt
					.getTicketCount("Escalated", "Last 14 Days", "", "", 0L));
			model.addObject("countAwaitingSparesTickets", ticketsServiceInt
					.getTicketCount("Awaiting Spares", "Last 14 Days", "", "",
							0L));
			model.addObject("countBridgedTickets", ticketsServiceInt
					.getTicketCount("SLA Bridged", "Last 14 Days", "", "", 0L));
			model.addObject("countResolvedTickets", ticketsServiceInt
					.getTicketCount("Resolved", "Last 14 Days", "", "", 0L));
			model.addObject("countClosedTickets", ticketsServiceInt
					.getTicketCount("Closed", "Last 14 Days", "", "", 0L));
			model.addObject("ticketNumbers",
					ticketsServiceInt.getTicketNumbers());
			model.addObject("technicians",
					employeeServiceInt.getAllTechnicians());
			model.setViewName("technicianDashboard");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	// ticket management for manager, admin and user
	@RequestMapping(value = { "ticketmanagement", "techticketmanagement",
			"userticketmanagement" }, method = RequestMethod.GET)
	public ModelAndView displayTicketManagement() {

		heading = "All Tickets";
		selectedDateRange = "Select Date";
		customerName = "All Customers";
		technicianEmail = "All Technicians";

		allTechniciansSelected = null;
		technicianName = null;
		tempEmployee = null;
		ticketNumber = null;

		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| (userName.getRole().equalsIgnoreCase("Admin"))) {

				model.addObject("lastForteenList", ticketsServiceInt
						.getTicketListForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.addObject("countOpenTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "Open"));
				model.addObject("countAcknowledgedTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "Acknowledged"));
				model.addObject("countTakenTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "Taken"));
				model.addObject("countEscalatedTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "Escalated"));
				model.addObject("countAwaitingSparesTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "Awaiting Spares"));
				model.addObject("countBridgedTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "SLA Bridged"));
				model.addObject("countResolvedTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "Resolved"));
				model.addObject("countClosedTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "Closed"));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("newDate", selectedDateRange);

				if (technicianEmail != "All Technicians") {
					model.addObject("selectedTechnician",
							employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

				}

				model.addObject("selectedName", customerName);
				model.addObject("newDate", selectedDateRange);
				model.addObject("heading", heading);

				model.setViewName("ticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				model.addObject("lastForteenList", ticketsServiceInt
						.getTicketListByTechnicianEmail(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("ticketNumbers", ticketsServiceInt
						.getTicketNumbersForTech(userName.getEmail()));

				model.addObject("countOpenTickets", ticketsServiceInt
						.getTicketCountForTechnician(customerName,
								selectedDateRange, userName.getEmail(),
								ticketNumber, "Open"));
				model.addObject("countAcknowledgedTickets", ticketsServiceInt
						.getTicketCountForTechnician(customerName,
								selectedDateRange, userName.getEmail(),
								ticketNumber, "Acknowledged"));
				model.addObject("countTakenTickets", ticketsServiceInt
						.getTicketCountForTechnician(customerName,
								selectedDateRange, userName.getEmail(),
								ticketNumber, "Taken"));
				model.addObject("countEscalatedTickets", ticketsServiceInt
						.getTicketCountForTechnician(customerName,
								selectedDateRange, userName.getEmail(),
								ticketNumber, "Escalated"));
				model.addObject("countAwaitingSparesTickets", ticketsServiceInt
						.getTicketCountForTechnician(customerName,
								selectedDateRange, userName.getEmail(),
								ticketNumber, "Awaiting Spares"));
				model.addObject("countBridgedTickets", ticketsServiceInt
						.getTicketCountForTechnician(customerName,
								selectedDateRange, userName.getEmail(),
								ticketNumber, "SLA Bridged"));
				model.addObject("countResolvedTickets", ticketsServiceInt
						.getTicketCountForTechnician(customerName,
								selectedDateRange, userName.getEmail(),
								ticketNumber, "Resolved"));
				model.addObject("countClosedTickets", ticketsServiceInt
						.getTicketCountForTechnician(customerName,
								selectedDateRange, userName.getEmail(),
								ticketNumber, "Closed"));

				if (technicianEmail != "All Technicians") {
					model.addObject("selectedTechnician",
							employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

				}

				model.addObject("selectedName", customerName);
				model.addObject("newDate", selectedDateRange);
				model.addObject("heading", heading);

				model.setViewName("techticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				model.addObject("lastForteenList", ticketsServiceInt
						.getTicketListForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.addObject("countOpenTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "Open"));
				model.addObject("countAcknowledgedTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "Acknowledged"));
				model.addObject("countTakenTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "Taken"));
				model.addObject("countEscalatedTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "Escalated"));
				model.addObject("countAwaitingSparesTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "Awaiting Spares"));
				model.addObject("countBridgedTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "SLA Bridged"));
				model.addObject("countResolvedTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "Resolved"));
				model.addObject("countClosedTickets", ticketsServiceInt
						.getTicketCountForManager(customerName,
								selectedDateRange, technicianEmail,
								ticketNumber, "Closed"));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("newDate", selectedDateRange);

				if (technicianEmail != "All Technicians") {
					model.addObject("selectedTechnician",
							employeeDaoInt.getEmployeeByEmpNum(technicianEmail));
				}

				model.addObject("selectedName", customerName);
				model.addObject("newDate", selectedDateRange);
				model.addObject("heading", heading);

				model.setViewName("userticketmanagement");

			}
		} else {
			model.setViewName("login");
		}
		return model;
	}

	// ticket management details
	@RequestMapping(value = { "ticketItemDetailsM", "ticketItemDetailsT",
			"ticketItemDetailsU" }, method = RequestMethod.GET)
	public ModelAndView ticketItemDetailsM(
			@RequestParam("recordID") Long recordID,
			@ModelAttribute Tickets ticket) {
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {

				ticket = logTicketService
						.getLoggedTicketByTicketNumber(recordID);
				model.addObject("ticketObject", ticket);
				model.addObject("OrderNum", ordersServiceInt.getOrder(recordID));
				model.addObject("status",
						historyInt.getAllOrderHistoryByOrderNumber(recordID));
				model.addObject("saveSpareParts", new SparePartsBean());
				getSerials = spareMasterServiceInt.getSerials();
				model.addObject("spareParts", getSerials);
				model.addObject(
						"contactPerson",
						contactDetailsServiceInt.getContactPerson(ticket
								.getDevice().getCustomerDevice()
								.getCustomerName()));
				model.addObject("ticketHistoryList",
						ticketHistoryInt.getHistoryByTicketNumber(recordID));
				model.addObject("OrderNumber", ordersServiceInt
						.getAllOrders(ticket.getEmployee().getEmail()));
				String technician = ticket.getEmployee().getFirstName() + " "
						+ ticket.getEmployee().getLastName();
				model.addObject("onLeaveTechnicians",
						leaveInt.techniciansOnLeave());
				model.addObject("serialNumbers", getSerialNumbers);
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("reassignToTechnician",
						employeeServiceInt.reassignTechnicianList(ticket
								.getEmployee().getEmail()));
				model.addObject("managersList",
						employeeServiceInt.getAllManagers());
				model.addObject("customerList",
						customerServiceInt.getClientList());
				model.addObject("bootStock",
						bootStockint.getAllOrders(technician, recordID));
				model.addObject("siteStock", siteStock.getOrdersForCustomer(
						ticket.getDevice().getCustomerDevice()
								.getCustomerName(), recordID));
				model.setViewName("ticketItemDetailsM");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				String technician = userName.getFirstName() + " "
						+ userName.getLastName();
				ticket = logTicketService
						.getLoggedTicketByTicketNumber(recordID);
				model.addObject("ticketObject", ticket);
				model.addObject("OrderNum", ordersServiceInt.getOrder(recordID));
				model.addObject("status",
						historyInt.getAllOrderHistoryByOrderNumber(recordID));
				model.addObject("saveSpareParts", new SparePartsBean());
				getSerials = spareMasterServiceInt.getSerials();
				model.addObject("spareParts", getSerials);
				model.addObject(
						"contactPerson",
						contactDetailsServiceInt.getContactPerson(ticket
								.getDevice().getCustomerDevice()
								.getCustomerName()));
				model.addObject("ticketHistoryList",
						ticketHistoryInt.getHistoryByTicketNumber(recordID));
				model.addObject("OrderNumber",
						ordersServiceInt.getAllOrders(userName.getEmail()));
				model.addObject("onLeaveTechnicians",
						leaveInt.techniciansOnLeave());
				model.addObject("serialNumbers", getSerialNumbers);
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("reassignToTechnician",
						employeeServiceInt.reassignTechnicianList(ticket
								.getEmployee().getEmail()));
				model.addObject("managersList",
						employeeServiceInt.getAllManagers());
				model.addObject("customerList",
						customerServiceInt.getClientList());
				model.addObject("bootStock",
						bootStockint.getAllOrders(technician, recordID));
				model.addObject("siteStock", siteStock.getOrdersForCustomer(
						ticket.getDevice().getCustomerDevice()
								.getCustomerName(), recordID));
				model.setViewName("ticketItemDetailsT");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				ticket = logTicketService
						.getLoggedTicketByTicketNumber(recordID);
				model.addObject("ticketObject", ticket);
				model.addObject("OrderNum", ordersServiceInt.getOrder(recordID));
				model.addObject("status",
						historyInt.getAllOrderHistoryByOrderNumber(recordID));
				model.addObject("saveSpareParts", new SparePartsBean());
				getSerials = spareMasterServiceInt.getSerials();
				model.addObject("spareParts", getSerials);
				model.addObject(
						"contactPerson",
						contactDetailsServiceInt.getContactPerson(ticket
								.getDevice().getCustomerDevice()
								.getCustomerName()));
				model.addObject("ticketHistoryList",
						ticketHistoryInt.getHistoryByTicketNumber(recordID));
				model.addObject("OrderNumber", ordersServiceInt
						.getAllOrders(ticket.getEmployee().getEmail()));
				String technician = ticket.getEmployee().getFirstName() + " "
						+ ticket.getEmployee().getLastName();
				model.addObject("onLeaveTechnicians",
						leaveInt.techniciansOnLeave());
				model.addObject("serialNumbers", getSerialNumbers);
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("reassignToTechnician",
						employeeServiceInt.reassignTechnicianList(ticket
								.getEmployee().getEmail()));
				model.addObject("managersList",
						employeeServiceInt.getAllManagers());
				model.addObject("customerList",
						customerServiceInt.getClientList());
				model.addObject("bootStock",
						bootStockint.getAllOrders(technician, recordID));
				model.addObject("siteStock", siteStock.getOrdersForCustomer(
						ticket.getDevice().getCustomerDevice()
								.getCustomerName(), recordID));
				model.setViewName("ticketItemDetailsU");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	// Open Tickets	
	@RequestMapping(value = "openTickets", method = RequestMethod.GET)
		public ModelAndView openTickets() {

			heading = "Open";

			model = new ModelAndView();
			userName = (Employee) session.getAttribute("loggedInUser");
			if (userName != null) {

				if (userName.getRole().equalsIgnoreCase("Technician")) {

					if (customerName != null) {

						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForTech(customerName,
										selectedDateRange, userName.getEmail(),
										ticketNumber, "Open"));

						model.addObject("customers",
								customerServiceInt.getClientList());
						model.addObject("dates", ordersServiceInt.getDates());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForTechnician(customerName,
										selectedDateRange, userName.getEmail(),
										ticketNumber, "Acknowledged"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForTechnician(
										customerName, selectedDateRange,
										userName.getEmail(), ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForTechnician(customerName,
										selectedDateRange, userName.getEmail(),
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets", ticketsServiceInt
								.getTicketCountForTechnician(customerName,
										selectedDateRange, userName.getEmail(),
										ticketNumber, "Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForTechnician(
										customerName, selectedDateRange,
										userName.getEmail(), ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets", ticketsServiceInt
								.getTicketCountForTechnician(customerName,
										selectedDateRange, userName.getEmail(),
										ticketNumber, "SLA Bridged"));
						model.addObject("countResolvedTickets", ticketsServiceInt
								.getTicketCountForTechnician(customerName,
										selectedDateRange, userName.getEmail(),
										ticketNumber, "Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForTechnician(customerName,
										selectedDateRange, userName.getEmail(),
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject("selectedTechnician", employeeDaoInt
									.getEmployeeByEmpNum(userName.getEmail()));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);

					}

					model.addObject("ticketNumbers", ticketsServiceInt
							.getTicketNumbersForTech(userName.getEmail()));

					model.addObject("customers", customerServiceInt.getClientList());
					model.addObject("dates", ordersServiceInt.getDates());

					model.setViewName("techticketmanagement");

				} else if (userName.getRole().equalsIgnoreCase("Manager")
						|| userName.getRole().equalsIgnoreCase("Admin")) {
					if (customerName != null) {
						if (customerName.length() >= 2) {
							model.addObject("lastForteenList", ticketsServiceInt
									.getTicketListByStatusForManager(customerName,
											selectedDateRange, technicianEmail,
											ticketNumber, "Open"));
							model.addObject("ticketNumbers",
									ticketsServiceInt.getTicketNumbers());

							model.addObject("countOpenTickets", ticketsServiceInt
									.getTicketCountForManager(customerName,
											selectedDateRange, technicianEmail,
											ticketNumber, "Acknowledged"));
							model.addObject("countAcknowledgedTickets",
									ticketsServiceInt.getTicketCountForManager(
											customerName, selectedDateRange,
											technicianEmail, ticketNumber,
											"Acknowledged"));
							model.addObject("countTakenTickets", ticketsServiceInt
									.getTicketCountForManager(customerName,
											selectedDateRange, technicianEmail,
											ticketNumber, "Taken"));
							model.addObject("countEscalatedTickets",
									ticketsServiceInt.getTicketCountForManager(
											customerName, selectedDateRange,
											technicianEmail, ticketNumber,
											"Escalated"));
							model.addObject("countAwaitingSparesTickets",
									ticketsServiceInt.getTicketCountForManager(
											customerName, selectedDateRange,
											technicianEmail, ticketNumber,
											"Awaiting Spares"));
							model.addObject("countBridgedTickets",
									ticketsServiceInt.getTicketCountForManager(
											customerName, selectedDateRange,
											technicianEmail, ticketNumber,
											"SLA Bridged"));
							model.addObject("countResolvedTickets",
									ticketsServiceInt.getTicketCountForManager(
											customerName, selectedDateRange,
											technicianEmail, ticketNumber,
											"Resolved"));
							model.addObject("countClosedTickets", ticketsServiceInt
									.getTicketCountForManager(customerName,
											selectedDateRange, technicianEmail,
											ticketNumber, "Closed"));

							if (technicianEmail != "All Technicians") {
								model.addObject(
										"selectedTechnician",
										employeeDaoInt
												.getEmployeeByEmpNum(technicianEmail));
							}

							model.addObject("selectedName", customerName);
							model.addObject("newDate", selectedDateRange);
							model.addObject("heading", heading);

						}
					}
					model.addObject("pendingOrderList",
							ordersServiceInt.pendingOrders(userName.getEmail()));
					model.addObject("inboxCount", ordersServiceInt
							.pendingOrdersCount(userName.getEmail()));
					model.addObject("customers", customerServiceInt.getClientList());
					model.addObject("dates", ordersServiceInt.getDates());
					model.addObject("technicians",
							employeeServiceInt.getAllTechnicians());
					model.addObject("orderNumbers",
							ordersServiceInt.getOrderNumbers());
					model.addObject("ticketCount", ticketsServiceInt
							.ticketCountForTechnician(userName.getEmail()));
					model.addObject("ticketNumbers",
							ticketsServiceInt.getTicketNumbers());

					model.setViewName("ticketmanagement");
				} else if (userName.getRole().equalsIgnoreCase("User")) {
					if (customerName != null) {
						if (customerName.length() >= 2) {
							model.addObject("lastForteenList", ticketsServiceInt
									.getTicketListByStatusForManager(customerName,
											selectedDateRange, technicianEmail,
											ticketNumber, "Open"));
							model.addObject("ticketNumbers",
									ticketsServiceInt.getTicketNumbers());

							model.addObject("countOpenTickets", ticketsServiceInt
									.getTicketCountForManager(customerName,
											selectedDateRange, technicianEmail,
											ticketNumber, "Acknowledged"));
							model.addObject("countAcknowledgedTickets",
									ticketsServiceInt.getTicketCountForManager(
											customerName, selectedDateRange,
											technicianEmail, ticketNumber,
											"Acknowledged"));
							model.addObject("countTakenTickets", ticketsServiceInt
									.getTicketCountForManager(customerName,
											selectedDateRange, technicianEmail,
											ticketNumber, "Taken"));
							model.addObject("countEscalatedTickets",
									ticketsServiceInt.getTicketCountForManager(
											customerName, selectedDateRange,
											technicianEmail, ticketNumber,
											"Escalated"));
							model.addObject("countAwaitingSparesTickets",
									ticketsServiceInt.getTicketCountForManager(
											customerName, selectedDateRange,
											technicianEmail, ticketNumber,
											"Awaiting Spares"));
							model.addObject("countBridgedTickets",
									ticketsServiceInt.getTicketCountForManager(
											customerName, selectedDateRange,
											technicianEmail, ticketNumber,
											"SLA Bridged"));
							model.addObject("countResolvedTickets",
									ticketsServiceInt.getTicketCountForManager(
											customerName, selectedDateRange,
											technicianEmail, ticketNumber,
											"Resolved"));
							model.addObject("countClosedTickets", ticketsServiceInt
									.getTicketCountForManager(customerName,
											selectedDateRange, technicianEmail,
											ticketNumber, "Closed"));

							if (technicianEmail != "All Technicians") {
								model.addObject(
										"selectedTechnician",
										employeeDaoInt
												.getEmployeeByEmpNum(technicianEmail));
							}

							model.addObject("selectedName", customerName);
							model.addObject("newDate", selectedDateRange);
							model.addObject("heading", heading);

						}
					}
					model.addObject("customers", customerServiceInt.getClientList());
					model.addObject("technicians",
							employeeServiceInt.getAllTechnicians());
					model.addObject("orderNumbers",
							ordersServiceInt.getOrderNumbers());
					model.addObject("ticketCount", ticketsServiceInt
							.ticketCountForTechnician(userName.getEmail()));
					model.addObject("ticketNumbers",
							ticketsServiceInt.getTicketNumbers());

					model.setViewName("userticketmanagement");
				}
			} else {
				model.setViewName("login");
			}

			return model;
		}

	// Acknowledged Tickets
	@RequestMapping(value = "acknowledgedTickets", method = RequestMethod.GET)
	public ModelAndView acknowledgedTickets() {

		heading = "Acknowledged";

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (customerName != null) {

					model.addObject("lastForteenList", ticketsServiceInt
							.getTicketListByStatusForTech(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Acknowledged"));

					model.addObject("customers",
							customerServiceInt.getClientList());
					model.addObject("dates", ordersServiceInt.getDates());

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Open"));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Acknowledged"));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Taken"));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Escalated"));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Awaiting Spares"));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "SLA Bridged"));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Resolved"));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Closed"));

					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician", employeeDaoInt
								.getEmployeeByEmpNum(userName.getEmail()));
					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);

				}

				model.addObject("ticketNumbers", ticketsServiceInt
						.getTicketNumbersForTech(userName.getEmail()));

				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());

				model.setViewName("techticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (customerName != null) {
					if (customerName.length() >= 2) {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Acknowledged"));
						model.addObject("ticketNumbers",
								ticketsServiceInt.getTicketNumbers());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Open"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"SLA Bridged"));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject(
									"selectedTechnician",
									employeeDaoInt
											.getEmployeeByEmpNum(technicianEmail));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);

					}
				}
				model.addObject("pendingOrderList",
						ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("inboxCount", ordersServiceInt
						.pendingOrdersCount(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());

				model.setViewName("ticketmanagement");
			} else if (userName.getRole().equalsIgnoreCase("User")) {
				if (customerName != null) {
					if (customerName.length() >= 2) {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Acknowledged"));
						model.addObject("ticketNumbers",
								ticketsServiceInt.getTicketNumbers());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Open"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"SLA Bridged"));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject(
									"selectedTechnician",
									employeeDaoInt
											.getEmployeeByEmpNum(technicianEmail));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);

					}
				}
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());

				model.setViewName("userticketmanagement");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	// Taken Tickets
	@RequestMapping(value = "takenTickets", method = RequestMethod.GET)
	public ModelAndView takenTickets() {

		heading = "Taken";

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (customerName != null) {

					model.addObject("lastForteenList", ticketsServiceInt
							.getTicketListByStatusForTech(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Taken"));

					model.addObject("customers",
							customerServiceInt.getClientList());
					model.addObject("dates", ordersServiceInt.getDates());

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Open"));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Acknowledged"));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Taken"));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Escalated"));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Awaiting Spares"));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "SLA Bridged"));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Resolved"));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Closed"));

					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician", employeeDaoInt
								.getEmployeeByEmpNum(userName.getEmail()));
					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);

				}

				model.addObject("ticketNumbers", ticketsServiceInt
						.getTicketNumbersForTech(userName.getEmail()));

				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());

				model.setViewName("techticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (customerName != null) {
					if (customerName.length() >= 2) {

						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("ticketNumbers",
								ticketsServiceInt.getTicketNumbers());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Open"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"SLA Bridged"));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject(
									"selectedTechnician",
									employeeDaoInt
											.getEmployeeByEmpNum(technicianEmail));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);

					}

				}

				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());

				model.setViewName("ticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				if (customerName != null) {
					if (customerName.length() >= 2) {

						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("ticketNumbers",
								ticketsServiceInt.getTicketNumbers());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Open"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"SLA Bridged"));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject(
									"selectedTechnician",
									employeeDaoInt
											.getEmployeeByEmpNum(technicianEmail));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);

					}
				}
				model.addObject("pendingOrderList",
						ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("inboxCount", ordersServiceInt
						.pendingOrdersCount(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("userticketmanagement");
			}
		}

		else {
			model.setViewName("login");
		}

		return model;
	}

	// Tickets Awaiting Spares
	@RequestMapping(value = "ticketsAwaitingSpares", method = RequestMethod.GET)
	public ModelAndView ticketsAwaitingSpares() {

		heading = "Awaiting Spares";

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (customerName != null) {

					model.addObject("lastForteenList", ticketsServiceInt
							.getTicketListByStatusForTech(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Awaiting Spares"));

					model.addObject("customers",
							customerServiceInt.getClientList());
					model.addObject("dates", ordersServiceInt.getDates());

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Open"));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Acknowledged"));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Taken"));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Escalated"));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Awaiting Spares"));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "SLA Bridged"));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Resolved"));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Closed"));

					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician", employeeDaoInt
								.getEmployeeByEmpNum(userName.getEmail()));
					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);

				}

				model.addObject("ticketNumbers", ticketsServiceInt
						.getTicketNumbersForTech(userName.getEmail()));

				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());

				model.setViewName("techticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (customerName != null) {
					if (customerName.length() >= 2) {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Awaiting Spares"));
						model.addObject("ticketNumbers",
								ticketsServiceInt.getTicketNumbers());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Open"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"SLA Bridged"));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject(
									"selectedTechnician",
									employeeDaoInt
											.getEmployeeByEmpNum(technicianEmail));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);

					}
				}
				model.addObject("pendingOrderList",
						ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("inboxCount", ordersServiceInt
						.pendingOrdersCount(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());

				model.setViewName("ticketmanagement");
			} else if (userName.getRole().equalsIgnoreCase("User")) {

				if (customerName != null) {
					if (customerName.length() >= 2) {

						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Awaiting Spares"));
						model.addObject("ticketNumbers",
								ticketsServiceInt.getTicketNumbers());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Open"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"SLA Bridged"));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject(
									"selectedTechnician",
									employeeDaoInt
											.getEmployeeByEmpNum(technicianEmail));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);
					}

				}
				model.addObject("pendingOrderList",
						ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("inboxCount", ordersServiceInt
						.pendingOrdersCount(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("userticketmanagement");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	// Escalated Tickets
	@RequestMapping(value = "escalatedTickets", method = RequestMethod.GET)
	public ModelAndView escalatedTickets() {

		heading = "Escalated";

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (customerName != null) {

					model.addObject("lastForteenList", ticketsServiceInt
							.getTicketListByStatusForTech(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Escalated"));

					model.addObject("customers",
							customerServiceInt.getClientList());
					model.addObject("dates", ordersServiceInt.getDates());

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Open"));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Acknowledged"));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Taken"));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Escalated"));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Awaiting Spares"));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "SLA Bridged"));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Resolved"));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Closed"));

					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician", employeeDaoInt
								.getEmployeeByEmpNum(userName.getEmail()));
					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);

				}

				model.addObject("ticketNumbers", ticketsServiceInt
						.getTicketNumbersForTech(userName.getEmail()));

				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());

				model.setViewName("techticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (customerName != null) {
					if (customerName.length() >= 2) {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Escalated"));
						model.addObject("ticketNumbers",
								ticketsServiceInt.getTicketNumbers());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Open"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"SLA Bridged"));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject(
									"selectedTechnician",
									employeeDaoInt
											.getEmployeeByEmpNum(technicianEmail));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);

					}

				}
				model.addObject("pendingOrderList",
						ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("inboxCount", ordersServiceInt
						.pendingOrdersCount(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("ticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				if (customerName != null) {
					if (customerName.length() >= 2) {

						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Escalated"));
						model.addObject("ticketNumbers",
								ticketsServiceInt.getTicketNumbers());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Open"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"SLA Bridged"));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject(
									"selectedTechnician",
									employeeDaoInt
											.getEmployeeByEmpNum(technicianEmail));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);

					}
				}
				model.addObject("pendingOrderList",
						ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("inboxCount", ordersServiceInt
						.pendingOrdersCount(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("userticketmanagement");
			}
		} else {
			model.setViewName("login");
		}
		return model;
	}

	// Resolved Tickets
	@RequestMapping(value = "resolvedTickets", method = RequestMethod.GET)
	public ModelAndView resolvedTickets() {
		heading = "Resolved";

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (customerName != null) {

					model.addObject("lastForteenList", ticketsServiceInt
							.getTicketListByStatusForTech(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Resolved"));

					model.addObject("customers",
							customerServiceInt.getClientList());
					model.addObject("dates", ordersServiceInt.getDates());

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Open"));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Acknowledged"));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Taken"));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Escalated"));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Awaiting Spares"));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "SLA Bridged"));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Resolved"));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Closed"));

					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician", employeeDaoInt
								.getEmployeeByEmpNum(userName.getEmail()));
					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);

				}

				model.addObject("ticketNumbers", ticketsServiceInt
						.getTicketNumbersForTech(userName.getEmail()));

				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());

				model.setViewName("techticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (customerName != null) {
					if (customerName.length() >= 2) {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Resolved"));
						model.addObject("ticketNumbers",
								ticketsServiceInt.getTicketNumbers());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Open"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"SLA Bridged"));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject(
									"selectedTechnician",
									employeeDaoInt
											.getEmployeeByEmpNum(technicianEmail));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);

					}
				}
				model.addObject("pendingOrderList",
						ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("inboxCount", ordersServiceInt
						.pendingOrdersCount(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("ticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				if (customerName != null) {
					if (customerName.length() >= 2) {

						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Resolved"));
						model.addObject("ticketNumbers",
								ticketsServiceInt.getTicketNumbers());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Open"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"SLA Bridged"));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject(
									"selectedTechnician",
									employeeDaoInt
											.getEmployeeByEmpNum(technicianEmail));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);

					}
				}
				model.addObject("pendingOrderList",
						ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("inboxCount", ordersServiceInt
						.pendingOrdersCount(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("userticketmanagement");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	// SLA Bridged Tickets
	@RequestMapping(value = "sLABridgedTickets", method = RequestMethod.GET)
	public ModelAndView sLABridgedTickets() {

		heading = "SLA Bridged";

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (customerName != null) {
					model.addObject("lastForteenList", ticketsServiceInt
							.getTicketListByStatusForTech(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "SLA Bridged"));

					model.addObject("customers",
							customerServiceInt.getClientList());
					model.addObject("dates", ordersServiceInt.getDates());

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Open"));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Acknowledged"));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Taken"));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Escalated"));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Awaiting Spares"));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "SLA Bridged"));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Resolved"));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Closed"));

					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician", employeeDaoInt
								.getEmployeeByEmpNum(userName.getEmail()));
					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);

				}
				model.addObject("ticketNumbers", ticketsServiceInt
						.getTicketNumbersForTech(userName.getEmail()));

				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());

				model.setViewName("techticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (customerName != null) {
					if (customerName.length() >= 2) {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "SLA Bridged"));
						model.addObject("ticketNumbers",
								ticketsServiceInt.getTicketNumbers());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Open"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"SLA Bridged"));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject(
									"selectedTechnician",
									employeeDaoInt
											.getEmployeeByEmpNum(technicianEmail));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);

					}
				}
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("ticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				if (customerName != null) {
					if (customerName.length() >= 2) {

						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "SLA Bridged"));
						model.addObject("ticketNumbers",
								ticketsServiceInt.getTicketNumbers());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Open"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"SLA Bridged"));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject(
									"selectedTechnician",
									employeeDaoInt
											.getEmployeeByEmpNum(technicianEmail));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);
					}
				}
				model.addObject("pendingOrderList",
						ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("inboxCount", ordersServiceInt
						.pendingOrdersCount(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("userticketmanagement");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	// Closed Tickets
	@RequestMapping(value = "closedTickets", method = RequestMethod.GET)
	public ModelAndView forClosedTickets() {

		heading = "Closed";

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (customerName != null) {

					model.addObject("lastForteenList", ticketsServiceInt
							.getTicketListByStatusForTech(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Closed"));

					model.addObject("customers",
							customerServiceInt.getClientList());
					model.addObject("dates", ordersServiceInt.getDates());

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Open"));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Acknowledged"));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Taken"));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Escalated"));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Awaiting Spares"));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "SLA Bridged"));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Resolved"));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Closed"));

					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician", employeeDaoInt
								.getEmployeeByEmpNum(userName.getEmail()));
					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);

				}
				model.addObject("ticketNumbers", ticketsServiceInt
						.getTicketNumbersForTech(userName.getEmail()));

				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());

				model.setViewName("techticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {

				if (customerName != null) {
					if (customerName.length() >= 2) {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));
						model.addObject("ticketNumbers",
								ticketsServiceInt.getTicketNumbers());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Open"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"SLA Bridged"));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject(
									"selectedTechnician",
									employeeDaoInt
											.getEmployeeByEmpNum(technicianEmail));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);

					}
				}
				model.addObject("pendingOrderList",
						ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("inboxCount", ordersServiceInt
						.pendingOrdersCount(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("ticketmanagement");
			} else if (userName.getRole().equalsIgnoreCase("User")) {

				if (customerName != null) {
					if (customerName.length() >= 2) {

						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByStatusForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));
						model.addObject("ticketNumbers",
								ticketsServiceInt.getTicketNumbers());

						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Open"));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Acknowledged"));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Taken"));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Escalated"));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Awaiting Spares"));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"SLA Bridged"));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCountForManager(
										customerName, selectedDateRange,
										technicianEmail, ticketNumber,
										"Resolved"));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCountForManager(customerName,
										selectedDateRange, technicianEmail,
										ticketNumber, "Closed"));

						if (technicianEmail != "All Technicians") {
							model.addObject(
									"selectedTechnician",
									employeeDaoInt
											.getEmployeeByEmpNum(technicianEmail));
						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);

					}

				}
				model.addObject("pendingOrderList",
						ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("inboxCount", ordersServiceInt
						.pendingOrdersCount(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("userticketmanagement");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	// acknowledge Tickets For Technician
	@RequestMapping(value = "acknowledgeTicketsForTech")
	public ModelAndView acknowledgeTicketsForTech(
			@RequestParam("recordID") Long recordID) {
		model = new ModelAndView();

		String acknowledgeTicketsForTech = "acknowledgeTicketsForTech";

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			
			if (userName.getRole().equalsIgnoreCase("Manager") || (userName.getRole().equalsIgnoreCase("Admin"))){

				model.addObject("retMessage",ticketsServiceInt.acknowledgeTicketsForTech(recordID));
				model.addObject("acknowledgeTicketsForTech",acknowledgeTicketsForTech);
	
				model.setViewName("confirmations");
				
			}else if (userName.getRole().equalsIgnoreCase("User")) {
				
				model.addObject("retMessage",ticketsServiceInt.acknowledgeTicketsForTech(recordID));
				model.addObject("acknowledgeTicketsForTech",acknowledgeTicketsForTech);
	
				model.setViewName("confirm");
			}
		} else {
			model.setViewName("login");
		}
		return model;
	}

	// take Tickets For Technician
	@RequestMapping(value = "takeTicketsForTech")
	public ModelAndView takeTicketsForTech(
			@RequestParam("recordID") Long recordID) {
		model = new ModelAndView();

		String takeTicketsForTech = "takeTicketsForTech";

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if(userName.getRole().equalsIgnoreCase("Manager") || (userName.getRole().equalsIgnoreCase("Admin"))){
				model.addObject("retMessage",
						ticketsServiceInt.takeTicketsForTech(recordID));
				model.addObject("takeTicketsForTech", takeTicketsForTech);

				model.setViewName("confirmations");
			}else if(userName.getRole().equalsIgnoreCase("User")){
				model.addObject("retMessage",
						ticketsServiceInt.takeTicketsForTech(recordID));
				model.addObject("takeTicketsForTech", takeTicketsForTech);

				model.setViewName("confirm");
			}
			
		} else {
			model.setViewName("login");
		}
		return model;
	}

	// Re-assign ticket
	@RequestMapping(value = "reAssign")
	public ModelAndView reAssign(@RequestParam("recordID") Long recordID) {
		model = new ModelAndView();

		String reAssign = "reAssign";

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage",ordersServiceInt.approveShipment(recordID));			
			model.addObject("reAssign", reAssign);

			model.setViewName("confirmation");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	// Re-open ticket
	@RequestMapping(value = "reOpen")
	public ModelAndView reOpen(@RequestParam("recordID") Long recordID) {
		model = new ModelAndView();

		String reOpen = "reOpen";

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage",
					ordersServiceInt.approveShipment(recordID));
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("reOpen", reOpen);

			model.setViewName("confirmation");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	// search ticket by customer
	@RequestMapping(value = "getTicketByCustomer", method = RequestMethod.GET)
	public ModelAndView getCustomerName(
			@RequestParam("customerName") String localCustomerName) {
		customerName = localCustomerName;
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (customerName != null) {

					model.addObject("selectedName", customerName);
					if (selectedDateRange != null && technicianName != null) {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketByDateAndCustomerForManager(
										selectedDateRange, localCustomerName,
										technicianName));
					} else {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByCustomerNameForTech(
										customerName, userName.getEmail()));
					}

					model.addObject("customers",
							customerServiceInt.getClientList());
					model.addObject("dates", ordersServiceInt.getDates());

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "Open"));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Acknowledged"));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "Taken"));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Escalated"));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Awaiting Spares"));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "SLA Bridged"));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "Resolved"));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "Closed"));

				}

				model.addObject("ticketNumbers", ticketsServiceInt
						.getTicketNumbersForTech(userName.getEmail()));

				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());

				model.setViewName("techticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (customerName != null) {
					if (customerName.length() >= 2) {
						model.addObject("selectedName", customerName);
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByCustomerName(customerName));
						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCount("Open", "", "", customerName,
										0L));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCount(
										"Acknowledged", "", "", customerName,
										0L));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCount("Taken", "", "", customerName,
										0L));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCount("Escalated",
										"", "", customerName, 0L));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCount(
										"Awaiting Spares", "", "",
										customerName, 0L));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCount("SLA Bridged",
										"", "", customerName, 0L));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCount("Resolved",
										"", "", customerName, 0L));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCount("Closed", "", "", customerName,
										0L));
					}
				}
				model.addObject("lastForteenList", ticketsServiceInt
						.getTicketListByCustomerName(customerName));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("ticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				model.setViewName("userticketmanagement");
			}

			else {
				model.setViewName("login");
			}
		}

		return model;
	}

	// search ticket by selected date
	@RequestMapping(value = "getTicketBySelectedDate", method = RequestMethod.GET)
	public ModelAndView getTicketBySelectedDate(
			@RequestParam("selectedDate") String localSelectedDate) {
		selectedDateRange = localSelectedDate;
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (selectedDateRange != null) {
					model.addObject("newDate", selectedDateRange);
					model.addObject("lastForteenList", ticketsServiceInt
							.getTicketListByStatusForTech(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Closed"));

					model.addObject("customers",
							customerServiceInt.getClientList());
					model.addObject("dates", ordersServiceInt.getDates());

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Open"));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Acknowledged"));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Taken"));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Escalated"));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Awaiting Spares"));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "SLA Bridged"));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Resolved"));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Closed"));

				}

				model.addObject("ticketNumbers", ticketsServiceInt
						.getTicketNumbersForTech(userName.getEmail()));

				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());

				model.setViewName("techticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (selectedDateRange != null) {
					model.addObject("newDate", selectedDateRange);

					if (customerName != null && technicianName != null) {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketByDateAndCustomerForManager(
										selectedDateRange, customerName,
										technicianName));
					} else {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByDateRange(selectedDateRange));
					}

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCount("Open", selectedDateRange, "", "",
									0L));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCount("Acknowledged",
									selectedDateRange, "", "", 0L));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCount("Taken", selectedDateRange, "", "",
									0L));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCount("Escalated", selectedDateRange, "",
									"", 0L));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCount("Awaiting Spares",
									selectedDateRange, "", "", 0L));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCount("SLA Bridged", selectedDateRange,
									"", "", 0L));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCount("Resolved", selectedDateRange, "",
									"", 0L));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCount("Closed", selectedDateRange, "",
									"", 0L));

				}
				model.addObject("newDate", selectedDateRange);
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("ticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				if (selectedDateRange != null) {
					model.addObject("newDate", selectedDateRange);

					if (customerName != null && technicianName != null) {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketByDateAndCustomerForManager(
										selectedDateRange, customerName,
										technicianName));
					} else {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByDateRange(selectedDateRange));
					}

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCount("Open", selectedDateRange, "", "",
									0L));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCount("Acknowledged",
									selectedDateRange, "", "", 0L));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCount("Taken", selectedDateRange, "", "",
									0L));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCount("Escalated", selectedDateRange, "",
									"", 0L));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCount("Awaiting Spares",
									selectedDateRange, "", "", 0L));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCount("SLA Bridged", selectedDateRange,
									"", "", 0L));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCount("Resolved", selectedDateRange, "",
									"", 0L));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCount("Closed", selectedDateRange, "",
									"", 0L));

				}
				model.addObject("newDate", selectedDateRange);
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("userticketmanagement");
			}

			else {
				model.setViewName("login");
			}
		}

		return model;
	}

	// Search ticket by technician
	@RequestMapping(value = "getTicketByTechnician", method = RequestMethod.GET)
	public ModelAndView getTicketByTechnician(
			@RequestParam("technicianName") String localTechnicianName) {
		technicianName = localTechnicianName;
		tempEmployee = employeeServiceInt
				.getEmployeeByEmpNumber(localTechnicianName);
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (tempEmployee != null) {
					if (selectedDateRange != null && technicianName != null) {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketByDateAndCustomerForManager(
										selectedDateRange, customerName,
										tempEmployee.getEmail()));
					} else {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByTechnicianEmail(tempEmployee
										.getEmail()));
					}

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCount("Open", "",
									tempEmployee.getEmail(), "", 0L));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCount("Acknowledged",
									"", tempEmployee.getEmail(), "", 0L));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCount("Taken", "",
									tempEmployee.getEmail(), "", 0L));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCount("Escalated", "",
									tempEmployee.getEmail(), "", 0L));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCount("Awaiting Spares",
									"", tempEmployee.getEmail(), "", 0L));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCount("SLA Bridged", "",
									tempEmployee.getEmail(), "", 0L));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCount("Resolved", "",
									tempEmployee.getEmail(), "", 0L));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCount("Closed", "",
									tempEmployee.getEmail(), "", 0L));
					model.addObject("selectedTechnician", tempEmployee);
				}

				else if (localTechnicianName != null) {

					if (localTechnicianName.equalsIgnoreCase("All Technicians")) {
						model.addObject(
								"lastForteenList",
								ticketsServiceInt
										.getTicketListByTechnicianEmail("All Technicians"));
						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCount("Open", "", "All Technicians",
										"", 0L));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCount(
										"Acknowledged", "", "All Technicians",
										"", 0L));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCount("Taken", "", "All Technicians",
										"", 0L));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCount("Escalated",
										"", "All Technicians", "", 0L));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCount(
										"Awaiting Spares", "",
										"All Technicians", "", 0L));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCount("SLA Bridged",
										"", "All Technicians", "", 0L));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCount("Resolved",
										"", "All Technicians", "", 0L));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCount("Closed", "",
										"All Technicians", "", 0L));

						// Get the value "All Technicians"
						allTechniciansSelected = localTechnicianName;

					}

				}
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());

				model.setViewName("ticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				if (tempEmployee != null) {
					if (selectedDateRange != null && technicianName != null) {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketByDateAndCustomerForManager(
										selectedDateRange, customerName,
										tempEmployee.getEmail()));
					} else {
						model.addObject("lastForteenList", ticketsServiceInt
								.getTicketListByTechnicianEmail(tempEmployee
										.getEmail()));
					}

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCount("Open", "",
									tempEmployee.getEmail(), "", 0L));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCount("Acknowledged",
									"", tempEmployee.getEmail(), "", 0L));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCount("Taken", "",
									tempEmployee.getEmail(), "", 0L));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCount("Escalated", "",
									tempEmployee.getEmail(), "", 0L));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCount("Awaiting Spares",
									"", tempEmployee.getEmail(), "", 0L));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCount("SLA Bridged", "",
									tempEmployee.getEmail(), "", 0L));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCount("Resolved", "",
									tempEmployee.getEmail(), "", 0L));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCount("Closed", "",
									tempEmployee.getEmail(), "", 0L));
					model.addObject("selectedTechnician", tempEmployee);
				}

				else if (localTechnicianName != null) {

					if (localTechnicianName.equalsIgnoreCase("All Technicians")) {
						model.addObject(
								"lastForteenList",
								ticketsServiceInt
										.getTicketListByTechnicianEmail("All Technicians"));
						model.addObject("countOpenTickets", ticketsServiceInt
								.getTicketCount("Open", "", "All Technicians",
										"", 0L));
						model.addObject("countAcknowledgedTickets",
								ticketsServiceInt.getTicketCount(
										"Acknowledged", "", "All Technicians",
										"", 0L));
						model.addObject("countTakenTickets", ticketsServiceInt
								.getTicketCount("Taken", "", "All Technicians",
										"", 0L));
						model.addObject("countEscalatedTickets",
								ticketsServiceInt.getTicketCount("Escalated",
										"", "All Technicians", "", 0L));
						model.addObject("countAwaitingSparesTickets",
								ticketsServiceInt.getTicketCount(
										"Awaiting Spares", "",
										"All Technicians", "", 0L));
						model.addObject("countBridgedTickets",
								ticketsServiceInt.getTicketCount("SLA Bridged",
										"", "All Technicians", "", 0L));
						model.addObject("countResolvedTickets",
								ticketsServiceInt.getTicketCount("Resolved",
										"", "All Technicians", "", 0L));
						model.addObject("countClosedTickets", ticketsServiceInt
								.getTicketCount("Closed", "",
										"All Technicians", "", 0L));

						// Get the value "All Technicians"
						allTechniciansSelected = localTechnicianName;

					}

				}
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("userticketmanagement");

			} else {
				model.setViewName("login");
			}
		}

		return model;
	}

	// search by ticket number
	@RequestMapping(value = "searchTicket", method = RequestMethod.POST)
	public ModelAndView searchTicket(
			@RequestParam("ticketNumber") String localTicketNumber,
			@RequestParam("customerName") String localCustomerName,
			@RequestParam("technicianName") String localTechnicianName,
			@RequestParam("selectDateRange") String localSelectDateRange) {

		heading = "All Tickets";
		selectedDateRange = localSelectDateRange;
		customerName = localCustomerName;
		technicianEmail = localTechnicianName;
		ticketNumber = null;

		if (localTicketNumber != null) {
			if (localTicketNumber.length() > 4) {
				ticketNumber = ticketsServiceInt
						.convertTicketToLong(localTicketNumber);
			}
		}
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (customerName != null) {
					model.addObject("lastForteenList", ticketsServiceInt
							.getTicketListForManager(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber));
					model.addObject("ticketNumbers",
							ticketsServiceInt.getTicketNumbers());

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCountForManager(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "Open"));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCountForManager(
									customerName, selectedDateRange,
									technicianEmail, ticketNumber,
									"Acknowledged"));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCountForManager(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "Taken"));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCountForManager(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "Escalated"));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCountForManager(
									customerName, selectedDateRange,
									technicianEmail, ticketNumber,
									"Awaiting Spares"));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCountForManager(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "SLA Bridged"));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCountForManager(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "Resolved"));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCountForManager(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "Closed"));

					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician", employeeDaoInt
								.getEmployeeByEmpNum(technicianEmail));

					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);

				}

				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("ticketmanagement");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				if (customerName != null) {
					model.addObject("lastForteenList", ticketsServiceInt
							.getTicketListForManager(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber));
					model.addObject("ticketNumbers",
							ticketsServiceInt.getTicketNumbers());

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCountForManager(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "Open"));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCountForManager(
									customerName, selectedDateRange,
									technicianEmail, ticketNumber,
									"Acknowledged"));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCountForManager(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "Taken"));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCountForManager(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "Escalated"));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCountForManager(
									customerName, selectedDateRange,
									technicianEmail, ticketNumber,
									"Awaiting Spares"));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCountForManager(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "SLA Bridged"));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCountForManager(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "Resolved"));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCountForManager(customerName,
									selectedDateRange, technicianEmail,
									ticketNumber, "Closed"));

					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician", employeeDaoInt
								.getEmployeeByEmpNum(technicianEmail));

					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);
				}

				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers",
						ordersServiceInt.getOrderNumbers());
				model.addObject("ticketNumbers",
						ticketsServiceInt.getTicketNumbers());
				model.setViewName("userticketmanagement");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "searchTicketForTech", method = RequestMethod.POST)
	public ModelAndView searchTicketForTech(
			@RequestParam("ticketNumber") String localTicketNumber,
			@RequestParam("customerName") String localCustomerName,
			@RequestParam("selectDateRange") String localSelectDateRange) {
		heading = "All Tickets";
		selectedDateRange = localSelectDateRange;
		customerName = localCustomerName;
		tempEmployee = null;

		ticketNumber = null;

		if (localTicketNumber != null) {
			if (localTicketNumber.length() > 4) {
				ticketNumber = ticketsServiceInt
						.convertTicketToLong(localTicketNumber);
			}
		}

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Technician")) {
				if (customerName != null) {

					model.addObject("lastForteenList", ticketsServiceInt
							.getTicketListForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber));

					model.addObject("customers",
							customerServiceInt.getClientList());
					model.addObject("dates", ordersServiceInt.getDates());

					model.addObject("countOpenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Open"));
					model.addObject("countAcknowledgedTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Acknowledged"));
					model.addObject("countTakenTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Taken"));
					model.addObject("countEscalatedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Escalated"));
					model.addObject("countAwaitingSparesTickets",
							ticketsServiceInt.getTicketCountForTechnician(
									customerName, selectedDateRange,
									userName.getEmail(), ticketNumber,
									"Awaiting Spares"));
					model.addObject("countBridgedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "SLA Bridged"));
					model.addObject("countResolvedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Resolved"));
					model.addObject("countClosedTickets", ticketsServiceInt
							.getTicketCountForTechnician(customerName,
									selectedDateRange, userName.getEmail(),
									ticketNumber, "Closed"));

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);
				}
				model.addObject("ticketNumbers", ticketsServiceInt
						.getTicketNumbersForTech(userName.getEmail()));

				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());

				model.setViewName("techticketmanagement");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	// get ticket page
	@RequestMapping(value = "ticket", method = RequestMethod.GET)
	public ModelAndView loadTicket() {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("logTicket", new TicketsBean());
			getSerialNumbers = deviceServiceInt.getSerials();
			model.addObject("technicians",
					employeeServiceInt.getAllTechnicians());
			model.addObject("serialNumbers", getSerialNumbers);
			model.addObject("onLeaveTechnicians", leaveInt.techniciansOnLeave());
			model.setViewName("ticket");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	// Log call as a Manager || Admin
	@RequestMapping(value = "logTicketAdmin", method = RequestMethod.POST)
	public ModelAndView logTicketAdmin(
			@ModelAttribute("logTicketAdmin") TicketsBean logTickets) {

		model = new ModelAndView();
		String tickets = "tickets";
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			retMessage = logTicketService.logTicket(logTickets);
			if (retMessage.startsWith("K")) {
				model.addObject("retMessage", retMessage);
			} else {
				model.addObject("retMessage", retMessage);
			}
			model.addObject(tickets, "tickets");
			model.setViewName("confirmations");

		} else {
			model.setViewName("login");
		}
		return model;

	}

	// Log call as a User
	@RequestMapping(value = "UserlogTicket", method = RequestMethod.POST)
	public ModelAndView userLogTicket(
			@ModelAttribute("UserlogTicket") TicketsBean logTickets) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		String tickets = "tickets";
		if (userName != null) {
			retMessage = logTicketService.logTicket(logTickets);
			if (retMessage.startsWith("C")) {
				String message = retMessage;
				model.addObject("message", message);
			} else {
				model.addObject("retMessage", retMessage);
				model.addObject("tickets", tickets);
			}
			model.setViewName("confirm");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	// Replace Toner as Manager || Admin || Normal User || Technician
	@RequestMapping(value = {"tonerReplaceAdmin", "tonerReplaceUser", "tonerReplaceTech"}, method = RequestMethod.POST)
	public ModelAndView tonerReplaceAdmin(
			@RequestParam("compitableSiteStock") String compitableSiteStock,
			@RequestParam("currentMonoReading") String currentMonoReading,
			@RequestParam("currentColourReading") String currentColourReading,
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("loggedInUser") String loggedInUser,
			@RequestParam("contactEmail") String contactEmail,
			@RequestParam("contactTelephoneNumber") String contactTelephoneNumber,
			@RequestParam("contactCellNumber") String contactCellNumber,
			@RequestParam("description") String description,
			@RequestParam("device") String device) {

		model = new ModelAndView();
		String replaceToner = "replaceToner";
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			
			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {

			retMessage = deviceServiceInt.replaceToner(compitableSiteStock,
					currentMonoReading, currentColourReading, firstName,
					lastName, loggedInUser, contactEmail, contactTelephoneNumber,contactCellNumber, description,
					device);
			if (retMessage.startsWith("Toner Replaced successfully")) {
				model.addObject("retMessage", retMessage);
			} else {
				model.addObject("retMessage", retMessage);
			}
			model.addObject(replaceToner, "replaceToner");
			model.setViewName("confirmations");
		}else if (userName.getRole().equalsIgnoreCase("User")){
			
			retMessage = deviceServiceInt.replaceToner(compitableSiteStock,
					currentMonoReading, currentColourReading, firstName,
					lastName, loggedInUser, contactEmail, contactTelephoneNumber,contactCellNumber, description,
					device);
			if (retMessage.startsWith("Toner Replaced successfully")) {
				model.addObject("retMessage", retMessage);
			} else {
				model.addObject("retMessage", retMessage);
			}
			model.addObject(replaceToner, "replaceToner");
			model.setViewName("confirm");
		}else if(userName.getRole().equalsIgnoreCase("Technician")){
			
			retMessage = deviceServiceInt.replaceToner(compitableSiteStock,
					currentMonoReading, currentColourReading, firstName,
					lastName, loggedInUser, contactEmail, contactTelephoneNumber,contactCellNumber, description,
					device);
			if (retMessage.startsWith("Toner Replaced successfully")) {
				model.addObject("retMessage", retMessage);
			} else {
				model.addObject("retMessage", retMessage);
			}
			model.addObject(replaceToner, "replaceToner");
			model.setViewName("confirmation");
		}

		} else {
			model.setViewName("login");
		}
		return model;

	}
	// perform ticket action
	@RequestMapping("performTicketAction")
	public ModelAndView performTicketAction(
			@ModelAttribute("performTicketAction") TicketsBean updateTicket) {
		String performTicketAction = "performTicketAction";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {

				retMessage = ticketsServiceInt
						.performTicketAction(updateTicket);
				if (retMessage.startsWith("K")) {
					model.addObject("retMessage", retMessage);
				} else {
					model.addObject("retMessage", retMessage);

					model.addObject("performTicketAction", performTicketAction);
					model.setViewName("confirmations");
				}
			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				retMessage = ticketsServiceInt
						.performTicketAction(updateTicket);
				if (retMessage.startsWith("K")) {
					model.addObject("retMessage", retMessage);
				} else {
					model.addObject("retMessage", retMessage);

					model.addObject("performTicketAction", performTicketAction);
					model.setViewName("confirmation");
				}
			} else if (userName.getRole().equalsIgnoreCase("User")) {

				retMessage = ticketsServiceInt
						.performTicketAction(updateTicket);
				if (retMessage.startsWith("K")) {
					model.addObject("retMessage", retMessage);
				} else {
					model.addObject("retMessage", retMessage);

					model.addObject("performTicketAction", performTicketAction);
					model.setViewName("confirm");
				}
			}

		} else {
			model.setViewName("login");
		}

		return model;
	}

	// re-open ticket
	@RequestMapping("reOpenTicket")
	public ModelAndView reOpenTicket(
			@ModelAttribute("performTicketAction") TicketsBean updateTicket) {
		String performTicketAction = "performTicketAction";
		String techUpdateTicket = "techUpdateTicket";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {

				retMessage = ticketsServiceInt
						.performTicketAction(updateTicket);
				if (retMessage.startsWith("K")) {
					model.addObject("retMessage", retMessage);
				} else {
					model.addObject("retMessage", retMessage);

					model.addObject("performTicketAction", performTicketAction);
					model.setViewName("confirmations");
				}
			} else if (userName.getRole().equalsIgnoreCase("Technician")) {
				model.addObject("techUpdateTicket", techUpdateTicket);
				model.setViewName("confirmation");
			}

		} else {
			model.setViewName("login");
		}

		return model;
	}

	// acknowledge ticket
	@RequestMapping("acknowledgedTicket")
	public ModelAndView acknowledgedTicket(
			@ModelAttribute("performTicketAction") TicketsBean updateTicket) {
		String performTicketAction = "performTicketAction";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Technician")) {
				retMessage = ticketsServiceInt
						.performTicketAction(updateTicket);
				if (retMessage.startsWith("K")) {
					model.addObject("retMessage", retMessage);
				} else {
					model.addObject("retMessage", retMessage);

					model.addObject("performTicketAction", performTicketAction);
					model.setViewName("confirmation");
				}
			}

		} else {
			model.setViewName("login");
		}

		return model;
	}

	// take ticket
	@RequestMapping("taketicket")
	public ModelAndView taketicket(
			@ModelAttribute("performTicketAction") TicketsBean updateTicket) {
		String performTicketAction = "performTicketAction";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Technician")) {
				retMessage = ticketsServiceInt
						.performTicketAction(updateTicket);
				if (retMessage.startsWith("K")) {
					model.addObject("retMessage", retMessage);
				} else {
					model.addObject("retMessage", retMessage);

					model.addObject("performTicketAction", performTicketAction);
					model.setViewName("confirmation");
				}
			}

		} else {
			model.setViewName("login");
		}

		return model;
	}

	// escalate ticket
	@RequestMapping("escalateticket")
	public ModelAndView escalateticket(
			@ModelAttribute("performTicketAction") TicketsBean updateTicket) {
		String performTicketAction = "performTicketAction";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Technician")) {
				retMessage = ticketsServiceInt
						.performTicketAction(updateTicket);
				if (retMessage.startsWith("K")) {
					model.addObject("retMessage", retMessage);
				} else {
					model.addObject("retMessage", retMessage);

					model.addObject("performTicketAction", performTicketAction);
					model.setViewName("confirmation");
				}
			}

		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = { "monitoringTickets" })
	public ModelAndView displayLoggedTickets() {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("ticketList", logTicketService.getAllOpenTickets());
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("monitoringTickets");
		} else {
			model.setViewName("login");
		}
		return model;

	}

	@RequestMapping("ticketDetails")
	public ModelAndView loadTicketdetails(@RequestParam Long id,
			@ModelAttribute Tickets ticket) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			String technician = userName.getFirstName() + " "
					+ userName.getLastName();
			ticket = logTicketService.getLoggedTicketByTicketNumber(id);
			model.addObject("ticketObject", ticket);
			model.addObject("saveSpareParts", new SparePartsBean());
			getSerials = spareMasterServiceInt.getSerials();
			model.addObject("spareParts", getSerials);
			model.addObject(
					"contactPerson",
					contactDetailsServiceInt.getContactPerson(ticket
							.getDevice().getCustomerDevice().getCustomerName()));
			model.addObject("ticketHistoryList",
					ticketHistoryInt.getHistoryByTicketNumber(id));
			model.addObject("OrderNumber",
					ordersServiceInt.getAllOrders(userName.getEmail()));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("managersList", employeeServiceInt.getAllManagers());
			model.addObject("customerList", customerServiceInt.getClientList());
			model.addObject("bootStock",
					bootStockint.getAllOrders(technician, id));
			model.addObject(
					"siteStock",
					siteStock.getOrdersForCustomer(ticket.getDevice()
							.getCustomerDevice().getCustomerName(), id));

			model.setViewName("ticketDetails");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = "viewCustomerDetails")
	public ModelAndView viewCustomerDetails(
			@RequestParam("customerName") String customerName,
			@ModelAttribute Customer customer) {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("customer",
					customerServiceInt.contactDetails(customerName));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("customerDetails",
					contactDetailsServiceInt.contactDetails(customerName));
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("viewCustomerDetails");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping("updateTicketUser")
	public ModelAndView userUpdateTicket(
			@ModelAttribute("updateTicket") TicketsBean updateTicket) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			retMessage = logTicketService.updateTicket(updateTicket);
			model.addObject("retMessage", retMessage);
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("userUpdateTicket");
		} else {
			model.setViewName("login");
		}

		return model;

	}

	@RequestMapping("searchTechnician")
	public ModelAndView searchEmployee(
			@RequestParam("searchName") String searchName) {
		model.addObject("awaitingSparesTickets",
				ticketsServiceInt.countAwaitingSparesTickets());
		model.addObject("escalatedTickets",
				ticketsServiceInt.countEscalatedTickets());
		model.addObject("inboxCount",
				ordersServiceInt.pendingOrdersCount(userName.getEmail()));
		return new ModelAndView("");
	}

	@RequestMapping("clientInfo")
	public ModelAndView clientInfo() {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("", "");
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("clientInfo");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	// Replace Toner
	@RequestMapping(value = { "tonerReplacement", "tonerReplacementUser","tonerReplacementTech"}, method = RequestMethod.GET)
	public ModelAndView loadReplaceTonerAdmin() {

		model = new ModelAndView("logTicket");
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {

				model.addObject("logTicket", new TicketsBean());
				getSerialNumbers = deviceServiceInt.getSerials();
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("serialNumbers", getSerialNumbers);
				model.setViewName("tonerReplacement");
			} else if (userName.getRole().equalsIgnoreCase("User")) {
				model.addObject("logTicket", new TicketsBean());
				getSerialNumbers = deviceServiceInt.getSerials();
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("serialNumbers", getSerialNumbers);
				model.setViewName("tonerReplacementUser");

			}else if (userName.getRole().equalsIgnoreCase("Technician")) {
				model.addObject("logTicket", new TicketsBean());
				getSerialNumbers = deviceServiceInt.getSerials();
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("serialNumbers", getSerialNumbers);
				model.setViewName("tonerReplacementTech");

			}
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = "logTicket", method = RequestMethod.GET)
	public ModelAndView loadTicketAdmin() {

		model = new ModelAndView("logTicket");
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("logTicket", new TicketsBean());
			getSerialNumbers = deviceServiceInt.getSerials();
			model.addObject("technicians",
					employeeServiceInt.getAllTechnicians());
			model.addObject("serialNumbers", getSerialNumbers);
			model.addObject("onLeaveTechnicians", leaveInt.techniciansOnLeave());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("logTicket");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("AssignTicketToOtherTechnician")
	public ModelAndView assignTicketToAnotherTechnicia(
			@RequestParam Long ticketNumber, @ModelAttribute Tickets ticket) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			ticket = logTicketService
					.getLoggedTicketByTicketNumber(ticketNumber);
			model.addObject("ticketupdate", ticket);
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("technicians",
					employeeServiceInt.getAllTechnicians());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("ticketUpdate");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("userAssignTicketToOtherTechnician")
	public ModelAndView userAssignTicketToAnotherTechnicia(
			@RequestParam Long ticketNumber, @ModelAttribute Tickets ticket) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			ticket = logTicketService
					.getLoggedTicketByTicketNumber(ticketNumber);
			model.addObject("ticketupdate", ticket);
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("technicians",
					employeeServiceInt.getAllTechnicians());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("userUpdateTicket");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("updateTicketAdmin")
	public ModelAndView updateTicketAdmin(
			@ModelAttribute("updateTicket") TicketsBean updateTicket) {
		String managerUpdateTicket = "managerUpdateTicket";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			retMessage = logTicketService.updateTicket(updateTicket);
			model.addObject("retMessage", retMessage);
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("managerUpdateTicket", managerUpdateTicket);
			model.setViewName("confirmations");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "userTicket", method = RequestMethod.GET)
	public ModelAndView loadUserTicket(Integer offset, Integer maxResults) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			beanList = ticketsServiceInt.ticketsResults();
			model.addObject("ticketResults", beanList);
			model.addObject("count", count);
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("userTicket");
		} else {
			model.setViewName("login");
		}
		return model;

	}

	@RequestMapping(value = { "closedTicketsDetails",
			"closedTechTicketsDetails" })
	public ModelAndView loadClosedTicketsDetails(@RequestParam Long id,
			@ModelAttribute Tickets ticket) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			ticket = logTicketService.getLoggedTicketByTicketNumber(id);
			model.addObject("ticketObject", ticket);
			model.addObject(
					"contactPerson",
					contactDetailsServiceInt.getContactPerson(ticket
							.getDevice().getCustomerDevice().getCustomerName()));
			model.addObject("ticketHistoryList",
					ticketHistoryInt.getHistoryByTicketNumber(id));
			model.addObject("OrderNumber",
					ordersServiceInt.getAllOrders(userName.getEmail()));
			model.addObject("managersList", employeeServiceInt.getAllManagers());

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				model.addObject("escalatedTickets",
						ticketsServiceInt.countEscalatedTickets());
				model.setViewName("closedTicketsDetails");
			} else if (userName.getRole().equalsIgnoreCase("Technician")) {
				model.setViewName("closedTechTicketsDetails");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping("awaitingSpares")
	public ModelAndView awaitingSparesTickes() {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage", retMessage);
			model.addObject("ticketList",
					logTicketService.getAllAwaitingSpares());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("awaitingSpares");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("awaitingSparesDetails")
	public ModelAndView loadAwaitingSparesTicketdetails(@RequestParam Long id,
			@ModelAttribute Tickets ticket) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			String technician = userName.getFirstName() + " "
					+ userName.getLastName();
			ticket = logTicketService.getLoggedTicketByTicketNumber(id);
			model.addObject("ticketObject", ticket);
			model.addObject("saveSpareParts", new SparePartsBean());
			getSerials = spareMasterServiceInt.getSerials();
			model.addObject("spareParts", getSerials);
			model.addObject(
					"contactPerson",
					contactDetailsServiceInt.getContactPerson(ticket
							.getDevice().getCustomerDevice().getCustomerName()));
			model.addObject("ticketHistoryList",
					ticketHistoryInt.getHistoryByTicketNumber(id));
			model.addObject("OrderNumber",
					ordersServiceInt.getAllOrders(userName.getEmail()));
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("managersList", employeeServiceInt.getAllManagers());
			model.addObject("customerList", customerServiceInt.getClientList());
			model.addObject("bootStock", bootStockint.getAllOrders(technician));
			model.addObject("orderStatus",
					historyInt.getAllOrderHistoryTicketNumber(id));
			model.addObject(
					"siteStock",
					siteStock.getOrdersForCustomer(ticket.getDevice()
							.getCustomerDevice().getCustomerName()));

			model.setViewName("awaitingSparesDetails");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("awaitSpares")
	public ModelAndView awaitSpares() {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage", retMessage);
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("ticketList",
					logTicketService.getAllAwaitingSpares());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("awaitSpares");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("awaitingSparesTechDetails")
	public ModelAndView loadAwaitingSparesTechdetails(@RequestParam Long id,
			@ModelAttribute Tickets ticket) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			ticket = logTicketService.getLoggedTicketByTicketNumber(ticket
					.getRecordID());
			model.addObject("ticketObject", ticket);
			model.addObject(
					"contactPerson",
					contactDetailsServiceInt.getContactPerson(ticket
							.getDevice().getCustomerDevice().getCustomerName()));
			model.addObject("ticketHistoryList", ticketHistoryInt
					.getHistoryByTicketNumber(ticket.getRecordID()));
			model.addObject("OrderNumber",
					ordersServiceInt.getAllOrders(userName.getEmail()));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("managersList", employeeServiceInt.getAllManagers());
			model.addObject("orderStatus",
					historyInt.getAllOrderHistoryTicketNumber(id));
			model.setViewName("awaitingSparesTechDetails");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("bridgedTickets")
	public ModelAndView bridgedTickets() {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage", retMessage);
			model.addObject("ticketList",
					logTicketService.getAllBridgedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("bridgedTickets");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("bridgedTicketsDetails")
	public ModelAndView loadBridgedTicketsDetails(@RequestParam Long id,
			@ModelAttribute Tickets ticket) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			ticket = logTicketService.getLoggedTicketByTicketNumber(id);
			model.addObject("ticketObject", ticket);
			model.addObject(
					"contactPerson",
					contactDetailsServiceInt.getContactPerson(ticket
							.getDevice().getCustomerDevice().getCustomerName()));
			model.addObject("ticketHistoryList",
					ticketHistoryInt.getHistoryByTicketNumber(id));
			model.addObject("OrderNumber",
					ordersServiceInt.getAllOrders(userName.getEmail()));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("managersList", employeeServiceInt.getAllManagers());
			model.setViewName("bridgedTicketsDetails");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("slaBridged")
	public ModelAndView slaBridged() {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage", retMessage);
			model.addObject("ticketList",
					logTicketService.getAllBridgedTickets(userName.getEmail()));
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("slaBridged");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("bridgedTechDetails")
	public ModelAndView loadBridgedTechDetails(@RequestParam Long id,
			@ModelAttribute Tickets ticket) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			String technician = userName.getFirstName() + " "
					+ userName.getLastName();
			ticket = logTicketService.getLoggedTicketByTicketNumber(id);
			model.addObject("ticketObject", ticket);
			model.addObject(
					"contactPerson",
					contactDetailsServiceInt.getContactPerson(ticket
							.getDevice().getCustomerDevice().getCustomerName()));
			model.addObject("ticketHistoryList",
					ticketHistoryInt.getHistoryByTicketNumber(id));
			model.addObject("OrderNumber",
					ordersServiceInt.getAllOrders(userName.getEmail()));
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject(
					"siteStock",
					siteStock.getOrdersForCustomer(ticket.getDevice()
							.getCustomerDevice().getCustomerName()));
			model.addObject("bootStock", bootStockint.getAllOrders(technician));
			model.addObject("managersList", employeeServiceInt.getAllManagers());
			model.setViewName("bridgedTechDetails");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("escalatedTickes")
	public ModelAndView escalatedTickes() {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage", retMessage);
			model.addObject("ticketList",
					logTicketService.getAllEscalatedTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.setViewName("escalatedTickes");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("escalatedTickesDetails")
	public ModelAndView loadEscalatedTickesDetails(@RequestParam Long id,
			@ModelAttribute Tickets ticket) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			ticket = logTicketService.getLoggedTicketByTicketNumber(id);
			model.addObject("ticketObject", ticket);
			model.addObject(
					"contactPerson",
					contactDetailsServiceInt.getContactPerson(ticket
							.getDevice().getCustomerDevice().getCustomerName()));
			model.addObject("ticketHistoryList",
					ticketHistoryInt.getHistoryByTicketNumber(id));
			model.addObject("OrderNumber",
					ordersServiceInt.getAllOrders(userName.getEmail()));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("managersList", employeeServiceInt.getAllManagers());
			model.setViewName("escalatedTickesDetails");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("escalatedTech")
	public ModelAndView escalatedTech() {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage", retMessage);
			model.addObject("ticketList",
					logTicketService.getAllEscalatedTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("escalatedTech");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("escalatedTechDetails")
	public ModelAndView loadEscalatedTechDetails(@RequestParam Long id,
			@ModelAttribute Tickets ticket) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			ticket = logTicketService.getLoggedTicketByTicketNumber(id);
			model.addObject("ticketObject", ticket);
			model.addObject(
					"contactPerson",
					contactDetailsServiceInt.getContactPerson(ticket
							.getDevice().getCustomerDevice().getCustomerName()));
			model.addObject("ticketHistoryList",
					ticketHistoryInt.getHistoryByTicketNumber(id));
			model.addObject("OrderNumber",
					ordersServiceInt.getAllOrders(userName.getEmail()));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("managersList", employeeServiceInt.getAllManagers());
			model.addObject("manager", employeeServiceInt
					.getEmployeeByEmpNumber(ticket.getEscalatedTo()));
			model.setViewName("escalatedTechDetails");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = { "resolvedTickets", "userResolvedTickets" })
	public ModelAndView resolvedTicket() {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage", retMessage);
			model.addObject("ticketList",
					logTicketService.getAllResolvedTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {

				model.setViewName("resolvedTickets");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				model.setViewName("userResolvedTickets");
			}
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = { "resolvedTickectDetails",
			"userResolvedTickectDetails" })
	public ModelAndView loadResolvedTicketDetails(@RequestParam Long id,
			@ModelAttribute Tickets ticket) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			ticket = logTicketService.getLoggedTicketByTicketNumber(id);
			model.addObject("ticketObject", ticket);
			model.addObject(
					"contactPerson",
					contactDetailsServiceInt.getContactPerson(ticket
							.getDevice().getCustomerDevice().getCustomerName()));
			model.addObject("ticketHistoryList",
					ticketHistoryInt.getHistoryByTicketNumber(id));
			model.addObject("OrderNumber",
					ordersServiceInt.getAllOrders(userName.getEmail()));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("managersList", employeeServiceInt.getAllManagers());

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin"))

				model.setViewName("resolvedTickectDetails");
		} else if (userName.getRole().equalsIgnoreCase("User")) {

			model.setViewName("userResolvedTickets");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping("resolvedTechTickets")
	public ModelAndView resolvedTechTicket() {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage", retMessage);

			model.addObject("ticketList",
					logTicketService.getAllResolvedTickets(userName.getEmail()));
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("resolvedTechTickets");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("resolvedTechTicketsDetails")
	public ModelAndView loadResolvedTechTicketsDetails(@RequestParam Long id,
			@ModelAttribute Tickets ticket) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			ticket = logTicketService.getLoggedTicketByTicketNumber(id);
			model.addObject("ticketObject", ticket);
			model.addObject(
					"contactPerson",
					contactDetailsServiceInt.getContactPerson(ticket
							.getDevice().getCustomerDevice().getCustomerName()));
			model.addObject("ticketHistoryList",
					ticketHistoryInt.getHistoryByTicketNumber(id));
			model.addObject("OrderNumber",
					ordersServiceInt.getAllOrders(userName.getEmail()));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("managersList", employeeServiceInt.getAllManagers());
			model.setViewName("resolvedTechTicketsDetails");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = { "closedTicketsAdmin", "closedTechDetails" })
	public ModelAndView closedTicketsAdmin1(
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage", retMessage);
			model.addObject("ticketList",
					logTicketService.getAllClosedTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("ticketList",
					logTicketService.getAllClosedTickets(startDate, endDate));

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {

				model.setViewName("closedTickets");
			}

			else if (userName.getRole().equalsIgnoreCase("Technician")) {
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketList", logTicketService
						.getAllClosedTickets(userName.getEmail()));
				model.addObject("awaitingSparesTickets",
						ticketsServiceInt.countAwaitingSparesTickets());
				model.addObject("ticketList", logTicketService
						.getAllClosedTickets(startDate, endDate,
								userName.getEmail()));
				model.setViewName("closedTechTickets");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = { "escalateTechTicket", "adminEscalates" })
	public ModelAndView TicketsAdmin(
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage", retMessage);
			model.addObject("ticketList",
					logTicketService.getAllEscalatedTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("ticketList",
					logTicketService.getAllEscalatedTickets(startDate, endDate));

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {

				model.setViewName("escalatedTickes");
			}

			else if (userName.getRole().equalsIgnoreCase("Technician")) {
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketList", logTicketService
						.getAllEscalatedTickets(userName.getEmail()));
				model.addObject("awaitingSparesTickets",
						ticketsServiceInt.countAwaitingSparesTickets());
				model.addObject("ticketList", logTicketService
						.getAllEscalatedTickets(startDate, endDate,
								userName.getEmail()));
				model.setViewName("escalatedTech");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = { "bridgedTicketsAdmin", "adminEscalates" })
	public ModelAndView slaTicketsAdmin(
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage", retMessage);
			model.addObject("ticketList",
					logTicketService.getAllBridgedTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("ticketList",
					logTicketService.getAllBridgedTickets(startDate, endDate));

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {

				model.setViewName("bridgedTickets");
			}

			else if (userName.getRole().equalsIgnoreCase("Technician")) {
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketList", logTicketService
						.getAllBridgedTickets(userName.getEmail()));
				model.addObject("ticketList", logTicketService
						.getAllBridgedTickets(startDate, endDate,
								userName.getEmail()));
				model.setViewName("slaBridged");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = { "awaitingTechDetails", "awaitingAdminDetails" })
	public ModelAndView awaitingTicketsAdmin(
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage", retMessage);
			model.addObject("ticketList",
					logTicketService.getAllBridgedTickets());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("awaitingSparesTickets",
					ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("escalatedTickets",
					ticketsServiceInt.countEscalatedTickets());
			model.addObject("inboxCount",
					ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("ticketList", logTicketService
					.getAllAwaitingSparesTickets(startDate, endDate));

			if (userName.getRole().equalsIgnoreCase("Manager")	|| userName.getRole().equalsIgnoreCase("Admin")) {

				model.setViewName("awaitingSpares");
			}

			else if (userName.getRole().equalsIgnoreCase("Technician")) {
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				model.addObject("ticketList", logTicketService
						.getAllAwaitingSpares(userName.getEmail()));
				model.addObject("ticketList", logTicketService
						.getAllAwaitingSparesTickets(startDate, endDate,
								userName.getEmail()));
				model.setViewName("awaitSpares");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ModelAndView dataIntegrity(Exception ex) {
		ModelAndView model = new ModelAndView("405");

		model.addObject("exception", ex.getMessage());
		return model;

	}

	// service manual for manager, admin and user
	@RequestMapping(value = { "servicemanualmanager", "servicemanualtech",
			"servicemanualuser" }, method = RequestMethod.GET)
	public ModelAndView displayServiceManual() {

		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| (userName.getRole().equalsIgnoreCase("Admin"))) {

				model.setViewName("servicemanualmanager");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				model.setViewName("servicemanualtech");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				model.setViewName("servicemanualuser");

			}
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = { "canon", "canonForTechnician", "canonForUser" }, method = RequestMethod.GET)
	public ModelAndView loadCanon() {

		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| (userName.getRole().equalsIgnoreCase("Admin"))) {

				model.addObject("machineType", machineType);
				model.setViewName("canon");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				model.setViewName("canonForTechnician");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				model.setViewName("canonForUser");

			}
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = { "riso", "risoForTechnician", "risoForUser" }, method = RequestMethod.GET)
	public ModelAndView loadRiso() {

		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| (userName.getRole().equalsIgnoreCase("Admin"))) {

				model.addObject("machineType", machineType);
				model.setViewName("riso");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				model.setViewName("risoForTechnician");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				model.setViewName("risoForUser");

			}
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = { "samsung", "samsungForTechnician",
			"samsungForUser" }, method = RequestMethod.GET)
	public ModelAndView loadSamsung() {

		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| (userName.getRole().equalsIgnoreCase("Admin"))) {

				model.addObject("machineType", machineType);
				model.setViewName("samsung");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				model.setViewName("samsungForTechnician");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				model.setViewName("samsungForUser");

			}
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = { "toshiba", "toshibaForTechnician",
			"toshibaForUser" }, method = RequestMethod.GET)
	public ModelAndView loadToshiba() {

		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| (userName.getRole().equalsIgnoreCase("Admin"))) {

				model.addObject("machineType", machineType);
				model.setViewName("toshiba");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				model.setViewName("toshibaForTechnician");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				model.setViewName("toshibaForUser");

			}
		} else {
			model.setViewName("login");
		}
		return model;
	}

}

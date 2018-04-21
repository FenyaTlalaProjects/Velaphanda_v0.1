package com.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo.bean.LeaveBean;
import com.demo.bean.OrdersBean;
import com.demo.bean.SparePartsBean;
import com.demo.model.Employee;
import com.demo.model.Leave;
import com.demo.model.Tickets;
import com.demo.service.EmployeeServiceInt;
import com.demo.service.LeaveInt;
import com.demo.service.OrdersServiceInt;
import com.demo.service.TicketsServiceInt;

@Controller
public class LeaveController {

	@Autowired
	private OrdersServiceInt ordersServiceInt;
	@Autowired
	private HttpSession session;
	@Autowired
	private LeaveInt leaveInt;
	@Autowired
	private TicketsServiceInt ticketsServiceInt;
	@Autowired
	private EmployeeServiceInt employeeServiceInt;
	private ModelAndView model = null;
	private String retMessage = null;
	private Employee userName = null;
	private Employee tempEmployee = null;
	private String selectedDateRange;
	private String heading;

	@RequestMapping(value = { "leavemanagement", "techleavemanagement","userleavemanagement" }, method = RequestMethod.GET)
	public ModelAndView displayleaveManagement() {
		heading = "All Leaves";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		selectedDateRange = null;
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {
				
				model.addObject("countPendingLeave",leaveInt.countAllPendingLeave());
				model.addObject("countActiveLeave",	leaveInt.countAllActiveLeave());
				model.addObject("countApprovedLeave",leaveInt.countAllApprovedLeave());
				model.addObject("countCancelledLeave",leaveInt.countAllCancelledLeave());
				
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.addObject("dates", leaveInt.getLeaveDates());
				model.addObject("leaveList", leaveInt.getAllPendingActiveApprovedAndCancelledLeave());
				model.addObject("heading",heading);
				model.setViewName("leavemanagement");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
				model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
				model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
				model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
				model.addObject("dates", leaveInt.getLeaveDates());
				//model.addObject("leaveList", leaveInt.activeAndPendingLeaveByTechnician(userName.getEmail()));
				model.addObject("leaveList", leaveInt.pendingActiveApprovedAndCancelledLeaveByTechnician(userName.getEmail()));
				model.addObject("heading",heading);
				model.setViewName("techleavemanagement");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
				model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
				model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
				model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
							model.addObject("dates", leaveInt.getLeaveDates());
				//model.addObject("leaveList", leaveInt.activeAndPendingLeaveByTechnician(userName.getEmail()));				
				model.addObject("leaveList", leaveInt.pendingActiveApprovedAndCancelledLeaveByTechnician(userName.getEmail()));
				model.addObject("heading",heading);
				model.setViewName("userleavemanagement");
			}

		} else {
			model.setViewName("login");
		}

		return model;
	}

	// leave management details
	@RequestMapping(value = { "leaveDetailsAdmin", "leaveDetailsTechnician",
			"leaveDetailsUser" }, method = RequestMethod.GET)
	public ModelAndView leaveDetails(@RequestParam("leaveID") Long leaveID,
			@ModelAttribute("updateLeave") Leave leave) {
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Technician")) {
				model.addObject("leave", leaveInt.getLeave(leaveID));
				model.setViewName("leaveDetailsTechnician");

			} else if (userName.getRole().equalsIgnoreCase("Manager")|| userName.getRole().equalsIgnoreCase("Admin")) {

				model.addObject("leave", leaveInt.getLeave(leaveID));
				model.setViewName("leaveDetailsAdmin");

			} else if (userName.getRole().equalsIgnoreCase("User")) {
				model.addObject("leave", leaveInt.getLeave(leaveID));
				model.setViewName("leaveDetailsUser");

			}

		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "getTechnicianName1", method = RequestMethod.GET)
	public ModelAndView getTehnnicianName(
			@RequestParam("technicianName") String localTechnicianName) {
		tempEmployee = employeeServiceInt
				.getEmployeeByEmpNumber(localTechnicianName);

		model = new ModelAndView();
		selectedDateRange = null;

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(localTechnicianName));
				model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(localTechnicianName));
				model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(localTechnicianName));
				model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(localTechnicianName));
			
				//model.addObject("leaveList", leaveInt.activeAndPendingLeaveByTechnician(localTechnicianName));
				model.addObject("leaveList", leaveInt.pendingActiveApprovedAndCancelledLeaveByTechnician(localTechnicianName));
				model.addObject("technicians",
						employeeServiceInt.getAllTechnicians());
				model.addObject("selectedTechnician", tempEmployee);

				model.addObject("dates", ordersServiceInt.getDates());
				model.setViewName("leavemanagement");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "activeLeave", method = RequestMethod.GET)
	public ModelAndView activeLeave() {
		heading = "Active";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");

		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (tempEmployee != null) {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("leaveList",leaveInt.getActiveLeaveByTechnician(tempEmployee.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("selectedTechnician", tempEmployee);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.addObject("dates", leaveInt.getLeaveDates());

					model.setViewName("leavemanagement");

				} else if (selectedDateRange != null) {
					model.addObject("countPendingLeave",leaveInt.countpendingLeaveForSelectedDate(selectedDateRange));
					model.addObject("countApprovedLeave", leaveInt.countApprovedLeaveForSelectedDate(selectedDateRange));
					model.addObject("countActiveLeave",leaveInt.countActiveLeaveForSelectedDate(selectedDateRange));
					model.addObject("countCancelledLeave", leaveInt.countCancelledLeaveForSelectedDate(selectedDateRange));
				
					model.addObject("leaveList", leaveInt.getActiveLeaveForSelectedRange(selectedDateRange));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("selectedTechnician", tempEmployee);
					model.addObject("heading",heading);
					model.setViewName("leavemanagement");
				} else {
					model.addObject("countPendingLeave",leaveInt.countAllPendingLeave());
					model.addObject("countActiveLeave",	leaveInt.countAllActiveLeave());
					model.addObject("countApprovedLeave",leaveInt.countAllApprovedLeave());
					model.addObject("countCancelledLeave",leaveInt.countAllCancelledLeave());
					model.addObject("technicians",
							employeeServiceInt.getAllTechnicians());
					model.addObject("selectedTechnician", tempEmployee);
					model.addObject("leaveList", leaveInt.getActiveLeave());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.setViewName("leavemanagement");

				}
			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (selectedDateRange != null) {
					model.addObject("countPendingLeave", leaveInt.countTechpendingLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.countTechApprovedLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.countTechActiveLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.countTechCancelledLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					
					model.addObject("leaveList", leaveInt.getTechActiveLeaveForSelectedRange(selectedDateRange, userName.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.setViewName("techleavemanagement");
				} else {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("leaveList", leaveInt.getActiveLeaveByTechnician(userName.getEmail()));
					model.setViewName("techleavemanagement");
				}
			}else if (userName.getRole().equalsIgnoreCase("User")) {
				
				if (selectedDateRange != null) {
					model.addObject("countPendingLeave", leaveInt.countTechpendingLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.countTechApprovedLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.countTechActiveLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.countTechCancelledLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					
					model.addObject("leaveList", leaveInt.getTechActiveLeaveForSelectedRange(selectedDateRange, userName.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.setViewName("userleavemanagement");
				} else {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("leaveList", leaveInt.getActiveLeaveByTechnician(userName.getEmail()));
					model.addObject("heading",heading);
					model.setViewName("userleavemanagement");
				}
				
			}
			
			
		} else {
			model.setViewName("login");
		}

		return model;
	}
	
	
	@RequestMapping(value = "approvedLeave", method = RequestMethod.GET)
	public ModelAndView approvedLeave() {
		heading = "Approved";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		// selectedDateRange = null;
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (tempEmployee != null) {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("leaveList", leaveInt.getApprovedLeaveByTechnician(tempEmployee.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("selectedTechnician", tempEmployee);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.addObject("dates", leaveInt.getLeaveDates());
					model.setViewName("leavemanagement");

				} else if (selectedDateRange != null) {
					model.addObject("countPendingLeave",leaveInt.countpendingLeaveForSelectedDate(selectedDateRange));
					model.addObject("countApprovedLeave", leaveInt.countApprovedLeaveForSelectedDate(selectedDateRange));
					model.addObject("countActiveLeave",leaveInt.countActiveLeaveForSelectedDate(selectedDateRange));
					model.addObject("countCancelledLeave", leaveInt.countCancelledLeaveForSelectedDate(selectedDateRange));
				
				
					model.addObject("leaveList", leaveInt.getApprovedLeaveForSelectedRange(selectedDateRange));
					
					model.addObject("technicians",
							employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("selectedTechnician", tempEmployee);
					model.addObject("heading",heading);
					model.setViewName("leavemanagement");
				} else {
					model.addObject("countPendingLeave",leaveInt.countAllPendingLeave());
					model.addObject("countActiveLeave",	leaveInt.countAllActiveLeave());
					model.addObject("countApprovedLeave",leaveInt.countAllApprovedLeave());
					model.addObject("countCancelledLeave",leaveInt.countAllCancelledLeave());
					
					model.addObject("technicians",
							employeeServiceInt.getAllTechnicians());
					model.addObject("selectedTechnician", tempEmployee);
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("leaveList", leaveInt.getApprovedLeave());
					model.addObject("heading",heading);
					model.addObject("dates", leaveInt.getLeaveDates());
					model.setViewName("leavemanagement");

				}
			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (selectedDateRange != null) {
					model.addObject("countPendingLeave", leaveInt.countTechpendingLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.countTechApprovedLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.countTechActiveLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.countTechCancelledLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
				
					model.addObject("leaveList", leaveInt.getTechApprovedLeaveForSelectedRange(selectedDateRange, userName.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.addObject("dates", leaveInt.getLeaveDates());
					model.setViewName("techleavemanagement");
				} else {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("leaveList", leaveInt
							.getPendingLeaveByTechnician(userName.getEmail()));
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("heading",heading);
					model.setViewName("techleavemanagement");
				}				
			}else if (userName.getRole().equalsIgnoreCase("User")) {
				
				if (selectedDateRange != null) {
					model.addObject("countPendingLeave", leaveInt.countTechpendingLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.countTechApprovedLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.countTechActiveLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.countTechCancelledLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					
					model.addObject("leaveList", leaveInt.getTechApprovedLeaveForSelectedRange(selectedDateRange, userName.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.setViewName("userleavemanagement");
				} else {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("leaveList", leaveInt.getApprovedLeaveByTechnician(userName.getEmail()));
					model.setViewName("userleavemanagement");
				}
				
			}		
			
		} else {
			model.setViewName("login");
		}

		return model;
	}
	

	@RequestMapping(value = "pendingLeave", method = RequestMethod.GET)
	public ModelAndView pendingLeave() {
		heading = "Pending";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		// selectedDateRange = null;
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (tempEmployee != null) {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("leaveList", leaveInt.getPendingLeaveByTechnician(tempEmployee.getEmail()));
					model.addObject("technicians",
							employeeServiceInt.getAllTechnicians());
					model.addObject("selectedTechnician", tempEmployee);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.addObject("dates", leaveInt.getLeaveDates());
					model.setViewName("leavemanagement");

				} else if (selectedDateRange != null) {
					model.addObject("countPendingLeave",leaveInt.countpendingLeaveForSelectedDate(selectedDateRange));
					model.addObject("countApprovedLeave", leaveInt.countApprovedLeaveForSelectedDate(selectedDateRange));
					model.addObject("countActiveLeave",leaveInt.countActiveLeaveForSelectedDate(selectedDateRange));
					model.addObject("countCancelledLeave", leaveInt.countCancelledLeaveForSelectedDate(selectedDateRange));
				
					model.addObject("leaveList", leaveInt.getPendingLeaveForSelectedRange(selectedDateRange));
					model.addObject("technicians",
							employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("selectedTechnician", tempEmployee);
					model.addObject("heading",heading);
					model.setViewName("leavemanagement");
				} else {
					model.addObject("countPendingLeave",leaveInt.countAllPendingLeave());
					model.addObject("countActiveLeave",	leaveInt.countAllActiveLeave());
					model.addObject("countApprovedLeave",leaveInt.countAllApprovedLeave());
					model.addObject("countCancelledLeave",leaveInt.countAllCancelledLeave());
					model.addObject("technicians",
							employeeServiceInt.getAllTechnicians());
					model.addObject("selectedTechnician", tempEmployee);
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("leaveList", leaveInt.getPendingLeaves());
					model.addObject("heading",heading);
					model.addObject("dates", leaveInt.getLeaveDates());
					model.setViewName("leavemanagement");

				}
			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (selectedDateRange != null) {
					model.addObject("countPendingLeave", leaveInt.countTechpendingLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.countTechApprovedLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.countTechActiveLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.countTechCancelledLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					
					model.addObject("leaveList", leaveInt.getTechPendingLeaveForSelectedRange(selectedDateRange, userName.getEmail()));
					model.addObject("technicians",
							employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.addObject("dates", leaveInt.getLeaveDates());
					model.setViewName("techleavemanagement");
				} else {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("leaveList", leaveInt.getPendingLeaveByTechnician(userName.getEmail()));
					model.addObject("dates", leaveInt.getLeaveDates());

					model.setViewName("techleavemanagement");
				}
			}else if (userName.getRole().equalsIgnoreCase("User")) {
				
				if (selectedDateRange != null) {
					model.addObject("countPendingLeave", leaveInt.countTechpendingLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.countTechApprovedLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.countTechActiveLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.countTechCancelledLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					
					model.addObject("leaveList", leaveInt.getTechPendingLeaveForSelectedRange(selectedDateRange, userName.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.setViewName("userleavemanagement");
				} else {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("leaveList", leaveInt.getPendingLeaveByTechnician(userName.getEmail()));
					model.setViewName("userleavemanagement");
				}
				
			}	
			
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "cancelledLeave", method = RequestMethod.GET)
	public ModelAndView cancelledLeave() {
		heading = "Cancelled";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		// selectedDateRange = null;
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (tempEmployee != null) {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("leaveList", leaveInt.getCancelledLeaveByTechnician(tempEmployee.getEmail()));
					model.addObject("technicians", employeeServiceInt.getAllTechnicians());
					model.addObject("selectedTechnician", tempEmployee);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.addObject("dates", leaveInt.getLeaveDates());
					model.setViewName("leavemanagement");

				} else if (selectedDateRange != null) {
					model.addObject("countPendingLeave",leaveInt.countpendingLeaveForSelectedDate(selectedDateRange));
					model.addObject("countApprovedLeave", leaveInt.countApprovedLeaveForSelectedDate(selectedDateRange));
					model.addObject("countActiveLeave",leaveInt.countActiveLeaveForSelectedDate(selectedDateRange));
					model.addObject("countCancelledLeave", leaveInt.countCancelledLeaveForSelectedDate(selectedDateRange));
				
					model.addObject("leaveList", leaveInt.getCancelledLeaveForSelectedRange(selectedDateRange));					
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.addObject("selectedTechnician", tempEmployee);
					model.setViewName("leavemanagement");
				} else {
					model.addObject("countPendingLeave",leaveInt.countAllPendingLeave());
					model.addObject("countActiveLeave",	leaveInt.countAllActiveLeave());
					model.addObject("countApprovedLeave",leaveInt.countAllApprovedLeave());
					model.addObject("countCancelledLeave",leaveInt.countAllCancelledLeave());
					
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("selectedTechnician", tempEmployee);
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("leaveList", leaveInt.getCancelledLeave());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("heading",heading);
					model.setViewName("leavemanagement");

				}
			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (selectedDateRange != null) {
					model.addObject("countPendingLeave", leaveInt.countTechpendingLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.countTechApprovedLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.countTechActiveLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.countTechCancelledLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
				
					model.addObject("leaveList", leaveInt.getTechCancelledLeaveForSelectedRange(selectedDateRange, userName.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("heading",heading);
					model.setViewName("techleavemanagement");
				} else {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("leaveList", leaveInt.getCancelledLeaveByTechnician(userName.getEmail()));
					model.addObject("heading",heading);
					model.addObject("dates", leaveInt.getLeaveDates());

					model.setViewName("techleavemanagement");
				}
			}
			else if (userName.getRole().equalsIgnoreCase("User")) {
				
				if (selectedDateRange != null) {
					model.addObject("countPendingLeave", leaveInt.countTechpendingLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.countTechApprovedLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.countTechActiveLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.countTechCancelledLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					
					model.addObject("leaveList", leaveInt.getTechCancelledLeaveForSelectedRange(selectedDateRange, userName.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.setViewName("userleavemanagement");
				} else {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("leaveList", leaveInt.getCancelledLeaveByTechnician(userName.getEmail()));
					model.addObject("heading",heading);
					model.setViewName("userleavemanagement");
				}
				
			}	
			
			
		} else {
			model.setViewName("login");
		}

		return model;
	}
	
	@RequestMapping(value = "leaveHistory", method = RequestMethod.GET)
	public ModelAndView leaveHistory() {
		heading = "Leave History";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (tempEmployee != null) {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("leaveList", leaveInt.getPendingLeaveByTechnician(tempEmployee.getEmail()));
					model.addObject("leaveList",leaveInt.getLeaveHistoryByTechician(tempEmployee.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("selectedTechnician", tempEmployee);
					model.addObject("heading",heading);
					model.addObject("dates", leaveInt.getLeaveDates());
					model.setViewName("leavemanagement");

				}

				else if (selectedDateRange != null) {
					model.addObject("countPendingLeave",leaveInt.countpendingLeaveForSelectedDate(selectedDateRange));
					model.addObject("countApprovedLeave", leaveInt.countApprovedLeaveForSelectedDate(selectedDateRange));
					model.addObject("countActiveLeave",leaveInt.countActiveLeaveForSelectedDate(selectedDateRange));
					model.addObject("countCancelledLeave", leaveInt.countCancelledLeaveForSelectedDate(selectedDateRange));
				
					model.addObject("leaveList", leaveInt.getLeaveHistoryForSelectedRange(selectedDateRange));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.setViewName("leavemanagement");

				} else {
					model.addObject("countPendingLeave",leaveInt.countAllPendingLeave());
					model.addObject("countActiveLeave",	leaveInt.countAllActiveLeave());
					model.addObject("countApprovedLeave",leaveInt.countAllApprovedLeave());
					model.addObject("countCancelledLeave",leaveInt.countAllCancelledLeave());
					
					model.addObject("leaveList", leaveInt.leaveHistory());
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("newDate", selectedDateRange);
					model.addObject("selectedTechnician", tempEmployee);
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("heading",heading);
					model.setViewName("leavemanagement");
				}
			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (selectedDateRange != null) {
					model.addObject("countPendingLeave", leaveInt.countTechpendingLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.countTechApprovedLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.countTechActiveLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.countTechCancelledLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					
					model.addObject("leaveList", leaveInt.getTechLeaveHistoryForSelectedRange(selectedDateRange, userName.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.setViewName("techleavemanagement");
				} else {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("leaveList", leaveInt.getLeaveHistoryByTechician(userName.getEmail()));
					model.addObject("heading",heading);
					model.setViewName("techleavemanagement");
				}

			}else if (userName.getRole().equalsIgnoreCase("User")) {
				
				if (selectedDateRange != null) {
					model.addObject("countPendingLeave", leaveInt.countTechpendingLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.countTechApprovedLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.countTechActiveLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.countTechCancelledLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					
					model.addObject("leaveList", leaveInt.getTechLeaveHistoryForSelectedRange(selectedDateRange, userName.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading",heading);
					model.setViewName("userleavemanagement");
				} else {
					model.addObject("countPendingLeave", leaveInt.getPendingLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.getApprovedLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.getActiveLeaveCountByTechnician(userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.getCancelledLeaveCountByTechnician(userName.getEmail()));
					
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("leaveList", leaveInt.getLeaveHistoryByTechician(userName.getEmail()));
					model.setViewName("userleavemanagement");
				}
				
			}	

		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = { "leave", "requestLeave", "userMakeLeave" }, method = RequestMethod.GET)
	public ModelAndView loadLeave() {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");

		if (userName != null) {

			model.addObject("makeLeave", new LeaveBean());

			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {

				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.setViewName("requestLeave");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {
				model.setViewName("leave");

			} else if (userName.getRole().equalsIgnoreCase("User")) {				
				model.setViewName("userMakeLeave");
			}

		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = { "viewLeaveRequests", "viewRequestedLeave",
			"viewRequestedUserLeave" }, method = RequestMethod.GET)
	public ModelAndView loadViewLeaveRequestss() {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				model.addObject("leaveList", leaveInt.leaveRequests());
				model.setViewName("viewRequestedLeave");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				model.addObject("leaveList",
						leaveInt.leaveRequests(userName.getEmail()));

				model.setViewName("viewLeaveRequests");

			} else if (userName.getRole().equalsIgnoreCase("User")) {

				model.addObject("leaveList",
						leaveInt.leaveRequests(userName.getEmail()));

				model.setViewName("viewRequestedUserLeave");
			}

		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = { "updateLeave", "updateMakeLeave",
			"updateUserMakeLeave" }, method = RequestMethod.GET)
	public ModelAndView loadUpdateLeave(@RequestParam("leaveID") Long leaveID,
			@ModelAttribute("updateLeave") Leave leave) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("updateLeave", new LeaveBean());
			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {

				model.addObject("leave", leaveInt.getLeave(leaveID));
				model.setViewName("updateMakeLeave");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {
				model.addObject("leave", leaveInt.getLeave(leaveID));
				model.setViewName("updateLeave");

			} else if (userName.getRole().equalsIgnoreCase("User")) {
				model.addObject("leave", leaveInt.getLeave(leaveID));
				model.setViewName("updateUserMakeLeave");
			}

		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = { "makeLeave", "userMakeLeave" }, method = RequestMethod.POST)
	public ModelAndView submitLeave(@ModelAttribute("makeLeave") LeaveBean leave,@ModelAttribute("userMakeLeave") LeaveBean userleave) {
		model = new ModelAndView();
		;
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			retMessage = leaveInt.leaveRequest(leave);
			if (retMessage.startsWith("K")) {
				String retErrorMessage = retMessage;
				model.addObject("retErrorMessage", retErrorMessage);
			} else {
				model.addObject("retMessage", retMessage);
			}
			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {

				if (retMessage.startsWith("Leave already exist")) {
					String managerOnLeave = "managerOnLeave";
					model.addObject("retMessage", retMessage);
					model.addObject("managerOnLeave", managerOnLeave);
				} else {
					String managerAddLeave = "managerAddLeave";
					model.addObject("managerAddLeave", managerAddLeave);
				}

				model.setViewName("confirmations");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				if (retMessage.startsWith("Leave already exist")) {
					String techOnLeave = "techOnLeave";
					System.err.println(techOnLeave);
					model.addObject("retMessage", retMessage);
					model.addObject("techOnLeave", techOnLeave);
				} else {
					String techAddLeave = "techAddLeave";
					model.addObject("retMessage", retMessage);
					model.addObject("techAddLeave", techAddLeave);
				}

				model.setViewName("confirmation");

			} else if (userName.getRole().equalsIgnoreCase("User")) {
				
				if (retMessage.startsWith("Leave already exist")) {
					String techOnLeave = "techOnLeave";
					System.err.println(techOnLeave);
					model.addObject("retMessage", retMessage);
					model.addObject("techOnLeave", techOnLeave);
				} else {
					String techAddLeave = "techAddLeave";
					model.addObject("retMessage", retMessage);
					model.addObject("techAddLeave", techAddLeave);
				}
				model.setViewName("confirm");
			}
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = { "updateLeave", "updateMakeLeave","updateUserMakeLeave" }, method = RequestMethod.POST)
	public ModelAndView updateLeave(@ModelAttribute("updateLeave") LeaveBean leave) {

		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			retMessage = leaveInt.updateLeaveRequest(leave);

			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {
				String updateLeave = "updateLeave";
				model.addObject("updateLeave", updateLeave);
				model.addObject("retMessage", retMessage);
				model.setViewName("confirmations");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {
				String techUpdateLeave = "techUpdateLeave";
				model.addObject("retMessage", retMessage);
				model.addObject("techUpdateLeave", techUpdateLeave);
				model.setViewName("confirmation");

			} else if (userName.getRole().equalsIgnoreCase("User")) {
				String userUpdateLeave = "userUpdateLeave";
				model.addObject("retMessage", retMessage);
				model.addObject("userUpdateLeave", userUpdateLeave);
				model.setViewName("confirm");
			}
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = "getUserSelectedLeaveDate", method = RequestMethod.GET)
	public ModelAndView getUserSelectedDate(
			@RequestParam("selectedDate") String localSelectedDate) {
		selectedDateRange = localSelectedDate;
		tempEmployee = null;
		System.err.println(tempEmployee);
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {

				if (selectedDateRange != null) {
					model.addObject("countPendingLeave",leaveInt.countpendingLeaveForSelectedDate(selectedDateRange));
					model.addObject("countApprovedLeave", leaveInt.countApprovedLeaveForSelectedDate(selectedDateRange));
					model.addObject("countActiveLeave",leaveInt.countActiveLeaveForSelectedDate(selectedDateRange));
					model.addObject("countCancelledLeave", leaveInt.countCancelledLeaveForSelectedDate(selectedDateRange));
					
					model.addObject("leaveList", leaveInt.getAllLeaveForSelectedRange(selectedDateRange));
					model.addObject("technicians",
							employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.setViewName("leavemanagement");
				}
			} else if (userName.getRole().equalsIgnoreCase("Technician")) {
				if (selectedDateRange != null) {
					model.addObject("countPendingLeave", leaveInt.countTechpendingLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.countTechApprovedLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.countTechActiveLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.countTechCancelledLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
				
					model.addObject("leaveList", leaveInt.getAllTechLeaveForSelectedRange(selectedDateRange,userName.getEmail()));
					model.addObject("technicians",
							employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.setViewName("leavemanagement");
				}
				model.setViewName("techleavemanagement");
			}
			else if (userName.getRole().equalsIgnoreCase("User")) {
				
				if (selectedDateRange != null) {
					model.addObject("countPendingLeave", leaveInt.countTechpendingLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countApprovedLeave", leaveInt.countTechApprovedLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
					model.addObject("countActiveLeave", leaveInt.countTechActiveLeaveForSelectedDate(selectedDateRange, userName.getEmail()));
					model.addObject("countCancelledLeave", leaveInt.countTechCancelledLeaveForSelectedDate(selectedDateRange,userName.getEmail()));
				
					model.addObject("leaveList", leaveInt.getAllTechLeaveForSelectedRange(selectedDateRange,userName.getEmail()));
					model.addObject("technicians",
							employeeServiceInt.getAllTechnicians());
					model.addObject("dates", leaveInt.getLeaveDates());
					model.addObject("newDate", selectedDateRange);
					model.setViewName("leavemanagement");
				}
				model.setViewName("userleavemanagement");
			}	
			
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = { "leaveCancellation", "techLeaveCancellation",
			"userLeaveCancellation" }, method = RequestMethod.GET)
	public ModelAndView leaveCancellation(@RequestParam("leaveID") Long leaveID) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			retMessage = leaveInt.cancelLeave(leaveID);
			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				String managerCancel = "managerCancel";
				model.addObject("managerCancel", managerCancel);
				model.addObject("retMessage", retMessage);
				model.setViewName("confirmations");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {
				String technicianCancel = "technicianCancel";
				model.addObject("technicianCancel", technicianCancel);
				model.addObject("retMessage", retMessage);
				model.setViewName("confirmation");

			} else if (userName.getRole().equalsIgnoreCase("User")) {
				String userCancel = "userCancel";
				model.addObject("userCancel", userCancel);
				model.addObject("retMessage", retMessage);
				model.setViewName("confirm");
			}

		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "approveLeave", method = RequestMethod.GET)
	public ModelAndView approveLeave(@RequestParam("leaveID") Long leaveID) {

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			retMessage = leaveInt.approveLeave(leaveID);
			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				String managerCancel = "managerCancel";
				model.addObject("managerCancel", managerCancel);
				model.addObject("retMessage", retMessage);
				model.setViewName("confirmations");

			}

		} else {
			model.setViewName("login");
		}

		return model;
	}	
	
	@RequestMapping(value = "declineLeave", method = RequestMethod.GET)
	public ModelAndView displayDeclineLeave(@RequestParam("leaveID") Long leaveID) {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("managerCancel", new LeaveBean());
			model.addObject("leave", leaveInt.getLeave(leaveID));			
			model.setViewName("declineLeave");
		} else {
			model.setViewName("login");
		}

		return model;
	}
	@RequestMapping(value = "declinedLeave", method = RequestMethod.POST)
	public ModelAndView declinedLeave(@ModelAttribute("declinedLeave") LeaveBean leave) {
		String managerCancel = "managerCancel";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			retMessage = leaveInt.declineLeave(leave.getLeaveID(),leave.getComments());
			model.addObject("retMessage", retMessage);			
			model.addObject("managerCancel", managerCancel);
			model.setViewName("confirmations");
		} else {
			model.setViewName("login");
		}

		return model;
	}
	
	
}

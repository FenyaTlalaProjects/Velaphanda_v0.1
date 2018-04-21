package com.demo.controller;

import java.io.FileNotFoundException;
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

import com.demo.bean.OrdersBean;
import com.demo.dao.EmployeeDaoInt;
import com.demo.dao.OrderDetailsDaoInt;
import com.demo.model.Employee;
import com.demo.model.OrderHeader;
import com.demo.model.OrderDetails;
import com.demo.service.BootStockInt;
import com.demo.service.CustomerContactDetailsServiceInt;
import com.demo.service.CustomerServiceInt;
import com.demo.service.EmployeeServiceInt;
import com.demo.service.OrderDetailsInt;
import com.demo.service.OrderHistoryInt;
import com.demo.service.OrdersServiceInt;
import com.demo.service.HOStockServeceInt;
import com.demo.service.SiteStockInt;
import com.demo.service.TicketsServiceInt;
import com.demo.service.VelaphandaInt;

@Controller
public class OrdersController {

	@Autowired
	private OrdersServiceInt ordersServiceInt;
	@Autowired
	private HOStockServeceInt spareParts;
	@Autowired
	private EmployeeServiceInt employeeServiceInt;
	
	@Autowired
	private EmployeeDaoInt employeeDaoInt;
	
	@Autowired
	private CustomerServiceInt customerServiceInt;
	@Autowired
	private CustomerContactDetailsServiceInt contactDetailsServiceInt;
	@Autowired
	private OrderDetailsInt orderDetailsInt;
	@Autowired
	private BootStockInt bootStock;
	@Autowired
	private SiteStockInt siteStock;
	@Autowired
	private HttpSession session;
	@Autowired
	private TicketsServiceInt ticketsServiceInt;
	@Autowired
	private OrderDetailsDaoInt detailsDaoInt;
	@Autowired
	private OrderHistoryInt historyInt;
	@Autowired
	private VelaphandaInt profile;
	private ModelAndView model = null;
	private String retMessage = null;
	private Employee userName = null;
	private String customerName, orderNum,technicianName, technicianEmail,
			selectedDateRange, heading = null;
	public String[] getOrdersNumbers = null;
	private Employee tempEmployee = null;
	private Long orderNumber;

	@RequestMapping(value = {"ordermanagement","ordertechmanagement","userordermanagement"}, method = RequestMethod.GET)
	public ModelAndView displayOrderManagement() {
		model = new ModelAndView();
		heading = "All Orders";
		selectedDateRange = "Select Date";
		customerName = "All Customers";
		technicianEmail = "All Technicians";
		orderNumber = null;
		
		userName = (Employee) session.getAttribute("loggedInUser");		
		
		if (userName != null) {
			
			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {
					
				model.addObject("orderList",ordersServiceInt.getOrderListForManager(customerName, selectedDateRange, technicianEmail, orderNumber));
				
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("countNewOrders",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Pending"));
				model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Approved"));
				model.addObject("countShippedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Shipped"));
				model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Received"));
				model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Declined"));;
				
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				

				if (technicianEmail != "All Technicians") {
					model.addObject("selectedTechnician",
							employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

				}

				model.addObject("selectedName", customerName);
				model.addObject("newDate", selectedDateRange);
				model.addObject("heading", heading);
				
				model.setViewName("ordermanagement");
				
			}
			
			else if (userName.getRole().equalsIgnoreCase("User")) {
				
				model.addObject("orderList",ordersServiceInt.getOrderListForManager(customerName, selectedDateRange, technicianEmail, orderNumber));
				
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("countNewOrders",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Pending"));
				model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Approved"));
				model.addObject("countShippedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Shipped"));
				model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Received"));
				model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Declined"));;
				
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				

				if (technicianEmail != "All Technicians") {
					model.addObject("selectedTechnician",
							employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

				}

				model.addObject("selectedName", customerName);
				model.addObject("newDate", selectedDateRange);
				model.addObject("heading", heading);
				model.setViewName("userordermanagement");
				
			}
			else if (userName.getRole().equalsIgnoreCase("Technician")) {
				
				model.addObject("orderList",ordersServiceInt.getOrderListForManager(customerName, selectedDateRange, userName.getEmail(), orderNumber));
				model.addObject("customers",customerServiceInt.getClientList());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers(userName.getEmail()));
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.addObject("dates", ordersServiceInt.getDates());
				
				model.addObject("countNewOrders", ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Pending"));
				model.addObject("countOrdersReceive",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Shipped"));
				model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Approved"));
				model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Declined"));
				model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Received"));
				
				if (technicianEmail != "All Technicians") {
					model.addObject("selectedTechnician",
							employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

				}

				model.addObject("selectedName", customerName);
				model.addObject("newDate", selectedDateRange);
				model.addObject("heading", heading);
				model.setViewName("ordertechmanagement");
				
			} 
		}
		
		else 
		{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value = "order", method = RequestMethod.GET)
	public ModelAndView loadOrder() {

		model = new ModelAndView("order");
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("makeOrder", new OrdersBean());
			model.addObject("compatibility",spareParts.getAllSparePartsWithoutZero());
			model.addObject("managersList",employeeServiceInt.getAllManagers());
			model.addObject("ticketCount", ticketsServiceInt.ticketCountForTechnician(userName.getEmail()));
			model.addObject("customerList",customerServiceInt.getClientList());
			
			model.setViewName("order");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "makeOrder", method = RequestMethod.POST)
	public ModelAndView makeOrder(@ModelAttribute("makeOrder") OrdersBean order) {
		model = new ModelAndView();
		String orders = "orders";
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			retMessage = ordersServiceInt.prepareOrderMaking(order);
			if (retMessage.startsWith("Order cannot be")) {
				String retErrorMessage = retMessage;
				model.addObject("retErrorMessage", retErrorMessage);
			} else {
				model.addObject("retMessage", retMessage);
			}
			model.addObject("ticketCount", ticketsServiceInt.ticketCountForTechnician(userName.getEmail()));			
			if (userName.getRole().equalsIgnoreCase("Manager")|| userName.getRole().equalsIgnoreCase("Admin")) {
				model.addObject("orders", orders);
				model.setViewName("confirmations");				
			} else if (userName.getRole().equalsIgnoreCase("Technician")) {
				model.addObject("orders", orders);
				model.setViewName("confirmation");				
			} else if(userName.getRole().equalsIgnoreCase("User")){
				model.addObject("orders", orders);
				model.setViewName("confirm");
			}

		} else {
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value = "placeOrderForTechnician", method = RequestMethod.GET)
	public ModelAndView loadMakeOrder() {

		model = new ModelAndView("placeOrderForTechnician");
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("placeOrderForTechnician", new OrdersBean());
			model.addObject("compatibility",spareParts.getAllSparePartsWithoutZero());
			model.addObject("technicianList",employeeServiceInt.getAllTechnicians());			
			model.addObject("managersList", employeeServiceInt.getAllManagers());
			model.addObject("customerList", customerServiceInt.getClientList());
			
			model.setViewName("placeOrderForTechnician");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "userPlaceOrder", method = RequestMethod.GET)
	public ModelAndView loaduserMakeOrder() {

		model = new ModelAndView("userPlaceOrder");
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("userPlaceOrder", new OrdersBean());
			model.addObject("compatibility",spareParts.getAllSparePartsWithoutZero());
			model.addObject("technicianList",employeeServiceInt.getAllTechnicians());
			model.addObject("managersList", employeeServiceInt.getAllManagers());
			model.addObject("customerList", customerServiceInt.getClientList());
			
			model.setViewName("userPlaceOrder");
			
		} else {
			model.setViewName("login");
		}

		return model;
	}
	
	@RequestMapping(value = { "orderItemHistory", "ordersItemHistory", "userOrderItemHistory"}, method = RequestMethod.GET)
	public ModelAndView orderHistory(@RequestParam("recordID") Long recordID) {
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			
				OrderHeader ord = ordersServiceInt.getOrder(recordID);
				Employee appoer = employeeServiceInt.getEmployeeByEmpNumber(ord.getApprover());
				String approverName = appoer.getFirstName() + " "+ appoer.getLastName();
				model.addObject("pendingOrderList",orderDetailsInt.getOrderDetailsByOrderNum(recordID));
				model.addObject("OrderNum", ordersServiceInt.getOrder(recordID));
				model.addObject("status",historyInt.getAllOrderHistoryByOrderNumber(recordID));
				model.addObject("ticketCount", ticketsServiceInt.ticketCountForTechnician(userName.getEmail()));
				model.addObject("velaphandaAddres", profile.getVelaphandaAddress("Velaphanda Trading & Projects"));
				model.addObject("approver", approverName);

			if (userName.getRole().equalsIgnoreCase("Technician")) {
				
				model.setViewName("orderItemHistory"); 
				
			} else if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {
				if (userName.getEmail().equalsIgnoreCase(appoer.getEmail()) )
				{
					model.addObject("isApprover", "True");
				}
				model.addObject("pendingOrderList",	orderDetailsInt.getOrderDetailsByOrderNum(recordID));
				model.addObject("OrderNum", ordersServiceInt.getOrder(recordID));
				model.addObject("ticketCount", ticketsServiceInt.ticketCountForTechnician(userName.getEmail()));
				model.addObject("velaphandaAddres", profile.getVelaphandaAddress("Velaphanda Trading & Projects"));
				model.addObject("status",historyInt.getAllOrderHistoryByOrderNumber(recordID));
				
				model.setViewName("ordersItemHistory");
				
			}else if(userName.getRole().equalsIgnoreCase("User")){
				
				model.addObject("pendingOrderList",orderDetailsInt.getOrderDetailsByOrderNum(recordID));
				model.addObject("OrderNum", ordersServiceInt.getOrder(recordID));
			    model.addObject("ticketCount", ticketsServiceInt.ticketCountForTechnician(userName.getEmail()));
				model.addObject("velaphandaAddres", profile.getVelaphandaAddress("Velaphanda Trading & Projects"));
				model.addObject("status",historyInt.getAllOrderHistoryByOrderNumber(recordID));
				
				model.setViewName("userOrderItemHistory");
			}

		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping("approveOrder")
	public ModelAndView getOrderDetails(@RequestParam Long recordID,
			@ModelAttribute OrderHeader orderHeader) {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			orderHeader = ordersServiceInt.getOrder(recordID);
			if (orderHeader != null) {
				model.setViewName("orderUpdate");				
				model.addObject("orderObject", orderHeader);
				
			} else {

			}
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping("updateOrder")
	public ModelAndView updateOrder(OrdersBean order) {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			retMessage = ordersServiceInt.updateOrder(order);
			model.addObject("retMessage", retMessage);
			model.setViewName("orderUpdate");
		} else {
			model.setViewName("login");
		}
		return model;

	}

	@RequestMapping("approvedOrders")
	public ModelAndView approvedOrders() {
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("orderList", ordersServiceInt.getOpenOrders());
			
			model.setViewName("approvedOrders");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping("displayOrders")
	public ModelAndView displayOrders() {
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("pendingOrderList",
					ordersServiceInt.getAllOrders(userName.getEmail()));
			model.setViewName("displayOrders");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "pendingOrders", method = RequestMethod.GET)
	public ModelAndView displayPendingOrders() {
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("pendingOrderList",
					ordersServiceInt.pendingOrders(userName.getEmail()));
		    model.setViewName("pendingOrders");
		} else {
			model.setViewName("login");
		}

		
		return model;
	}

	@RequestMapping(value = "approveOrder", method = RequestMethod.GET)
	public ModelAndView approveOrder(
			@RequestParam("recordID") Long recordID,
			@ModelAttribute OrderDetails orderDetails) {
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("pendingOrderList",
					orderDetailsInt.getOrderDetailsByOrderNum(recordID));
			model.addObject("RecordID", ordersServiceInt.getOrder(recordID));
			
			model.setViewName("approveOrder");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "approveOrderItems")
	public ModelAndView approveOrderItems(
			@RequestParam("recordID") Long recordID) {
		model = new ModelAndView();
		String approverOrders = "approverOrders";

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage",
					ordersServiceInt.approveOrder(recordID));
			
			model.addObject("approverOrders", approverOrders);
			model.setViewName("confirmations");
			// model.setViewName("approveOrder");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "detailedOrders", method = RequestMethod.GET)
	public ModelAndView detailedOrders(
			@RequestParam("recordID") Long recordID) {
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("pendingOrderList",orderDetailsInt.getOrderDetailsByOrderNum(recordID));
			model.addObject("OrderNum", ordersServiceInt.getOrder(recordID));
			model.setViewName("detailedOrders");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "deliveryNote", method = RequestMethod.GET)
	public ModelAndView f(@RequestParam("recordID") Long recordID) {
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			OrderHeader order = ordersServiceInt.getOrder(recordID);
			List<OrderDetails> list = orderDetailsInt
					.getOrderDetailsByOrderNum("key", recordID);
			model.addObject("pendingOrderList", list);
			model.addObject("OrderNum", order);

			model.addObject("recordID", recordID);
			model.addObject("contactPerson", contactDetailsServiceInt
					.getContactPerson(userName.getFirstName()));
			// model.setViewName("deliveryNote");
		} else {
			model.setViewName("login");
		}

		return model;
	}

@RequestMapping(value = "printdeliveryNote", method = RequestMethod.GET)
	public ModelAndView deliveriesNote(
			@RequestParam("recordID") Long recordID) throws Exception {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if(userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")){
				OrderHeader order = ordersServiceInt.getOrder(recordID);
				List<OrderDetails> list = orderDetailsInt.getOrderDetailsByOrderNum("key", recordID);
				model.addObject("pendingOrderList", list);
				model.addObject("OrderNum", order);
				model.addObject("recordID", recordID);
				model.addObject("contactPerson", contactDetailsServiceInt.getContactPerson(userName.getFirstName()));
				model.setViewName("ordersItemHistory");
			}else if (userName.getRole().equalsIgnoreCase("Technician")){
				OrderHeader order = ordersServiceInt.getOrder(recordID);
				List<OrderDetails> list = orderDetailsInt.getOrderDetailsByOrderNum("key", recordID);
				model.addObject("pendingOrderList", list);
				model.addObject("OrderNum", order);
				model.addObject("recordID", recordID);
				model.addObject("contactPerson", contactDetailsServiceInt.getContactPerson(userName.getFirstName()));
				model.setViewName("orderItemHistory");
			}else if (userName.getRole().equalsIgnoreCase("User")){
				OrderHeader order = ordersServiceInt.getOrder(recordID);
				List<OrderDetails> list = orderDetailsInt.getOrderDetailsByOrderNum("key", recordID);
				model.addObject("pendingOrderList", list);
				model.addObject("OrderNum", order);
				model.addObject("recordID", recordID);
				model.addObject("contactPerson", contactDetailsServiceInt.getContactPerson(userName.getFirstName()));				
				model.setViewName("userOrderItemHistory");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "availableStock", method = RequestMethod.GET)
	public ModelAndView availableStock() {
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("availableOrders", siteStock.getAllOrders());
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.setViewName("availableStock");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "availableBootStock", method = RequestMethod.GET)
	public ModelAndView availableBootStock() {
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			String technician = userName.getFirstName() + " "
					+ userName.getLastName();
			model.addObject("availableOrders",
					bootStock.getAllOrders(technician));
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.setViewName("availableBootStock");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "viewApprovedOrders", method = RequestMethod.GET)
	public ModelAndView LoadViewApprovedOrders() {

		model = new ModelAndView("viewApprovedOrders");
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("shipment",
					ordersServiceInt.shippedOrders(userName.getEmail()));
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.setViewName("viewApprovedOrders");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = { "adminOrderHistory", "techOrderHistory" })
	public ModelAndView LoadOrderHistoryByDate(
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {
		model = new ModelAndView("orderHistory");
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("orderList",
					ordersServiceInt.getAllOrders(userName.getEmail()));
			
			if (userName.getRole().equalsIgnoreCase("Technician")) {
				model.addObject("orderList", ordersServiceInt.getAllOrders(
						startDate, endDate, userName.getEmail()));
				model.setViewName("orderHistory");
			}

			else if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {
				model.addObject("orderList",ordersServiceInt.getAllOrders(startDate, endDate));
				model.addObject("ticketCount", ticketsServiceInt.ticketCountForTechnician(userName.getEmail()));				
				model.setViewName("ordersHistory");
			}

		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = { "orderHistory", "ordersHistory" }, method = RequestMethod.GET)
	public ModelAndView LoadOrderHistory() {

		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			
			if (userName.getRole().equalsIgnoreCase("Technician")) {
				List<OrderHeader> list = ordersServiceInt
						.getAllOrdersByDate(userName.getEmail());
				System.out.println();
				model.addObject("orderList", list);
				model.setViewName("orderHistory");
			}

			else if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				model.addObject("orderList",
						ordersServiceInt.getAllOrdersByDate());
				model.addObject("ticketCount", ticketsServiceInt
						.ticketCountForTechnician(userName.getEmail()));
				
				model.setViewName("ordersHistory");
			}

		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "shipment")
	public ModelAndView shipment(@RequestParam("recordID") Long recordID) {
		model = new ModelAndView();
		String shipOrder = "shipOrder";

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage",
					ordersServiceInt.approveShipment(recordID));
			model.addObject("shipOrder", shipOrder);
			model.setViewName("confirmations");
		} else {
			model.setViewName("login");
		}
		return model;
	}

	@RequestMapping(value = "receive")
	public ModelAndView receive(@RequestParam("recordID") Long recordID) {
		model = new ModelAndView();

		String receiveOrder = "receiveOrder";

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			model.addObject("retMessage",
					ordersServiceInt.approveShipment(recordID));
			model.addObject("receiveOrder", receiveOrder);
			model.setViewName("confirmation");
		} else {
			model.setViewName("login");
		}
		return model;
	}


	@RequestMapping(value = "shipmentReceived")
	public ModelAndView shipmentReceived(@RequestParam("recordID") Long recordID) {
		String receiveOrder = "receiveOrder";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("ticketCount", ticketsServiceInt
					.ticketCountForTechnician(userName.getEmail()));
			model.addObject("retMessage",
					ordersServiceInt.approveShipment(recordID));
			model.addObject("receiveOrder", receiveOrder);
			model.setViewName("confirmations");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	

	@RequestMapping(value = "declineOrder", method = RequestMethod.GET)
	public ModelAndView displayDeclineOrders(@RequestParam("recordID") Long recordID) {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("declinedOrder", new OrdersBean());
			model.addObject("OrderNum", ordersServiceInt.getOrder(recordID));			
			model.setViewName("declineOrder");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "declinedOrder", method = RequestMethod.POST)
	public ModelAndView declineOrders(
			@ModelAttribute("declinedOrder") OrdersBean order) {
		String declineOrder = "declineOrder";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			retMessage = ordersServiceInt.declineOrder(order.getRecordID(),order.getComments());
			model.addObject("retMessage", retMessage);			
			model.addObject("declineOrder", declineOrder);
			model.setViewName("confirmations");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "viewAllOrders", method = RequestMethod.GET)
	public ModelAndView viewOrders() {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("orders", ordersServiceInt.getAllOrders());
			model.setViewName("viewAllOrders");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "viewAllUserOrders", method = RequestMethod.GET)
	public ModelAndView viewUserOrders() {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("orders", ordersServiceInt.getAllOrders());
			model.setViewName("viewAllUserOrders");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "viewAllOrderDetails", method = RequestMethod.GET)
	public ModelAndView displayOrderDeails(
			@RequestParam("recordID") Long recordID,
			@ModelAttribute OrderDetails orderDetails) {
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("pendingOrderList",
					orderDetailsInt.getOrderDetailsByOrderNum(recordID));
			model.addObject("RecordID", ordersServiceInt.getOrder(recordID));
			model.setViewName("viewAllOrderDetails");
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "viewAllUserOrderDetails", method = RequestMethod.GET)
	public ModelAndView displayUserOrderDeails(
			@RequestParam("recordID") Long recordID,
			@ModelAttribute OrderDetails orderDetails) {
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			model.addObject("pendingOrderList",
					orderDetailsInt.getOrderDetailsByOrderNum(recordID));
			model.addObject("RecordID", ordersServiceInt.getOrder(recordID));
			model.setViewName("viewAllUserOrderDetails");
		} else {
			model.setViewName("login");
		}

		return model;
	}
	

	@RequestMapping(value = "searchOrder")
	public ModelAndView searchOrder(@RequestParam("orderNumber") String localOrderNumber,
			@RequestParam("customerName") String localCustomerName,
			@RequestParam("technicianName") String localTechnicianName,
			@RequestParam("selectDateRange") String localSelectDateRange) {
		orderNumber = null;
		heading = "All Orders";
		selectedDateRange = localSelectDateRange;
		customerName = localCustomerName;
		technicianEmail = localTechnicianName;
		
		if (localOrderNumber != null)
		{
			if (localOrderNumber.length() > 4)
			{
				orderNumber = ticketsServiceInt.convertTicketToLong(localOrderNumber);
			}
		}

		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		orderNum = "";
		
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {

				model.addObject("orderList",ordersServiceInt.getOrderListForManager(customerName, selectedDateRange, technicianEmail, orderNumber));
				
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("countNewOrders",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Pending"));
				model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Approved"));
				model.addObject("countShippedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Shipped"));
				model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Received"));
				model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Declined"));;
				
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				

				if (technicianEmail != "All Technicians") {
					model.addObject("selectedTechnician",
							employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

				}

				model.addObject("selectedName", customerName);
				model.addObject("newDate", selectedDateRange);
				model.addObject("heading", heading);
				model.setViewName("ordermanagement");
			}
			else if (userName.getRole().equalsIgnoreCase("User")) {


				model.addObject("orderList",ordersServiceInt.getOrderListForManager(customerName, selectedDateRange, technicianEmail, orderNumber));
				
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("countNewOrders",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Pending"));
				model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Approved"));
				model.addObject("countShippedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Shipped"));
				model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Received"));
				model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Declined"));;
				
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				

				if (technicianEmail != "All Technicians") {
					model.addObject("selectedTechnician",
							employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

				}

				model.addObject("selectedName", customerName);
				model.addObject("newDate", selectedDateRange);
				model.addObject("heading", heading);
				model.setViewName("userordermanagement");
			}
		} 
		else 
		{
			model.setViewName("login");
		}

		return model;
	}
	
	@RequestMapping(value = "searchOrderForTech")
	public ModelAndView searchOrderForTech(@RequestParam("orderNumber") String localOrderNumber,
			@RequestParam("customerName") String localCustomerName,
			@RequestParam("selectDateRange") String localSelectDateRange) {
		orderNumber = null;
		heading = "All Orders";
		selectedDateRange = localSelectDateRange;
		customerName = localCustomerName;
		
		if (localOrderNumber != null)
		{
			if (localOrderNumber.length() > 4)
			{
				orderNumber = ticketsServiceInt.convertTicketToLong(localOrderNumber);
			}
		}
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		orderNum = "";
		
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Technician")) {

				model.addObject("orderList",ordersServiceInt.getOrderListForManager(customerName, selectedDateRange, userName.getEmail(), orderNumber));
				model.addObject("customers",customerServiceInt.getClientList());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers(userName.getEmail()));
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.addObject("dates", ordersServiceInt.getDates());
				

				model.addObject("countNewOrders", ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Pending"));
				model.addObject("countOrdersReceive",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Shipped"));
				model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Approved"));
				model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Declined"));
				model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Received"));


				if (technicianEmail != "All Technicians") {
					model.addObject("selectedTechnician",
							employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

				}

				model.addObject("selectedName", customerName);
				model.addObject("newDate", selectedDateRange);
				model.addObject("heading", heading);
				model.setViewName("ordertechmanagement");
			} 
		} else {
			model.setViewName("login");
		}

		return model;
	}


	

	@RequestMapping(value = "ordersToApprove", method = RequestMethod.GET)
	public ModelAndView ordersToApprove() {
		heading = "Pending";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (customerName != null) {
				if (customerName.length() > 3) {
	
					
					model.addObject("orderList",ordersServiceInt.getOrderListByStatusForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Pending"));
					
					model.addObject("dates", ordersServiceInt.getDates());
					model.addObject("countNewOrders",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Pending"));
					model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Approved"));
					model.addObject("countShippedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Shipped"));
					model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Received"));
					model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Declined"));;
					
					model.addObject("customers", customerServiceInt.getClientList());
					model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					

					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician",
								employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);	
					model.addObject("heading", heading);
				}
			} else if (selectedDateRange != null) {
				model.addObject("newDate", selectedDateRange);
				model.addObject("heading", heading);
				model.addObject("countNewOrders", ordersServiceInt.countNewOrdersForSelectedDate(selectedDateRange));
				model.addObject("countApprovedOrder", ordersServiceInt.countApprovedOrdersForSelectedDate(selectedDateRange));
				model.addObject("countShippedOrder", ordersServiceInt.countShippedOrdersForSelectedDate(selectedDateRange));
				model.addObject("countClosedOrder", ordersServiceInt.countClosedOrderForSelectedDate(selectedDateRange));
				model.addObject("countRejectedOrder", ordersServiceInt.countRejectedOrderForSelectedDate(selectedDateRange));
				model.addObject("countOrdersReceive", ordersServiceInt.countOrdersReceiveForSelectedDate(selectedDateRange));

				model.addObject("orderList",ordersServiceInt.getLastFourteenDaysPendingOrdersForSelectedDate(selectedDateRange));

				model.addObject("pendingOrderList",ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				}else if(tempEmployee!=null){
				
				model.addObject("countNewOrders", ordersServiceInt.countNewOrdersForCustomerNewSearch(tempEmployee.getEmail()));
				model.addObject("countApprovedOrder", ordersServiceInt.countApprovedOrdersForCustomerNewSearch(tempEmployee.getEmail()));
				model.addObject("countShippedOrder", ordersServiceInt.countShippedOrdersForCustomerNewSearch(tempEmployee.getEmail()));
				model.addObject("countClosedOrder", ordersServiceInt.countClosedOrderForCustomerNewSearch(tempEmployee.getEmail()));
				model.addObject("countRejectedOrder", ordersServiceInt.countRejectedOrderForCustomerNewSearch(tempEmployee.getEmail()));
				model.addObject("customers",customerServiceInt.getClientList());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.addObject("orderList",ordersServiceInt.getLastFourteenDaysPendingOrdersForCustomerNewSearch(tempEmployee.getEmail()));
				
				model.addObject("selectedTechnician", tempEmployee);
				
				
			} else {

				model.addObject("countNewOrders",ordersServiceInt.countNewOrders(""));
				model.addObject("countApprovedOrder",ordersServiceInt.countApprovedOrders(""));
				model.addObject("countShippedOrder",ordersServiceInt.countShippedOrders(""));
				model.addObject("countClosedOrder",ordersServiceInt.countClosedOrder(""));
				model.addObject("countRejectedOrder",ordersServiceInt.countRejectedOrders(""));
				model.addObject("orderList",ordersServiceInt.getLastFourteenDaysPendingOrders());

			}

			model.addObject("pendingOrderList",ordersServiceInt.pendingOrders(userName.getEmail()));
			model.addObject("customers", customerServiceInt.getClientList());
			model.addObject("technicians",employeeServiceInt.getAllTechnicians());
			model.addObject("dates", ordersServiceInt.getDates());
			model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
			model.setViewName("ordermanagement");
		} else {
			model.setViewName("login");
		}

		return model;
	}
	
	

	@RequestMapping(value = "ordersToShip", method = RequestMethod.GET)
	public ModelAndView ordersToShip() {
		heading = "Approved";
		model = new ModelAndView();
		
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			
			if (customerName != null) {

				model.addObject("orderList",ordersServiceInt.getOrderListByStatusForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Approved"));
				
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("countNewOrders",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Pending"));
				model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Approved"));
				model.addObject("countShippedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Shipped"));
				model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Received"));
				model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Declined"));;
				
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				

				if (technicianEmail != "All Technicians") {
					model.addObject("selectedTechnician",
							employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

				}

				model.addObject("selectedName", customerName);
				model.addObject("newDate", selectedDateRange);	
				model.addObject("heading", heading);
				
			}else if(selectedDateRange != null){
				model.addObject("newDate", selectedDateRange);
				model.addObject("heading", heading);
				model.addObject("countNewOrders", ordersServiceInt.countNewOrdersForSelectedDate(selectedDateRange));
				model.addObject("countApprovedOrder",ordersServiceInt.countApprovedOrdersForSelectedDate(selectedDateRange));
				model.addObject("countShippedOrder",ordersServiceInt.countShippedOrdersForSelectedDate(selectedDateRange));
				model.addObject("countClosedOrder", ordersServiceInt.countClosedOrderForSelectedDate(selectedDateRange));
				model.addObject("countRejectedOrder",ordersServiceInt.countRejectedOrderForSelectedDate(selectedDateRange));
				model.addObject("countOrdersReceive",ordersServiceInt.countOrdersReceiveForSelectedDate(selectedDateRange));

				model.addObject("orderList",ordersServiceInt.getLastFourteenDaysApprovedOrdersForSelectedDate(selectedDateRange));

				model.addObject("pendingOrderList",ordersServiceInt.pendingOrders(userName.getEmail()));
				
			}else if(tempEmployee !=null){
				System.err.println(tempEmployee.getFirstName());
				
				model.addObject("countNewOrders", ordersServiceInt.countNewOrdersForCustomerNewSearch(tempEmployee.getEmail()));
				model.addObject("countApprovedOrder", ordersServiceInt.countApprovedOrdersForCustomerNewSearch(tempEmployee.getEmail()));
				model.addObject("countShippedOrder", ordersServiceInt.countShippedOrdersForCustomerNewSearch(tempEmployee.getEmail()));
				model.addObject("countClosedOrder", ordersServiceInt.countClosedOrderForCustomerNewSearch(tempEmployee.getEmail()));
				model.addObject("countRejectedOrder", ordersServiceInt.countRejectedOrderForCustomerNewSearch(tempEmployee.getEmail()));
				model.addObject("customers",customerServiceInt.getClientList());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.addObject("orderList",ordersServiceInt.getLastFourteenDaysApprovedOrdersForCustomerNewSearch(tempEmployee.getEmail()));
				model.addObject("selectedTechnician", tempEmployee);
				
			}
			else{
				model.addObject("newDate", selectedDateRange);
				model.addObject("heading", heading);
				model.addObject("countNewOrders",ordersServiceInt.countNewOrders(""));
				model.addObject("countApprovedOrder",ordersServiceInt.countApprovedOrders(""));
				model.addObject("countShippedOrder",ordersServiceInt.countShippedOrders(""));
				model.addObject("countClosedOrder",ordersServiceInt.countClosedOrder(""));
				model.addObject("countRejectedOrder",ordersServiceInt.countRejectedOrders(""));

				model.addObject("orderList",ordersServiceInt.getLastFourteenDaysApprovedOrders());
			}
			model.addObject("pendingOrderList",ordersServiceInt.pendingOrders(userName.getEmail()));
			model.addObject("customers", customerServiceInt.getClientList());
			model.addObject("technicians",employeeServiceInt.getAllTechnicians());
			model.addObject("dates", ordersServiceInt.getDates());
			model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
			model.setViewName("ordermanagement");
		} else {
			model.setViewName("login");
		}

		return model;
	}
	
	@RequestMapping(value = "ShippedOrders", method = RequestMethod.GET)
	public ModelAndView shippedOrderDetails() {
		heading ="Shipped";
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Manager")
					|| userName.getRole().equalsIgnoreCase("Admin")) {
				if (customerName != null) {
					
					model.addObject("orderList",ordersServiceInt.getOrderListByStatusForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Shipped"));
					
					model.addObject("dates", ordersServiceInt.getDates());
					model.addObject("countNewOrders",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Pending"));
					model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Approved"));
					model.addObject("countShippedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Shipped"));
					model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Received"));
					model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Declined"));
					
					model.addObject("customers", customerServiceInt.getClientList());
					model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					

					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician",
								employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);

					
				} else if (selectedDateRange != null) {
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);
					model.addObject("countNewOrders", ordersServiceInt.countNewOrdersForSelectedDate(selectedDateRange));
					model.addObject("countApprovedOrder",ordersServiceInt.countApprovedOrdersForSelectedDate(selectedDateRange));
					model.addObject("countShippedOrder",ordersServiceInt.countShippedOrdersForSelectedDate(selectedDateRange));
					model.addObject("countClosedOrder", ordersServiceInt.countClosedOrderForSelectedDate(selectedDateRange));
					model.addObject("countRejectedOrder",ordersServiceInt.countRejectedOrderForSelectedDate(selectedDateRange));
					model.addObject("countOrdersReceive",ordersServiceInt.countOrdersReceiveForSelectedDate(selectedDateRange));

					model.addObject("orderList",ordersServiceInt.getLastFourteenDaysShippedOrdersForSelectedDate(selectedDateRange));

					model.addObject("pendingOrderList",ordersServiceInt.pendingOrders(userName.getEmail()));
				}else if(tempEmployee !=null){
					model.addObject("countNewOrders", ordersServiceInt.countNewOrdersForCustomerNewSearch(tempEmployee.getEmail()));
					model.addObject("countApprovedOrder", ordersServiceInt.countApprovedOrdersForCustomerNewSearch(tempEmployee.getEmail()));
					model.addObject("countShippedOrder", ordersServiceInt.countShippedOrdersForCustomerNewSearch(tempEmployee.getEmail()));
					model.addObject("countClosedOrder", ordersServiceInt.countClosedOrderForCustomerNewSearch(tempEmployee.getEmail()));
					model.addObject("countRejectedOrder", ordersServiceInt.countRejectedOrderForCustomerNewSearch(tempEmployee.getEmail()));
					model.addObject("customers",customerServiceInt.getClientList());
					model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
					model.addObject("dates", ordersServiceInt.getDates());
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("orderList",ordersServiceInt.getLastFourteenDaysShippedOrdersForCustomerNewSearch(tempEmployee.getEmail()));
					model.addObject("selectedTechnician", tempEmployee);
				}

				else {
					model.addObject("countNewOrders",ordersServiceInt.countNewOrders(""));
					model.addObject("countApprovedOrder",ordersServiceInt.countApprovedOrders(""));
					model.addObject("countShippedOrder",ordersServiceInt.countShippedOrders(""));
					model.addObject("countClosedOrder",ordersServiceInt.countClosedOrder(""));
					model.addObject("countRejectedOrder",ordersServiceInt.countRejectedOrders(""));

					model.addObject("orderList",ordersServiceInt.getLastFourteenDaysShippedOrders());
				}
				model.addObject("pendingOrderList",ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.setViewName("ordermanagement");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	@RequestMapping(value = "closedOrders", method = RequestMethod.GET)
	public ModelAndView closedOrders() {
		heading = "Received";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {

			if (userName.getRole().equalsIgnoreCase("Technician")) {
				if (customerName != null) {
					model.addObject("orderList",ordersServiceInt.getOrderListByStatusForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Closed"));
					
					model.addObject("orderList",ordersServiceInt.getOrderListByStatusForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber,  "Received"));
					model.addObject("customers",customerServiceInt.getClientList());
					model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers(userName.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", ordersServiceInt.getDates());
					

					model.addObject("countNewOrders", ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Pending"));
					model.addObject("countOrdersReceive",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Shipped"));
					model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Approved"));
					model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Declined"));
					model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Received"));


					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician",
								employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);
					model.setViewName("ordertechmanagement");
				}
			} else if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {
				if (customerName != null) {
					if (customerName.length() > 3) {
						model.addObject("orderList",ordersServiceInt.getOrderListByStatusForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Received"));
						
						model.addObject("dates", ordersServiceInt.getDates());
						model.addObject("countNewOrders",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Pending"));
						model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Approved"));
						model.addObject("countShippedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Shipped"));
						model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Received"));
						model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Declined"));;
						
						model.addObject("customers", customerServiceInt.getClientList());
						model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
						model.addObject("technicians",employeeServiceInt.getAllTechnicians());
						

						if (technicianEmail != "All Technicians") {
							model.addObject("selectedTechnician",
									employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

						}

						model.addObject("selectedName", customerName);
						model.addObject("newDate", selectedDateRange);
						model.addObject("heading", heading);
					}
				}

				model.addObject("pendingOrderList",ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
			
				model.setViewName("ordermanagement");
			} else {
				model.setViewName("login");
			}
		}

		return model;
	}
	
	//pending orders
	@RequestMapping(value = "pendingOrdersForTechnician", method = RequestMethod.GET)
	public ModelAndView pendingOrders() {
		heading = "Pending";
		model = new ModelAndView();
		
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {			
				
				if (customerName != null) {
					
					model.addObject("orderList",ordersServiceInt.getOrderListByStatusForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber,  "Pending"));
					model.addObject("customers",customerServiceInt.getClientList());
					model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers(userName.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", ordersServiceInt.getDates());
					

					model.addObject("countNewOrders", ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Pending"));
					model.addObject("countOrdersReceive",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Shipped"));
					model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Approved"));
					model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Declined"));
					model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Received"));


					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician",
								employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);
					model.setViewName("ordertechmanagement");
				}
		
		}else{
			model.setViewName("login");	
		}
		
		return model;
	}//pending orders
	
	
	@RequestMapping(value = "rejectedOrders", method = RequestMethod.GET)
	public ModelAndView rejectedOrders() {
		heading = "Declined";
		model = new ModelAndView();		
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if(userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")){
				if(customerName !=null){
					model.addObject("orderList",ordersServiceInt.getOrderListByStatusForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Declined"));
					
					model.addObject("dates", ordersServiceInt.getDates());
					model.addObject("countNewOrders",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Pending"));
					model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Approved"));
					model.addObject("countShippedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Shipped"));
					model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Closed"));
					model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForManager(customerName, selectedDateRange, technicianEmail, orderNumber, "Declined"));;
					
					model.addObject("customers", customerServiceInt.getClientList());
					model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					

					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician",
								employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);
					
					model.setViewName("ordermanagement");
				}
			
			}else if(userName.getRole().equalsIgnoreCase("Technician")){
				
				if (customerName != null) {
               	 
					model.addObject("orderList",ordersServiceInt.getOrderListByStatusForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber,  "Declined"));
					model.addObject("customers",customerServiceInt.getClientList());
					model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers(userName.getEmail()));
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("dates", ordersServiceInt.getDates());
					

					model.addObject("countNewOrders", ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Pending"));
					model.addObject("countOrdersReceive",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Shipped"));
					model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Approved"));
					model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Declined"));
					model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Received"));


					if (technicianEmail != "All Technicians") {
						model.addObject("selectedTechnician",
								employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

					}

					model.addObject("selectedName", customerName);
					model.addObject("newDate", selectedDateRange);
					model.addObject("heading", heading);
					model.setViewName("ordertechmanagement");
				}
			}
		}else{
			model.setViewName("login");	
		}
				
		
		return model;
	}
		
	
	@RequestMapping(value = "OrdersToReceive", method = RequestMethod.GET)
	public ModelAndView receiveOrders() {
		heading = "Shipped";
		model = new ModelAndView();		
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			
			if (customerName != null) {
				
				model.addObject("orderList",ordersServiceInt.getOrderListByStatusForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber,  "Shipped"));
				model.addObject("customers",customerServiceInt.getClientList());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers(userName.getEmail()));
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.addObject("dates", ordersServiceInt.getDates());
				

				model.addObject("countNewOrders", ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Pending"));
				model.addObject("countOrdersReceive",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Shipped"));
				model.addObject("countApprovedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Approved"));
				model.addObject("countRejectedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Declined"));
				model.addObject("countClosedOrder",ordersServiceInt.getOrderCountForTechnician(customerName, selectedDateRange, userName.getEmail(), orderNumber, "Received"));


				if (technicianEmail != "All Technicians") {
					model.addObject("selectedTechnician",
							employeeDaoInt.getEmployeeByEmpNum(technicianEmail));

				}

				model.addObject("selectedName", customerName);
				model.addObject("newDate", selectedDateRange);
				model.addObject("heading", heading);
				model.setViewName("ordertechmanagement");
					
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}

	
	@RequestMapping(value = "getCustomerName", method = RequestMethod.GET)
	public ModelAndView getCustomerName(@RequestParam("customerName") String localCustomerName) {
		customerName = localCustomerName;
		selectedDateRange = null;
		tempEmployee = null;
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Manager")|| userName.getRole().equalsIgnoreCase("Admin")) {
				
				model.addObject("countNewOrders", ordersServiceInt.countNewOrdersForCustomer(customerName));
				model.addObject("countApprovedOrder", ordersServiceInt.countApprovedOrdersForCustomer(customerName));
				model.addObject("countShippedOrder", ordersServiceInt.countShippedOrdersForCustomer(customerName));
				model.addObject("countClosedOrder", ordersServiceInt.countClosedOrderForCustomer(customerName));
				model.addObject("countRejectedOrder", ordersServiceInt.countRejectedOrderForCustomer(customerName));

				model.addObject("orderList",ordersServiceInt.getLastFourteenDaysOrdersForCustomer(localCustomerName));
				model.addObject("pendingOrderList",ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("customers",customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.addObject("selectedName", customerName);
				model.setViewName("ordermanagement");

			} else if (userName.getRole().equalsIgnoreCase("Technician")) {

				model.addObject("countNewOrders", ordersServiceInt.countNewOrdersForTechnician_Customer(userName.getEmail(), customerName));

				model.addObject("countClosedOrder", ordersServiceInt.countClosedOrderForTechnician_Customer(userName.getEmail(), customerName));
				model.addObject("countRejectedOrder", ordersServiceInt.countRejectedOrderForTechnicianCustomer(userName.getEmail(), customerName));
				model.addObject("countOrdersReceive", ordersServiceInt.countOrdersReceiveForTechnician_Customer(userName.getEmail(), customerName));
				model.addObject("pendingOrderList",ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("ticketCount", ticketsServiceInt.ticketCountForTechnician(userName.getEmail()));
				model.addObject("selectedName", customerName);
				model.addObject("customers",customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers(userName.getEmail()));
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.addObject("selectedName", customerName);
                model.addObject("orderList", ordersServiceInt.getLastFourteenDaysOrdersForTechnicianCustomer(userName.getEmail(), customerName));

				model.setViewName("ordertechmanagement");
			}else if (userName.getRole().equalsIgnoreCase("User")) {

				model.addObject("countNewOrders", ordersServiceInt.countNewOrdersForCustomer(customerName));
				model.addObject("countApprovedOrder", ordersServiceInt.countApprovedOrdersForCustomer(customerName));
				model.addObject("countShippedOrder", ordersServiceInt.countShippedOrdersForCustomer(customerName));
				model.addObject("countClosedOrder", ordersServiceInt.countClosedOrderForCustomer(customerName));
				model.addObject("countRejectedOrder", ordersServiceInt.countRejectedOrderForCustomer(customerName));

				model.addObject("orderList",ordersServiceInt.getLastFourteenDaysOrdersForCustomer(localCustomerName));
				model.addObject("pendingOrderList",ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("customers", customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("customers",customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.addObject("selectedName", customerName);
				model.setViewName("userordermanagement");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}
	
	@RequestMapping(value = "getTechnicianName", method = RequestMethod.GET)
	public ModelAndView getTehnnicianName(
			@RequestParam("technicianName") String localTechnicianName) {
		tempEmployee = employeeServiceInt.getEmployeeByEmpNumber(localTechnicianName);
		selectedDateRange = null;
		customerName = null;
		model = new ModelAndView();

		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Manager")|| userName.getRole().equalsIgnoreCase("Admin")) {
				model.addObject("countNewOrders", ordersServiceInt.countNewOrdersForCustomerNewSearch(localTechnicianName));
				model.addObject("countApprovedOrder", ordersServiceInt.countApprovedOrdersForCustomerNewSearch(localTechnicianName));
				model.addObject("countShippedOrder", ordersServiceInt.countShippedOrdersForCustomerNewSearch(localTechnicianName));
				model.addObject("countClosedOrder", ordersServiceInt.countClosedOrderForCustomerNewSearch(localTechnicianName));
				model.addObject("countRejectedOrder", ordersServiceInt.countRejectedOrderForCustomerNewSearch(localTechnicianName));
				model.addObject("customers",customerServiceInt.getClientList());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.addObject("orderList",ordersServiceInt.getLastFourteenDaysOrdersForCustomerNewSearch(localTechnicianName));
				model.addObject("pendingOrderList",ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("selectedTechnician", tempEmployee);
				model.setViewName("ordermanagement");
			}else if (userName.getRole().equalsIgnoreCase("User")){
				model.addObject("countNewOrders", ordersServiceInt.countNewOrdersForCustomerNewSearch(localTechnicianName));
				model.addObject("countApprovedOrder", ordersServiceInt.countApprovedOrdersForCustomerNewSearch(localTechnicianName));
				model.addObject("countShippedOrder", ordersServiceInt.countShippedOrdersForCustomerNewSearch(localTechnicianName));
				model.addObject("countClosedOrder", ordersServiceInt.countClosedOrderForCustomerNewSearch(localTechnicianName));
				model.addObject("countRejectedOrder", ordersServiceInt.countRejectedOrderForCustomerNewSearch(localTechnicianName));
				model.addObject("customers",customerServiceInt.getClientList());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.addObject("orderList",ordersServiceInt.getLastFourteenDaysOrdersForCustomerNewSearch(localTechnicianName));
				model.addObject("pendingOrderList",ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("selectedTechnician", tempEmployee);
				model.setViewName("userordermanagement");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}
	@RequestMapping(value = "getUserSelectedDate", method = RequestMethod.GET)
	public ModelAndView getUserSelectedDate(
			@RequestParam("selectedDate") String localSelectedDate) {
		selectedDateRange = localSelectedDate;
		customerName = null;
		tempEmployee = null;
		userName = (Employee) session.getAttribute("loggedInUser");
		if (userName != null) {
			if (userName.getRole().equalsIgnoreCase("Manager")|| userName.getRole().equalsIgnoreCase("Admin")) {
				
				if (selectedDateRange != null) {
		
					model.addObject("countNewOrders", ordersServiceInt.countNewOrdersForSelectedDate(selectedDateRange));
					model.addObject("countApprovedOrder",ordersServiceInt.countApprovedOrdersForSelectedDate(selectedDateRange));
					model.addObject("countShippedOrder",ordersServiceInt.countShippedOrdersForSelectedDate(selectedDateRange));
					model.addObject("countClosedOrder", ordersServiceInt.countClosedOrderForSelectedDate(selectedDateRange));
					model.addObject("countRejectedOrder",ordersServiceInt.countRejectedOrderForSelectedDate(selectedDateRange));
					model.addObject("countOrdersReceive",ordersServiceInt.countOrdersReceiveForSelectedDate(selectedDateRange));

					model.addObject("orderList",ordersServiceInt.getLastFourteenDaysOrdersForSelectedDate(selectedDateRange));

					model.addObject("pendingOrderList",ordersServiceInt.pendingOrders(userName.getEmail()));
					model.addObject("customers",customerServiceInt.getClientList());
					model.addObject("dates", ordersServiceInt.getDates());
					model.addObject("technicians",employeeServiceInt.getAllTechnicians());
					model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers());
					model.addObject("newDate", selectedDateRange);
					model.setViewName("ordermanagement");
				}
			}else if (userName.getRole().equalsIgnoreCase("Technician")) {
				model.addObject("countNewOrders", ordersServiceInt.countNewOrdersForSelectedDate(userName.getEmail(),selectedDateRange));
				model.addObject("countApprovedOrder",ordersServiceInt.countApprovedOrdersForSelectedDate(userName.getEmail(),selectedDateRange));
				model.addObject("countShippedOrder",ordersServiceInt.countShippedOrdersForSelectedDate(userName.getEmail(),selectedDateRange));
				model.addObject("countClosedOrder", ordersServiceInt.countClosedOrderForSelectedDate(userName.getEmail(),selectedDateRange));
				model.addObject("countRejectedOrder",ordersServiceInt.countRejectedOrderForSelectedDate(userName.getEmail(),selectedDateRange));
				model.addObject("countOrdersReceive",ordersServiceInt.countOrdersReceiveForSelectedDate(userName.getEmail(),selectedDateRange));

				model.addObject("orderList",ordersServiceInt.getAllLastFourteenDaysOrdersForSelectedDate(userName.getEmail(),selectedDateRange));

				model.addObject("pendingOrderList",ordersServiceInt.pendingOrders(userName.getEmail()));
				model.addObject("customers",customerServiceInt.getClientList());
				model.addObject("dates", ordersServiceInt.getDates());
				model.addObject("technicians",employeeServiceInt.getAllTechnicians());
				model.addObject("orderNumbers", ordersServiceInt.getOrderNumbers(userName.getEmail()));
				model.addObject("newDate", selectedDateRange);
				model.setViewName("ordertechmanagement");
			}
		} else {
			model.setViewName("login");
		}

		return model;
	}
	@ExceptionHandler({FileNotFoundException.class})
    public ModelAndView dataIntegrity(Exception ex) {
        ModelAndView model = new ModelAndView("405");
 
        model.addObject("exception"," Create a folder 'VelaphandaReports' on C drive to save the reports");
        return model;
        
    }
	@ExceptionHandler({OutOfMemoryError.class})
    public ModelAndView PermGem(Exception ex) {
        ModelAndView model = new ModelAndView("405");
 
        model.addObject("exception","Please restart the server");
        return model;
    }
	@ExceptionHandler({DataIntegrityViolationException.class})
    public ModelAndView dataIntegrityViolation(Exception ex) {
        ModelAndView model = new ModelAndView("405");
 
        model.addObject("exception","Could not execute JDBC batch update");
        return model;
    }
}

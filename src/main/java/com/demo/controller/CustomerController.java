package com.demo.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo.bean.CustomerBean;
import com.demo.model.Customer;
import com.demo.model.Device;
import com.demo.model.Employee;
import com.demo.service.AccessoriesInt;
import com.demo.service.CustomerContactDetailsServiceInt;
import com.demo.service.CustomerServiceInt;
import com.demo.service.DeviceServiceInt;
import com.demo.service.OrdersServiceInt;
import com.demo.service.TicketsServiceInt;




@Controller
public class CustomerController {
	
	@Autowired
	private CustomerServiceInt customerServiceInt;
	@Autowired
	private DeviceServiceInt deviceServiceInt;
	@Autowired
	private CustomerContactDetailsServiceInt contactDetailsServiceInt;
	@Autowired
	private TicketsServiceInt ticketsServiceInt;
	/*Order inbox count on every page*/
	@Autowired
	private OrdersServiceInt ordersServiceInt;	
	
	@Autowired
	private HttpSession session;
	@Autowired
	private AccessoriesInt accessoriesInt;
	private String retMessage = null;
	ModelAndView model = null;
	List<Device> deviceList =null;
	Customer customer = null;
	Employee userName = null;
	
	@RequestMapping(value="addClient",method=RequestMethod.GET)
	public ModelAndView loadAddClient() {
		
	    model = new ModelAndView("addClient");
	    userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			model.addObject("saveClient", new CustomerBean());
			model.setViewName("addClient");		
		}
		else{
			model.setViewName("login");
		}
		
		return model;
	}
	@RequestMapping(value="saveClient",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView addClient(@ModelAttribute("saveClient")CustomerBean customerBean)
	{
		String addCustomer = "addCustomer";
	    model = new ModelAndView();
	    userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			model.addObject("retMessage",customerServiceInt.saveCustomer(customerBean));			
			model.addObject("addCustomer", addCustomer);
			model.setViewName("confirmations");
		}else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value="clientInformation",method=RequestMethod.GET)
	public ModelAndView loadClientInformation() {
		
	    model = new ModelAndView();
	    userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){		
			model.setViewName("clientInformation");
		}else{
			model.setViewName("login");
		}
		
		return model;
	}
	@RequestMapping(value="clientInformation")
	public ModelAndView searchClient(@RequestParam("clientName") String clientName,Integer offset, Integer maxResults) {
		model = new ModelAndView();
		
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			deviceList = deviceServiceInt.getAllEmployees(offset, maxResults, clientName);
		      for(Device dev:deviceList){
			       customer = dev.getCustomerDevice();
			      break;
		      }
		
		        model.addObject("clientInformation",deviceList );
				model.addObject("customer", customer);
				model.setViewName("clientInformation");
		}
		else{
			
			model.setViewName("login");
		}
		
		return model;
	}
	@RequestMapping(value="searchClientforProduct")
	public ModelAndView searchClientforProduct(@RequestParam("customerName") String customerName,@ModelAttribute Customer customer) {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
		
			customer = customerServiceInt.getClientByClientName(customerName);
			if(customer != null){
				model.addObject("customer", customer);
				model.addObject("escalatedTickets", ticketsServiceInt.countEscalatedTickets());
				model.addObject("awaitingSparesTickets", ticketsServiceInt.countAwaitingSparesTickets());
				model.addObject("inboxCount",ordersServiceInt.pendingOrdersCount(userName.getEmail()));
				model.addObject("customerContact",contactDetailsServiceInt.contactDetails(customerName));
			}
			else
			{
				model.addObject("retMessage", "Customer : " + customerName + " does not exist");
				model.addObject("customer", null);
			}
			model.addObject("escalatedTickets", ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets", ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("addProduct");
		}
		else{
			model.setViewName("login");
		}
		
		return model;
	}
	@RequestMapping(value="updateCustomer",method=RequestMethod.GET)
	public ModelAndView loadUpdateCustomerPage(){
		
		model = new ModelAndView("updateCustomer");
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
		
			model.addObject("updateCustomerData", new CustomerBean());
			model.addObject("awaitingSparesTickets", ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("escalatedTickets", ticketsServiceInt.countEscalatedTickets());
			model.addObject("inboxCount",ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("updateCustomer");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	@RequestMapping(value="updateCustomerData",method=RequestMethod.POST)
	public ModelAndView updateCustomer(@ModelAttribute("updateCustomerData")CustomerBean customerBean){
		String updateCustomer = "updateCustomer";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
		
			//retMessage =customerServiceInt.prepareCustomer(customerBean); 
			retMessage =customerServiceInt.updateCustomer(customerBean);
			model.addObject("retMessage", retMessage);
			model.addObject("escalatedTickets", ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets", ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.addObject("updateCustomer", updateCustomer);
			model.setViewName("confirmations");
		}
		else
		{
			model.setViewName("login");
		}
		return model;
	}
	@RequestMapping(value="searchCustomer")
	public ModelAndView searchCustomer(@RequestParam("customerName") String customerName,@ModelAttribute Customer customer) {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
		
			model.addObject("customer", customerServiceInt.contactDetails(customerName));
			model.addObject("customerDetails", contactDetailsServiceInt.contactDetails(customerName));
			model.addObject("escalatedTickets", ticketsServiceInt.countEscalatedTickets());
			model.addObject("awaitingSparesTickets", ticketsServiceInt.countAwaitingSparesTickets());
			model.addObject("inboxCount",ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("updateCustomer");
		}
		else{
			model.setViewName("login");
		}
		
		return model;
	}
	
	@RequestMapping(value={"viewCustomer","userViewCustomer"})
	public ModelAndView viewCustomer(@RequestParam("customerName") String customerName,@ModelAttribute Customer customer) {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {
				model.addObject("customer", customerServiceInt.contactDetails(customerName));
				model.addObject("customerDetails", contactDetailsServiceInt.contactDetails(customerName));			
				model.setViewName("viewCustomer");
			}
			else if (userName.getRole().equalsIgnoreCase("User")){
				model.addObject("customer", customerServiceInt.contactDetails(customerName));
				model.addObject("customerDetails", contactDetailsServiceInt.contactDetails(customerName));				
				model.setViewName("userViewCustomer");	
			}
			
		}
		else{
			model.setViewName("login");
		}
		
		return model;
	}
	/*@RequestMapping(value="viewCustomerDetails")
	public ModelAndView viewCustomerDetails(@RequestParam("customerName") String customerName,@ModelAttribute Customer customer) {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			model.addObject("customer", customerServiceInt.contactDetails(customerName));
			model.addObject("customerDetails", contactDetailsServiceInt.contactDetails(customerName));
			model.addObject("inboxCount",ordersServiceInt.pendingOrdersCount(userName.getEmail()));
			model.setViewName("viewCustomerDetails");
		}
		else{
			model.setViewName("login");
		}
		
		return model;
	}
	*/	
	
	@RequestMapping(value={"displayCustomers","userDisplayCustomers"},method=RequestMethod.GET)
	public ModelAndView displayCustomers(Integer offset,Integer maxResults){
		model= new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
		
			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {
				
				model.addObject("displayCustomers", customerServiceInt.getClientList());			
				model.setViewName("displayCustomers");
			
		   }else if(userName.getRole().equalsIgnoreCase("User")){			   
			   model.addObject("displayCustomers", customerServiceInt.getClientList());				
			   model.setViewName("userDisplayCustomers"); 
			   
		   }
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	@RequestMapping(value={"searchCustomerdevices", "userSearchCustomerDevices"})
	public ModelAndView searchCustomerDevices(@RequestParam("customerName") String customerName,@ModelAttribute Customer customer) {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){		
			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {
				model.addObject("deviceList",deviceServiceInt.getDeviceListByClientName(customerName));				
				model.setViewName("customerListDevices");
		   }else if(userName.getRole().equalsIgnoreCase("User")){			   
			   model.addObject("deviceList",deviceServiceInt.getDeviceListByClientName(customerName));			  
			   model.setViewName("userViewCustomerListDevices");
		   }
		}
		else{
			model.setViewName("login");
		}
		
		return model;
	}
}

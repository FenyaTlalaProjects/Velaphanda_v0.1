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

import com.demo.bean.DeviceBean;
import com.demo.model.Accessories;
import com.demo.model.Device;
import com.demo.model.Employee;
import com.demo.model.TicketHistory;
import com.demo.model.Tickets;
import com.demo.service.AccessoriesInt;
import com.demo.service.CustomerContactDetailsServiceInt;
import com.demo.service.CustomerServiceInt;
import com.demo.service.EmployeeServiceInt;
import com.demo.service.DeviceServiceInt;
import com.demo.service.LeaveInt;
import com.demo.service.OrdersServiceInt;
import com.demo.service.SiteStockInt;
import com.demo.service.SpareMasterServiceInt;
import com.demo.service.TicketHistoryInt;
import com.demo.service.TicketsServiceInt;


@Controller
public class DeviceController {
	
	@Autowired
	private SpareMasterServiceInt spareMasterServiceInt;
	@Autowired
	private TicketHistoryInt ticketHistoryInt;
	@Autowired
	private TicketsServiceInt logTicketService;
	@Autowired
	private OrdersServiceInt ordersServiceInt;
	@Autowired
	private DeviceServiceInt deviceServiceInt;
    @Autowired
    private CustomerServiceInt customerServiceInt;
    @Autowired
	private EmployeeServiceInt employeeServiceInt;
    @Autowired
    private AccessoriesInt accessoriesInt;
    @Autowired
	private TicketsServiceInt ticketsServiceInt;
    @Autowired
    private CustomerContactDetailsServiceInt contactDetailsServiceInt;
    @Autowired
    private LeaveInt leaveInt;
    @Autowired
    private SiteStockInt stockInt;
    
    @Autowired 
    HttpSession session;
    
    private List<Accessories> accessories=null;
    private Device device = null; 
    private	ModelAndView model = null;
    private Employee userName = null;
    private String retMessage = null;
    public String[] getSerials = null;
    public String[] getSerialNumbers = null;
    
   //Add Device
	@RequestMapping(value="addProduct", method=RequestMethod.GET)
	public ModelAndView loadSaveProduct()
	{
	    model = new ModelAndView();
	    userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
		
			model.addObject("saveProduct", new DeviceBean());
			model.setViewName("addProduct");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	//Save Device
	@RequestMapping(value="saveProduct", method=RequestMethod.POST)
	public ModelAndView saveProduct(@ModelAttribute("saveProduct")DeviceBean deviceBean){
		String addDevice ="addDevice";
		String customerName = deviceBean.getCustomerName();
		model = new ModelAndView();
		 userName = (Employee) session.getAttribute("loggedInUser");
			if(userName != null){
				model.addObject("customer", customerServiceInt.getClientByClientName(customerName));
				retMessage =deviceServiceInt.prepareDeviceData(deviceBean);
		        model.addObject("retMessage", retMessage);
		        model.addObject("customerName", customerName);
		        model.addObject("addDevice", addDevice);
		        model.setViewName("confirmations");
			}
			else{
				model.setViewName("login");
			}
		return model;
	}
	//Show Device
	@RequestMapping(value = {"showProducts"})
    public ModelAndView showProducts() {
		
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			model.addObject("productList", deviceServiceInt.getDeviceList());
			model.setViewName("showProducts");
		}
		else{
			model.setViewName("login");
		}
		
		return model;
       
    }
	// Device Detailed
	@RequestMapping(value={"detailedProduct","userDetailedProduct"})
	public ModelAndView detailedProduct(@RequestParam("serialNumber")String serialNumber,@ModelAttribute Accessories accessory){
	   model = new ModelAndView();
	   userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {				
				accessories = accessoriesInt.getAccessoriesByDeviceSerial(serialNumber);
				model.addObject("accessories", accessories);
		        model.addObject("device",deviceServiceInt.getDeviceBySerialNumber(serialNumber) );
		        model.setViewName("detailedProduct");
			}else if(userName.getRole().equalsIgnoreCase("User")){
				accessories = accessoriesInt.getAccessoriesByDeviceSerial(serialNumber);
				model.addObject("accessories", accessories);
		        model.addObject("device",deviceServiceInt.getDeviceBySerialNumber(serialNumber) );
		        model.setViewName("userDetailedProduct");
			}
	   }else{
		   model.setViewName("login");
	   }
	   return model;
	}
	// Device History
	@RequestMapping(value={"deviceHistory","userDeviceHistory"}, method = RequestMethod.GET)
	public ModelAndView deviceHistory(Long recordID,@RequestParam("serialNumber")String serialNumber,@ModelAttribute Accessories accessory,@ModelAttribute Tickets ticket){
	   model = new ModelAndView();
	   userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {	
				
				model.addObject("tickets",ticketsServiceInt.getAllTicketsBySerialNumber(serialNumber));
				model.addObject("device",deviceServiceInt.getDeviceBySerialNumber(serialNumber) );				
		        model.setViewName("deviceHistory");
			}else if(userName.getRole().equalsIgnoreCase("User")){
				
				model.addObject("tickets",ticketsServiceInt.getAllTicketsBySerialNumber(serialNumber));
				model.addObject("device",deviceServiceInt.getDeviceBySerialNumber(serialNumber) );
		        model.setViewName("userDeviceHistory");
			}
	   }else{
		   model.setViewName("login");
	   }
	   return model;
	}
	
	// Device History
	@RequestMapping(value={"viewMoreDeviceHistory","userViewMoreDeviceHistory"}, method = RequestMethod.GET)
	public ModelAndView moreDeviceHistory(@RequestParam("recordID")Long recordID){
		   model = new ModelAndView();
		   userName = (Employee) session.getAttribute("loggedInUser");
			if(userName != null){
				List<TicketHistory> ticketHistory = ticketHistoryInt.getHistoryByTicketNumber(recordID);
				model.addObject("ticket", logTicketService.getLoggedTicketByTicketNumber(recordID));
				if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {	
					
					model.addObject("ticketHistory",ticketHistory );			
			        model.setViewName("viewMoreDeviceHistory");
				}else if(userName.getRole().equalsIgnoreCase("User")){
					
					model.addObject("ticketHistory",ticketHistory );
			        model.setViewName("userViewMoreDeviceHistory");
				}
		   }else{
			   model.setViewName("login");
		   }
		   return model;
		}
	@RequestMapping(value="searchSerialNumber")
	public ModelAndView searchClientforProduct(@RequestParam("serialNumber") String serialNumber,@ModelAttribute Device device) {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
		device = deviceServiceInt.getDeviceBySerialNumber(serialNumber);
		if(device != null){
			
			model.addObject("technicians",employeeServiceInt.getAllTechnicians());
			model.addObject("product", device);
		}
		else{
			model.addObject("product", null);
		}
		model.setViewName("ticket");
		}
		else{
			model.setViewName("login");
		}
		
		return model;
	}
	@RequestMapping(value="updateDevice",method=RequestMethod.GET)
	public ModelAndView updateDevice()
	{
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			
			model.addObject("updateDevice", new DeviceBean());	
		    model.setViewName("updateDevice");
		}
		else{
			model.setViewName("login");
		}
		return model;
		
	}
	@RequestMapping(value={"searchDevice","userSearchDevice"},method=RequestMethod.GET)
	public ModelAndView searchDevice()
	{
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		
		if(userName != null){
			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {
				model.addObject("deviceList", deviceServiceInt.getDeviceList());
				model.setViewName("searchDevice");
			}else if(userName.getRole().equalsIgnoreCase("User")){
				model.addObject("deviceList", deviceServiceInt.getDeviceList());
				model.setViewName("userSearchDevice");
			}
		}
		else{
			model.setViewName("login");
		}
		return model;
		
	}
	@RequestMapping(value="searchDeviceSerialNumber")
	public ModelAndView searchDeviceBySerialNo(@RequestParam("serialNumber") String serialNumber,Device device,DeviceBean deviceBean){
		model= new ModelAndView();
		
		userName = (Employee) session.getAttribute("loggedInUser");
		
		if(userName != null){					
			
			if (userName.getRole().equalsIgnoreCase("Manager")
					|| (userName.getRole().equalsIgnoreCase("Admin"))) {

				
				List<String> addAccessory = accessoriesInt.getAccessoriesList(deviceBean.getSerialNumber());
			     device = deviceServiceInt.getDeviceBySerialNumber(serialNumber);
			     deviceBean = deviceServiceInt.getAccessoriesForUpdate(serialNumber);
			     if(device != null)
					{
			    		accessories = accessoriesInt.getAccessoriesByDeviceSerial(serialNumber);
			    	
					    model.addObject("customer",contactDetailsServiceInt.contactDetails(device.getCustomerDevice().getCustomerName()));
					    model.addObject("productObject", device);
					    model.addObject("AccessoryObject", deviceBean);
					    model.addObject("accessories", accessories);
					    model.addObject("addAccessory", addAccessory);
					    								
					}
					else{
						model.addObject("retMessage", "Device :" + serialNumber + " does not exist");
					}
					model.setViewName("updateDevice");
			}
			 else if (userName.getRole().equalsIgnoreCase("User")) {

				 List<String> addAccessory = accessoriesInt.getAccessoriesList(deviceBean.getSerialNumber());
			     device = deviceServiceInt.getDeviceBySerialNumber(serialNumber);
			     deviceBean = deviceServiceInt.getAccessoriesForUpdate(serialNumber);
			     if(device != null)
					{
			    		accessories = accessoriesInt.getAccessoriesByDeviceSerial(serialNumber);
			    		model.addObject("customer",contactDetailsServiceInt.contactDetails(device.getCustomerDevice().getCustomerName()));
					    model.addObject("productObject", device);
					    model.addObject("AccessoryObject", deviceBean);
					    model.addObject("accessories", accessories);
					    model.addObject("addAccessory", addAccessory);
					    								
					}
					else{
						model.addObject("retMessage", "Device :" + serialNumber + " does not exist");
					}
				 
				model.setViewName("userUpdateDevice");

			}		
		
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value="searchDeviceBySerialNo")
	public ModelAndView searchDeviceBySerialNo1(@RequestParam("serialNumber") String serialNumber,Device device){
		model= new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
		
		device = deviceServiceInt.getDeviceBySerialNumber(serialNumber);
		if(device != null){
			accessories = accessoriesInt.getAccessoriesByDeviceSerial(serialNumber);
			model.addObject("productObject", device);
		    model.addObject("accessories", accessories);
		    
		}
		else{
			model.addObject("retMessage", "Device : "+ serialNumber + " does not exist");
		    
		}
		
		model.setViewName("searchDevice");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value="updateProduct")
	public ModelAndView updateProduct(@ModelAttribute("updateProduct")DeviceBean deviceBean)
	{
		model = new ModelAndView();
		String updateDevice ="updateDevice";
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
		    deviceBean.setUpdateFlag("YES");
		    retMessage = deviceServiceInt.prepareDeviceData(deviceBean);
			model.addObject("retMessage", retMessage);
			model.addObject("serial", deviceBean.getSerialNumber());
			if(retMessage.startsWith("Accessories removed")){
				String removeAcessory = "removeAcessory";
				model.addObject("removeAcessory", removeAcessory);
			}else{
				model.addObject("updateDevice", updateDevice);
			}
			 model.setViewName("confirmations");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	// search Serial Number to log ticket as user and admin/manager
	@RequestMapping(value = { "searchSerialNumberLogtickr","searchSerialNumberUserLogticket" })
		public ModelAndView searchProductForLogTicket(@RequestParam("serialNumber") String serialNumber,@ModelAttribute Device device) {
			
			model = new ModelAndView();
			//String tickets ="tickets";		
			userName = (Employee) session.getAttribute("loggedInUser");

			if (userName != null) {

				device = deviceServiceInt.getDeviceBySerialNumber(serialNumber);

				if (device != null) {

					model.addObject("technicians", employeeServiceInt.getAllTechnicians());
					model.addObject("product", device);
				}else {
					
					model.addObject("message", "Device does not exist.");				
				}
				if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {
					//model.addObject("tickets", tickets);
					//model.setViewName("confirmations");
					model.setViewName("logTicket");
					
				} else if (userName.getRole().equalsIgnoreCase("User")) {
					//model.addObject("tickets", tickets);
					//model.setViewName("confirm");
					model.setViewName("ticket");
				}
			}else{
				model.setViewName("login");
			}
			return model;
		}
	
		// search Serial Number to Replace Toner as user and admin /manager
		@RequestMapping(value = { "searchSerialNumberReplaceToner","searchSerialNumberUserReplaceToner" , "searchSerialNumberTechReplaceToner"})
			public ModelAndView searchSerialForReplaceToner(@RequestParam("serialNumber") String serialNumber,@ModelAttribute Device device) {
				
				model = new ModelAndView();
				//String tickets ="tickets";		
				userName = (Employee) session.getAttribute("loggedInUser");

				if (userName != null) {

					device = deviceServiceInt.getDeviceBySerialNumber(serialNumber);

					if (device != null) {
						model.addObject("product", device);
					}else {
						
						model.addObject("message", "Device does not exist.");				
					}
					if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {
						//model.addObject("tickets", tickets);
						model.addObject("compitableSiteStock",stockInt.getOrdersForCustomer(device.getCustomerDevice().getCustomerName(),device.getModelNumber()));
						model.addObject("productObject", device);
						model.setViewName("tonerReplacement");
						
					} else if (userName.getRole().equalsIgnoreCase("User")) {
						//model.addObject("tickets", tickets);						
						model.addObject("compitableSiteStock",stockInt.getOrdersForCustomer(device.getCustomerDevice().getCustomerName(),device.getModelNumber()));
						model.addObject("productObject", device);
						model.setViewName("tonerReplacementUser");
					}else if (userName.getRole().equalsIgnoreCase("Technician")) {
						//model.addObject("tickets", tickets);						
						model.addObject("compitableSiteStock",stockInt.getOrdersForCustomer(device.getCustomerDevice().getCustomerName(),device.getModelNumber()));
						model.addObject("productObject", device);
						model.setViewName("tonerReplacementTech");
					}
				}else{
					model.setViewName("login");
				}
				return model;
	}


	
	@RequestMapping(value="removeAccessory")
	public ModelAndView removeAccessory(@ModelAttribute("removeAccessory")DeviceBean removeAccessory)
	{
		String updateDevice ="updateDevice";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			
			model.addObject("retMessage",accessoriesInt.removeAccessory(removeAccessory.getChkAccessories()));
			model.addObject("updateDevice",updateDevice);
			model.setViewName("confirmations");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
}

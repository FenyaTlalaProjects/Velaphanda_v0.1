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

import com.demo.bean.ModelNumbersBean;
import com.demo.bean.SparePartsBean;
import com.demo.bean.SpareQuantity;
import com.demo.model.Employee;
import com.demo.model.HOStock;
import com.demo.model.ModelNumbers;
import com.demo.model.SpareMaster;
import com.demo.service.BootStockInt;
import com.demo.service.CustomerServiceInt;
import com.demo.service.EmployeeServiceInt;
import com.demo.service.HistoryServiceInt;
import com.demo.service.ModelNumbersMasterServiceInt;
import com.demo.service.OrderDetailsInt;
import com.demo.service.OrdersServiceInt;
import com.demo.service.SiteStockInt;
import com.demo.service.SpareMasterServiceInt;
import com.demo.service.HOStockServeceInt;
import com.demo.service.SpareQuantityInt;
import com.demo.service.TicketsServiceInt;

@Controller
public class SparePartsController {
	@Autowired
	private CustomerServiceInt customerServiceInt;
	@Autowired
	private EmployeeServiceInt employeeService;
	@Autowired
	private OrdersServiceInt ordersServiceInt;
	@Autowired
	private EmployeeServiceInt employeeServiceInt;
	@Autowired
	private HOStockServeceInt hOStockServeceInt;
	@Autowired
	private OrderDetailsInt orderDetailsInt;
	@Autowired
	private SpareMasterServiceInt spareMasterServiceInt;
	@Autowired
	private BootStockInt bootStock;	
	@Autowired
	private SiteStockInt siteStock;
	@Autowired
	private SpareQuantityInt spareInt;
	@Autowired
	private HttpSession session = null;
	@Autowired
	private TicketsServiceInt ticketsServiceInt;
	@Autowired
	private HistoryServiceInt spareHOHistoryServiceInt;
	@Autowired
	private HistoryServiceInt spareBootStockHistoryServiceInt;
	@Autowired
	private HistoryServiceInt spareSiteStockHistoryServiceInt;
	@Autowired
	private ModelNumbersMasterServiceInt modelNumbersMasterServiceInt;
	
	private String retMessage = null;
	private ModelAndView model = null;
	private Employee userName,technicianName = null;
	public String[] getSerials = null;
	private SpareMaster master;
	private String globalTechnicianName = null;
	private String globalCustomerName = null;
	private String globalPartNumber = null;
	public String[] getModelNumbers = null;
	
	//spare management
	@RequestMapping(value = {"sparemanagement","techsparemanagement","usersparemanagement"}, method = RequestMethod.GET)
	public ModelAndView displayOrderTechManagement(String partNumber) {
			
			model = new ModelAndView();
			globalCustomerName = null;
			globalTechnicianName = null;
		
			userName = (Employee) session.getAttribute("loggedInUser");
			if (userName != null) {
				if(userName.getRole().equalsIgnoreCase("Manager") || (userName.getRole().equalsIgnoreCase("Admin"))){
				//HO Count
				model.addObject("hoCount", hOStockServeceInt.countHeadOfficeStock());
				//Site Count
				model.addObject("siteCount", siteStock.countSiteStock());
				//Boot Count
				model.addObject("bootCount", bootStock.countBootStock());			
				//Load Data of HO 
				model.addObject("spareParts", hOStockServeceInt.getAllSparePartsWithoutZero());			
				//Load Data Boot Site
				model.addObject("employees",spareInt.spareQuantityForTechnicians());			
				//Load Data of bootSite
				model.addObject("customer",spareInt.spareQuantity());
				//Load Data of Spares HO History
				model.addObject("displayHOSparesHistory",spareHOHistoryServiceInt.getHistoryByPartNumber(partNumber));
				model.setViewName("sparemanagement");
				
			}else if (userName.getRole().equalsIgnoreCase("Technician")){
				//HO Count
				model.addObject("hoCount", hOStockServeceInt.countHeadOfficeStock());
				//Site Count
				model.addObject("siteCount", siteStock.countSiteStock());
				//Boot Count
				model.addObject("bootCount", bootStock.countBootStock(userName.getFirstName()+" "+ userName.getLastName()));
				//Load Data of HO 
				/*model.addObject("firstName", userName.getFirstName());
				model.addObject("lastName", userName.getLastName());
				model.addObject("email", userName.getEmail());*/
				//Load Data Boot Site
				model.addObject("employees",spareInt.spareQuantityForTechnician(userName.getFirstName()+" "+ userName.getLastName()));
				//Load Data of bootSite
				model.addObject("customer",spareInt.spareQuantityForTechnicianSiteStock());			
				model.setViewName("techsparemanagement");
				
			}else if(userName.getRole().equalsIgnoreCase("User")){	
				//HO Count
				model.addObject("hoCount", hOStockServeceInt.countHeadOfficeStock());
				//Site Count
				model.addObject("siteCount", siteStock.countSiteStock());
				//Boot Count
				model.addObject("bootCount", bootStock.countBootStock());			
				//Load Data of HO 
				model.addObject("spareParts", hOStockServeceInt.getAllSparePartsWithoutZero());			
				//Load Data Boot Site
				model.addObject("employees",spareInt.spareQuantityForTechnicians());			
				//Load Data of bootSite
				model.addObject("customer",spareInt.spareQuantity());	
				model.setViewName("usersparemanagement");
			}
				
			}else {
				model.setViewName("login");
			}
			return model;
		}
	
	
		//spare history movement
		@RequestMapping(value = {"headOfficeSpareHistory","techSpareHistory","userHeadOfficeSpareHistory"}, method = RequestMethod.GET)
		public ModelAndView displayHistoryHOMovement(String partNumber) {
				
				model = new ModelAndView();
				globalCustomerName = null;
				globalTechnicianName = null;
				globalPartNumber = partNumber;
				userName = (Employee) session.getAttribute("loggedInUser");
				if (userName != null) {
					if(userName.getRole().equalsIgnoreCase("Manager") || (userName.getRole().equalsIgnoreCase("Admin"))){
					//HO Count
					model.addObject("hoCount", hOStockServeceInt.countHeadOfficeStock());
					//Site Count
					model.addObject("siteCount", siteStock.countSiteStock());
					//Boot Count
					model.addObject("bootCount", bootStock.countBootStock());			
					//Load Data of HO 
					model.addObject("spareParts", hOStockServeceInt.getAllSparePartsWithoutZero());			
					//Load Data Boot Site
					model.addObject("employees",spareInt.spareQuantityForTechnicians());			
					//Load Data of bootSite
					model.addObject("customer",spareInt.spareQuantity());
					//Load Data of Spares HO History
					model.addObject("partNumber",partNumber);
					model.addObject("displayHOSparesHistory",spareHOHistoryServiceInt.getHistoryByPartNumber(partNumber));
					model.setViewName("sparemanagement");
					
				}else if (userName.getRole().equalsIgnoreCase("Technician")){
					//HO Count
					model.addObject("hoCount", hOStockServeceInt.countHeadOfficeStock());
					//Site Count
					model.addObject("siteCount", siteStock.countSiteStock());
					//Boot Count
					model.addObject("bootCount", bootStock.countBootStock(userName.getFirstName()+" "+ userName.getLastName()));
					//Load Data of HO 
					/*model.addObject("firstName", userName.getFirstName());
					model.addObject("lastName", userName.getLastName());
					model.addObject("email", userName.getEmail());*/
					//Load Data Boot Site
					model.addObject("employees",spareInt.spareQuantityForTechnician(userName.getFirstName()+" "+ userName.getLastName()));
					//Load Data of bootSite
					model.addObject("customer",spareInt.spareQuantityForTechnicianSiteStock());			
					model.setViewName("techsparemanagement");
					
				}else if(userName.getRole().equalsIgnoreCase("User")){	
					//HO Count
					model.addObject("hoCount", hOStockServeceInt.countHeadOfficeStock());
					//Site Count
					model.addObject("siteCount", siteStock.countSiteStock());
					//Boot Count
					model.addObject("bootCount", bootStock.countBootStock());			
					//Load Data of HO 
					model.addObject("spareParts", hOStockServeceInt.getAllSparePartsWithoutZero());			
					//Load Data Boot Site
					model.addObject("employees",spareInt.spareQuantityForTechnicians());			
					//Load Data of bootSite
					model.addObject("customer",spareInt.spareQuantity());
					//Load Data of Spares HO History
					model.addObject("partNumber",partNumber);
					model.addObject("displayHOSparesHistory",spareHOHistoryServiceInt.getHistoryByPartNumber(partNumber));
					model.setViewName("usersparemanagement");
				}
					
				}else {
					model.setViewName("login");
				}
				return model;
			}
	
	
	
	@RequestMapping(value="addSpares", method=RequestMethod.GET)
	public ModelAndView loadAddSpares()
	{
	    model = new ModelAndView("addSpares");
	    userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			model.addObject("saveSpareParts", new SparePartsBean());
			getSerials = spareMasterServiceInt.getSerials();
			model.addObject("spareParts",getSerials);		
			model.setViewName("addSpares");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value="addModelNumbers", method=RequestMethod.GET)
	public ModelAndView loadAddModelNumbers()
	{
	    model = new ModelAndView("addSpares");
	    userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			model.addObject("saveModelNumberData", new ModelNumbersBean());
			getModelNumbers = modelNumbersMasterServiceInt.getModelNumbers();
			model.addObject("modelNumbers",getModelNumbers);	
			model.setViewName("addModelNumbers");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value="addSparesParts", method=RequestMethod.POST)
	public ModelAndView SaveSpareParts(@ModelAttribute("addSparesParts")HOStock spareParts,SparePartsBean sparePartsBean){
		model = new ModelAndView();
		String addSpares = "addSpares";
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){			
			retMessage = hOStockServeceInt.saveSpareparts(spareParts,sparePartsBean);	
			model.addObject("addSpares", addSpares);
			model.setViewName("confirmations");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value="moveSpares", method=RequestMethod.POST)
	public ModelAndView moveSpareParts(@RequestParam("fromCustomerName")String fromCustomerName, @RequestParam("fromTechnicianName")String fromTechnicianName, @RequestParam("partNumberList")String partNumberList,@RequestParam("quantityList")String quantityList,@RequestParam("options")String options,@RequestParam("customerName")String customerName,@RequestParam("technicianName")String technicianName,@RequestParam("reasonForSite")String reasonForSite,@RequestParam("reasonForBoot")String reasonForBoot,@RequestParam("headOffice")String headOffice,@RequestParam("reasonForHeadOffice")String reasonForHeadOffice){
		model = new ModelAndView();
		String moveSpares = "moveSpares";
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			
			if(userName.getRole().equalsIgnoreCase("Manager") || (userName.getRole().equalsIgnoreCase("Admin"))){
				retMessage = siteStock.moveStock(fromCustomerName, fromTechnicianName, partNumberList, quantityList, options, customerName, technicianName, reasonForSite, reasonForBoot,headOffice,reasonForHeadOffice);
				model.addObject("retMessage", retMessage);			
				model.addObject("moveSpares", moveSpares);
				model.setViewName("confirmations");				
			}
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value={"receiveParts","userRecieveParts"}, method=RequestMethod.GET)
	public ModelAndView loadSaveSpareParts()
	{
	    model = new ModelAndView("receiveParts");
	    userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			if(userName.getRole().equalsIgnoreCase("Manager") || (userName.getRole().equalsIgnoreCase("Admin"))){
				model.addObject("saveSpareParts", new SparePartsBean());
				getSerials = spareMasterServiceInt.getSerials();
				model.addObject("spareParts",getSerials);
				model.setViewName("receiveParts");
				
			}else if (userName.getRole().equalsIgnoreCase("User")){
				model.addObject("saveSpareParts", new SparePartsBean());
				getSerials = spareMasterServiceInt.getSerials();
				model.addObject("spareParts",getSerials);			
				model.setViewName("userReceiveParts");
			}
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value="saveSpareParts", method=RequestMethod.POST)
	public ModelAndView saveSaveSpareParts(@ModelAttribute("saveSpareParts")HOStock spareParts,SparePartsBean sparePartsBean){
		String receiveSpareParts = "receiveSpareParts";
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			
			retMessage = hOStockServeceInt.saveSpareparts(spareParts,sparePartsBean);			
			model.addObject("retMessage", retMessage);			
			model.addObject("receiveSpareParts", receiveSpareParts);
			model.setViewName("confirmations");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value="availableSpareParts", method=RequestMethod.GET)
	public ModelAndView getSpareParts(){
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			
			model.addObject("spareParts", hOStockServeceInt.getAllSparePartsWithoutZero());		
			model.setViewName("availableSpareParts");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	@RequestMapping(value="bootSite", method=RequestMethod.GET)
	public ModelAndView getSparePartBootStock(){
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			
			model.addObject("employees",employeeService.getAllTechnicians());			
			model.setViewName("bootSite");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	@RequestMapping(value="stockSite", method=RequestMethod.GET)
	public ModelAndView getSparePartSiteStock(){
		model = new ModelAndView();
				
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			model.addObject("customer",customerServiceInt.getClientList());		
			
			model.setViewName("stockSite");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}	
		
	@RequestMapping(value="availableSites", method=RequestMethod.GET)
	public ModelAndView getSparePartSite(){
		model = new ModelAndView();
				
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
		
			model.addObject("shipment",	ordersServiceInt.shippedOrders(userName.getEmail()));			
			model.addObject("customer",customerServiceInt.getClientList());
			
			model.setViewName("availableSites");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	@RequestMapping(value="loadBootStock")
	public ModelAndView loadBootStock(@RequestParam("technician") String technician){
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		globalTechnicianName = technician;
		if(userName != null){
			model.addObject("countPartForTech",bootStock.countPartsForTechnician(technician));
			model.addObject("countTonerForTech",bootStock.countTonerForTechnician(globalTechnicianName));
			model.addObject("orders",bootStock.getAllOrders(technician));
			model.addObject("technician",technician);
			model.addObject("technicianName", employeeService.getEmployeeByEmpNumber(technician));
			model.addObject("customerList",customerServiceInt.getClientList());
			model.addObject("technicianList",employeeServiceInt.getAllTechnicians());
			model.setViewName("bootSiteOrders");
			}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	
	@RequestMapping(value={"loadStockSite"},method=RequestMethod.GET)
	public ModelAndView loadStockSite(@RequestParam("customerName")String customerName, String partNumber){
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		globalTechnicianName = null;
		globalCustomerName = customerName;
		globalPartNumber = partNumber;
		if(userName != null){
			model.addObject("orders", siteStock.getOrdersForCustomer(globalCustomerName));
			model.addObject("countPartForCustomer",siteStock.countPartsForCustomer(globalCustomerName));
			model.addObject("countTonerForCustomer",siteStock.countTonerForCustomer(globalCustomerName));
			//Load customers/technician lists
			model.addObject("customerList",customerServiceInt.getClientList(customerName));
			model.addObject("displaySiteStockMovement",spareSiteStockHistoryServiceInt.getSiteStockHistoryByPartNumber(partNumber));
			model.addObject("customerName",customerName);			
			model.addObject("technicianList",employeeServiceInt.getAllTechnicians());
			model.setViewName("stockSiteOrders");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value={"loadBootStockHistoryMovement"}, method=RequestMethod.GET)
	public ModelAndView loadBootStockHistoryMovement(String technician,String partNumber){
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		globalTechnicianName = technician;
		globalPartNumber = partNumber;
		if(userName != null){
			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")){
				//Load technician,display Boot Stock Movement history lists
				model.addObject("technician",technician);
				model.addObject("partNumber",partNumber);
				model.addObject("displayBootStockMovement", spareBootStockHistoryServiceInt.getBootStockHistoryByPartNumber(partNumber));
				model.setViewName("bootSiteOrders");	
			}
			if(userName.getRole().equalsIgnoreCase("User")){
				//Load customer,display Site Stock Movement history lists
				model.addObject("technician",technician);
				model.addObject("partNumber",partNumber);
				model.addObject("displayBootStockMovement", spareBootStockHistoryServiceInt.getBootStockHistoryByPartNumber(partNumber));
				model.setViewName("bootSiteOrdersForUser");
			}else if (userName.getRole().equalsIgnoreCase("Technician")){
				model.addObject("technician",technician);
				model.addObject("partNumber",partNumber);
				model.addObject("displayBootStockMovement", spareBootStockHistoryServiceInt.getBootStockHistoryByPartNumber(partNumber));
				model.setViewName("bootSiteOrdersForTechnician");
			}
			
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value={"loadSiteStockHistoryMovement"},method=RequestMethod.GET)
	public ModelAndView loadSiteStockMistoryMovement(String customerName,String partNumber, String technician){
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		globalTechnicianName = null;
		globalCustomerName = customerName;
		globalPartNumber = partNumber;
		globalTechnicianName = technician;
		if(userName != null){

			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")){
				//Load customer,display Site Stock Movement history lists
				model.addObject("customerName",customerName);
				model.addObject("partNumber",partNumber);
				model.addObject("displaySiteStockMovement",spareSiteStockHistoryServiceInt.getSiteStockHistoryByPartNumber(partNumber));
				model.setViewName("stockSiteOrders");
			}if(userName.getRole().equalsIgnoreCase("User")){
				//Load customer,display Site Stock Movement history lists
				model.addObject("customerName",customerName);
				model.addObject("partNumber",partNumber);
				model.addObject("displaySiteStockMovement",spareSiteStockHistoryServiceInt.getSiteStockHistoryByPartNumber(partNumber));
				model.setViewName("stockSiteOrdersForUser");
			}else if (userName.getRole().equalsIgnoreCase("Technician")){
				//Load customer,display Site Stock Movement history lists
				model.addObject("customerName",customerName);
				model.addObject("partNumber",partNumber);
				model.addObject("technician",technician);
				model.addObject("displaySiteStockMovement",spareSiteStockHistoryServiceInt.getSiteStockHistoryByPartNumber(partNumber));
				model.setViewName("stockSiteOrdersForTechnician");
			}
			
			
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	
	@RequestMapping(value="loadStockSiteForTechnician")
	public ModelAndView loadStockSiteforTechnician(@RequestParam("customerName") String customerName){
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		globalCustomerName = customerName;
		if(userName != null){
			
			//Load customers/technician lists
			model.addObject("customerList",customerServiceInt.getClientList(customerName));
			model.addObject("technicianList",employeeServiceInt.getAllTechnicians());
			
			if (userName.getRole().equalsIgnoreCase("Technician"))
			{
				model.addObject("orders", siteStock.getOrdersForCustomer(globalCustomerName));
				model.addObject("countPartForCustomer",siteStock.countPartsForCustomer(globalCustomerName));
				model.addObject("customerName",customerName);
				model.addObject("countTonerForCustomer",siteStock.countTonerForCustomer(globalCustomerName));	
				model.setViewName("stockSiteOrdersForTechnician");
			}else if(userName.getRole().equalsIgnoreCase("User")){
				model.addObject("orders", siteStock.getOrdersForCustomer(globalCustomerName));
				model.addObject("countPartForCustomer",siteStock.countPartsForCustomer(globalCustomerName));
				model.addObject("countTonerForCustomer",siteStock.countTonerForCustomer(globalCustomerName));
				model.addObject("customerName",customerName);
				model.setViewName("stockSiteOrdersForUser");
			}

		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value="loadBootStockForTechnician")
	public ModelAndView loadBootStockForTechnician(@RequestParam("technician") String technician){
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		globalTechnicianName = technician;
		userName = (Employee) session.getAttribute("loggedInUser");
		String techfullname = userName.getFirstName() + " " + userName.getLastName();
		if(userName != null){

			//Load customers/technician lists
			model.addObject("customerList",customerServiceInt.getClientList());
			model.addObject("technicianList",employeeServiceInt.getAllTechnicians(technician));
			if (userName.getRole().equalsIgnoreCase("Technician"))
			{
				model.addObject("countPartForTech",bootStock.countPartsForTechnician(technician));
				model.addObject("countTonerForTech",bootStock.countTonerForTechnician(technician));
				model.addObject("orders",bootStock.getAllOrders(technician));
				model.addObject("technician",technician);
				model.addObject("technicianName", employeeService.getEmployeeByEmpNumber(technician));
				model.setViewName("bootSiteOrdersForTechnician");
				
			}else if(userName.getRole().equalsIgnoreCase("User")){
				model.addObject("countPartForTech",bootStock.countPartsForTechnician(technician));
				model.addObject("countTonerForTech",bootStock.countTonerForTechnician(globalTechnicianName));
				model.addObject("orders",bootStock.getAllOrders(technician));
				model.addObject("technician",technician);
				//model.addObject("customerList",customerServiceInt.getClientList(customerName));
				model.addObject("technicianName", employeeService.getEmployeeByEmpNumber(technician));
				model.addObject("technicianList",employeeServiceInt.getAllTechnicians());			
				model.setViewName("bootSiteOrdersForUser");
			}
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value={"searchpartNumber","userSearchPartNumber"})
	public ModelAndView searchPartNumber(@RequestParam("partNumber") String partNumber) {
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			if(userName.getRole().equalsIgnoreCase("Manager") || (userName.getRole().equalsIgnoreCase("Admin"))){
			master =spareMasterServiceInt.getSpareMaster(partNumber);
			if(master !=null){
				model.addObject("sparePart", master);
				model.addObject("models", spareMasterServiceInt.getModelDevice(partNumber));			
			}else{
				model.addObject("errorRetMessage", "Part Number does not exist.");
			}
			model.setViewName("receiveParts");
		}else if(userName.getRole().equalsIgnoreCase("User")){
			
			master =spareMasterServiceInt.getSpareMaster(partNumber);			
			if(master !=null){
				model.addObject("sparePart", master);
				model.addObject("models", spareMasterServiceInt.getModelDevice(partNumber));			
			}else{
				model.addObject("errorRetMessage", "Part Number does not exist.");
			}
			model.setViewName("userReceiveParts");
		  }
		}
		else{
			model.setViewName("login");
		}		
		return model;
	}
	@RequestMapping(value="spareToMasterData", method=RequestMethod.POST)
	public ModelAndView addSpareToMAsterData(@ModelAttribute("spareToMasterData")SpareMaster spareMaster){
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){

			retMessage = spareMasterServiceInt.saveSpareMasterData(spareMaster);
			
			if(retMessage.startsWith("Part number already exist")){
				String errorRetMessage = retMessage;
				model.addObject("errorRetMessage", errorRetMessage);
			}else{
				model.addObject("retMessage", retMessage);
			}			
			model.setViewName("addSpares");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value="modelNumbersToMasterData", method=RequestMethod.POST)
	public ModelAndView addModelNumbersToMasterData(@ModelAttribute("modelNumbersToMasterData")ModelNumbers modelNumber){
		model = new ModelAndView();
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			
			
			retMessage = modelNumbersMasterServiceInt.saveModelNumberData(modelNumber);
			
			if(retMessage.startsWith("Model Number already exist")){
				String errorRetMessage = retMessage;
				model.addObject("errorRetMessage", errorRetMessage);
			}else{
				model.addObject("retMessage", retMessage);
			}			
			model.setViewName("addModelNumbers");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	//Number of parts
	@RequestMapping(value="numberOfParts", method=RequestMethod.GET)
	public ModelAndView getNumberOfSpareParts(){
		model = new ModelAndView();
				
		userName = (Employee) session.getAttribute("loggedInUser");
		if(userName != null){
			
			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin"))
			{ 
				if (globalTechnicianName != null){
					model.addObject("countPartForTech",bootStock.countPartsForTechnician(globalTechnicianName));
					model.addObject("countTonerForTech",bootStock.countTonerForTechnician(globalTechnicianName));
					model.addObject("orders", bootStock.getPartsForTechnician(globalTechnicianName));					
					model.setViewName("bootSiteOrders");	
				}
				else if (globalCustomerName != null){
					model.addObject("countPartForCustomer",siteStock.countPartsForCustomer(globalCustomerName));
					model.addObject("countTonerForCustomer",siteStock.countTonerForCustomer(globalCustomerName));
					model.addObject("orders", siteStock.getPartsForCustomer(globalCustomerName));					
					model.setViewName("stockSiteOrders");
				}
				
			}
			else if (userName.getRole().equalsIgnoreCase("Technician"))
			{ 
				String localTechnician = userName.getFirstName() + " " + userName.getLastName();
				if (globalTechnicianName != null)
				{
					if (localTechnician != null){
						model.addObject("countPartForTech",bootStock.countPartsForTechnician(localTechnician));
						model.addObject("countTonerForTech",bootStock.countTonerForTechnician(localTechnician));
						model.addObject("orders", bootStock.getPartsForTechnician(localTechnician));						
						model.setViewName("bootSiteOrdersForTechnician");	
					}
				}
				else if (globalCustomerName != null)
				{
						model.addObject("countPartForCustomer",siteStock.countPartsForCustomer(globalCustomerName));
						model.addObject("countTonerForCustomer",siteStock.countTonerForCustomer(globalCustomerName));
						model.addObject("orders", siteStock.getPartsForCustomer(globalCustomerName));						
						model.setViewName("stockSiteOrdersForTechnician");			
				}
				
			}
			else if (userName.getRole().equalsIgnoreCase("User"))
			{ 
				String localTechnician = userName.getFirstName() + " " + userName.getLastName();
				if (globalTechnicianName != null)
				{
					if (localTechnician != null){
						model.addObject("countPartForTech",bootStock.countPartsForTechnician(localTechnician));
						model.addObject("countTonerForTech",bootStock.countTonerForTechnician(localTechnician));
						model.addObject("orders", bootStock.getPartsForTechnician(localTechnician));						
						model.setViewName("bootSiteOrdersForUser");	
					}
				}
				else if (globalCustomerName != null)
				{
						model.addObject("countPartForCustomer",siteStock.countPartsForCustomer(globalCustomerName));
						model.addObject("countTonerForCustomer",siteStock.countTonerForCustomer(globalCustomerName));
						model.addObject("orders", siteStock.getPartsForCustomer(globalCustomerName));						
						model.setViewName("stockSiteOrdersForUser");			
				}
				
			}
		}
		else{
			model.setViewName("login");
		}
		return model;
	}	
	
	//Number of toners
	@RequestMapping(value="numberOfToners", method=RequestMethod.GET)
	public ModelAndView getNumberOfToners(){
	model = new ModelAndView();
					
			userName = (Employee) session.getAttribute("loggedInUser");
			if (userName.getRole().equalsIgnoreCase("Manager") || userName.getRole().equalsIgnoreCase("Admin")) {
				if (globalTechnicianName != null){
					model.addObject("countPartForTech",bootStock.countPartsForTechnician(globalTechnicianName));
					model.addObject("countTonerForTech",bootStock.countTonerForTechnician(globalTechnicianName));
					model.addObject("orders", bootStock.getTonerForTechnician(globalTechnicianName));
					
					model.setViewName("bootSiteOrders");	
				}
				else if (globalCustomerName != null){
					
					model.addObject("countPartForCustomer",siteStock.countPartsForCustomer(globalCustomerName));
					model.addObject("countTonerForCustomer",siteStock.countTonerForCustomer(globalCustomerName));
					model.addObject("orders", siteStock.getTonerForCustomer(globalCustomerName));
					
					model.setViewName("stockSiteOrders");
				}
			}
			else if (userName.getRole().equalsIgnoreCase("Technician"))	{
				String localTechnician = userName.getFirstName() + " " + userName.getLastName();
				if (globalTechnicianName != null)
				{
					if (localTechnician != null){
						model.addObject("countPartForTech",bootStock.countPartsForTechnician(localTechnician));
						model.addObject("countTonerForTech",bootStock.countTonerForTechnician(localTechnician));
						model.addObject("orders", bootStock.getTonerForTechnician(localTechnician));
						
						model.setViewName("bootSiteOrdersForTechnician");	
					}
				}
				else if (globalCustomerName != null)
				{
					model.addObject("countPartForCustomer",siteStock.countPartsForCustomer(globalCustomerName));
					model.addObject("countTonerForCustomer",siteStock.countTonerForCustomer(globalCustomerName));
					model.addObject("orders", siteStock.getTonerForCustomer(globalCustomerName));
					
					model.setViewName("stockSiteOrdersForTechnician");
				}			
			}else if (userName.getRole().equalsIgnoreCase("User"))	{
				String localTechnician = userName.getFirstName() + " " + userName.getLastName();
				if (globalTechnicianName != null)
				{
					if (localTechnician != null){
						model.addObject("countPartForTech",bootStock.countPartsForTechnician(localTechnician));
						model.addObject("countTonerForTech",bootStock.countTonerForTechnician(localTechnician));
						model.addObject("orders", bootStock.getTonerForTechnician(localTechnician));
						
						model.setViewName("bootSiteOrdersForUser");	
					}
				}
				else if (globalCustomerName != null)
				{
					model.addObject("countPartForCustomer",siteStock.countPartsForCustomer(globalCustomerName));
					model.addObject("countTonerForCustomer",siteStock.countTonerForCustomer(globalCustomerName));
					model.addObject("orders", siteStock.getTonerForCustomer(globalCustomerName));
					
					model.setViewName("stockSiteOrdersForUser");
				}			
			}
			else{
				model.setViewName("login");
			}
			return model;
		}	
		
}
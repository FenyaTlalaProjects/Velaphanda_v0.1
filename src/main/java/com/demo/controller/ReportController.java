package com.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo.dao.AccessoriesDaoInt;
import com.demo.dao.CustomerDaoInt;
import com.demo.dao.DeviceDaoInt;
import com.demo.dao.OrderDetailsDaoInt;
import com.demo.dao.TicketHistoryDaoInt;
import com.demo.dao.TicketsDaoInt;
import com.demo.model.Accessories;
import com.demo.model.Customer;
import com.demo.model.Device;
import com.demo.model.OrderDetails;
import com.demo.model.TicketHistory;
import com.demo.model.Tickets;
import com.demo.service.CustomerServiceInt;



@Controller
public class ReportController {
	@Autowired
	private OrderDetailsDaoInt detailsDaoInt;
	@Autowired
	private TicketsDaoInt ticketDaoInt;
	@Autowired
	private DeviceDaoInt deviceDaoInt;
	@Autowired
	private CustomerServiceInt customerServiceInt;
	@Autowired
	private CustomerDaoInt customerDaoInt;
	@Autowired
	private AccessoriesDaoInt accessoriesDaoInt;
	
	private Tickets ticket = null;
	private Device device = null;
	
	Map<String, Object> model;
	
	@RequestMapping(value = "/orderDownloadPDF" , method = RequestMethod.GET)
	public ModelAndView downloadExcel(@RequestParam("recordID") Long recordID) {
		// return a view which will be resolved by an excel view resolver
		 model = new HashMap<String, Object>();
		
		List<OrderDetails> listItems = detailsDaoInt.getOrderDetailsByOrderNum(recordID);
		return new ModelAndView("orderPdfView", "listItems", listItems);
	}	

	@RequestMapping(value = "/ticketDownloadPDF" , method = RequestMethod.GET)
	public ModelAndView ticketPDF(@RequestParam("recordID") Long recordID) {
		// return a view which will be resolved by an excel view resolver
		 model = new HashMap<String, Object>();
		
		 ticket= ticketDaoInt.getLoggedTicketsByTicketNumber(recordID);
		return new ModelAndView("ticketPdfView", "ticket", ticket);
	}
	
	@RequestMapping(value = "/deviceDetailsDownloadPDF" , method = RequestMethod.GET)
	public ModelAndView deviceDetailsPDF(@RequestParam("serialNumber") String serialNumber) {
		// return a view which will be resolved by an excel view resolver
		 model = new HashMap<String, Object>();		
		 List<Accessories> accessories = accessoriesDaoInt.getAccessoriesByDeviceSerial(serialNumber);
		return new ModelAndView("deviceDetailsPdfView", "accessories", accessories);
	}	
	@RequestMapping(value = "/deviceListDownloadPDF" , method = RequestMethod.GET)
	public ModelAndView deviceListPDF() {
		// return a view which will be resolved by an excel view resolver
		 model = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		List<Device> deviceList = (List<Device>) deviceDaoInt.getDeviceList();
		return new ModelAndView("deviceListPdfView", "deviceList", deviceList);
	}	
	@RequestMapping(value = "/customerListDownloadPDF" , method = RequestMethod.GET)
	public ModelAndView customerDetailsPDF(@RequestParam("clientName") String clientName) {
		// return a view which will be resolved by an excel view resolver
		 model = new HashMap<String, Object>();
		
		@SuppressWarnings("unchecked")
		List<Customer> customerList = (List<Customer>) customerDaoInt.getClientList();
		return new ModelAndView("customerListPdfView", "customerList", customerList);
	}
	
	
}

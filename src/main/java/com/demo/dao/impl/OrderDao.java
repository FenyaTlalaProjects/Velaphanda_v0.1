
package com.demo.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.bean.OrdersBean;
import com.demo.dao.ApprovedOrderStockDaoInt;
import com.demo.dao.BootStockDaoInt;
import com.demo.dao.CustomerDaoInt;
import com.demo.dao.DeliveryNoteHeaderDaoInt;
import com.demo.dao.DeliveryNoteLineItemsDaoInt;
import com.demo.dao.EmployeeDaoInt;
import com.demo.dao.OrderDetailsDaoInt;
import com.demo.dao.OrderHistoryDaoInt;
import com.demo.dao.OrdersDaoInt;
import com.demo.dao.DeviceDaoInt;
import com.demo.dao.HOStockDaoInt;
import com.demo.dao.SiteStocDaoInt;
import com.demo.dao.TicketHistoryDaoInt;
import com.demo.dao.TicketsDaoInt;
import com.demo.model.Customer;
import com.demo.model.DeliveryNoteHeader;
import com.demo.model.Employee;
import com.demo.model.OrderHeader;
import com.demo.model.OrderDetails;
import com.demo.model.HOStock;
import com.demo.model.Device;
import com.demo.model.Tickets;

@Repository("orderDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class OrderDao implements OrdersDaoInt {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private HttpSession session;
	@Autowired
	private EmployeeDaoInt employeeDaoInt;
	@Autowired
	private HOStockDaoInt hOStockDaoInt;
	@Autowired
	private DeviceDaoInt deviceDaoInt;
	@Autowired
	private CustomerDaoInt customerDaoInt;
	@Autowired
	private OrderDetailsDaoInt detailsDaoInt;
	@Autowired
	private ApprovedOrderStockDaoInt approvedOrderStockDaoInt;
	@Autowired
	private SiteStocDaoInt siteStocDaoInt;
	@Autowired
	private BootStockDaoInt bootStockDaoInt;
	@Autowired
	private OrderHistoryDaoInt historyDaoInt;
	@Autowired
	private TicketsDaoInt ticketsDaoInt;
	@Autowired
	private TicketHistoryDaoInt ticketHistoryDaoInt;
	@Autowired
	private DeliveryNoteLineItemsDaoInt deliveryLineItem;
	@Autowired
	private DeliveryNoteHeaderDaoInt deliveryNoteHeaderInt;
	private OrderHeader orderHeader,tempOrderHeader = null;
	

	private String retMessage = null;
	private String orderNumber = "ORD000";
	private Customer cus;
	private Employee emp = null;
	private HOStock part = null;
	private Device device = null;
	private OrderHeader cusOrder = null;
	private DateFormat dateFormat = null;
	private Date date = null;
	private List<OrderDetails> orderDetailList = null;
	private Set<OrderDetails> orderDetailsList= null;
	private OrderDetails orderDetails = null;
	private DeliveryNoteHeader deliveryNoteHeader = null;
	private List<OrderHeader> pendingOrders;
	private List<OrderDetails> listOrders;
	private Long retRecordID = 0L;

	
	@Override
	public long convertOrderToLong(String orderNo)
	{
		long orderNumberV = 0;
		
		if (orderNo.length() > 4)
		{
			String orderNum = orderNo.substring(3);
			orderNumberV = Long.parseLong(orderNum);
			
		}
		return orderNumberV;
	}
	
	
	@Override
	public String makeOrder(OrderHeader orderHeader) {
		emp = employeeDaoInt.getEmployeeByEmpNum(orderHeader.getApprover());
		try {
			sessionFactory.getCurrentSession().save(orderHeader);
			if(orderHeader.getStockType().equalsIgnoreCase("Site")){
				JavaMail.sendEmailOrderForSiteStock(orderHeader.getApprover(), orderHeader.getEmployee().getEmail(), emp.getFirstName(), orderHeader);
			}else if(orderHeader.getStockType().equalsIgnoreCase("Boot")){
				JavaMail.sendEmailOrderForBootStock(orderHeader.getApprover(), orderHeader
						.getEmployee().getEmail(), emp.getFirstName(), orderHeader);
			}
				
			historyDaoInt.insetOrderHistory(orderHeader);
			deliveryNoteHeaderInt.saveDeliveryNoteHeader(orderHeader);
			retMessage = "Order : " + " " + orderNumber + orderHeader.getRecordID() + " "
					+ "successfuly placed.";
		} catch (Exception e) {
			retMessage = "Order : " + " " + orderNumber + orderHeader.getRecordID() + " "
					+ "not placed " + e.getMessage()+".";
		}
		return retMessage;
	}
	
	@Override
	public void saveDeliveryNoteHeader(DeliveryNoteHeader deliveryNote) {
		
		try {
			sessionFactory.getCurrentSession().save(deliveryNote);
			
		} catch (Exception e) {
			retMessage = "Order : " + " " + orderNumber+orderHeader.getRecordID() + " "
					+ "not placed " + e.getMessage()+".";
		}
	}

	@Override
	public String updateOrder(OrdersBean orders) {

		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();
		String n = " ";
		cusOrder = new OrderHeader();
		try {

			part = hOStockDaoInt.getSparePartBySerial(orders.getPart());
			device = deviceDaoInt.getDeviceBySerialNumbuer(orders.getDevice());
			emp = employeeDaoInt.getEmployeeByEmpNum(orders.getEmployee());
			if (part != null) {
				orders.setApproved(true);
				orders.setDateApproved(dateFormat.format(date));
				String approvedBy = (String) session
						.getAttribute("loggedInUser");
				orders.setOrderNum(orders.getOrderNum());
				cusOrder.setEmployee(emp);
				cusOrder.setDateOrdered(orders.getDateOrdered());

				sessionFactory.getCurrentSession().update(cusOrder);
				historyDaoInt.insetOrderHistory(cusOrder);
				retMessage = "Order " + " " + orderNumber +cusOrder.getRecordID() + " "
						+ " is approved.";
			} else {
				retMessage = "Order " + " " + n + orderNumber +cusOrder.getRecordID() + " "
						+ "not approved because it"
						+ " is not available in store.";
			}

		} catch (Exception e) {
			retMessage = "Order " + " " + orders.getOrderNum() + " "
					+ "not updated " + e.getMessage()+".";
		}
		return retMessage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderHeader> getAllOrders() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				OrderHeader.class);
		return (List<OrderHeader>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderHeader> getApprovedOrdersByTechnicianName(String userName) {

		ArrayList<OrderHeader> aList = new ArrayList<OrderHeader>();
		ArrayList<OrderHeader> orderList = new ArrayList<OrderHeader>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				OrderHeader.class);

		aList.addAll(criteria.list());
		for (Object order : aList) {
			if (order instanceof OrderHeader) {
				if (((OrderHeader) order).getEmployee().getEmail()
						.equalsIgnoreCase(userName)
						&& ((OrderHeader) order).isApproved() == true) {
					orderList.add((OrderHeader) order);
				}
			}
		}
		return orderList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderHeader> getOpenOrders() {
		ArrayList<?> aList = new ArrayList<Object>();
		ArrayList<OrderHeader> orderList = new ArrayList<OrderHeader>();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				OrderHeader.class);

		aList.addAll(criteria.list());
		for (Object order : aList) {
			if (order instanceof OrderHeader) {
				if (((OrderHeader) order).getStatus().equalsIgnoreCase(
						"Approved")) {
					orderList.add((OrderHeader) order);
				}
			}
		}
		return orderList;
	}

	@Override
	public OrderHeader getOrder(Long recordID) {

		return (OrderHeader) sessionFactory.getCurrentSession().get(
				OrderHeader.class, recordID);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderHeader> getAllOrders(String orderedBy) {
		List<OrderHeader> aList = getAllOrders();
		ArrayList<OrderHeader> orderList = new ArrayList<OrderHeader>();
		

		for (OrderHeader order : aList) {
			if(order.getEmployee().getEmail().equalsIgnoreCase(orderedBy)&& ((order.getStatus().equalsIgnoreCase("Pending"))||(order.getStatus().equalsIgnoreCase("Approved"))||(order.getStatus().equalsIgnoreCase("Shipped")))){
				orderList.add(order);
			}
		}
		return orderList;
	}

	@Override
	public String prepareOrderMaking(OrdersBean orderBean) {
		
      synchronized(this){
    	cusOrder = new OrderHeader();
  		String orderNumber = null;
  		Integer recordID = 1;
  		String[] split;
  		String partNumber,splitString = null;
  		int quatity = 0;
  		String tempString = null;

  		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  		date = new Date();
  		//String[] quantityArray = quantity(orderBean.getQuantity(), orderBean.getSelectedItem().length);
  		
  		String[] quantityArray = quantity(orderBean.getQuantityList(),
  				orderBean.getPartNumberList().length);
  		
  		try {

  			Employee user = (Employee) session.getAttribute("loggedInUser");
  			emp = employeeDaoInt.getEmployeeByEmpNum(user.getEmail());
  			String customer = orderBean.getCustomer();
  			if(customer !=null){
  				cus = customerDaoInt.getClientByClientName(customer);
  				cusOrder.setCustomer(cus);
  			}else{
  				cusOrder.setCustomer(null);
  			}
  			if(emp.getRole().equalsIgnoreCase("Manager") || emp.getRole().equalsIgnoreCase("Admin")){
  				
  				cusOrder.setApprover(emp.getEmail());
  				if(orderBean.getStockType().equalsIgnoreCase("Boot")){
  					emp = employeeDaoInt.getEmployeeByEmpNum(orderBean.getTechnicianUserName());  					
  				}
  				cusOrder.setEmployee(emp);
  				String tempTechnician = emp.getFirstName() + " " +emp.getLastName();
  				
  			}else if(emp.getRole().equalsIgnoreCase("User")){                 
  				cusOrder.setApprover(emp.getEmail());
  				if(cusOrder.getStockType() == "Site"){
  					//emp = employeeDaoInt.getEmployeeByEmpNum(orderBean.getTechnicianUserName());  					
  				}if(cusOrder.getStockType() == "Boot"){
  					emp = employeeDaoInt.getEmployeeByEmpNum(orderBean.getTechnicianUserName());  					
  				}
  				cusOrder.setEmployee(emp);
  			}
  			else{
  				cusOrder.setEmployee(emp);
  				cusOrder.setApprover(orderBean.getApprover());
  			}
  			cusOrder.setStockType(orderBean.getStockType());
  			cusOrder.setStatus("Pending");
  			
  			cusOrder.setApproved(false);
  			cusOrder.setDateOrdered(dateFormat.format(date));

  			ArrayList< String> tempList = new ArrayList<String>();
  			for (int i = 0; i < orderBean.getPartNumberList().length; i++) {
  				splitString = orderBean.getPartNumberList()[i];
  				split = splitString.split(",");
  				partNumber = split[0];
  				
  				part = hOStockDaoInt.getSparePartBySerial(partNumber);
  				if(part != null){
  					quatity = Integer.parseInt(quantityArray[i]);
  					tempString = partNumber+":"+ quatity;
  					tempList.add(tempString);
  				}
  			}
  			
  			orderDetailList = new ArrayList<OrderDetails>();
  			orderDetailsList = new HashSet<OrderDetails>();
              Set<String> uniqueParts = removeDuplicates(tempList);
              
              for(String ord:uniqueParts){
              	split = ord.split(":");
  				partNumber = split[0];
  				quatity = Integer.parseInt(split[1]);
  				orderDetails = new OrderDetails();
              	part = hOStockDaoInt.getSparePartBySerial(partNumber);
  				if(part != null){
  					orderDetails.setPartNumber(partNumber);
  					orderDetails.setCompatibleDevice(part.getCompitableDevice());
  					orderDetails.setModelBrand(part.getModelBrand());
  					orderDetails.setItemDescription(part.getItemDescription());
  					orderDetails.setModel(part.getCompitableDevice());
  					orderDetails.setQuantity(quatity);
  					orderDetails.setLocation(orderBean.getLocation());
  					orderDetails.setStockType(cusOrder.getStockType());
  					orderDetails.setItemType(part.getItemType());
  					if(orderBean.getTechnician()!= null){
  						orderDetails.setTechnician(orderBean.getTechnician());
  					}
  					else{
  						orderDetails.setTechnician(emp.getFirstName()+" " +emp.getLastName());
  					}
  					
  					orderDetails.setOrderHeader(cusOrder);
  					
  					orderDetails.setDateTime(dateFormat.format(date));
  					orderDetailList.add(orderDetails);
  					orderDetailsList.add(orderDetails);
  					
  				}	
  				
              }
              cusOrder.setOrderDetails(orderDetailsList);
  			retMessage = makeOrder(cusOrder);
  			//String retMsg = detailsDaoInt.saveOrderDetails(orderDetailList);
  			deliveryLineItem.saveDeliveryNoteLineItems(orderDetailList);
  		} catch (Exception ex) {
  			retMessage = "Order cannot be proccessed.";
  		}
  		return retMessage;
      }
		
	}

	private Set<String> removeDuplicates(ArrayList<String> tempList){
		Set<String> uniqueParts = new HashSet<>(tempList);
		return uniqueParts;
	}
	private String[] quantity(String[] arr, int length) {
		String[] newQuantity = new String[length];
		int x = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != "") {
				newQuantity[x] = arr[i];
				x++;
			}
		}
		return newQuantity;

	}

	@Override
	public List<OrderHeader> pendingOrders() {
		ArrayList<OrderHeader> pendingList = new ArrayList<OrderHeader>();
		try {
			pendingOrders = getAllOrders();
			for (OrderHeader orderHeader : pendingOrders) {
				if (orderHeader.getStatus().equalsIgnoreCase("Pending")) {
					pendingList.add(orderHeader);
				}
			}
		} catch (Exception e) {

		}
		return pendingList;
	}

	public List<OrderHeader> receivedOrders() {
		ArrayList<OrderHeader> pendingList = new ArrayList<OrderHeader>();
		try {
			pendingOrders = getAllOrders();
			for (OrderHeader orderHeader : pendingOrders) {
				if (orderHeader.getStatus().equalsIgnoreCase("Received")) {
					pendingList.add(orderHeader);
				}
			}
		} catch (Exception e) {

		}
		return pendingList;
	}

	@Override
	public String approveOrder(Long recordID) {
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();
		
		try {
			cusOrder = getOrder(recordID);
			
			
			orderDetailList = detailsDaoInt.getOrderDetailsByOrderNum(recordID);
			retMessage = subtractOrderItems(orderDetailList);
			if (retMessage.equalsIgnoreCase("Ok")) {

				cusOrder.setStatus("Approved");
				cusOrder.setApproved(true);
				cusOrder.setDateApproved(dateFormat.format(date));
				sessionFactory.getCurrentSession().update(cusOrder);
				listOrders = detailsDaoInt.getOrderDetailsByOrderNum(recordID);

				String addStockToApprovedTable = approvedOrderStockDaoInt
						.approveOrderStock(listOrders);
				
				emp = employeeDaoInt.getEmployeeByEmpNum(cusOrder.getApprover());
				JavaMail.sendEmailForOrderApproved(cusOrder.getEmployee().getEmail(), cusOrder.getApprover(), emp.getFirstName(), cusOrder.getEmployee().getFirstName(), cusOrder);
				historyDaoInt.insetOrderHistory(cusOrder);
				retMessage = "Order " + orderNumber+cusOrder.getRecordID() + " approved.";
			} else {
				retMessage = "Order cannot be approved because ordered items are more that available items.";
			}

		} catch (Exception e) {
			retMessage = "Order " + orderNumber+cusOrder.getRecordID() + " not approved.";
		}
		return retMessage;
	}

	private String subtractOrderItems(List<OrderDetails> orderDetails) {
		int tempQuantity = 0;

		try {
			for (OrderDetails subtractQuatity : orderDetails) {
				part = hOStockDaoInt.getSparePartBySerial(subtractQuatity
						.getPartNumber());
				tempQuantity = part.getQuantity()
						- subtractQuatity.getQuantity();
				if (tempQuantity >= 0) {

					part.setQuantity(tempQuantity);
					sessionFactory.getCurrentSession().update(part);
					retMessage = "Ok";
				} else {
					retMessage = "Error";
					break;
				}
			}

		} catch (Exception e) {

		}
		return retMessage;
	}

	@Override
	public List<OrderHeader> pendingOrders(String approveName) {
		List<OrderHeader> pendingOrder = new ArrayList<OrderHeader>();
		try {
			List<OrderHeader> pendingForApprover = pendingOrders();

			for (OrderHeader order : pendingForApprover) {
				if (order.getApprover().equalsIgnoreCase(approveName)) {

					pendingOrder.add(order);
				}
			}
		} catch (Exception e) {
			retMessage = e.getMessage();
		}
		return pendingOrder;
	}

	@Override
	public String approveShipment(Long recordID) {
		
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();
		orderHeader = getOrder(recordID);
		date = new Date();
		if(orderHeader!=null && orderHeader.getStatus().equalsIgnoreCase("Approved")){
			
			orderHeader.setStatus("Shipped");
			orderHeader.setShippingDate(dateFormat.format(date));
			sessionFactory.getCurrentSession().update(orderHeader);
			JavaMail.sendEmailForShipment(orderHeader.getApprover(),orderHeader);
			historyDaoInt.insetOrderHistory(orderHeader);
			retMessage = "Order Number "+ orderNumber+orderHeader.getRecordID()+" shipped to "+ orderHeader.getEmployee().getFirstName()+ " "+orderHeader.getEmployee().getLastName()+".";
		}
		else if (orderHeader!=null && orderHeader.getStatus().equalsIgnoreCase("Shipped")){
			orderHeader.setStatus("Received");
			orderHeader.setOrderReceivedDateTime(dateFormat.format(date));
			
			sessionFactory.getCurrentSession().update(orderHeader);
			historyDaoInt.insetOrderHistory(orderHeader);
			List<Tickets> ticketList = ticketsDaoInt.getAwaitingSparesTickets();
			for(Tickets tick:ticketList){
				if(tick.getOrderHeader()!=null){
					if(tick.getOrderHeader().getRecordID()==orderHeader.getRecordID()){
						
						tick.setStatus("Re-Open");
						tick.setDateTime(dateFormat.format(date));
						sessionFactory.getCurrentSession().update(tick);
						ticketHistoryDaoInt.insertTicketHistory(tick);
					}
				}
				
			}
			
			if(orderHeader.getStockType().equalsIgnoreCase("Site")){
				orderDetailList = detailsDaoInt.getOrderDetailsByOrderNum(recordID);
				siteStocDaoInt.saveSiteStock(orderDetailList);
				retMessage = "Order Number "+ orderNumber+orderHeader.getRecordID()+" now available for site "+ orderHeader.getCustomer().getCustomerName()+".";
			}
			else{
				orderDetailList = detailsDaoInt.getOrderDetailsByOrderNum(recordID);
				 bootStockDaoInt.saveBootStock(orderDetailList);
				 System.out.println("After Save");
				 retMessage = "Order Number "+ orderNumber+orderHeader.getRecordID()+" now available in boot stock for "+ orderHeader.getEmployee().getFirstName() +" "+ orderHeader.getEmployee().getLastName()+".";
			}
		}
		return retMessage;
	}

	@Override
	public List<OrderHeader> shippedOrders() {
		pendingOrders = getAllOrders();
		List<OrderHeader> pendingOrder = new ArrayList<OrderHeader>();
		try{
			for(OrderHeader orderHeader:pendingOrders){
				if(orderHeader.getStatus().equalsIgnoreCase("Shipped")){
					pendingOrder.add(orderHeader);
				}
			}
		}catch(Exception exception){
			exception.getMessage();
		}
		return pendingOrder;
	}@Override
	public List<OrderHeader> shippedOrders(String technicianEmail){
		pendingOrders = getAllOrders();
		List<OrderHeader> pendingOrder = new ArrayList<OrderHeader>();
		try{
			for(OrderHeader orderHeader:pendingOrders){
				if(orderHeader.getStatus().equalsIgnoreCase("Shipped") && orderHeader.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)){
					pendingOrder.add(orderHeader);
				}
			}
		}catch(Exception exception){
			exception.getMessage();
		}
		return pendingOrder;
	}

	@Override
	public String declineOrder(Long recordID,String reasonForeclined) {
		
		date = new Date();
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try{
			cusOrder = declineOrder(recordID);
			
			cusOrder.setComments(reasonForeclined);
			cusOrder.setStatus("Declined");
			sessionFactory.getCurrentSession().update(cusOrder);
			historyDaoInt.insetOrderHistory(cusOrder);
			List<Tickets> ticketList = ticketsDaoInt.getAwaitingSparesTickets();
			for(Tickets tick:ticketList){
				
				if(tick.getOrderHeader()!= null){
					String trmpOrderNum = "ORD000"+tick.getOrderHeader().getRecordID();
					Long result = (long) Integer.parseInt(trmpOrderNum.substring(5));
					
					if(result == cusOrder.getRecordID()){
						tick.setStatus("Taken");
						tick.setDateTime(dateFormat.format(date));
						sessionFactory.getCurrentSession().update(tick);
						ticketHistoryDaoInt.insertTicketHistory(tick);
					}
				}
				
			}
			retMessage = "Order " + orderNumber+cusOrder.getRecordID()+ " Rejected";
		}catch(Exception e){
			retMessage = e.getMessage();
		}
		return retMessage;
	}
	private OrderHeader declineOrder(Long recordID){
		OrderHeader order = null;
		try{
			order = new OrderHeader();
			pendingOrders = getAllOrders();
			for(OrderHeader tempOrder:pendingOrders){
				Long tempFromDB = tempOrder.getRecordID();
				Long tempFromFontEnd = recordID;
				
				if ( tempFromDB.compareTo(tempFromFontEnd) == 0)
				{
					order = tempOrder;
				}
			}
		}catch(Exception exception){
			exception.getMessage();
		}
		return order;
	}

	@Override
	public int pendingOrdersCount(String approveName) {
	
		int tempCount = 0;
		try {
			List<OrderHeader> pendingForApprover = pendingOrders();

			
			for (OrderHeader order : pendingForApprover) {
				if (order.getApprover().equalsIgnoreCase(approveName)) {

					tempCount ++;
				}
			}
		} catch (Exception e) {
			retMessage = e.getMessage();
		}
		return tempCount;
	}
    @Override
	public int technicianOrdersCount(String technicianName) {
		int tempCount = 0;
		try {
			List<OrderHeader> shipperOrdersForTechnician = getShippedOrders();

			
			for (OrderHeader order : shipperOrdersForTechnician) {
				if (order.getEmployee().getEmail().equalsIgnoreCase(technicianName)) {

					tempCount ++;
				}
			}
		} catch (Exception e) {
			retMessage = e.getMessage();
		}
		return tempCount;
	}
	private List<OrderHeader> getShippedOrders(){
		List<OrderHeader> shippedOrders = new ArrayList<OrderHeader>();
		try{
			pendingOrders = getAllOrders();
			for(OrderHeader orderHeader:pendingOrders){
				if(orderHeader.getStatus().equalsIgnoreCase("Shipped")){
					shippedOrders.add(orderHeader);
				}
			}
		}catch(Exception e)
		{
			e.getMessage();
		}
		return shippedOrders;
	}
	
	private List<OrderHeader> getRejectedOrders(){
		List<OrderHeader> shippedOrders = new ArrayList<OrderHeader>();
		try{
			pendingOrders = getAllOrders();
			for(OrderHeader orderHeader:pendingOrders){
				if(orderHeader.getStatus().equalsIgnoreCase("Declined")){
					shippedOrders.add(orderHeader);
				}
			}
		}catch(Exception e)
		{
			e.getMessage();
		}
		return shippedOrders;
	}

	@Override
	public List<OrderHeader> getAllOrders(String startDate, String endDate) {
		List<Tickets> tempTickets = null;

		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateData = null;
		Calendar cal = Calendar.getInstance();
		date = new Date();
		Date previoueDay = new Date();
		Date currentDate = new Date();
		Date dataDate = new Date();
		List<OrderHeader>orders = new ArrayList<OrderHeader>();
		try {
			

			// convert to date
			currentDate = myFormat.parse(endDate);
			previoueDay = myFormat.parse(startDate);

			List<OrderHeader> tickets = getAllOrders();
			for (OrderHeader tic : tickets) {
				String convDate = tic.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (previoueDay.compareTo(dateData) <= 0
						&& currentDate.compareTo(dateData) >= 0) {
					orders.add(tic);
				}

			}

		} catch (Exception e) {
			e.getMessage();
		}

		return orders;
	}

	@Override
	public List<OrderHeader> getAllOrders(String startDate, String endDate,
			String technicianEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderHeader> getAllOrdersByDate() {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList = getAllOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getAllOrdersByDate(String technicianName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList = getAllOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianName)) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public int countNewOrders(String lastFourteenDays) {
		
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = pendingOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public int countApprovedOrders(String lastFourteenDays) {
		
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = approvedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}
    
	@Override
	public int countShippedOrders(String lastFourteenDays) {
		
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = shippedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}
	
	@Override
	public int countOrdersReceive(String lastFourteenDays) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = shippedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public int  getOrderCountForManager(String customer,
			String dateRange, String technician, Long orderNumber, String status) {
		
		boolean allCustomers = false;
		boolean allTechnicians = false;
		boolean dateNotSelected = false;
		int count = 0;
		List<OrderHeader> orderList = null;
		List<OrderHeader> aList = new ArrayList<OrderHeader>();

		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date myStart = new Date();
		Date myEnd = new Date();
		Date dateData = new Date();
		String convDate = "";
		String normalDate = "";

		try {

			allCustomers = customer.equalsIgnoreCase("All Customers");
			allTechnicians = technician.equalsIgnoreCase("All Technicians");
			dateNotSelected = dateRange.equalsIgnoreCase("Select Date");
			String startDate = "";
			String endDate = "";
			if (dateNotSelected != true) {
				startDate = dateRange.substring(0, 10);
				endDate = dateRange.substring(13);

				myStart = new Date();
				myEnd = new Date();
				dateData = new Date();

				myStart = myFormat.parse(startDate);
				myEnd = myFormat.parse(endDate);

			}

			if (orderNumber != null) {
				orderList = getAllOrders();
				for (OrderHeader order : orderList) {				
						if (order.getStatus().equalsIgnoreCase(status))
						{
							Long tempFromDB = order.getRecordID();
							Long tempFromFontEnd = orderNumber;
							
							if ( tempFromDB.compareTo(tempFromFontEnd) == 0)
							{
								count++;
							}
						}
				}
			} else if (orderNumber == null) {
				// Get OrderHeader for All Customer and All Technicians based on
				// date range
				if (allCustomers == true && allTechnicians == true) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							//convDate = order.getDateTime().substring(0, 10);
							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								// withinRange = true;
								if (order.getStatus().equalsIgnoreCase(status))
								{
									count++;
								}
							}
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getStatus().equalsIgnoreCase(status))
							{
								count++;
							}
						}
					}

				}
				// Get OrderHeader assigned to a specific technician for all
				// customers
				else if (allCustomers == true && allTechnicians == false) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								withinRange = true;
							}
							if (order.getEmployee().getEmail()
									.equalsIgnoreCase(technician)
									&& withinRange == true && order.getStatus().equalsIgnoreCase(status)) {
								count++;
							}

						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getEmployee().getEmail()
									.equalsIgnoreCase(technician) && order.getStatus().equalsIgnoreCase(status)) {
								count++;
							}
						}
					}
				}
				// Get OrderHeader assigned to all technicians for a specific
				// customer
				else if (allCustomers == false && allTechnicians == true) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								withinRange = true;
							}
						if (order.getCustomer() != null ) 
						{
							if (order.getCustomer()
									.getCustomerName()
									.equalsIgnoreCase(customer)
									&& withinRange == true && order.getStatus().equalsIgnoreCase(status))
							{
								count++;
							}

						}
							
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getCustomer() != null ) 
							{
								if (order.getCustomer()
										.getCustomerName()
										.equalsIgnoreCase(customer) && order.getStatus().equalsIgnoreCase(status)) {
									count++;
								}

							}
						
						}
					}
				}
				// Get order assigned to a technician for a customer
				else if (allCustomers == false && allTechnicians == false) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								withinRange = true;
							}
							
							if (order.getCustomer() != null ) 
							{
								if (order.getCustomer()
										.getCustomerName()
										.equalsIgnoreCase(customer)
										&& order.getEmployee().getEmail()
												.equalsIgnoreCase(technician)
										&& withinRange == true && order.getStatus().equalsIgnoreCase(status)) {
									count++;
								}
							}
							
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getCustomer() != null ) 
							{
								if (order.getCustomer()
										.getCustomerName()
										.equalsIgnoreCase(customer)
										&& order.getEmployee().getEmail()
												.equalsIgnoreCase(technician) && order.getStatus().equalsIgnoreCase(status)) {
									count++;
								}
							}
							

						}
					}
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return count;
	}
	
	@Override
	public List<OrderHeader>  getOrderListByStatusForManager(String customer,
			String dateRange, String technician, Long orderNumber, String status) {
		
		boolean allCustomers = false;
		boolean allTechnicians = false;
		boolean dateNotSelected = false;
		int count = 0;
		List<OrderHeader> orderList = null;
		List<OrderHeader> aList = new ArrayList<OrderHeader>();

		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date myStart = new Date();
		Date myEnd = new Date();
		Date dateData = new Date();
		String convDate = "";
		String normalDate = "";

		try {

			allCustomers = customer.equalsIgnoreCase("All Customers");
			allTechnicians = technician.equalsIgnoreCase("All Technicians");
			dateNotSelected = dateRange.equalsIgnoreCase("Select Date");
			String startDate = "";
			String endDate = "";
			if (dateNotSelected != true) {
				startDate = dateRange.substring(0, 10);
				endDate = dateRange.substring(13);

				myStart = new Date();
				myEnd = new Date();
				dateData = new Date();

				myStart = myFormat.parse(startDate);
				myEnd = myFormat.parse(endDate);

			}

			if (orderNumber != null) {
				orderList = getAllOrders();
				for (OrderHeader order : orderList) {				
						if (order.getStatus().equalsIgnoreCase(status))
						{
								Long tempFromDB = order.getRecordID();
								Long tempFromFontEnd = orderNumber;
								
								if ( tempFromDB.compareTo(tempFromFontEnd) == 0)
								{
									aList.add(order);
								}
						}
				}
			} else if (orderNumber == null) {
				// Get OrderHeader for All Customer and All Technicians based on
				// date range
				if (allCustomers == true && allTechnicians == true) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							//convDate = order.getDateTime().substring(0, 10);
							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								// withinRange = true;
								if (order.getStatus().equalsIgnoreCase(status))
								{
									aList.add(order);
								}
							}
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getStatus().equalsIgnoreCase(status))
							{
								aList.add(order);
							}
						}
					}

				}
				// Get OrderHeader assigned to a specific technician for all
				// customers
				else if (allCustomers == true && allTechnicians == false) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								withinRange = true;
							}
							if (order.getEmployee().getEmail()
									.equalsIgnoreCase(technician)
									&& withinRange == true && order.getStatus().equalsIgnoreCase(status)) {
								aList.add(order);
							}

						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getEmployee().getEmail()
									.equalsIgnoreCase(technician) && order.getStatus().equalsIgnoreCase(status)) {
								aList.add(order);
							}
						}
					}
				}
				// Get OrderHeader assigned to all technicians for a specific
				// customer
				else if (allCustomers == false && allTechnicians == true) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								withinRange = true;
							}
							if (order.getCustomer() != null ) 
							{
								if (order.getCustomer()
										.getCustomerName()
										.equalsIgnoreCase(customer)
										&& withinRange == true && order.getStatus().equalsIgnoreCase(status)) {
									aList.add(order);
								}

							}
							
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							
							if (order.getCustomer() != null ) 
							{
								if (order.getCustomer()
										.getCustomerName()
										.equalsIgnoreCase(customer) && order.getStatus().equalsIgnoreCase(status)) {
									aList.add(order);
								}
							}
							

						}
					}
				}
				// Get order assigned to a technician for a customer
				else if (allCustomers == false && allTechnicians == false) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								withinRange = true;
							}
							
							if (order.getCustomer() != null ) 
							{
								if (order.getCustomer()
										.getCustomerName()
										.equalsIgnoreCase(customer)
										&& order.getEmployee().getEmail()
												.equalsIgnoreCase(technician)
										&& withinRange == true && order.getStatus().equalsIgnoreCase(status)) {
									aList.add(order);
								}
							}
							
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getCustomer() != null ) 
							{
								if (order.getCustomer()
										.getCustomerName()
										.equalsIgnoreCase(customer)
										&& order.getEmployee().getEmail()
												.equalsIgnoreCase(technician) && order.getStatus().equalsIgnoreCase(status)) {
									aList.add(order);
								}
							}
							

						}
					}
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}
	

	@Override
	public List<OrderHeader> getOrderListForManager(String customer,
			String dateRange, String technician, Long orderNumber) {
		
		boolean allCustomers = false;
		boolean allTechnicians = false;
		boolean dateNotSelected = false;

		List<OrderHeader> orderList = null;
		List<OrderHeader> aList = new ArrayList<OrderHeader>();

		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date myStart = new Date();
		Date myEnd = new Date();
		Date dateData = new Date();
		String convDate = "";
		String normalDate = "";

		try {

			allCustomers = customer.equalsIgnoreCase("All Customers");
			allTechnicians = technician.equalsIgnoreCase("All Technicians");
			dateNotSelected = dateRange.equalsIgnoreCase("Select Date");
			String startDate = "";
			String endDate = "";
			if (dateNotSelected != true) {
				startDate = dateRange.substring(0, 10);
				endDate = dateRange.substring(13);

				myStart = new Date();
				myEnd = new Date();
				dateData = new Date();

				myStart = myFormat.parse(startDate);
				myEnd = myFormat.parse(endDate);

			}

			if (orderNumber != null) {
				orderList = getAllOrders();
				for (OrderHeader order : orderList) {
					
						Long tempFromDB = order.getRecordID();
						Long tempFromFontEnd = orderNumber;
						
						if ( tempFromDB.compareTo(tempFromFontEnd) == 0)
						{
							aList.add(order);
						}
					
				}
			} else if (orderNumber == null) {
				// Get OrderHeader for All Customer and All Technicians based on
				// date range
				if (allCustomers == true && allTechnicians == true) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							//convDate = order.getDateTime().substring(0, 10);
							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								// withinRange = true;
								aList.add(order);
							}
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							aList.add(order);
						}
					}

				}
				// Get OrderHeader assigned to a specific technician for all
				// customers
				else if (allCustomers == true && allTechnicians == false) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								withinRange = true;
							}
							if (order.getEmployee().getEmail()
									.equalsIgnoreCase(technician)
									&& withinRange == true) {
								aList.add(order);
							}

						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getEmployee().getEmail()
									.equalsIgnoreCase(technician)) {
								aList.add(order);
							}
						}
					}
				}
				// Get OrderHeader assigned to all technicians for a specific
				// customer
				else if (allCustomers == false && allTechnicians == true) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								withinRange = true;
							}
							if (order.getCustomer() != null ) 
							{
								if (order.getCustomer()
										.getCustomerName()
										.equalsIgnoreCase(customer)
										&& withinRange == true) {
									aList.add(order);
								}

							}
							
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getCustomer() != null )
							{
								if (order.getCustomer() != null ) 
								{
									if (order.getCustomer()
											.getCustomerName()
											.equalsIgnoreCase(customer)) {
										aList.add(order);
									}
								}
								
							}
							

						}
					}
				}
				// Get order assigned to a technician for a customer
				else if (allCustomers == false && allTechnicians == false) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								withinRange = true;
							}
							if (order.getCustomer() != null ) 
							{
								if (order.getCustomer()
										.getCustomerName()
										.equalsIgnoreCase(customer)
										&& order.getEmployee().getEmail()
												.equalsIgnoreCase(technician)
										&& withinRange == true) {
									aList.add(order);
								}
							}
							
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getCustomer() != null ) 
							{
								if (order.getCustomer()
										.getCustomerName()
										.equalsIgnoreCase(customer)
										&& order.getEmployee().getEmail()
												.equalsIgnoreCase(technician)) {
									aList.add(order);
								}

							}
							
						}
					}
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}
	
	@Override
	public int getOrderCountForTechnician(String customer,
			String dateRange, String technician, Long orderNumber, String status) {
		
		boolean allCustomers = false;
		boolean allTechnicians = false;
		boolean dateNotSelected = false;
		
		int count = 0;
		List<OrderHeader> orderList = null;
		List<OrderHeader> aList = new ArrayList<OrderHeader>();

		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date myStart = new Date();
		Date myEnd = new Date();
		Date dateData = new Date();
		String convDate = "";
		String normalDate = "";

		try {

			allCustomers = customer.equalsIgnoreCase("All Customers");
			dateNotSelected = dateRange.equalsIgnoreCase("Select Date");
			String startDate = "";
			String endDate = "";
			if (dateNotSelected != true) {
				startDate = dateRange.substring(0, 10);
				endDate = dateRange.substring(13);

				myStart = new Date();
				myEnd = new Date();
				dateData = new Date();

				myStart = myFormat.parse(startDate);
				myEnd = myFormat.parse(endDate);

			}

			if (orderNumber != null) {
				orderList = getAllOrders();
				for (OrderHeader order : orderList) {
						if (order.getEmployee().getEmail().equalsIgnoreCase(technician) && order.getStatus().equalsIgnoreCase(status))
						{
							Long tempFromDB = order.getRecordID();
							Long tempFromFontEnd = orderNumber;
							
							if ( tempFromDB.compareTo(tempFromFontEnd) == 0)
							{
								count++;
							}	
						}
					
				}
			} else if (orderNumber == null) {
				// Get OrderHeader for All Customer and All Technicians based on
				// date range
				if (allCustomers == true) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							//convDate = order.getDateTime().substring(0, 10);
							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								// withinRange = true;
								if (order.getEmployee().getEmail().equalsIgnoreCase(technician) && order.getStatus().equalsIgnoreCase(status))
								{
									count++;
								}
							}
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getEmployee().getEmail().equalsIgnoreCase(technician) && order.getStatus().equalsIgnoreCase(status))
							{
								count++;
							}
							
						}
					}

				}
				else if (allCustomers == false) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								withinRange = true;
							}
							if (order.getCustomer() != null ) 
							{
								if (order.getCustomer()
										.getCustomerName()
										.equalsIgnoreCase(customer)
										&& withinRange == true) {
									
									if (order.getEmployee().getEmail().equalsIgnoreCase(technician) && order.getStatus().equalsIgnoreCase(status))
									{
										count++;
									}
									
								}

							}
							
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getCustomer() != null )
							{
								if (order.getCustomer() != null ) 
								{
									if (order.getCustomer()
											.getCustomerName()
											.equalsIgnoreCase(customer)) {
										
										if (order.getEmployee().getEmail().equalsIgnoreCase(technician) && order.getStatus().equalsIgnoreCase(status))
										{
											count++;
										}
										
									}
								}
								
							}
							

						}
					}
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return count;
	}
	
	
	@Override
	public List<OrderHeader> getOrderListByStatusForTechnician(String customer,
			String dateRange, String technician, Long orderNumber, String status) {
		
		boolean allCustomers = false;
		boolean allTechnicians = false;
		boolean dateNotSelected = false;
		
		int count = 0;
		List<OrderHeader> orderList = null;
		List<OrderHeader> aList = new ArrayList<OrderHeader>();

		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date myStart = new Date();
		Date myEnd = new Date();
		Date dateData = new Date();
		String convDate = "";
		String normalDate = "";

		try {

			allCustomers = customer.equalsIgnoreCase("All Customers");
			dateNotSelected = dateRange.equalsIgnoreCase("Select Date");
			String startDate = "";
			String endDate = "";
			if (dateNotSelected != true) {
				startDate = dateRange.substring(0, 10);
				endDate = dateRange.substring(13);

				myStart = new Date();
				myEnd = new Date();
				dateData = new Date();

				myStart = myFormat.parse(startDate);
				myEnd = myFormat.parse(endDate);

			}

			if (orderNumber != null) {
				orderList = getAllOrders();
				for (OrderHeader order : orderList) {					
						if (order.getEmployee().getEmail().equalsIgnoreCase(technician))
						{
							Long tempFromDB = order.getRecordID();
							Long tempFromFontEnd = orderNumber;
							
							if ( tempFromDB.compareTo(tempFromFontEnd) == 0)
							{
								count++;
							}	
						}
					}
			} else if (orderNumber == null) {
				// Get OrderHeader for All Customer and All Technicians based on
				// date range
				if (allCustomers == true) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							//convDate = order.getDateTime().substring(0, 10);
							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								// withinRange = true;
								if (order.getEmployee().getEmail().equalsIgnoreCase(technician) && order.getStatus().equalsIgnoreCase(status))
								{
									aList.add(order);
								}
							}
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getEmployee().getEmail().equalsIgnoreCase(technician) && order.getStatus().equalsIgnoreCase(status))
							{
								aList.add(order);
							}
							
						}
					}

				}
				else if (allCustomers == false) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								withinRange = true;
							}
							if (order.getCustomer() != null ) 
							{
								if (order.getCustomer()
										.getCustomerName()
										.equalsIgnoreCase(customer)
										&& withinRange == true) {
									
									if (order.getEmployee().getEmail().equalsIgnoreCase(technician) && order.getStatus().equalsIgnoreCase(status))
									{
										aList.add(order);
									}
									
								}

							}
							
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getCustomer() != null )
							{
								if (order.getCustomer() != null ) 
								{
									if (order.getCustomer()
											.getCustomerName()
											.equalsIgnoreCase(customer)) {
										
										if (order.getEmployee().getEmail().equalsIgnoreCase(technician) && order.getStatus().equalsIgnoreCase(status))
										{
											aList.add(order);
										}
										
									}
								}
								
							}
							

						}
					}
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}
	
	
	@Override
	public List<OrderHeader> getOrderListForTechnician(String customer,
			String dateRange, String technician, Long orderNumber) {
		
		boolean allCustomers = false;
		boolean allTechnicians = false;
		boolean dateNotSelected = false;

		List<OrderHeader> orderList = null;
		List<OrderHeader> aList = new ArrayList<OrderHeader>();

		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date myStart = new Date();
		Date myEnd = new Date();
		Date dateData = new Date();
		String convDate = "";
		String normalDate = "";

		try {

			allCustomers = customer.equalsIgnoreCase("All Customers");
			dateNotSelected = dateRange.equalsIgnoreCase("Select Date");
			String startDate = "";
			String endDate = "";
			if (dateNotSelected != true) {
				startDate = dateRange.substring(0, 10);
				endDate = dateRange.substring(13);

				myStart = new Date();
				myEnd = new Date();
				dateData = new Date();

				myStart = myFormat.parse(startDate);
				myEnd = myFormat.parse(endDate);

			}

			if (orderNumber != null) {
				orderList = getAllOrders();
				for (OrderHeader order : orderList) {					
						if (order.getEmployee().getEmail().equalsIgnoreCase(technician))
						{
						
							Long tempFromDB = order.getRecordID();
							Long tempFromFontEnd = orderNumber;
							
							if ( tempFromDB.compareTo(tempFromFontEnd) == 0)
							{
								aList.add(order);
							}	
						}
				}
			} else if (orderNumber == null) {
				// Get OrderHeader for All Customer and All Technicians based on
				// date range
				if (allCustomers == true) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							//convDate = order.getDateTime().substring(0, 10);
							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								// withinRange = true;
								if (order.getEmployee().getEmail().equalsIgnoreCase(technician))
								{
									aList.add(order);
								}
							}
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getEmployee().getEmail().equalsIgnoreCase(technician))
							{
								aList.add(order);
							}
						}
					}

				}
				// Get OrderHeader assigned to a specific technician for all
				// customers
				else if (allCustomers == true && allTechnicians == false) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								withinRange = true;
							}
							if (order.getEmployee().getEmail()
									.equalsIgnoreCase(technician)
									&& withinRange == true) {
								aList.add(order);
							}

						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getEmployee().getEmail()
									.equalsIgnoreCase(technician)) {
								aList.add(order);
							}
						}
					}
				}
				// Get OrderHeader assigned to all technicians for a specific
				// customer
				else if (allCustomers == false) {
					if (dateNotSelected == false) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {

							convDate = order.getDateOrdered().substring(0, 10);
							normalDate = convDate.replace("/", "-");
							dateData = myFormat.parse(normalDate);
							boolean withinRange = false;
							if (myStart.compareTo(dateData) <= 0
									&& myEnd.compareTo(dateData) >= 0) {
								withinRange = true;
							}
							if (order.getCustomer() != null ) 
							{
								if (order.getCustomer()
										.getCustomerName()
										.equalsIgnoreCase(customer)
										&& withinRange == true) {
									
									if (order.getEmployee().getEmail().equalsIgnoreCase(technician))
									{
										aList.add(order);
									}
									
								}

							}
							
						}
					} else if (dateNotSelected == true) {
						orderList = getAllOrders();
						for (OrderHeader order : orderList) {
							if (order.getCustomer() != null )
							{
								if (order.getCustomer() != null ) 
								{
									if (order.getCustomer()
											.getCustomerName()
											.equalsIgnoreCase(customer)) {
										
										if (order.getEmployee().getEmail().equalsIgnoreCase(technician))
										{
											aList.add(order);
										}
										
									}
								}
								
							}
							

						}
					}
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}
	
	@Override
	public List<OrderHeader> getLastFourteenDaysOrders() {
		
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList = getAllOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> approvedOrders() {
		ArrayList<OrderHeader> pendingList = new ArrayList<OrderHeader>();
		try {
			pendingOrders = getAllOrders();
			for (OrderHeader orderHeader : pendingOrders) {
				if (orderHeader.getStatus().equalsIgnoreCase("Approved")) {
					pendingList.add(orderHeader);
				}
			}
		} catch (Exception e) {

		}
		return pendingList;
	}
    
	@Override
	public List<OrderHeader> getLastFourteenDaysApprovedOrders() {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =approvedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrders() {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =pendingOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedOrders() {
		
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =shippedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public int countClosedOrder(String lastFourteenDays) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = receivedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrders() {
	    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =receivedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public int countNewOrders(String lastFourteenDays, String technicianName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = pendingOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianName)) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public int countClosedOrder(String lastFourteenDays, String technicianName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countApprovedOrders(String lastFourteenDays,
			String technicianName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countShippedOrders(String lastFourteenDays, String technicianName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countOrdersReceive(String lastFourteenDays, String technicianName) {
				SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = new Date();
				// get Calendar instance
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				int tempCount = 0;
				List<OrderHeader> pendingOrders = null;
				try {
					// substract 7 days
					// If we give 7 there it will give 8 days back
					cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
					// convert to date
					Date myDate = cal.getTime();

					String date1 = myFormat.format(myDate);
					String Date2 = myFormat.format(currentDate);
					Date current = new Date();
					Date previous = new Date();
					Date dateData = new Date();

					current = myFormat.parse(date1);
					previous = myFormat.parse(Date2);

					pendingOrders = shippedOrders();
					for (OrderHeader order : pendingOrders) {
						String convDate = order.getDateOrdered().substring(0, 10);
						String normalDate = convDate.replace("/", "-");
						dateData = myFormat.parse(normalDate);
						if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianName)) {
							tempCount++;
						}
					}
				} catch (Exception exception) {
					exception.getMessage();
				}
		       System.out.println(tempCount);
				return tempCount;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrders(String technicianName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList = getAllOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianName)   ) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersToReceive(
			String technicianName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList = getAllOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianName)&& order.getStatus().equalsIgnoreCase("Shipped")) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrders(
			String technicianName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =receivedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianName)) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public int countNewOrders(String lastFourteenDays, String technicianName,
			String customerName) {
		Date date = null;
		Date date1 = null;
		int count = 0;
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date previoueDay = new Date();
		Date currentDate = new Date();
		System.out.println(lastFourteenDays.length());
		try{
			if(lastFourteenDays.length()>5 && customerName.length()>3){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				List<OrderHeader>pendingOrders = pendingOrders();
				for(OrderHeader order:pendingOrders){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
						&& currentDate.compareTo(dateData) <= 0 && order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						count++;
					}
				}
				
			}else if(lastFourteenDays.length()>5 && customerName.equalsIgnoreCase("")){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				List<OrderHeader>pendingOrders = pendingOrders();
				for(OrderHeader order:pendingOrders){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
							&& currentDate.compareTo(dateData) <= 0){
						count++;
					}
				}
			}
			
			
		}catch(Exception e){
			e.getMessage();
		}
		return count;
	}

	@Override
	public int countClosedOrder(String lastFourteenDays, String technicianName,
			String customerName) {
		Date date = null;
		Date date1 = null;
		int count = 0;
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date previoueDay = new Date();
		Date currentDate = new Date();
		System.out.println(lastFourteenDays.length());
		try{
			if(lastFourteenDays.length()>5 && customerName.length()>3){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				List<OrderHeader>pendingOrders = receivedOrders();
				for(OrderHeader order:pendingOrders){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
						&& currentDate.compareTo(dateData) <= 0 && order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						count++;
					}
				}
				
			}else if(lastFourteenDays.length()>5 && customerName.equalsIgnoreCase("")){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				List<OrderHeader>pendingOrders = receivedOrders();
				for(OrderHeader order:pendingOrders){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
							&& currentDate.compareTo(dateData) <= 0){
						count++;
					}
				}
			}
			
			
		}catch(Exception e){
			e.getMessage();
		}
		return count;
	}

	@Override
	public int countApprovedOrders(String lastFourteenDays,
			String technicianName, String customerName) {
		Date date = null;
		Date date1 = null;
		int count = 0;
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date previoueDay = new Date();
		Date currentDate = new Date();
		System.out.println(lastFourteenDays.length());
		try{
			if(lastFourteenDays.length()>5 && customerName.length()>3){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				List<OrderHeader>pendingOrders = approvedOrders();
				for(OrderHeader order:pendingOrders){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
						&& currentDate.compareTo(dateData) <= 0 && order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						count++;
					}
				}
				
			}else if(lastFourteenDays.length()>5 && customerName.equalsIgnoreCase("")){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				List<OrderHeader>pendingOrders = approvedOrders();
				for(OrderHeader order:pendingOrders){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
							&& currentDate.compareTo(dateData) <= 0){
						count++;
					}
				}
			}
			
			
		}catch(Exception e){
			e.getMessage();
		}
		return count;
	}

	@Override
	public int countShippedOrders(String lastFourteenDays,
			String technicianName, String customerName) {
		Date date = null;
		Date date1 = null;
		int count = 0;
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date previoueDay = new Date();
		Date currentDate = new Date();
		try{
			if(lastFourteenDays.length()>5 && customerName.length()>3){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				List<OrderHeader>pendingOrders = shippedOrders();
				for(OrderHeader order:pendingOrders){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
						&& currentDate.compareTo(dateData) <= 0 && order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						count++;
					}
				}
				
			}else if(lastFourteenDays.length()>5 && customerName.equalsIgnoreCase("")){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				List<OrderHeader>pendingOrders = shippedOrders();
				for(OrderHeader order:pendingOrders){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
							&& currentDate.compareTo(dateData) <= 0){
						count++;
					}
				}
			}
			
			
		}catch(Exception e){
			e.getMessage();
		}
		return count;
	}

	@Override
	public int countOrdersReceive(String lastFourteenDays,
			String technicianName, String customerName) {
		Date date = null;
		Date date1 = null;
		int count = 0;
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date previoueDay = new Date();
		Date currentDate = new Date();
		System.out.println(lastFourteenDays.length());
		try{
			if(lastFourteenDays.length()>5 && customerName.length()>3){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				List<OrderHeader>pendingOrders = shippedOrders();
				for(OrderHeader order:pendingOrders){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
						&& currentDate.compareTo(dateData) <= 0 && order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						count++;
					}
				}
				
			}else if(lastFourteenDays.length()>5 && customerName.equalsIgnoreCase("")){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				List<OrderHeader>pendingOrders = shippedOrders();
				for(OrderHeader order:pendingOrders){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
							&& currentDate.compareTo(dateData) <= 0){
						count++;
					}
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return count;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysApprovedOrders(
			String lastFourteenDays, String technicianName, String customerName) {
		
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		Date date = null;
		Date date1 = null;
		int count = 0;
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date previoueDay = new Date();
		Date currentDate = new Date();
		System.out.println(lastFourteenDays.length());
		try{
			if(lastFourteenDays.length()>5 && customerName.length()>3){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				ticketList =approvedOrders();
				for(OrderHeader order:ticketList){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
						&& currentDate.compareTo(dateData) <= 0 && order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						aList.add(order);
					}
				}
				
			}else if(lastFourteenDays.length()>5 && customerName.equalsIgnoreCase("")){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				ticketList =approvedOrders();
				for(OrderHeader order:ticketList){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
							&& currentDate.compareTo(dateData) <= 0){
						aList.add(order);
					}
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrders(
			String lastFourteenDays, String technicianName, String customerName) {
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		Date date = null;
		Date date1 = null;
		int count = 0;
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date previoueDay = new Date();
		Date currentDate = new Date();
		System.out.println(lastFourteenDays.length());
		try{
			if(lastFourteenDays.length()>5 && customerName.length()>3){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				ticketList =pendingOrders();
				for(OrderHeader order:ticketList){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
						&& currentDate.compareTo(dateData) <= 0 && order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						aList.add(order);
					}
				}
				
			}else if(lastFourteenDays.length()>5 && customerName.equalsIgnoreCase("")){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				ticketList =pendingOrders();
				for(OrderHeader order:ticketList){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
							&& currentDate.compareTo(dateData) <= 0){
						aList.add(order);
					}
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedOrders(
			String lastFourteenDays, String technicianName, String customerName) {
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		Date date = null;
		Date date1 = null;
		int count = 0;
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date previoueDay = new Date();
		Date currentDate = new Date();
		System.out.println(lastFourteenDays.length());
		try{
			if(lastFourteenDays.length()>5 && customerName.length()>3){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				ticketList =shippedOrders();
				for(OrderHeader order:ticketList){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
						&& currentDate.compareTo(dateData) <= 0 && order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						aList.add(order);
					}
				}
				
			}else if(lastFourteenDays.length()>5 && customerName.equalsIgnoreCase("")){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				ticketList =shippedOrders();
				for(OrderHeader order:ticketList){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
							&& currentDate.compareTo(dateData) <= 0){
						aList.add(order);
					}
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrders(
			String lastFourteenDays, String technicianName, String customerName) {
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		Date date = null;
		Date date1 = null;
		int count = 0;
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date previoueDay = new Date();
		Date currentDate = new Date();
		try{
			if( customerName.length()>3){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				ticketList =receivedOrders();
				for(OrderHeader order:ticketList){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
						&& currentDate.compareTo(dateData) <= 0 && order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						aList.add(order);
					}
				}
				
			}else if(lastFourteenDays.length()>5 && customerName.equalsIgnoreCase("")){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				ticketList =receivedOrders();
				for(OrderHeader order:ticketList){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
							&& currentDate.compareTo(dateData) <= 0){
						aList.add(order);
					}
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersToReceive(
			String lastFourteenDays, String technicianName, String customerName) {
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		Date date = null;
		Date date1 = null;
		int count = 0;
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date previoueDay = new Date();
		Date currentDate = new Date();
		try{
			if(lastFourteenDays.length()>5 && customerName.length()>3){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				ticketList = getAllOrders();
				for(OrderHeader order:ticketList){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
						&& currentDate.compareTo(dateData) <= 0 && order.getCustomer().getCustomerName().equalsIgnoreCase(customerName) &&order.getStatus().equalsIgnoreCase("Shipped")){
						aList.add(order);
					}
				}
				
			}else if(lastFourteenDays.length()>5 && customerName.equalsIgnoreCase("")){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				ticketList = getAllOrders();
				for(OrderHeader order:ticketList){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
							&& currentDate.compareTo(dateData) <= 0 && order.getStatus().equalsIgnoreCase("Shipped")){
						aList.add(order);
					}
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrders(String lastFourteenDays,
			String technicianName, String customerName) {
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		Date date = null;
		Date date1 = null;
		int count = 0;
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date previoueDay = new Date();
		Date currentDate = new Date();
		System.out.println(lastFourteenDays.length());
		try{
			if(lastFourteenDays.length()>5 && customerName.length()>3){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				ticketList = getAllOrders();
				for(OrderHeader order:ticketList){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
						&& currentDate.compareTo(dateData) <= 0 && order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						aList.add(order);
					}
				}
				
			}else if(lastFourteenDays.length()>5 && customerName.equalsIgnoreCase("")){
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				ticketList = getAllOrders();
				for(OrderHeader order:ticketList){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(previoueDay.compareTo(dateData) >= 0
							&& currentDate.compareTo(dateData) <= 0){
						aList.add(order);
					}
				}
			}else{
				String firstDate = lastFourteenDays.substring(0, 10);
				String secondDate = lastFourteenDays.substring(13,23);
				
				date = new SimpleDateFormat("MM/dd/yyyy").parse(firstDate);
				date1 = new SimpleDateFormat("MM/dd/yyyy").parse(secondDate);
				
				String newFirstDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
				String newSecondDate = new SimpleDateFormat("yyyy-MM-dd").format(date1);
				
				currentDate = myFormat.parse(newFirstDate);
				previoueDay = myFormat.parse(newSecondDate);
				
				ticketList = getAllOrders();
				for(OrderHeader order:ticketList){
					String convDate = order.getDateOrdered().substring(0, 10);
					String normalDate = convDate.replace("/", "-");
					Date dateData = myFormat.parse(normalDate);
					if(order.getCustomer().getCustomerName().equalsIgnoreCase(customerName) ){
						aList.add(order);
					}
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return aList;
	}

	@Override
	public int countRejectedOrder(String lastFourteenDays) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = getRejectedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public int countRejectedOrder(String lastFourteenDays,
			String technicianName, String customerName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countRejectedOrder(String lastFourteenDays, String technicianName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = getRejectedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianName)) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrders(
			String lastFourteenDays, String technicianName, String customerName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderHeader> getLastFourteenRejectedOrders(
			String lastFourteenDays) {
		
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =getRejectedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(lastFourteenDays)) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrders() {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =getRejectedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
        System.err.println(aList.size());
		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrders(
			String technicianName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =getRejectedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianName)) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
        System.err.println(aList.size());
		return aList;
	}

	@Override
	public int countNewOrdersForCustomer(String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			

			pendingOrders = pendingOrders();
			for (OrderHeader order : pendingOrders) {
				
				if (order.getCustomer()!=null) {
					if(order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						tempCount++;
					}
					
				}else if(customerName.equalsIgnoreCase("All Customers")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public int countClosedOrderForCustomer(String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			

			pendingOrders = receivedOrders();
			for (OrderHeader order : pendingOrders) {
				
				if (order.getCustomer()!=null) {
					if(order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						tempCount++;
					}
					
				}else if(customerName.equalsIgnoreCase("All Customers")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public int countApprovedOrdersForCustomer(String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {

			pendingOrders = approvedOrders();
			for (OrderHeader order : pendingOrders) {
				
				if (order.getCustomer()!=null) {
					if(order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						tempCount++;
					}
					
				}else if(customerName.equalsIgnoreCase("All Customers")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public int countShippedOrdersForCustomer(String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			pendingOrders = shippedOrders();
			for (OrderHeader order : pendingOrders) {
				
				if (order.getCustomer()!=null) {
					if(order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						tempCount++;
					}
					
				}else if(customerName.equalsIgnoreCase("All Customers")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public int countOrdersReceiveForCustomer(String customerName) {
				SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date currentDate = new Date();
				// get Calendar instance
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				int tempCount = 0;
				List<OrderHeader> pendingOrders = null;
				try {
					

					pendingOrders = shippedOrders();
					for (OrderHeader order : pendingOrders) {
						
						if (order.getCustomer()!=null) {
							if(order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
								tempCount++;
							}
							
						}else if(customerName.equalsIgnoreCase("All Customers")){
							tempCount++;
						}
					}
				} catch (Exception exception) {
					exception.getMessage();
				}
		       System.out.println(tempCount);
				return tempCount;
	}

	@Override
	public int countRejectedOrderForCustomer(String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			
			pendingOrders = getRejectedOrders();
			for (OrderHeader order : pendingOrders) {
				if (order.getCustomer()!=null) {
					if(order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)){
						tempCount++;
					}
					
				}else if(customerName.equalsIgnoreCase("All Customers")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForCustomer(
			String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			ticketList =approvedOrders();
			for (OrderHeader order : ticketList) {
				if(order.getCustomer()!=null){
					if (order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)) {
						aList.add(order);
					}
				}else if(customerName.equalsIgnoreCase("All Customers")){
					aList.add(order);
				}
				
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForCustomer(
			String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			
			ticketList =pendingOrders();
			for (OrderHeader order : ticketList) {
					if(order.getCustomer()!= null){
						if (order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)) {
							aList.add(order);
						}
					}else if(customerName.equalsIgnoreCase("All Customers")){
						aList.add(order);
					}
				
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForCustomer(
			String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			

			ticketList =shippedOrders();
			for (OrderHeader order : ticketList) {
				if(order.getCustomer()!= null){
					if ( order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)) {
						aList.add(order);
					}
				}else if(customerName.equalsIgnoreCase("All Customers")){
					aList.add(order);
				}
				
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForCustomer(
			String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			
			ticketList =receivedOrders();
			for (OrderHeader order : ticketList) {
				
				if(order.getCustomer()!= null){
					if ( order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)) {
						aList.add(order);
					}
				}else if(customerName.equalsIgnoreCase("All Customers")){
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForCustomer(
			String customerName) {
		
		return null;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForCustomer(
			String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			
			ticketList =getRejectedOrders();
			for (OrderHeader order : ticketList) {
				
				if(order.getCustomer()!= null){
					if ( order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)) {
						aList.add(order);
					}
				}else if(customerName.equalsIgnoreCase("All Customers")){
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersForCustomer(String customerName) {
		
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			
			ticketList = getAllOrders();
			for (OrderHeader order : ticketList) {
				if(customerName.equalsIgnoreCase("All Customers")){
					aList.add(order);
				}else if(order.getCustomer()!= null){
					if ( order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)) {
						aList.add(order);
					}
				}
				
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public int countNewOrdersForTechnician_Customer(String technician,
			String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = pendingOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (order.getEmployee().getEmail().equalsIgnoreCase(technician)&& order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)) {
					tempCount++;
				}else if(customerName.equalsIgnoreCase("All Customers")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public int countClosedOrderForTechnician_Customer(String technician,
			String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			
			pendingOrders = receivedOrders();
			for (OrderHeader order : pendingOrders) {
				
				if (order.getEmployee().getEmail().equalsIgnoreCase(technician)&& order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)) {
					tempCount++;
				}else if(customerName.equalsIgnoreCase("All Customers")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public int countApprovedOrdersForTechnician_Customer(String technician,
			String customerName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countShippedOrdersForTechnicianCustomer(String technician,
			String customerName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countOrdersReceiveForTechnician_Customer(String technician,
			String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			
			pendingOrders = shippedOrders();
			for (OrderHeader order : pendingOrders) {
				
				if (order.getEmployee().getEmail().equalsIgnoreCase(technician)&& order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)) {
					tempCount++;
				}else if(customerName.equalsIgnoreCase("All Customers")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public int countRejectedOrderForTechnicianCustomer(String technician,
			String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			

			pendingOrders = getRejectedOrders();
			for (OrderHeader order : pendingOrders) {
				
				if (order.getEmployee().getEmail().equalsIgnoreCase(technician)&& order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)) {
					tempCount++;
				}else if(customerName.equalsIgnoreCase("All Customers")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForTechnicianCustomer(
			String technician, String customerName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForCustomer(
			String technician, String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			
			ticketList =pendingOrders();
			for (OrderHeader order : ticketList) {
				if(order.getCustomer()!= null){
					if ( order.getEmployee().getEmail().equalsIgnoreCase(technician)&& order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)) {
						aList.add(order);
					}
				}else if(customerName.equalsIgnoreCase("All Customer")){
					aList.add(order);
				}
				
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForTechnicianCustomer(
			String technician, String customerName) {
		
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			
			ticketList =shippedOrders();
			for (OrderHeader order : ticketList) {
				if(order.getCustomer()!= null){
					if ( order.getEmployee().getEmail().equalsIgnoreCase(technician)&& order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)) {
						aList.add(order);
					}
				}else if(customerName.equalsIgnoreCase("All Customer")){
					aList.add(order);
				}
				
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForTechnicianCustomer(
			String technician, String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			
			ticketList =receivedOrders();
			for (OrderHeader order : ticketList) {
				
				if(order.getCustomer() != null){
					if (order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)&& order.getEmployee().getEmail().equalsIgnoreCase(technician)) {
						aList.add(order);
					}
				}
				else if(customerName.equalsIgnoreCase("All Customers")){
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForTechnicianCustomer(
			String technician, String customerName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForTechnicianCustomer(
			String technician, String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			

			ticketList =getRejectedOrders();
			for (OrderHeader order : ticketList) {
				if(order.getCustomer()!=null){
					if (order.getEmployee().getEmail().equalsIgnoreCase(technician)&& order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)) {
						aList.add(order);
					}
				}else if (customerName.equalsIgnoreCase("All Customers")){
					aList.add(order);
				}
				
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
        System.err.println(aList.size());
		return aList;
	}

	@Override
	public int countClosedOrderForTechnician(String technician) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = receivedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technician)) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersForTechnicianCustomer(
			String technician,String customerName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
		      ticketList = getAllOrders();
			for (OrderHeader order : ticketList) {
				
				if(order.getCustomer()!=null){
					if (order.getEmployee().getEmail().equalsIgnoreCase(technician)&& order.getCustomer().getCustomerName().equalsIgnoreCase(customerName)) {
						aList.add(order);
					}else if(customerName.equalsIgnoreCase("All Customers")){
						aList.add(order);
					}
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public int countNewOrdersForSelectedDate(String lastFourteenDays) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			if(lastFourteenDays.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);


			pendingOrders = pendingOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public int countClosedOrderForSelectedDate(String lastFourteenDays) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			if(lastFourteenDays.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = receivedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public int countApprovedOrdersForSelectedDate(String lastFourteenDays) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			if(lastFourteenDays.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = approvedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public int countShippedOrdersForSelectedDate(String lastFourteenDays) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			if(lastFourteenDays.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = shippedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public int countOrdersReceiveForSelectedDate(String lastFourteenDays) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			if(lastFourteenDays.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = shippedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public int countRejectedOrderForSelectedDate(String lastFourteenDays) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			if(lastFourteenDays.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = getRejectedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
		
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersForSelectedDate(String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
						if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
							cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
 						}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
							cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
						}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
							cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
						}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
							cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
						}
						// convert to date
						Date myDate = cal.getTime();

						String date1 = myFormat.format(myDate);
						String Date2 = myFormat.format(currentDate);
						Date current = new Date();
						Date previous = new Date();
						Date dateData = new Date();

						current = myFormat.parse(date1);
						previous = myFormat.parse(Date2);

			ticketList = getAllOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		System.err.println(aList.size());
		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForSelectedDate(
			String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
			if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
				}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =approvedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForSelectedDate(
			String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
			if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
				}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =pendingOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForSelectedDate(
			String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
			if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
				}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =shippedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForSelectedDate(
			String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
			if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
				}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =receivedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForSelectedDate(
			String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
			if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
				}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =receivedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForSelectedDate(
			String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
			if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
				}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =getRejectedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public int countNewOrdersForSelectedDate(String technician,
			String lastFourteenDays) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			if(lastFourteenDays.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);


			pendingOrders = pendingOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technician)) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public int countClosedOrderForSelectedDate(String technician,
			String lastFourteenDays) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			if(lastFourteenDays.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = receivedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technician)) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public int countApprovedOrdersForSelectedDate(String technician,
			String lastFourteenDays) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countShippedOrdersForSelectedDate(String technician,
			String lastFourteenDays) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			if(lastFourteenDays.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = shippedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0  &&order.getEmployee().getEmail().equalsIgnoreCase(technician)) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public int countOrdersReceiveForSelectedDate(String technician,
			String lastFourteenDays) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			if(lastFourteenDays.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = shippedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technician)) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public int countRejectedOrderForSelectedDate(String technician,
			String lastFourteenDays) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			if(lastFourteenDays.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(lastFourteenDays.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			pendingOrders = getRejectedOrders();
			for (OrderHeader order : pendingOrders) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technician)) {
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForSelectedDate(
			String technicianEmail, String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
			if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =approvedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForSelectedDate(
			String technicianEmail, String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
			if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =pendingOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForSelectedDate(
			String technicianEmail, String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
			if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =shippedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForSelectedDate(
			String technicianEmail, String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
			if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =receivedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForSelectedDate(
			String technicianEmail, String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
			if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =receivedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForSelectedDate(
			String technicianEmail, String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
			if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =getRejectedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersForSelectedDate(
			String technicianEmail, String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
			if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
						// convert to date
						Date myDate = cal.getTime();

						String date1 = myFormat.format(myDate);
						String Date2 = myFormat.format(currentDate);
						Date current = new Date();
						Date previous = new Date();
						Date dateData = new Date();

						current = myFormat.parse(date1);
						previous = myFormat.parse(Date2);

			ticketList = pendingOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		System.err.println(aList.size());
		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedForTechnicianOrders(
			String technician) {

		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =shippedOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technician)) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public String[] getOrderNumbers() {
		List<OrderHeader> list = null;
		ArrayList<String> newList = null;
		String array[] = null;
		try {
			list = getAllOrders();
			newList = new ArrayList<String>();

			for (OrderHeader order : list) {
				newList.add("ORD000"+order.getRecordID());
			}

			array = new String[newList.size()];

			for (int i = 0; i < newList.size(); i++) {
				array[i] = newList.get(i);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return array;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersNumber(
			String technicianName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			ticketList = getAllOrders();
			for (OrderHeader order : ticketList) {
				/*if (order.getOrderNum().equalsIgnoreCase(technicianName)) {
					aList.add(order);
				}*/
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public int countNewOrdersForCustomerNewSearch(String technicianEmail) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			

			pendingOrders = pendingOrders();
			for (OrderHeader order : pendingOrders) {
				if (order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					tempCount++;
				}else if(technicianEmail.equalsIgnoreCase("All Technicians")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public int countClosedOrderForCustomerNewSearch(String technicianEmail) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			

			pendingOrders = receivedOrders();
			for (OrderHeader order : pendingOrders) {
				
				if (order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					tempCount++;
				}else if(technicianEmail.equalsIgnoreCase("All Technicians")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public int countApprovedOrdersForCustomerNewSearch(String technicianEmail) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {

			pendingOrders = approvedOrders();
			for (OrderHeader order : pendingOrders) {
				
				if (order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					tempCount++;
				}else if(technicianEmail.equalsIgnoreCase("All Technicians")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public int countShippedOrdersForCustomerNewSearch(String technicianEmail) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			pendingOrders = shippedOrders();
			for (OrderHeader order : pendingOrders) {
				
				if (order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					tempCount++;
				}else if(technicianEmail.equalsIgnoreCase("All Technicians")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return tempCount;
	}

	@Override
	public int countOrdersReceiveForCustomerNewSearch(String technicianEmail) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			

			pendingOrders = shippedOrders();
			for (OrderHeader order : pendingOrders) {
				
				if (order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					tempCount++;
				}else if(technicianEmail.equalsIgnoreCase("All Technicians")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public int countRejectedOrderForCustomerNewSearch(String technicianEmail) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int tempCount = 0;
		List<OrderHeader> pendingOrders = null;
		try {
			
			pendingOrders = getRejectedOrders();
			for (OrderHeader order : pendingOrders) {
				if (order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					tempCount++;
				}else if(technicianEmail.equalsIgnoreCase("All Technicians")){
					tempCount++;
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
       System.out.println(tempCount);
		return tempCount;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForCustomerNewSearch(String technicianEmail) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			ticketList =approvedOrders();
			for (OrderHeader order : ticketList) {
				
				if (order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					aList.add(order);
				}else if(technicianEmail.equalsIgnoreCase("All Technicians")){
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return aList;
	}
	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForCustomerNewSearch(
			String technicianEmail) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			  ticketList =pendingOrders();
			   for (OrderHeader order : ticketList) {
				
				   if (order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
						aList.add(order);
					}else if(technicianEmail.equalsIgnoreCase("All Technicians")){
						aList.add(order);
					}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForCustomerNewSearch(
			String technicianEmail) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			

			ticketList =shippedOrders();
			for (OrderHeader order : ticketList) {
				
				if (order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					aList.add(order);
				}else if(technicianEmail.equalsIgnoreCase("All Technicians")){
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForCustomerNewSearch(
			String technicianEmail) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			
			ticketList =receivedOrders();
			for (OrderHeader order : ticketList) {
				if (order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					aList.add(order);
				}else if(technicianEmail.equalsIgnoreCase("All Technicians")){
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return aList;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForCustomerNewSearch(
			String technicianEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForCustomerNewSearch(
			String technicianEmail) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			
			ticketList =getRejectedOrders();
			for (OrderHeader order : ticketList) {
				
				if (order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					aList.add(order);
				}else if(technicianEmail.equalsIgnoreCase("All Technicians")){
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}
		return aList;
	}

	@Override
	public List<String> getDates() {
		List<String> newDates = new ArrayList<String>();
		newDates.add("Last 24 Hours");
		newDates.add("Last 7 Days");
		newDates.add("Last 14 Days");
		newDates.add("Last 30 Days");
		return newDates;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersForCustomerNewSearch(
			String technician) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			
			ticketList = getAllOrders();
			for (OrderHeader order : ticketList) {
				
				if (order.getEmployee().getEmail().equalsIgnoreCase(technician)) {
					aList.add(order);
				}else if(technician.equalsIgnoreCase("All Technicians")){
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public String[] getOrderNumbers(String technicianEmail) {
		List<OrderHeader> list = null;
		ArrayList<String> newList = null;
		String array[] = null;
		try {
			list = getAllOrders();
			newList = new ArrayList<String>();

			for (OrderHeader order : list) {
				if(order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)){
					newList.add("ORD000"+order.getRecordID());
				}
			}

			array = new String[newList.size()];

			for (int i = 0; i < newList.size(); i++) {
				array[i] = newList.get(i);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return array;
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrders(
			String technicianName) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			// convert to date
			Date myDate = cal.getTime();

			String date1 = myFormat.format(myDate);
			String Date2 = myFormat.format(currentDate);
			Date current = new Date();
			Date previous = new Date();
			Date dateData = new Date();

			current = myFormat.parse(date1);
			previous = myFormat.parse(Date2);

			ticketList =pendingOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianName)) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		return aList;
	}

	@Override
	public List<OrderHeader> getAllLastFourteenDaysOrdersForSelectedDate(
			String technicianEmail, String selectedDate) {
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		// get Calendar instance
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List <OrderHeader> aList = new ArrayList<OrderHeader>();
	    List<OrderHeader>	ticketList =null;
		try {
			// substract 7 days
			// If we give 7 there it will give 8 days back
			// If we give 7 there it will give 8 days back
			if(selectedDate.equalsIgnoreCase("Last 24 Hours")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			}else if(selectedDate.equalsIgnoreCase("Last 7 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);
			}else if(selectedDate.equalsIgnoreCase("Last 14 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 13);
			}else if(selectedDate.equalsIgnoreCase("Last 30 Days")){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 30);
			}
						// convert to date
						Date myDate = cal.getTime();

						String date1 = myFormat.format(myDate);
						String Date2 = myFormat.format(currentDate);
						Date current = new Date();
						Date previous = new Date();
						Date dateData = new Date();

						current = myFormat.parse(date1);
						previous = myFormat.parse(Date2);

			ticketList = getAllOrders();
			for (OrderHeader order : ticketList) {
				String convDate = order.getDateOrdered().substring(0, 10);
				String normalDate = convDate.replace("/", "-");
				dateData = myFormat.parse(normalDate);
				if (current.compareTo(dateData) <= 0 && order.getEmployee().getEmail().equalsIgnoreCase(technicianEmail)) {
					aList.add(order);
				}
			}
		} catch (Exception exception) {
			exception.getMessage();
		}

		System.err.println(aList.size());
		return aList;
	}

}
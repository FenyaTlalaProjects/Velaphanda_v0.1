package com.demo.dao;

import java.util.List;

import com.demo.bean.OrdersBean;
import com.demo.model.DeliveryNoteHeader;
import com.demo.model.OrderHeader;

public interface OrdersDaoInt {
	
	public String makeOrder(OrderHeader orderHeader);
	public String updateOrder(OrdersBean order);
	public List<OrderHeader> getAllOrders();
	public List<OrderHeader> getApprovedOrdersByTechnicianName(String userName);
	public List<OrderHeader> getOpenOrders();
	public OrderHeader getOrder(Long recordID);
	public List<OrderHeader> getAllOrders(String orderedBy);
	public String prepareOrderMaking(OrdersBean order);
	public List<OrderHeader> pendingOrders();
	public String approveOrder(Long recordID);
	public List<OrderHeader>pendingOrders(String approveName);
	public int pendingOrdersCount(String approveName);
	public String approveShipment(Long recordID);
	public List<OrderHeader> shippedOrders();
	public List<OrderHeader> shippedOrders(String technicianEmail);
	public String declineOrder(Long orderNum,String reasonForeclined);
	public int technicianOrdersCount(String technicianName);
	public List<OrderHeader> getAllOrders(String startDate, String endDate);
	public List<OrderHeader> getAllOrders(String startDate, String endDate, String technicianEmail);
	public List<OrderHeader> getAllOrdersByDate();
	public List<OrderHeader> getAllOrdersByDate(String technicianName);
	
	public int countNewOrders(String lastFourteenDays);
	public int countClosedOrder(String lastFourteenDays);
	public int countApprovedOrders(String lastFourteenDays);
	public int countShippedOrders(String lastFourteenDays);
	public int countOrdersReceive(String lastFourteenDays);
	public int countRejectedOrder(String lastFourteenDays);


	public int countNewOrders(String lastFourteenDays, String technicianName,String customerName);
	public int countClosedOrder(String lastFourteenDays ,String technicianName,String customerName);
	public int countApprovedOrders(String lastFourteenDays,String technicianName,String customerName);
	public int countShippedOrders(String lastFourteenDays,String technicianName,String customerName);
	public int countOrdersReceive(String lastFourteenDays,String technicianName,String customerName);
	public int countRejectedOrder(String lastFourteenDays,String technicianName,String customerName);
	
	public int countNewOrders(String lastFourteenDays, String technicianName);
	public int countClosedOrder(String lastFourteenDays ,String technicianName);
	public int countApprovedOrders(String lastFourteenDays,String technicianName);
	public int countShippedOrders(String lastFourteenDays,String technicianName);
	public int countOrdersReceive(String lastFourteenDays,String technicianName);
	public int countRejectedOrder(String lastFourteenDays,String technicianName);
	
	
	public List<OrderHeader> getLastFourteenDaysOrders();
	public List<OrderHeader> getLastFourteenDaysOrdersForSelectedDate(String selectedDate);
	public List<OrderHeader> getLastFourteenDaysOrdersForSelectedDate(String technicianEmail,String selectedDate);
	public List<OrderHeader> getAllLastFourteenDaysOrdersForSelectedDate(String technicianEmail, String selectedDate);
	public List<OrderHeader> getLastFourteenDaysOrdersForCustomer(String customerName);
	public List<OrderHeader> getLastFourteenDaysOrders(String lastFourteenDays,String technicianName,String customerName );
	public List<OrderHeader> getLastFourteenDaysOrders(String technicianName);
	
	public List<OrderHeader> approvedOrders();
	
	public List<OrderHeader> getLastFourteenDaysApprovedOrders();
	public List<OrderHeader> getLastFourteenDaysPendingOrders();
	public List<OrderHeader> getLastFourteenDaysShippedOrders();
	public List<OrderHeader> getLastFourteenDaysShippedForTechnicianOrders(String technician);
	public List<OrderHeader> getLastFourteenDaysClosedOrders();
	public List<OrderHeader> getLastFourteenDaysOrdersToReceive(String technicianName);
	public List<OrderHeader> getLastFourteenDaysClosedOrders(String technicianName );
	
	public List<OrderHeader> getLastFourteenRejectedOrders(String lastFourteenDays);
	public List<OrderHeader> getLastFourteenDaysRejectedOrders();
	public List<OrderHeader>getLastFourteenDaysRejectedOrders(String technicianName);
	List<OrderHeader> getLastFourteenDaysPendingOrders(String technicianName);
	
	public List<OrderHeader> getLastFourteenDaysApprovedOrders(String lastFourteenDays,String technicianName,String customerName );
	public List<OrderHeader> getLastFourteenDaysPendingOrders(String lastFourteenDays,String technicianName,String customerName);
	public List<OrderHeader> getLastFourteenDaysShippedOrders(String lastFourteenDays,String technicianName,String customerName);
	public List<OrderHeader> getLastFourteenDaysClosedOrders(String lastFourteenDays,String technicianName,String customerName);
	public List<OrderHeader> getLastFourteenDaysOrdersToReceive(String lastFourteenDays,String technicianName,String customerName);
	public List<OrderHeader> getLastFourteenDaysRejectedOrders(String lastFourteenDays,String technicianName,String customerName);
	
	
	public int countNewOrdersForCustomer(String customerName);
	public int countClosedOrderForCustomer(String customerName);
	public int countApprovedOrdersForCustomer(String customerName);
	public int countShippedOrdersForCustomer(String customerName);
	public int countOrdersReceiveForCustomer(String customerName);
	public int countRejectedOrderForCustomer(String customerName);
	
	public int countNewOrdersForCustomerNewSearch(String technicianEmail);
	public int countClosedOrderForCustomerNewSearch(String technicianEmail);
	public int countApprovedOrdersForCustomerNewSearch(String technicianEmail);
	public int countShippedOrdersForCustomerNewSearch(String technicianEmail);
	public int countOrdersReceiveForCustomerNewSearch(String technicianEmail);
	public int countRejectedOrderForCustomerNewSearch(String technicianEmail);
	
	public int countNewOrdersForSelectedDate(String lastFourteenDays);
	public int countClosedOrderForSelectedDate(String lastFourteenDays);
	public int countApprovedOrdersForSelectedDate(String lastFourteenDays);
	public int countShippedOrdersForSelectedDate(String lastFourteenDays);
	public int countOrdersReceiveForSelectedDate(String lastFourteenDays);
	public int countRejectedOrderForSelectedDate(String lastFourteenDays);
	
	public int countNewOrdersForSelectedDate(String technician,String lastFourteenDays);
	public int countClosedOrderForSelectedDate(String technician,String lastFourteenDays);
	public int countApprovedOrdersForSelectedDate(String technician,String lastFourteenDays);
	public int countShippedOrdersForSelectedDate(String technician,String lastFourteenDays);
	public int countOrdersReceiveForSelectedDate(String technician,String lastFourteenDays);
	public int countRejectedOrderForSelectedDate(String technician,String lastFourteenDays);
	
	public int countClosedOrderForTechnician(String customerName);
	
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForCustomer(String customerName );
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForCustomer(String customerName);
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForCustomer(String customerName);
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForCustomer(String customerName);
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForCustomer(String customerName);
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForCustomer(String customerName);
	
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForCustomerNewSearch(String technicianEmail );
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForCustomerNewSearch(String technicianEmail);
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForCustomerNewSearch(String technicianEmail);
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForCustomerNewSearch(String technicianEmail);
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForCustomerNewSearch(String technicianEmail);
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForCustomerNewSearch(String technicianEmail);
	
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForSelectedDate(String selectedDate );
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForSelectedDate(String selectedDate);
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForSelectedDate(String selectedDate);
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForSelectedDate(String selectedDate);
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForSelectedDate(String selectedDate);
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForSelectedDate(String selectedDate);
	
	
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForSelectedDate(String technicianEmail,String selectedDate );
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForSelectedDate(String technicianEmail,String selectedDate);
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForSelectedDate(String technicianEmail,String selectedDate);
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForSelectedDate(String technicianEmail,String selectedDate);
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForSelectedDate(String technicianEmail,String selectedDate);
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForSelectedDate(String technicianEmail,String selectedDate);
	
	
	
	public int countNewOrdersForTechnician_Customer(String technician,String customerName);
	public int countClosedOrderForTechnician_Customer(String technician,String customerName);
	public int countApprovedOrdersForTechnician_Customer(String technician,String customerName);
	public int countShippedOrdersForTechnicianCustomer(String technician,String customerName);
	public int countOrdersReceiveForTechnician_Customer(String technician,String customerName);
	public int countRejectedOrderForTechnicianCustomer(String technician,String customerName);
	
	public List<OrderHeader>getLastFourteenDaysOrdersForTechnicianCustomer(String technician,String customerName);
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForTechnicianCustomer(String technician,String customerName);
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForCustomer(String technician,String customerName);
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForTechnicianCustomer(String technician,String customerName);
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForTechnicianCustomer(String technician,String customerName);
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForTechnicianCustomer(String technician,String customerName);
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForTechnicianCustomer(String technician,String customerName);
	String[] getOrderNumbers();
	String[] getOrderNumbers(String technicianEmail);
	List<OrderHeader> getLastFourteenDaysOrdersForCustomerNewSearch(String technician);
	public List<OrderHeader>getLastFourteenDaysOrdersNumber(String technicianName);
	public List<String> getDates();
	void saveDeliveryNoteHeader(DeliveryNoteHeader deliveryNote);
	List<OrderHeader> getOrderListForManager(String customer, String dateRange,
			String technician, Long orderNumber);
	int getOrderCountForManager(String customer, String dateRange,
			String technician, Long orderNumber, String status);
	List<OrderHeader> getOrderListByStatusForManager(String customer,
			String dateRange, String technician, Long orderNumber, String status);
	List<OrderHeader> getOrderListForTechnician(String customer,
			String dateRange, String technician, Long orderNumber);
	int getOrderCountForTechnician(String customer, String dateRange,
			String technician, Long orderNumber, String status);
	List<OrderHeader> getOrderListByStatusForTechnician(String customer,
			String dateRange, String technician, Long orderNumber, String status);
	long convertOrderToLong(String orderNo);
}

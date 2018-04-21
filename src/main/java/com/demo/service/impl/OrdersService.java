package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.bean.OrdersBean;
import com.demo.dao.OrdersDaoInt;
import com.demo.model.OrderHeader;
import com.demo.service.OrdersServiceInt;

@Service("odersService")
@Transactional
public class OrdersService implements OrdersServiceInt{
	
	@Autowired
	private OrdersDaoInt ordersDAO;
	private String retMessage = null;
	private Long retrecordID = 0L;

	@Override
	public String makeOrder(OrderHeader orderHeader) {
		try{
			retMessage = ordersDAO.makeOrder(orderHeader);
		}
		catch(Exception e){}
		return retMessage;
	}

	@Override
	public String updateOrder(OrdersBean order) {
		
		return retMessage= ordersDAO.updateOrder(order);
	}

	@Override
	public List<OrderHeader> getAllOrders() {
		return ordersDAO.getAllOrders();
	}

	@Override
	public List<OrderHeader> getApprovedOrdersByTechnicianName(String userName) {
		
		return ordersDAO.getApprovedOrdersByTechnicianName(userName);
	}

	@Override
	public List<OrderHeader> getOpenOrders() {
		return ordersDAO.getOpenOrders();
	}

	@Override
	public OrderHeader getOrder(Long recordID) {
	
		return ordersDAO.getOrder(recordID);
	}

	@Override
	public List<OrderHeader> getAllOrders(String orderedBy) {
		return ordersDAO.getAllOrders(orderedBy);
	}

	@Override
	public String prepareOrderMaking(OrdersBean order) {
		return ordersDAO.prepareOrderMaking(order);
	}

	@Override
	public List<OrderHeader> pendingOrders() {
		return ordersDAO.pendingOrders();
	}

	@Override
	public String approveOrder(Long recordID) {
		return ordersDAO.approveOrder(recordID);
	}

	@Override
	public List<OrderHeader> pendingOrders(String approveName) {
		return ordersDAO.pendingOrders(approveName);
	}

	@Override
	public String approveShipment(Long recordID) {
		return ordersDAO.approveShipment(recordID);
		
	}

	@Override
	public List<OrderHeader> shippedOrders() {

		return ordersDAO.shippedOrders();
	}

	@Override
	public List<OrderHeader> shippedOrders(String technicianEmail) {
		return ordersDAO.shippedOrders(technicianEmail);
	}

	@Override
	public String declineOrder(Long orderNum,String reasonForeclined) {
		return ordersDAO.declineOrder(orderNum,reasonForeclined);
	}

	@Override
	public int pendingOrdersCount(String approveName) {

		return ordersDAO.pendingOrdersCount(approveName);
	}

	@Override
	public int technicianOrdersCount(String technicianName) {
		
		return ordersDAO.technicianOrdersCount(technicianName);
	}

	@Override
	public List<OrderHeader> getAllOrders(String startDate, String endDate) {
		return ordersDAO.getAllOrders(startDate, endDate);
	}

	@Override
	public List<OrderHeader> getAllOrders(String startDate, String endDate,
			String technicianEmail) {
		return ordersDAO.getAllOrders(startDate, endDate, technicianEmail);
	}

	@Override
	public List<OrderHeader> getAllOrdersByDate() {
		return ordersDAO.getAllOrdersByDate();
	}

	@Override
	public List<OrderHeader> getAllOrdersByDate(String technicianName) {
		return null;
	}

	@Override
	public int countNewOrders(String lastFourteenDays) {
		return ordersDAO.countNewOrders(lastFourteenDays);
	}

	@Override
	public int countApprovedOrders(String lastFourteenDays) {
		return ordersDAO.countApprovedOrders(lastFourteenDays);
	}

	@Override
	public int countShippedOrders(String lastFourteenDays) {
		return ordersDAO.countShippedOrders(lastFourteenDays);
	}
	
	@Override
	public int countOrdersReceive(String lastFourteenDays) {
		return ordersDAO.countOrdersReceive(lastFourteenDays);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrders() {
		return ordersDAO.getLastFourteenDaysOrders();
	}

	@Override
	public List<OrderHeader> approvedOrders() {
		return ordersDAO.approvedOrders();
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysApprovedOrders() {
		return ordersDAO.getLastFourteenDaysApprovedOrders();
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrders() {
		return ordersDAO.getLastFourteenDaysPendingOrders();
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedOrders() {
		return ordersDAO.getLastFourteenDaysShippedOrders();
	}

	@Override
	public int countClosedOrder(String lastFourteenDays) {
		// 
		return ordersDAO.countClosedOrder(lastFourteenDays);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrders() {
		return ordersDAO.getLastFourteenDaysClosedOrders();
	}

	@Override
	public int countNewOrders(String lastFourteenDays, String technicianName) {
		return ordersDAO.countNewOrders(lastFourteenDays, technicianName);
	}

	@Override
	public int countClosedOrder(String lastFourteenDays, String technicianName) {
		return ordersDAO.countClosedOrder(lastFourteenDays, technicianName);
	}

	@Override
	public int countApprovedOrders(String lastFourteenDays,
			String technicianName) {
		return ordersDAO.countApprovedOrders(lastFourteenDays, technicianName);
	}

	@Override
	public int countShippedOrders(String lastFourteenDays, String technicianName) {
		return ordersDAO.countShippedOrders(lastFourteenDays, technicianName);
	}

	@Override
	public int countOrdersReceive(String lastFourteenDays, String technicianName) {
		return ordersDAO.countOrdersReceive(lastFourteenDays, technicianName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrders(String technicianName) {
		
		return ordersDAO.getLastFourteenDaysOrders(technicianName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersToReceive(
			String technicianName) {
		// 
		return ordersDAO.getLastFourteenDaysOrdersToReceive(technicianName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrders(
			String technicianName) {
		return ordersDAO.getLastFourteenDaysClosedOrders(technicianName);
	}

	@Override
	public int countNewOrders(String lastFourteenDays, String technicianName,
			String customerName) {
		return ordersDAO.countNewOrders(lastFourteenDays, technicianName, customerName);
	}

	@Override
	public int countClosedOrder(String lastFourteenDays, String technicianName,
			String customerName) {
		return ordersDAO.countClosedOrder(lastFourteenDays, technicianName, customerName);
	}

	@Override
	public int countApprovedOrders(String lastFourteenDays,
			String technicianName, String customerName) {
		return ordersDAO.countApprovedOrders(lastFourteenDays, technicianName, customerName);
	}

	@Override
	public int countShippedOrders(String lastFourteenDays,
			String technicianName, String customerName) {
		return ordersDAO.countShippedOrders(lastFourteenDays, technicianName, customerName);
	}

	@Override
	public int countOrdersReceive(String lastFourteenDays,
			String technicianName, String customerName) {
		return ordersDAO.countOrdersReceive(lastFourteenDays, technicianName, customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysApprovedOrders(
			String lastFourteenDays, String technicianName, String customerName) {
		return ordersDAO.getLastFourteenDaysApprovedOrders(lastFourteenDays, technicianName, customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrders(
			String lastFourteenDays, String technicianName, String customerName) {
		return ordersDAO.getLastFourteenDaysPendingOrders(lastFourteenDays, technicianName, customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedOrders(
			String lastFourteenDays, String technicianName, String customerName) {
		return ordersDAO.getLastFourteenDaysShippedOrders(lastFourteenDays, technicianName, customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrders(
			String lastFourteenDays, String technicianName, String customerName) {
		return ordersDAO.getLastFourteenDaysClosedOrders(lastFourteenDays, technicianName, customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersToReceive(
			String lastFourteenDays, String technicianName, String customerName) {
		return ordersDAO.getLastFourteenDaysOrdersToReceive(lastFourteenDays, technicianName, customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrders(String lastFourteenDays,
			String technicianName, String customerName) {
		return ordersDAO.getLastFourteenDaysOrders(lastFourteenDays, technicianName, customerName);
	}

	
	@Override
	public int countRejectedOrders(String lastFourteenDays) {
		
		return ordersDAO.countRejectedOrder(lastFourteenDays);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrders() {
		return ordersDAO.getLastFourteenDaysRejectedOrders();
	}

	@Override
	public int countRejectedOrder(String lastFourteenDays, String technicianName) {
		return ordersDAO.countRejectedOrder(lastFourteenDays, technicianName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrders(
			String technicianName) {
		return ordersDAO.getLastFourteenDaysRejectedOrders(technicianName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrders(String lastFourteenDays, 
			String technicianName, String customerName) {
		return ordersDAO.getLastFourteenDaysOrders(lastFourteenDays, technicianName, customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenRejectedOrders(
			String lastFourteenDays) {
		return ordersDAO.getLastFourteenRejectedOrders(lastFourteenDays);
	}

	@Override
	public int countNewOrdersForCustomer(String customerName) {
		return ordersDAO.countNewOrdersForCustomer(customerName);
	}

	@Override
	public int countClosedOrderForCustomer(String customerName) {
		
		return ordersDAO.countClosedOrderForCustomer(customerName);
	}

	@Override
	public int countApprovedOrdersForCustomer(String customerName) {
		return ordersDAO.countApprovedOrdersForCustomer(customerName);
	}

	@Override
	public int countShippedOrdersForCustomer(String customerName) {
		
		return ordersDAO.countShippedOrdersForCustomer(customerName);
	}

	@Override
	public int countOrdersReceiveForCustomer(String customerName) {
		return ordersDAO.countOrdersReceiveForCustomer(customerName);
	}

	@Override
	public int countRejectedOrderForCustomer(String customerName) {
		return ordersDAO.countRejectedOrderForCustomer(customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForCustomer(
			String customerName) {
		return ordersDAO.getLastFourteenDaysApprovedOrdersForCustomer(customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForCustomer(
			String customerName) {
		return ordersDAO.getLastFourteenDaysPendingOrdersForCustomer(customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForCustomer(
			String customerName) {
		return ordersDAO.getLastFourteenDaysShippedOrdersForCustomer(customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForCustomer(
			String customerName) {
		
		return ordersDAO.getLastFourteenDaysClosedOrdersForCustomer(customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForCustomer(
			String customerName) {
		return ordersDAO.getLastFourteenDaysOrdersToReceiveForCustomer(customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForCustomer(
			String customerName) {
		return ordersDAO.getLastFourteenDaysRejectedOrdersForCustomer(customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersForCustomer(String customerName) {
		return ordersDAO.getLastFourteenDaysOrdersForCustomer(customerName);
	}

	@Override
	public int countNewOrdersForTechnician_Customer(String technician,
			String customerName) {
		return ordersDAO.countNewOrdersForTechnician_Customer(technician,customerName);
	}

	@Override
	public int countClosedOrderForTechnician_Customer(String technician,
			String customerName) {
		return ordersDAO.countClosedOrderForTechnician_Customer(technician, customerName);
	}

	@Override
	public int countApprovedOrdersForTechnician_Customer(String technician,
			String customerName) {
		return ordersDAO.countApprovedOrdersForTechnician_Customer(technician, customerName);
	}

	@Override
	public int countShippedOrdersForTechnicianCustomer(String technician,
			String customerName) {
		return ordersDAO.countShippedOrdersForTechnicianCustomer(technician, customerName);
	}

	@Override
	public int countOrdersReceiveForTechnician_Customer(String technician,
			String customerName) {
		return ordersDAO.countOrdersReceiveForTechnician_Customer(technician, customerName);
	}

	@Override
	public int countRejectedOrderForTechnicianCustomer(String technician,
			String customerName) {
		return ordersDAO.countRejectedOrderForTechnicianCustomer(technician, customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForTechnicianCustomer(
			String technician, String customerName) {
		return ordersDAO.getLastFourteenDaysApprovedOrdersForTechnicianCustomer(technician,customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForCustomer(
			String technician, String customerName) {
		return ordersDAO.getLastFourteenDaysPendingOrdersForCustomer(technician, customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForTechnicianCustomer(
			String technician, String customerName) {
		return ordersDAO.getLastFourteenDaysShippedOrdersForTechnicianCustomer(technician, customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForTechnicianCustomer(
			String technician, String customerName) {
		return ordersDAO.getLastFourteenDaysClosedOrdersForTechnicianCustomer(technician, customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForTechnicianCustomer(
			String technician, String customerName) {
		return ordersDAO.getLastFourteenDaysOrdersToReceiveForTechnicianCustomer(technician, customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForTechnicianCustomer(
			String technician, String customerName) {
		return ordersDAO.getLastFourteenDaysRejectedOrdersForTechnicianCustomer(technician,customerName);
	}

	@Override
	public int countClosedOrderForTechnician(String customerName) {
		return ordersDAO.countClosedOrderForTechnician(customerName);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersForTechnicianCustomer(
			String technician,String customerName) {
		return ordersDAO.getLastFourteenDaysOrdersForTechnicianCustomer(technician,customerName);
	}

	@Override
	public int countNewOrdersForSelectedDate(String lastFourteenDays) {
		return ordersDAO.countNewOrdersForSelectedDate(lastFourteenDays);
	}

	@Override
	public int countClosedOrderForSelectedDate(String lastFourteenDays) {
		return ordersDAO.countClosedOrderForSelectedDate(lastFourteenDays);
	}

	@Override
	public int countApprovedOrdersForSelectedDate(String lastFourteenDays) {

		return ordersDAO.countApprovedOrdersForSelectedDate(lastFourteenDays);
	}

	@Override
	public int countShippedOrdersForSelectedDate(String lastFourteenDays) {
		
		return ordersDAO.countShippedOrdersForSelectedDate(lastFourteenDays);
	}

	@Override
	public int countOrdersReceiveForSelectedDate(String lastFourteenDays) {
		return ordersDAO.countOrdersReceiveForSelectedDate(lastFourteenDays);
	}

	@Override
	public int countRejectedOrderForSelectedDate(String lastFourteenDays) {
		return ordersDAO.countRejectedOrderForSelectedDate(lastFourteenDays);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersForSelectedDate(String selectedDate) {
		return ordersDAO.getLastFourteenDaysOrdersForSelectedDate(selectedDate);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForSelectedDate(
			String selectedDate) {
		return ordersDAO.getLastFourteenDaysApprovedOrdersForSelectedDate(selectedDate);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForSelectedDate(
			String selectedDate) {
		return ordersDAO.getLastFourteenDaysPendingOrdersForSelectedDate(selectedDate);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForSelectedDate(
			String selectedDate) {
		return ordersDAO.getLastFourteenDaysShippedOrdersForSelectedDate(selectedDate);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForSelectedDate(
			String selectedDate) {
		return ordersDAO.getLastFourteenDaysClosedOrdersForSelectedDate(selectedDate);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForSelectedDate(
			String selectedDate) {
		return ordersDAO.getLastFourteenDaysOrdersToReceiveForSelectedDate(selectedDate);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForSelectedDate(
			String selectedDate) {
		return ordersDAO.getLastFourteenDaysRejectedOrdersForSelectedDate(selectedDate);
	}

	@Override
	public int countNewOrdersForSelectedDate(String technician,
			String lastFourteenDays) {
		return ordersDAO.countNewOrdersForSelectedDate(technician,lastFourteenDays);
	}

	@Override
	public int countClosedOrderForSelectedDate(String technician,
			String lastFourteenDays) {
		return ordersDAO.countClosedOrderForSelectedDate(technician, lastFourteenDays);
	}

	@Override
	public int countApprovedOrdersForSelectedDate(String technician,
			String lastFourteenDays) {

		return ordersDAO.countApprovedOrdersForSelectedDate(technician, lastFourteenDays);
	}

	@Override
	public int countShippedOrdersForSelectedDate(String technician,
			String lastFourteenDays) {
		return ordersDAO.countShippedOrdersForSelectedDate(technician, lastFourteenDays);
	}

	@Override
	public int countOrdersReceiveForSelectedDate(String technician,
			String lastFourteenDays) {
		return ordersDAO.countOrdersReceiveForSelectedDate(technician, lastFourteenDays);
	}

	@Override
	public int countRejectedOrderForSelectedDate(String technician,
			String lastFourteenDays) {
		return ordersDAO.countRejectedOrderForSelectedDate(technician, lastFourteenDays);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForSelectedDate(
			String technicianEmail, String selectedDate) {
		return ordersDAO.getLastFourteenDaysApprovedOrdersForSelectedDate(technicianEmail, selectedDate);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForSelectedDate(
			String technicianEmail, String selectedDate) {
		return ordersDAO.getLastFourteenDaysPendingOrdersForSelectedDate(technicianEmail, selectedDate);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForSelectedDate(
			String technicianEmail, String selectedDate) {
		return ordersDAO.getLastFourteenDaysShippedOrdersForSelectedDate(technicianEmail, selectedDate);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForSelectedDate(
			String technicianEmail, String selectedDate) {
		return ordersDAO.getLastFourteenDaysClosedOrdersForSelectedDate(technicianEmail, selectedDate);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForSelectedDate(
			String technicianEmail, String selectedDate) {
		return ordersDAO.getLastFourteenDaysOrdersToReceiveForSelectedDate(technicianEmail, selectedDate);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForSelectedDate(
			String technicianEmail, String selectedDate) {
		return ordersDAO.getLastFourteenDaysRejectedOrdersForSelectedDate(technicianEmail, selectedDate);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersForSelectedDate(
			String technicianEmail, String selectedDate) {
		return ordersDAO.getLastFourteenDaysOrdersForSelectedDate(technicianEmail,selectedDate);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedForTechnicianOrders(
			String technician) {
		return ordersDAO.getLastFourteenDaysShippedForTechnicianOrders(technician);
	}

	@Override
	public String[] getOrderNumbers() {
		return ordersDAO.getOrderNumbers();
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersNumber(
			String technicianName) {
		return ordersDAO.getLastFourteenDaysOrdersNumber(technicianName);
	}

	@Override
	public int countNewOrdersForCustomerNewSearch(String technicianEmail) {
		return ordersDAO.countNewOrdersForCustomerNewSearch(technicianEmail);
	}

	@Override
	public int countClosedOrderForCustomerNewSearch(String technicianEmail) {
		return ordersDAO.countClosedOrderForCustomerNewSearch(technicianEmail);
	}

	@Override
	public int countApprovedOrdersForCustomerNewSearch(String technicianEmail) {
		return ordersDAO.countApprovedOrdersForCustomerNewSearch(technicianEmail);
	}

	@Override
	public int countShippedOrdersForCustomerNewSearch(String technicianEmail) {
		return ordersDAO.countShippedOrdersForCustomerNewSearch(technicianEmail);
	}

	@Override
	public int countOrdersReceiveForCustomerNewSearch(String technicianEmail) {
		return ordersDAO.countOrdersReceiveForCustomerNewSearch(technicianEmail);
	}

	@Override
	public int countRejectedOrderForCustomerNewSearch(String technicianEmail) {
		return ordersDAO.countRejectedOrderForCustomerNewSearch(technicianEmail);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysApprovedOrdersForCustomerNewSearch(
			String technicianEmail) {
		
		return ordersDAO.getLastFourteenDaysApprovedOrdersForCustomerNewSearch(technicianEmail);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrdersForCustomerNewSearch(
			String technicianEmail) {
		return ordersDAO.getLastFourteenDaysPendingOrdersForCustomerNewSearch(technicianEmail);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysShippedOrdersForCustomerNewSearch(
			String technicianEmail) {
		return ordersDAO.getLastFourteenDaysShippedOrdersForCustomerNewSearch(technicianEmail);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysClosedOrdersForCustomerNewSearch(
			String technicianEmail) {
		return ordersDAO.getLastFourteenDaysClosedOrdersForCustomerNewSearch(technicianEmail);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersToReceiveForCustomerNewSearch(
			String technicianEmail) {
		return ordersDAO.getLastFourteenDaysOrdersToReceiveForCustomerNewSearch(technicianEmail);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysRejectedOrdersForCustomerNewSearch(
			String technicianEmail) {
		return ordersDAO.getLastFourteenDaysRejectedOrdersForCustomerNewSearch(technicianEmail);
	}

	@Override
	public List<String> getDates() {
		return ordersDAO.getDates();
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysOrdersForCustomerNewSearch(
			String technician) {
		return ordersDAO.getLastFourteenDaysOrdersForCustomerNewSearch(technician);
	}

	@Override
	public String[] getOrderNumbers(String technicianEmail) {
		return ordersDAO.getOrderNumbers(technicianEmail);
	}

	@Override
	public List<OrderHeader> getLastFourteenDaysPendingOrders(
			String technicianName) {
		
		return ordersDAO.getLastFourteenDaysPendingOrders(technicianName);
	}

	@Override
	public List<OrderHeader> getAllLastFourteenDaysOrdersForSelectedDate(
			String technicianEmail, String selectedDate) {
		return ordersDAO.getAllLastFourteenDaysOrdersForSelectedDate(technicianEmail, selectedDate);
	}

	@Override
	public List<OrderHeader> getOrderListForManager(String customer,
			String dateRange, String technician, Long orderNumber) {
		// TODO Auto-generated method stub
		return ordersDAO.getOrderListForManager(customer,
				 dateRange, technician, orderNumber);
	}

	@Override
	public int getOrderCountForManager(String customer, String dateRange,
			String technician, Long orderNumber, String status) {
		// TODO Auto-generated method stub
		return  ordersDAO.getOrderCountForManager(customer,
				 dateRange, technician, orderNumber, status);

	}

	@Override
	public List<OrderHeader> getOrderListByStatusForManager(String customer,
			String dateRange, String technician, Long orderNumber, String status) {
		// TODO Auto-generated method stub
		return  ordersDAO.getOrderListByStatusForManager(customer,
				 dateRange, technician, orderNumber, status);
	}

	@Override
	public List<OrderHeader> getOrderListForTechnician(String customer,
			String dateRange, String technician, Long orderNumber) {
		// TODO Auto-generated method stub
		return  ordersDAO.getOrderListForTechnician(customer,
				 dateRange, technician, orderNumber);
	}

	@Override
	public int getOrderCountForTechnician(String customer, String dateRange,
			String technician, Long orderNumber, String status) {
		return  ordersDAO.getOrderCountForTechnician(customer,
				 dateRange, technician, orderNumber, status);
	}

	@Override
	public List<OrderHeader> getOrderListByStatusForTechnician(String customer,
			String dateRange, String technician, Long orderNumber, String status) {
		return  ordersDAO.getOrderListByStatusForTechnician(customer,
				 dateRange, technician, orderNumber, status);
	}

	@Override
	public long convertOrderToLong(String orderNo) {
		// TODO Auto-generated method stub
		return ordersDAO.convertOrderToLong(orderNo);
	}	
}

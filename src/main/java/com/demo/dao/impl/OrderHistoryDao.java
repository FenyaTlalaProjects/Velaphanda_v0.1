package com.demo.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.OrderHistoryDaoInt;
import com.demo.dao.OrdersDaoInt;
import com.demo.dao.TicketsDaoInt;
import com.demo.model.OrderHeader;
import com.demo.model.OrderHistory;
import com.demo.model.Tickets;


@Repository("orderHistoryDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class OrderHistoryDao implements OrderHistoryDaoInt{

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private OrdersDaoInt daoInt;
	@Autowired
	private TicketsDaoInt ticketsDaoInt;
	
	private OrderHistory orderHistory = null;
	private OrderHeader orderHeader = null;
	
	private DateFormat dateFormat = null;
	private Date date = null;
	private String orderNumber ="ORD000";
	
	
	@Override
	public void insetOrderHistory(OrderHeader order) {
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();
		orderHistory = new OrderHistory();
		try{
			
			orderHistory.setOrderNum(orderNumber+ order.getRecordID());
			orderHistory.setOrderStatus(order.getStatus());
			
			if(order.getStatus().equalsIgnoreCase("Approved") ){
				orderHistory.setStatusDateTime(order.getDateApproved());
			}else if(order.getStatus().equalsIgnoreCase("Pending")){
				orderHistory.setStatusDateTime(order.getDateOrdered());
			}
			else if(order.getStatus().equalsIgnoreCase("Shipped")){
				orderHistory.setStatusDateTime(order.getShippingDate());
			}
			else if(order.getStatus().equalsIgnoreCase("Received")){
				orderHistory.setStatusDateTime(order.getOrderReceivedDateTime());
			}else{
				orderHistory.setStatusDateTime(dateFormat.format(date));
			}
			
			sessionFactory.getCurrentSession().save(orderHistory);
			
		}catch(Exception e){
			e.getMessage();
		}
		
	}
	@Override
	public List<OrderHistory> getAllOrderHistoryByOrderNumber(Long recordID) {
		List<OrderHistory> newList = null;
		try{
			orderHeader = daoInt.getOrder(recordID);
			List<OrderHistory> list = getAllOrderHistoryByOrderNumber();
			 newList = new ArrayList<OrderHistory>();
			for(OrderHistory orderHistory:list){
				if(orderHistory.getOrderNum().equalsIgnoreCase(orderNumber+orderHeader.getRecordID())){
					newList.add(orderHistory);
				}
			}
			
		}catch(Exception e){
			e.getMessage();
		}
		return newList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderHistory> getAllOrderHistoryByOrderNumber() {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				OrderHistory.class);
		return (List<OrderHistory>) criteria.list();
	}
	@Override
	public List<OrderHistory> getAllOrderHistoryTicketNumber(Long ticketNumber) {
		Tickets ticket = ticketsDaoInt.getLoggedTicketsByTicketNumber(ticketNumber);
		
		List<OrderHistory> list = new ArrayList<OrderHistory>();
		List<OrderHistory> listOrders =getAllOrderHistoryByOrderNumber();
		for(OrderHistory order:listOrders){
			if(order.getOrderNum().equalsIgnoreCase(orderNumber+ticket.getOrderHeader().getRecordID())){
				list.add(order);
			}
		}
		return list;
	}

}

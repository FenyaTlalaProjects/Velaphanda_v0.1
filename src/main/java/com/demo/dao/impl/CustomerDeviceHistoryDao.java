package com.demo.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.bean.CustomerDeviceHistoryBean;
import com.demo.bean.HistoryBean;
import com.demo.dao.CustomerDeviceHistoryDaoInt;
import com.demo.dao.EmployeeDaoInt;
import com.demo.dao.HistoryDaoInt;
import com.demo.model.Customer;
import com.demo.model.CustomerDeviceHistory;
import com.demo.model.Employee;
import com.demo.model.History;
import com.demo.model.Leave;
import com.demo.model.TicketHistory;


@Repository("CustomerDeviceHistoryDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class CustomerDeviceHistoryDao implements CustomerDeviceHistoryDaoInt {

	@Autowired
	private SessionFactory sessionFactory;	
	@Autowired
	private HttpSession session;
	@Autowired
	private EmployeeDaoInt employeeDaoInt;
	private CustomerDeviceHistory globalHistory;
	private String retMessage = null;
	CustomerDeviceHistory temp = null;
	Customer customer = null;
	@Override
	public String saveCustomerDeviceHistory (CustomerDeviceHistoryBean history) {
		
		globalHistory = new CustomerDeviceHistory();	
		//Get Current Time Stamp
		Calendar cal = Calendar.getInstance();		
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:s");
		Date currentDate = new Date();
		cal.setTime(new Date());

		currentDate = cal.getTime();
		
		try {
			
			globalHistory.setAction(history.getAction());
			globalHistory.setClassification(history.getClassification());
			globalHistory.setUserEmail(history.getUserEmail());
			globalHistory.setUserName(history.getUserName());
			globalHistory.setObjectId(history.getObjectId());
			globalHistory.setDateTime(myFormat.format(currentDate));
			
			globalHistory.setDescription(history.getDescription());
			sessionFactory.getCurrentSession().save(globalHistory);
			
			} catch (Exception e) {
		retMessage = "History not saved " + e.getMessage() + ".";
		}
		return retMessage;
	}
	
	@Override
	public List<CustomerDeviceHistory> getHistoryByCustomer(String customerName) {
		
		List<CustomerDeviceHistory> newList = null;
		try{
			
			List<CustomerDeviceHistory> list = getAllHistoryByCustomer();
			newList = new ArrayList<CustomerDeviceHistory>();
			for(CustomerDeviceHistory customerHistory:list){
				if(customerHistory.getObjectId().equals(customerName)){
					newList.add(customerHistory);
					System.err.println("Customer History: History By Customer");
				}
			}
			
		}catch(Exception e){
			e.getMessage();
		}
		return newList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDeviceHistory> getAllHistoryByCustomer() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				CustomerDeviceHistory.class);
		return (List<CustomerDeviceHistory>) criteria.list();	
	}
	
	@Override
	public List<CustomerDeviceHistory> getHistoryBySerialNumber(String serialNumber) {
		
		List<CustomerDeviceHistory> newList = null;
		try{
			
			List<CustomerDeviceHistory> list = getAllHistoryBySerialNumber();
			newList = new ArrayList<CustomerDeviceHistory>();
			for(CustomerDeviceHistory deviceHistory:list){
				if(deviceHistory.getObjectId().equals(serialNumber)){
					newList.add(deviceHistory);
					System.err.println("Device History: History By Device");
				}
			}
			
		}catch(Exception e){
			e.getMessage();
		}
		return newList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDeviceHistory> getAllHistoryBySerialNumber() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				CustomerDeviceHistory.class);
		return (List<CustomerDeviceHistory>) criteria.list();	
	}
	
}
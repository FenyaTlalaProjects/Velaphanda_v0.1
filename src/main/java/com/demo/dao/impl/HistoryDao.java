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

import com.demo.bean.HistoryBean;
import com.demo.dao.EmployeeDaoInt;
import com.demo.dao.HistoryDaoInt;
import com.demo.model.Customer;
import com.demo.model.Employee;
import com.demo.model.History;
import com.demo.model.Leave;
import com.demo.model.TicketHistory;


@Repository("HistoryDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class HistoryDao implements HistoryDaoInt {

	@Autowired
	private SessionFactory sessionFactory;	
	@Autowired
	private HttpSession session;
	@Autowired
	private EmployeeDaoInt employeeDaoInt;
	private History globalHistory;
	private String retMessage = null;
	History temp = null;
	Customer customer = null;
	@Override
	public String saveHistory (HistoryBean history) {
		
		globalHistory = new History();	
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
			globalHistory.setQuantity(history.getQuantity());
			globalHistory.setDataField1(history.getDataField1());
			globalHistory.setDataField2(history.getDataField2());
			globalHistory.setDateTime(myFormat.format(currentDate));
			/*//for Head Office
			globalHistory.setHoObjectId(history.getHoObjectId());
			globalHistory.setHoSpareRecievedBy(history.getHoSpareRecievedBy());
			globalHistory.setHoActionSpares(history.getHoActionSpares());
			globalHistory.setHoSupplierName(history.getHoSupplierName());
			globalHistory.setHoSupplierOrderNo(history.getHoSupplierOrderNo());
			globalHistory.setHoQuantityRecieved(history.getHoQuantityRecieved());
			globalHistory.setHoDateSpareRecieved(myFormat.format(currentDate));*/
			
			
			globalHistory.setDescription(history.getDescription());
			sessionFactory.getCurrentSession().save(globalHistory);
			
			} catch (Exception e) {
		retMessage = "History not saved " + e.getMessage() + ".";
		}
		return retMessage;
	}
	
	@Override
	public List<History> getHistoryByCustomer(String customerName) {
		
		List<History> newList = null;
		try{
			
			List<History> list = getAllHistoryByCustomer();
			newList = new ArrayList<History>();
			for(History customerHistory:list){
				if(customerHistory.getObjectId().equals(customerName)){
					newList.add(customerHistory);
				}
			}
			
		}catch(Exception e){
			e.getMessage();
		}
		return newList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getAllHistoryByCustomer() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				History.class);
		return (List<History>) criteria.list();	
	}
	
	@Override
	public List<History> getHistoryBySerialNumber(String serialNumber) {
		
		List<History> newList = null;
		try{
			
			List<History> list = getAllHistoryBySerialNumber();
			newList = new ArrayList<History>();
			for(History deviceHistory:list){
				if(deviceHistory.getObjectId().equals(serialNumber)){
					newList.add(deviceHistory);
				}
			}
			
		}catch(Exception e){
			e.getMessage();
		}
		return newList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getAllHistoryBySerialNumber() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				History.class);
		return (List<History>) criteria.list();	
	}
	
	@Override
	public List<History> getHistoryByPartNumber(String partNumber) {
		
		List<History> newList = null;
		try{
			
			List<History> list = getAllHistoryByPartNumber();
			newList = new ArrayList<History>();
			for(History partNumberHOHistory:list){
				if(partNumberHOHistory.getObjectId().equals(partNumber)){
					newList.add(partNumberHOHistory);
				}
			}
			
		}catch(Exception e){
			e.getMessage();
		}
		return newList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getAllHistoryByPartNumber() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				History.class);
		return (List<History>) criteria.list();	
	}
	
	@Override
	public List<History> getSiteStockHistoryByPartNumber(String partNumber) {
		
		List<History> newList = null;
		try{
			
			List<History> list = getAllSiteStockHistoryByPartNumber();
			newList = new ArrayList<History>();
			for(History siteStockHistory:list){
				if(siteStockHistory.getObjectId().equals(partNumber)){
					newList.add(siteStockHistory);
					System.err.println("Site Stock: History By Part Number for Movement");
				}
			}			
			
		}catch(Exception e){
			e.getMessage();
		}
		return newList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getAllSiteStockHistoryByPartNumber() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				History.class);
		return (List<History>) criteria.list();	
	}
	
	@Override
	public List<History> getBootStockHistoryByPartNumber(String partNumber) {
		
		List<History> newList = null;
		try{
			
			List<History> list = getAllBootStockHistoryByPartNumber();
			newList = new ArrayList<History>();
			for(History siteStockHistory:list){
				if(siteStockHistory.getObjectId().equals(partNumber)){
					newList.add(siteStockHistory);
				}
			}
			
		}catch(Exception e){
			e.getMessage();
		}
		return newList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<History> getAllBootStockHistoryByPartNumber() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				History.class);
		return (List<History>) criteria.list();	
	}

	
}
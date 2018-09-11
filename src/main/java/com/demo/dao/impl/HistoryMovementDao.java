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

import com.demo.bean.HistoryMovementBean;
import com.demo.dao.EmployeeDaoInt;
import com.demo.dao.HistoryMovementDaoInt;
import com.demo.model.Employee;
import com.demo.model.History;
import com.demo.model.HistoryMovement;
import com.demo.model.Leave;
import com.demo.model.TicketHistory;


@Repository("HistoryMovementDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class HistoryMovementDao implements HistoryMovementDaoInt {

	@Autowired
	private SessionFactory sessionFactory;	
	@Autowired
	private HttpSession session;
	@Autowired
	private EmployeeDaoInt employeeDaoInt;
	private HistoryMovement globalHistory;
	private String retMessage = null;
	HistoryMovement temp = null;
	
	@Override
	public String saveHistoryMovement(HistoryMovementBean historyMovement) {
		
		globalHistory = new HistoryMovement();	
		//Get Current Time Stamp
		Calendar cal = Calendar.getInstance();		
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:s");
		Date currentDate = new Date();
		cal.setTime(new Date());

		currentDate = cal.getTime();
		
		try {
			
			globalHistory.setAction(historyMovement.getAction());
			globalHistory.setClassification(historyMovement.getClassification());
			globalHistory.setUserEmail(historyMovement.getUserEmail());
			globalHistory.setUserName(historyMovement.getUserName());
			globalHistory.setObjectId(historyMovement.getObjectId());
			globalHistory.setQuantityMoved(historyMovement.getQuantityMoved());
			globalHistory.setMovedFrom(historyMovement.getMovedFrom());
			globalHistory.setMovedTo(historyMovement.getMovedTo());
			globalHistory.setDateTimeMoved(myFormat.format(currentDate));
			globalHistory.setDescription(historyMovement.getDescription());
			
			sessionFactory.getCurrentSession().save(globalHistory);
			
			} catch (Exception e) {
		retMessage = "History not saved " + e.getMessage() + ".";
		}
		return retMessage;
	}

	@Override
	public List<HistoryMovement> getHistoryMovementByPartNumber(String partNumber) {
		
		List<HistoryMovement> newList = null;
		try{
			
			List<HistoryMovement> list = getAllHistoryMovementByPartNumber();
			newList = new ArrayList<HistoryMovement>();
			for(HistoryMovement partNumberHistoryMovement:list){
				if( partNumberHistoryMovement.getObjectId().equals(partNumber) ){
					newList.add(partNumberHistoryMovement);
				}		
			}
			
		}catch(Exception e){
			e.getMessage();
		}
		return newList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoryMovement> getAllHistoryMovementByPartNumber() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				History.class);
		return (List<HistoryMovement>) criteria.list();	
	}
	
	@Override
	public List<HistoryMovement> getSiteStockHistoryMovementByPartNumber(String partNumber) {
		
		List<HistoryMovement> newList = null;
		try{
			
			List<HistoryMovement> list = getAllSiteStockHistoryMovementByPartNumber();
			newList = new ArrayList<HistoryMovement>();
			for(HistoryMovement siteStockHistory:list){
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
	public List<HistoryMovement> getAllSiteStockHistoryMovementByPartNumber() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HistoryMovement.class);
		return (List<HistoryMovement>) criteria.list();	
	}
	
	@Override
	public List<HistoryMovement> getBootStockHistoryMovementByPartNumber(String partNumber) {
		
		List<HistoryMovement> newList = null;
		try{
			
			List<HistoryMovement> list = getAllBootStockHistoryMovementByPartNumber();
			newList = new ArrayList<HistoryMovement>();
			for(HistoryMovement siteStockHistory:list){
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
	public List<HistoryMovement> getAllBootStockHistoryMovementByPartNumber() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HistoryMovement.class);
		return (List<HistoryMovement>) criteria.list();	
	}


	
}
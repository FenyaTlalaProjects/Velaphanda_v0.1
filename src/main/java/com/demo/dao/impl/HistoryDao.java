package com.demo.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.bean.HistoryBean;
import com.demo.dao.EmployeeDaoInt;
import com.demo.dao.HistoryDaoInt;
import com.demo.model.Employee;
import com.demo.model.History;
import com.demo.model.Leave;


@Repository("HistoryDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class HistoryDao implements HistoryDaoInt {

	@Autowired
	private SessionFactory sessionFactory;
	private Session session2;
	@Autowired
	private HttpSession session;
	@Autowired
	private EmployeeDaoInt employeeDaoInt;
	private List<History> tempHistory = null;
	private List<History> historyList = null;

	private History globalHistory;

	private String retMessage = null;
	private Date currentDate, secondDate = null;
	private SimpleDateFormat myFormat = null;
	private int recordID = 0;
	private Employee emp = null;
	History temp = null;

	@Override
	public String saveHistory (HistoryBean history) {
		Employee employee = (Employee) session.getAttribute("loggedInUser");
		globalHistory = new History();
		String userName = null;
		
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
			globalHistory.setObjectId(history.getObjectId());
			globalHistory.setQuantity(history.getQuantity());
			globalHistory.setDataField1(history.getDataField1());
			globalHistory.setDataField2(history.getDataField2());
			globalHistory.setDateTime(myFormat.format(currentDate));
			globalHistory.setDescription(history.getDescription());
			sessionFactory.getCurrentSession().save(globalHistory);
				retMessage = "Leave successfully submited.";
		

		} catch (Exception e) {
			retMessage = "Leave not submitted " + e.getMessage() + ".";
		}
		return retMessage;
	}
		
	
}
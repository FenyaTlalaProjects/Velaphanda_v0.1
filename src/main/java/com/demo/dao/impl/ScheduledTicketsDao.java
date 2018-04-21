package com.demo.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.EmployeeDaoInt;
import com.demo.dao.ScheduledTickets;
import com.demo.dao.TicketHistoryDaoInt;
import com.demo.model.Employee;
import com.demo.model.Tickets;


@Repository("scheduledTicketsDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class ScheduledTicketsDao implements ScheduledTickets{

	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TicketHistoryDaoInt historyDaoInt;
	@Autowired
	private EmployeeDaoInt employeeDaoInt;
	
	Calendar cal = Calendar.getInstance();
	DateFormat dateFormat = null;
	Date date = null;
	private Date currentDate = null;
	private SimpleDateFormat myFormat = null;
	private List<Tickets> ticketList = null;
	ArrayList<Tickets> aList = null;
	private Tickets ticket = null;
	Employee userName = null;
	private Employee emp = null;
	
	@SuppressWarnings("unchecked")
	public List<Tickets> getAllLoggedTickets() {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Tickets.class);
		return (List<Tickets>)criteria.list(); 
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Scheduled(fixedRate = 600000)
	@Override
	public void calculateSLAHours() {
		long day =0,diff=0;
		try {
			Calendar cal = Calendar.getInstance();
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDate =  dateFormat.format(cal.getTime());
			
			Date systemDate = dateFormat.parse(currentDate);
			List<Tickets> openTickets = getAllOpenTickets();
			
			for (Tickets openTicket : openTickets) {
				
				String loggedTimeTicket = openTicket.getDateTime();
				Date loggedTicketDate = dateFormat.parse(loggedTimeTicket);
				
				diff =systemDate.getTime()- loggedTicketDate.getTime();
	            day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	           
	              long hour = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);

				if(hour >=1 && hour< 4 && openTicket.isOneHourFlag()==false){
					
					openTicket.setComments("System update");
					openTicket.setOneHourFlag(true);
					updateSLA(openTicket);
					
					emp = employeeDaoInt.getEmployeeByEmpNum(openTicket.getEmployee().getEmail());
										
					JavaMail.SendMailToTechniciansForOneHourReminder(openTicket);
					
				} else if (hour>=4 && !openTicket.getStatus().equalsIgnoreCase("SLA Bridged") && openTicket.isFourHourFlag()==false && !openTicket.getStatus().equalsIgnoreCase("Awaiting Spares")) {
					openTicket.setStatus("SLA Bridged");
					openTicket.setFourHourFlag(true);
					openTicket.setComments("System update");
					updateSLA(openTicket);
					historyDaoInt.insertTicketHistory(openTicket);
					
					String []managerEmails = employeeDaoInt.managersEmails();
					
						JavaMail.SendMailToManagersForFourHoursReminder(openTicket,managerEmails);
					
				}else if(hour>=8 && openTicket.getStatus().equalsIgnoreCase("Escalated")){
				// Send emails here
			   }else if(hour>=48 && openTicket.getStatus().equalsIgnoreCase("Awaiting Spares")){
				
				   openTicket.setStatus("SLA Bridged");
			     }
			}
		} catch (Exception e) {
			
			e.getMessage();
		}
	}
	public List<Tickets> getAllOpenTickets() {
	    List<Tickets>aList = new ArrayList<Tickets>();
		try{
			ticketList = getAllLoggedTickets();
			for(Tickets ticket:ticketList){
				if((ticket.getStatus().equalsIgnoreCase("SLA Bridged")||ticket.getStatus().equalsIgnoreCase("Escalated")||ticket.getStatus().equalsIgnoreCase("Awaiting Spares")||ticket.getStatus().equalsIgnoreCase("Taken")||ticket.getStatus().equalsIgnoreCase("Acknowledged")||ticket.getStatus().equalsIgnoreCase("Open")|| ticket.getStatus().equalsIgnoreCase("Re-Open")&& ticket.isOneHourFlag()==false)){
					aList.add(ticket);
				}else if((ticket.getStatus().equalsIgnoreCase("SLA Bridged")||ticket.getStatus().equalsIgnoreCase("Escalated")||ticket.getStatus().equalsIgnoreCase("Awaiting Spares")||ticket.getStatus().equalsIgnoreCase("Taken")||ticket.getStatus().equalsIgnoreCase("Acknowledged")||ticket.getStatus().equalsIgnoreCase("Open")|| ticket.getStatus().equalsIgnoreCase("Re-Open")&& ticket.isOneHourFlag()==true && ticket.isFourHourFlag()==false)){
					aList.add(ticket);
				}
			}
		}catch(Exception exception){
			exception.getMessage();
		}
		
		return aList;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Scheduled(fixedRate = 60000)
	@Override
	public void resolveToClosedTicketUpdate() {
		myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		currentDate = new Date();
		Calendar cal = Calendar.getInstance();
	    String date1 =  myFormat.format(cal.getTime());
	    String date2= null;
	    Date secondDate =null;
	    try{
	    	List<Tickets> ticketList = getResolvedTickets();
	    	currentDate = myFormat.parse(date1);
	    	
	    	for(Tickets ticket:ticketList){
	    		date2 = ticket.getDateResolved();
	    		currentDate= myFormat.parse(date1);
	    		secondDate= myFormat.parse(date2);
	    		long difference = currentDate.getTime()- secondDate.getTime();
	    		// 86400 is equal to 24 hrs
	    		if(difference > 86400){
	    			
	    			ticket.setStatus("Closed");
	    			sessionFactory.getCurrentSession().update(ticket);
	    			historyDaoInt.insertTicketHistory(ticket);
	    			
	    		}
	    		
	    	}
	    	
	    }catch(Exception e){
	    	e.getMessage();
	    }
	}
	private List<Tickets> getResolvedTickets() {
	    aList = new ArrayList<Tickets>();
		try{
			ticketList = getAllLoggedTickets();
			for(Tickets ticket:ticketList){
				if((ticket.getStatus().equalsIgnoreCase("Resolved"))){
					aList.add(ticket);
				}
			}
		}catch(Exception exception){
			exception.getMessage();
		}
		
		return aList;
	}

	@Override
	public void updateSLA(Tickets tickets) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(tickets);
			sessionFactory.getCurrentSession().beginTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}

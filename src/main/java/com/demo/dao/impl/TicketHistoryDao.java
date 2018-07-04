package com.demo.dao.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.bean.DeviceBean;
import com.demo.dao.DeviceDaoInt;
import com.demo.dao.TicketHistoryDaoInt;
import com.demo.model.Device;
import com.demo.model.DeviceContactPerson;
import com.demo.model.Employee;
import com.demo.model.OrderDetails;
import com.demo.model.OrderHeader;
import com.demo.model.OrderHistory;
import com.demo.model.TicketHistory;
import com.demo.model.Tickets;
import com.demo.reports.initializer.DeviceReportBean;
import com.demo.reports.initializer.OrderReportBean;
import com.demo.reports.initializer.TicketReportBean;

@Repository("ticketHistoryDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class TicketHistoryDao implements TicketHistoryDaoInt{

	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private DeviceDaoInt deviceDaoInt;
	private TicketHistory ticketHistory = null;
	private DeviceBean deviceBean = null;
	private DeviceContactPerson deviceContactPerson;
	DateFormat dateFormat = null;
	Date date = null;
	
	
	private String newTicketNum = "VTC000";
	List<TicketHistory> ticketHistoryList = null;
	ArrayList<?> aList = null;
	ArrayList list = null;
	private Device device = null;
	private Tickets ticket = null;
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void insertTicketHistory(Tickets ticket) {
		ticketHistory = new TicketHistory();
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		date = new Date();
		try{
			
			  ticketHistory.setTicketNo(ticket.getRecordID());
			  ticketHistory.setComment(ticket.getComments());
			  ticketHistory.setEscalatedDate(dateFormat.format(date));
			  ticketHistory.setEmployee(ticket.getEmployee());
			  ticketHistory.setEscalatedReason(ticket.getEscalateReason());
			  ticketHistory.setSolution(ticket.getSolution());
			  ticketHistory.setActionTaken(ticket.getActionTaken());
			  ticketHistory.setStatus(ticket.getStatus());
			  
			  ticketHistory.setMonoReading(ticket.getDevice().getMonoReading());
			  ticketHistory.setColourReading(ticket.getDevice().getColourReading());
			  sessionFactory.getCurrentSession().save(ticketHistory);
			  
			  
			  if(ticketHistory.getStatus().equalsIgnoreCase("SLA Bridged")){
				  sessionFactory.getCurrentSession().beginTransaction().commit();
			  }
			
		}catch(Exception e){
			e.getMessage();
		}
	}
	
	
	@Override
	public Tickets getLoggedTicketsByTicketNumber(Long ticketNumber) {

		return (Tickets) sessionFactory.getCurrentSession().get(Tickets.class,
				ticketNumber);
	}

	@Override
	public List<TicketHistory> getHistoryByTicketNumber(Long ticketNumber) {
		
		List<TicketHistory> newList = null;
		try{
			
			List<TicketHistory> list = getAllTicketHistoryByTicketNumber();
			newList = new ArrayList<TicketHistory>();
			for(TicketHistory ticketHistory:list){
				if(ticketHistory.getTicketNo().equals(ticketNumber)){
					newList.add(ticketHistory);
				}
			}
			
		}catch(Exception e){
			e.getMessage();
		}
		return newList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TicketHistory> getAllTicketHistoryByTicketNumber() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				TicketHistory.class);
		return (List<TicketHistory>) criteria.list();	
	}

	@Override
	public List<TicketHistory>  getAllTicketHistoryByTicketNumber(Long recordID) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				TicketHistory.class);
		return (List<TicketHistory>) criteria.list();
	}
	
	
	@Override
	public JRDataSource getDeviceHistoryDataSource(Long recordID) {
		JRDataSource ds = null;
		
		List<TicketReportBean> result = new ArrayList<TicketReportBean>();
		try{
			
			List<TicketHistory> ticketsHistory = getHistoryByTicketNumber(recordID);			
			ticket = getLoggedTicketsByTicketNumber(recordID);
			TicketReportBean ticketBean = new TicketReportBean();
        	
        	ticketBean.setCustomerName(ticket.getDevice().getCustomerDevice().getCustomerName());
			ticketBean.setSerialNumber(ticket.getDevice().getSerialNumber());
			ticketBean.setModelNumber(ticket.getDevice().getModelNumber());
			
			ticketBean.setDeviceContactPersonFirstLastName(ticket.getFirstName()+ " "+ ticket.getLastName());
			ticketBean.setDeviceContactPersonCellphone(ticket.getContactCellNumber());
			ticketBean.setDeviceContactPersonTellphone(ticket.getContactTelephoneNumber());
			ticketBean.setDeviceContactPersonEmail(ticket.getContactEmail());
						
			for(TicketHistory tickHistory:ticketsHistory){            	            	
            	            	
            	ticketBean.setTicketNo("VTC000"+tickHistory.getTicketNo());
            	ticketBean.setDate(tickHistory.getTickets().getDateTime());           	
            	ticketBean.setStatus(tickHistory.getStatus());
            	ticketBean.setActionTaken(tickHistory.getActionTaken());
            	ticketBean.setAssignedTo(tickHistory.getEmployee().getFirstName() +" "+tickHistory.getEmployee().getLastName());
            	ticketBean.setDescription(tickHistory.getDescription());
               	ticketBean.setComment(tickHistory.getComment());
            	ticketBean.setMonoReading(tickHistory.getColourReading());
            	ticketBean.setColourReading(tickHistory.getMonoReading());            			
    		
    			result.add(ticketBean);			
    			ds = new JRBeanCollectionDataSource(result);
			
			}
			
	}catch(Exception e){
		e.getMessage();
	}
	return ds;
	}
}

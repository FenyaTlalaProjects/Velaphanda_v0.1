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

import com.demo.dao.TicketHistoryDaoInt;
import com.demo.model.Device;
import com.demo.model.OrderHistory;
import com.demo.model.TicketHistory;
import com.demo.model.Tickets;
import com.demo.reports.initializer.DeviceReportBean;
import com.demo.reports.initializer.TicketReportBean;

@Repository("ticketHistoryDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class TicketHistoryDao implements TicketHistoryDaoInt{

	
	@Autowired
	private SessionFactory sessionFactory;
	
	private TicketHistory ticketHistory=null;
	
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
	public TicketHistory getLoggedTicketsByTicketNumber(Long ticketNumber) {

		return (TicketHistory) sessionFactory.getCurrentSession().get(TicketHistory.class,
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
	public TicketHistory  getAllTicketHistoryByTicketNumber(Long recordID) {
		return (TicketHistory) sessionFactory.getCurrentSession().get(TicketHistory.class,
				recordID);
	}
	@Override
	public JRDataSource getDeviceHistoryDataSource(Long recordID) {
		JRDataSource ds = null;
		List<TicketReportBean> result = new ArrayList<TicketReportBean>();
					
		try{
													
			ticketHistory =  getAllTicketHistoryByTicketNumber(recordID);	
			TicketReportBean ticketBean = new TicketReportBean();					
				
			//ticketBean.setCustomerName(ticketHistory.getTickets().getDevice().getCustomerDevice().getCustomerName());
			/*ticketBean.setSerialNumber(ticketHistory.getTickets().getDevice().getSerialNumber());
			ticketBean.setModelNumber(ticketHistory.getTickets().getDevice().getModelNumber());
			ticketBean.setModelBrand(ticketHistory.getTickets().getDevice().getModelBrand());
			ticketBean.setStatus(ticketHistory.getStatus());
			ticketBean.setDate(ticketHistory.getTickets().getDateTime());			
			ticketBean.setMonoReading(ticketHistory.getTickets().getDevice().getMonoReading());
			ticketBean.setColourReading(ticketHistory.getTickets().getDevice().getColourReading());		
			ticketBean.setDescription(ticketHistory.getDescription());
			ticketBean.setComment(ticketHistory.getTickets().getComments());
			ticketBean.setActionTaken(ticketHistory.getActionTaken());	
			
			ticketBean.setAssignedTo(ticketHistory.getEmployee().getFirstName() +" "+ticketHistory.getEmployee().getLastName());
			ticketBean.setTechnicianEmail(ticketHistory.getEmployee().getEmail());
			
			ticketBean.setDeviceContactPersonFirstLastName(ticketHistory.getTickets().getFirstName()+ " "+ ticketHistory.getTickets().getLastName());
			ticketBean.setDeviceContactPersonCellphone(ticketHistory.getTickets().getContactCellNumber());
			ticketBean.setDeviceContactPersonTellphone(ticketHistory.getTickets().getContactTelephoneNumber());
			ticketBean.setDeviceContactPersonEmail(ticketHistory.getTickets().getContactEmail());
			
			ticketBean.setTicketNo("VTC000"+ticketHistory.getTicketNo());
			ticketBean.setPriority(ticketHistory.getTickets().getPriority());*/
		
							
			result.add(ticketBean);			
			ds = new JRBeanCollectionDataSource(result);
			
	}catch(Exception e){
		e.getMessage();
	}
	return ds;
	}
}

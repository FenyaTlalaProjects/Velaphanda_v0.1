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

@Repository("ticketHistoryDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class TicketHistoryDao implements TicketHistoryDaoInt{

	
	@Autowired
	private SessionFactory sessionFactory;
	Device device = null;
	private TicketHistory ticketHistory=null;
	
	DateFormat dateFormat = null;
	Date date = null;
	
	
	private String newTicketNum = "VTC000";
	List<TicketHistory> ticketHistoryList = null;
	ArrayList<?> aList = null;
	ArrayList list = null;
	
	
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

	@Override
	public List<TicketHistory> getAllTicketHistoryByTicketNumber() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				TicketHistory.class);
		return (List<TicketHistory>) criteria.list();	
	}
	
	@Override
	public JRDataSource getDeviceHistoryDataSource(Long recordID) {
		JRDataSource ds = null;
		List<DeviceReportBean> result = new ArrayList<DeviceReportBean>();
		try{
			//ticket = (Tickets) getHistoryByTicketNumber(recordID);
			DeviceReportBean deviceBean = new DeviceReportBean();
					
			/*	
			deviceBean.setCustomerName(ticket.getDevice().getCustomerDevice().getCustomerName());
			deviceBean.setSerialNumber(ticket.getDevice().getSerialNumber());
			deviceBean.setModelNumber(ticket.getDevice().getModelNumber());
			deviceBean.setModelBrand(ticket.getDevice().getModelBrand());
			deviceBean.setCustomerName(ticket.getStatus());
			deviceBean.setDate(ticket.getDateTime());			
			deviceBean.setMonoReading(ticket.getDevice().getMonoReading());
			deviceBean.setColourReading(ticket.getDevice().getColourReading());		
			deviceBean.setFirstName(ticket.getFirstName());
			deviceBean.setLastName(ticket.getLastName());
			deviceBean.setDescription(ticket.getDescription());
			deviceBean.setComment(ticket.getComments());
			deviceBean.setActionTaken(ticket.getActionTaken());			
			
			deviceBean.setContactPersonEmail(device.getContactPerson().getEmail());
			deviceBean.setContactPersonFirstName(device.getContactPerson().getFirstName());
			deviceBean.setContactPersonLastName(device.getContactPerson().getLastName());
			deviceBean.setContactPersonCellphone(device.getContactPerson().getCellphone());
			deviceBean.setContactPersonTellphone(device.getContactPerson().getTelephone());*/
			result.add(deviceBean);
			ds = new JRBeanCollectionDataSource(result);
			
	}catch(Exception e){
		e.getMessage();
	}
	return ds;
	}
}

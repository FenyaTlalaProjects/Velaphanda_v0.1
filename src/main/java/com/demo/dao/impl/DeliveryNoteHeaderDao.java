package com.demo.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.DeliveryNoteHeaderDaoInt;
import com.demo.dao.VelaphandaProfileDaoInt;
import com.demo.model.DeliveryNoteHeader;
import com.demo.model.OrderHeader;
import com.demo.model.VelaphandaProfile;


@Repository("deliveryNoteHeaderDao")
@Transactional(propagation = Propagation.REQUIRED)
public class DeliveryNoteHeaderDao implements DeliveryNoteHeaderDaoInt{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private VelaphandaProfileDaoInt companyProfileInt;
	
	private String orderNumber = "ORD000";
	
	@Override
	public DeliveryNoteHeader getDeliveryNoteHeader(Integer recordID) {
		// Get delivery note by recordID
		return (DeliveryNoteHeader) sessionFactory.getCurrentSession().get(DeliveryNoteHeader.class, recordID);
	}

	@Override
	public void saveDeliveryNoteHeader(OrderHeader orderHeader) {
		// Save delivery note info into a table delivery_Header when order is created
		try{
			DeliveryNoteHeader deliveryNoteHeader = new DeliveryNoteHeader();
			
			VelaphandaProfile companyProfile =companyProfileInt.getVelaphandaAddress("Velaphanda Trading & Projects");
			if(companyProfile!= null){
				// Set company info
				//deliveryNoteHeader.setCompanyCity(companyProfile.get);
				deliveryNoteHeader.setCompanyEmail(companyProfile.getEmailAdress());
				deliveryNoteHeader.setCompanyFax(companyProfile.getFaxNumber());
				deliveryNoteHeader.setCompanyName(companyProfile.getCompanyName());
				deliveryNoteHeader.setCompanyProvince(companyProfile.getCompanyProvince());
				deliveryNoteHeader.setCompanyRegistration(companyProfile.getCompanyRegistration());
				deliveryNoteHeader.setCompanyStreet(companyProfile.getStreetNumber()+" "+companyProfile.getStreetName());
				deliveryNoteHeader.setCompanyTelephone(companyProfile.getTelephoneNumber());
			}
				
				//Set customer info if it's site order
				if(orderHeader.getCustomer()!=null){
					deliveryNoteHeader.setCustomerName(orderHeader.getCustomer().getCustomerName());
					deliveryNoteHeader.setCustomerAreaCode(orderHeader.getCustomer().getZipcode());
					deliveryNoteHeader.setCustomerCity(orderHeader.getCustomer().getCity_town());
					deliveryNoteHeader.setCustomerProvince(orderHeader.getCustomer().getProvince());
					deliveryNoteHeader.setCustomerStreet(orderHeader.getCustomer().getStreetNumber()+" "+ orderHeader.getCustomer().getCustomerName());
				}
				// Set delivery info
				deliveryNoteHeader.setDateOrdered(orderHeader.getDateOrdered());
				deliveryNoteHeader.setOrderNum(orderNumber+orderHeader.getRecordID());
				//deliveryNoteHeader.setRecordID(orderHeader.getRecordID());
				// Set technician info
				deliveryNoteHeader.setContactEmail(orderHeader.getEmployee().getEmail());
				deliveryNoteHeader.setContactNumber(orderHeader.getEmployee().getCellNumber());
				deliveryNoteHeader.setContactPerson(orderHeader.getEmployee().getFirstName()+" "+orderHeader.getEmployee().getLastName());
				
				sessionFactory.getCurrentSession().save(deliveryNoteHeader);
				
		
		}catch(Exception e){
			
		}
		
	}

}

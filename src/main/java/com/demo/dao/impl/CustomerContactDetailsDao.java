package com.demo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.bean.CustomerBean;
import com.demo.dao.CustomerContactDetailsDaoInt;
import com.demo.model.Customer;
import com.demo.model.CustomerContactDetails;
import com.demo.reports.initializer.CustomerReportBean;

@Repository("customerContactDetailsDao")
@Transactional(propagation=Propagation.REQUIRED)
public class CustomerContactDetailsDao implements CustomerContactDetailsDaoInt{

	@Autowired
	private SessionFactory sessionFactory;
	
	private List<CustomerContactDetails> tempContacts = null;
	
	@Override
	public void saveContactDetails(List<CustomerContactDetails> contacts) {
		try{
			for(CustomerContactDetails contactDetails:contacts){
				
			  			
				 //Assign Contact Type for Contact 1 and Contact 2
				 if (contacts.indexOf(contactDetails) == 0)
				 {
					 contactDetails.setContactType("Primary");
					 // ContactKey = Customer Name + Contact Type
					 contactDetails.setContactKey(contactDetails.getCustomerContactDetails().getCustomerName().trim() + " " + contactDetails.getContactType().trim());
				 }
				 else if((contacts.indexOf(contactDetails) == 1))
				 {
					 contactDetails.setContactType("Seconday");
					 // ContactKey = Customer Name + Contact Type
					 contactDetails.setContactKey(contactDetails.getCustomerContactDetails().getCustomerName().trim() + " " + contactDetails.getContactType().trim());
				 }
				
				sessionFactory.getCurrentSession().saveOrUpdate(contactDetails);
			}
		}catch(Exception e){
			
		}
		
	}

	@Override
	public CustomerBean contactDetails(String customerName) {
		CustomerBean returnCustomerContact = new CustomerBean();
        try{
			
			tempContacts = contacts();
			String currentContactType; 
			for(CustomerContactDetails temp:tempContacts){
				
				if(temp.getCustomerContactDetails().getCustomerName().equalsIgnoreCase(customerName)== true)
				{
				    currentContactType = temp.getContactType();
				if (currentContactType.equals("Primary") == true)
				   {
					//Retrieve Primary Contact from Database
				  if (temp.getContactKey().equals(temp.getCustomerContactDetails().getCustomerName() + " " + "Primary") == true)
					{	  
						returnCustomerContact.setFirstName(temp.getFirstName());
						returnCustomerContact.setLastName(temp.getLastName());
						returnCustomerContact.setContactEmail(temp.getContactEmail());
						returnCustomerContact.setContactTelephoneNumber(temp.getContactTelephoneNumber());
						returnCustomerContact.setContactCellNumber(temp.getContactCellNumber());
						returnCustomerContact.setContactType(temp.getContactType());

					}
				   }
				   //Retrieve Alternate Contact from Database
				  else if (currentContactType.equals("Primary") == false)
				{
				    if(temp.getContactKey().equals(temp.getCustomerContactDetails().getCustomerName() + " " + "Seconday") == true)
						returnCustomerContact.setFirstName1(temp.getFirstName());
						returnCustomerContact.setLastName1(temp.getLastName());
						returnCustomerContact.setContactEmail1(temp.getContactEmail());
						returnCustomerContact.setContactTelephoneNumber1(temp.getContactTelephoneNumber());
						returnCustomerContact.setContactCellNumber1(temp.getContactCellNumber());
						returnCustomerContact.setContactType(temp.getContactType());
					}
			   }
			}
					
        }
		catch(Exception e){
			
		}
		return returnCustomerContact;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerContactDetails> contacts(String customerName) {
		try{
			
			tempContacts = contacts();
			for(CustomerContactDetails temp:tempContacts){
				if(temp.getCustomerContactDetails().getCustomerName().equalsIgnoreCase(customerName));
				
			}
		}
		catch(Exception e){
			
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerContactDetails> contacts() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CustomerContactDetails.class);
		return (List<CustomerContactDetails>)criteria.list(); 
	}

	@Override
	public CustomerContactDetails getContactPerson(String customerName) {
		CustomerContactDetails contactDetails= null;
		
		try{
			   List<CustomerContactDetails> list = contacts();
			   for(CustomerContactDetails temp:list){
				   if(temp.getCustomerContactDetails().getCustomerName().equalsIgnoreCase(customerName)){
					   contactDetails = temp;
					   break;
				   }
			   }
		}catch(Exception e){
			
		}

		return contactDetails;
	}

	@Override
	public JRDataSource getCustomerContactDetailsDataSource(String customerName) {
		JRDataSource ds = null;
		List<CustomerReportBean> result = new ArrayList<CustomerReportBean>();
        try{        	
        				
			tempContacts = contacts();
			String currentContactType;
			for(CustomerContactDetails temp:tempContacts ){
				CustomerReportBean returnCustomerContact = new CustomerReportBean();
				if(temp.getCustomerContactDetails().getCustomerName().equalsIgnoreCase(customerName)== true)
				{
				    currentContactType = temp.getContactType();
				if (currentContactType.equals("Primary") == true)
				   {
					//Retrieve Primary Contact from Database
				  if (temp.getContactKey().equals(temp.getCustomerContactDetails().getCustomerName() + " " + "Primary") == true)
					{	  

						returnCustomerContact.setFirstName(temp.getFirstName());
						returnCustomerContact.setLastName(temp.getLastName());
						returnCustomerContact.setContactEmail(temp.getContactEmail());
						returnCustomerContact.setContactTelephoneNumber(temp.getContactTelephoneNumber());
						returnCustomerContact.setContactCellNumber(temp.getContactCellNumber());
						
						returnCustomerContact.setCustomerName(temp.getCustomerContactDetails().getCustomerName());
						returnCustomerContact.setStreetName(temp.getCustomerContactDetails().getStreetName());
						returnCustomerContact.setStreetNumber(temp.getCustomerContactDetails().getStreetNumber());
						returnCustomerContact.setCity_town(temp.getCustomerContactDetails().getCity_town());
						returnCustomerContact.setZipcode(temp.getCustomerContactDetails().getZipcode());
						returnCustomerContact.setProvince(temp.getCustomerContactDetails().getProvince());
						
						result.add(returnCustomerContact);
						ds = new JRBeanCollectionDataSource(result);
					}
				   }
				   //Retrieve Alternate Contact from Database
				  else if (currentContactType.equals("Primary") == false)
				{
				    if(temp.getContactKey().equals(temp.getCustomerContactDetails().getCustomerName() + " " + "Seconday") == true)
						returnCustomerContact.setFirstName1(temp.getFirstName());
						returnCustomerContact.setLastName1(temp.getLastName());
						returnCustomerContact.setContactEmail1(temp.getContactEmail());
						returnCustomerContact.setContactTelephoneNumber1(temp.getContactTelephoneNumber());
						returnCustomerContact.setContactCellNumber1(temp.getContactCellNumber());	
						
						returnCustomerContact.setCustomerName(temp.getCustomerContactDetails().getCustomerName());
						returnCustomerContact.setStreetName(temp.getCustomerContactDetails().getStreetName());
						returnCustomerContact.setStreetNumber(temp.getCustomerContactDetails().getStreetNumber());
						returnCustomerContact.setCity_town(temp.getCustomerContactDetails().getCity_town());
						returnCustomerContact.setZipcode(temp.getCustomerContactDetails().getZipcode());
						returnCustomerContact.setProvince(temp.getCustomerContactDetails().getProvince());
						result.add(returnCustomerContact);
						ds = new JRBeanCollectionDataSource(result);
					}
			   }
			}
		}catch(Exception e){
			e.getMessage();
		}
		return ds;
		}
}

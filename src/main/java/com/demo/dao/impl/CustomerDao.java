package com.demo.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.bean.CustomerBean;
import com.demo.dao.CustomerDaoInt;
import com.demo.dao.CustomerContactDetailsDaoInt;
import com.demo.model.Customer;
import com.demo.model.CustomerContactDetails;
import com.demo.model.Employee;
import com.demo.reports.initializer.CustomerReportBean;
import com.demo.service.CustomerContactDetailsServiceInt;
import com.demo.service.CustomerServiceInt;
import com.demo.service.DeviceServiceInt;


@Repository("clientDAO")
@Transactional(propagation = Propagation.REQUIRED)



public class CustomerDao implements CustomerDaoInt {
	@Autowired
	private HttpSession session;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private CustomerServiceInt customerServiceInt;
	@Autowired
	private DeviceServiceInt deviceServiceInt;
	@Autowired
	private CustomerContactDetailsServiceInt contactDetailsServiceInt;
	@Autowired
	private CustomerContactDetailsDaoInt customerContactDetailsDaoIntDaoInt;

	private String retMessage = null;
	List<Customer> clientList = null;
	Customer customer = null;
	Employee userName, emp = null;

	@Override
	public Customer getClientByClientName(String clientName) {
		return (Customer) sessionFactory.getCurrentSession().get(
				Customer.class, clientName);
	}
	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date now = new Date();
	String dateTimeClientAdded = sdfDate.format(now);
	String dateTimeCleintUpdated = sdfDate.format(now);
	@Override
	public String saveCustomer(CustomerBean customerBean) {

		CustomerContactDetails contactDetails, contactDetails1 = null;
		List<CustomerContactDetails> list = null;
		Customer tempCustomer = new Customer();
		try {

			customer = getClientByClientName(customerBean.getCustomerName());
			if (customer == null) {
				
				emp = (Employee) session.getAttribute("loggedInUser");
				// Customer Object
				
				//Customer.builder().isActive(true).city_town(customerBean.getCity_town());
				
				tempCustomer.setActive(true);
				tempCustomer.setCity_town(customerBean.getCity_town());
				tempCustomer.setCustomerName(customerBean.getCustomerName());
				tempCustomer.setEmail(customerBean.getEmail());
				tempCustomer.setFaxNumber(customerBean.getFaxNumber());
				tempCustomer.setProvince(customerBean.getProvince());
				tempCustomer.setStreetName(customerBean.getStreetName());
				tempCustomer.setStreetNumber(customerBean.getStreetNumber());
				tempCustomer.setContactEmail(customerBean.getContactEmail());				
				tempCustomer.setTelephoneNumber(customerBean.getTelephoneNumber());				
				tempCustomer.setZipcode(customerBean.getZipcode());
				
				tempCustomer.setDateTimeClientAdded(dateTimeClientAdded);
				tempCustomer.setClientAddedBy(emp.getFirstName()+" "+emp.getLastName());				
				tempCustomer.setClientUpdatedBy(customerBean.getClientUpdatedBy());
				tempCustomer.setTimeClientUpdated(customerBean.getTimeClientUpdated());
				

				list = new ArrayList<CustomerContactDetails>();

				// Required contact person object
				contactDetails = new CustomerContactDetails();
				contactDetails.setContactCellNumber(customerBean
						.getContactCellNumber());
				contactDetails.setContactTelephoneNumber(customerBean
						.getContactTelephoneNumber());
				contactDetails.setContactEmail(customerBean.getContactEmail());
				contactDetails.setFirstName(customerBean.getFirstName());
				contactDetails.setLastName(customerBean.getLastName());
				contactDetails.setCustomerContactDetails(tempCustomer);
				list.add(contactDetails);

				// Optional contact person object
				if (customerBean.getFirstName1() != null
						&& customerBean.getFirstName1().length() > 0) {
					contactDetails1 = new CustomerContactDetails();
					contactDetails1.setContactCellNumber(customerBean
							.getContactCellNumber1());
					contactDetails1.setContactEmail(customerBean
							.getContactEmail1());
					contactDetails1.setFirstName(customerBean.getFirstName1());
					contactDetails1.setLastName(customerBean.getLastName1());
					contactDetails1.setContactTelephoneNumber(customerBean
							.getContactTelephoneNumber1());
					contactDetails1.setCustomerContactDetails(tempCustomer);
					list.add(contactDetails1);
				}
				sessionFactory.getCurrentSession().save(tempCustomer);
				customerContactDetailsDaoIntDaoInt.saveContactDetails(list);
				retMessage = "Customer " + tempCustomer.getCustomerName() + " "
						+ "successfully added.";
			} else {
				retMessage = "Customer " + customer.getCustomerName() + " "
						+ "already exist. Please add new customer with a different name.";
			}
		} catch (Exception e) {
			retMessage = "Customer " + customer.getCustomerName()
					+ " not added\n" + e.getMessage()+".";
		}

		return retMessage;
	}

	@Override
	public String updateCustomer(CustomerBean customerBean) {

		CustomerContactDetails contactDetails, contactDetails1 = null;
		List<CustomerContactDetails> list = null;
		Customer tempCustomer = new Customer();
		
		emp = (Employee) session.getAttribute("loggedInUser");
		
		try {
			// Customer Object
			tempCustomer.setActive(true);
			tempCustomer.setCity_town(customerBean.getCity_town());
			tempCustomer.setCustomerName(customerBean.getCustomerName());
			tempCustomer.setEmail(customerBean.getEmail());
			tempCustomer.setFaxNumber(customerBean.getFaxNumber());
			tempCustomer.setProvince(customerBean.getProvince());
			tempCustomer.setStreetName(customerBean.getStreetName());
			tempCustomer.setStreetNumber(customerBean.getStreetNumber());
			tempCustomer.setTelephoneNumber(customerBean.getTelephoneNumber());
			tempCustomer.setContactEmail(customerBean.getContactEmail());			
			tempCustomer.setZipcode(customerBean.getZipcode());
			
			tempCustomer.setClientAddedBy(customerBean.getClientAddedBy());
			tempCustomer.setDateTimeClientAdded(customerBean.getDateTimeClientAdded());			
			tempCustomer.setClientUpdatedBy(emp.getFirstName()+" "+emp.getLastName());
			tempCustomer.setTimeClientUpdated(dateTimeCleintUpdated);

			list = new ArrayList<CustomerContactDetails>();

			// Required contact person object
			contactDetails = new CustomerContactDetails();
			contactDetails.setContactCellNumber(customerBean
					.getContactCellNumber());
			contactDetails.setContactTelephoneNumber(customerBean
					.getContactTelephoneNumber());
			contactDetails.setContactEmail(customerBean.getContactEmail());
			contactDetails.setFirstName(customerBean.getFirstName());
			contactDetails.setLastName(customerBean.getLastName());
			contactDetails.setCustomerContactDetails(tempCustomer);
			list.add(contactDetails);

			// Optional contact person object
			if (customerBean.getFirstName1() != null
					&& customerBean.getFirstName1().length() > 0) {
				contactDetails1 = new CustomerContactDetails();
				contactDetails1.setContactCellNumber(customerBean
						.getContactCellNumber1());
				contactDetails1
						.setContactEmail(customerBean.getContactEmail1());
				contactDetails1.setFirstName(customerBean.getFirstName1());
				contactDetails1.setLastName(customerBean.getLastName1());
				contactDetails1.setContactTelephoneNumber(customerBean
						.getContactTelephoneNumber1());
				contactDetails1.setCustomerContactDetails(tempCustomer);
				list.add(contactDetails1);
			}

			sessionFactory.getCurrentSession().update(tempCustomer);
			retMessage = "Customer " + tempCustomer.getCustomerName()
					+ " successfully updated.";

			customerContactDetailsDaoIntDaoInt.saveContactDetails(list);
		} catch (Exception e) {
			retMessage = "Customer " + customer.getCustomerName()
					+ " not updated\n" + e.getMessage()+".";
		}
		return retMessage;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getClientList() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Customer.class);
		return (List<Customer>) criteria.list();
	}

	@Override
	public CustomerBean contactDetails(String customerName) {
		CustomerBean returnCustomerContact = new CustomerBean();
		try {
			Customer customer = getClientByClientName(customerName);
			returnCustomerContact.setCustomerName(customer.getCustomerName());
			returnCustomerContact.setCity_town(customer.getCity_town());
			returnCustomerContact.setEmail(customer.getEmail());
			returnCustomerContact.setFaxNumber(customer.getFaxNumber());
			returnCustomerContact.setProvince(customer.getProvince());
			returnCustomerContact.setStreetName(customer.getStreetName());
			returnCustomerContact.setStreetNumber(customer.getStreetNumber());
			returnCustomerContact.setTelephoneNumber(customer.getTelephoneNumber());
			returnCustomerContact.setZipcode(customer.getZipcode());
			//history for client
			returnCustomerContact.setClientAddedBy(customer.getClientAddedBy());
			returnCustomerContact.setDateTimeClientAdded(customer.getDateTimeClientAdded());			
			returnCustomerContact.setClientUpdatedBy(customer.getClientUpdatedBy());
			returnCustomerContact.setTimeClientUpdated(customer.getTimeClientUpdated());
			
		} catch (Exception ex) {

		}
		return returnCustomerContact;
	}

	@Override
	public List<Customer> getClientList(String customerName) {
		try{
			List<Customer> customerList = getClientList();
			clientList = new ArrayList<Customer>();
			for(Customer cust:customerList){
				if(!cust.getCustomerName().equalsIgnoreCase(customerName)){
					clientList.add(cust);
				}
			}
			
		}catch(Exception e){
			
		}
		return clientList;
	}

	@Override
	public JRDataSource getCustomerListDataSource() {
		JRDataSource ds = null;
		List<CustomerReportBean> result = new ArrayList<CustomerReportBean>();
		try{
			clientList = getClientList();
			for(Customer cust:clientList){
				CustomerReportBean custBean = new CustomerReportBean();
				
				custBean.setCustomerName(cust.getCustomerName());
				custBean.setContactPersonEmail(cust.getContactEmail());
				custBean.setDeviceContactPersonTellphone(cust.getTelephoneNumber());
				custBean.setDeviceContactPersonCellphone(cust.getContactEmail());
				result.add(custBean);
				ds = new JRBeanCollectionDataSource(result);
			}
		}catch(Exception e){
			e.getMessage();
		}
		return ds;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerContactDetails> contacts() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CustomerContactDetails.class);
		return (List<CustomerContactDetails>)criteria.list(); 
	}

	@Override
	public JRDataSource getCustomerDetailsDataSource(String customerName) {
		JRDataSource ds = null;
		List<CustomerReportBean> result = new ArrayList<CustomerReportBean>();
		try{
				customer = getClientByClientName(customerName);			
			
				CustomerReportBean custBean = new CustomerReportBean();				
				custBean.setCustomerName(customer.getCustomerName());
				custBean.setTelephoneNumber(customer.getTelephoneNumber());
				custBean.setFaxNumber(customer.getFaxNumber());
				custBean.setStreetName(customer.getStreetName());
				custBean.setStreetNumber(customer.getStreetNumber());
				custBean.setCity_town(customer.getCity_town());
				custBean.setProvince(customer.getProvince());
				custBean.setZipcode(customer.getZipcode());
				result.add(custBean);
				
				//ds = new JRBeanCollectionDataSource(result);
				
			
		}catch(Exception e){
			e.getMessage();
		}
		return ds;
	}
	
}

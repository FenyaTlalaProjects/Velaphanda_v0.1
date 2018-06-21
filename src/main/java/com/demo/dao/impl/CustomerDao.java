package com.demo.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
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
import com.demo.model.OrderDetails;
import com.demo.model.OrderHeader;
import com.demo.reports.initializer.CustomerReportBean;
import com.demo.reports.initializer.OrderReportBean;


@Repository("clientDAO")
@Transactional(propagation = Propagation.REQUIRED)



public class CustomerDao implements CustomerDaoInt {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CustomerContactDetailsDaoInt customerContactDetailsDaoIntDaoInt;

	private String retMessage = null;
	List<Customer> clientList = null;
	Customer customer = null;

	@Override
	public Customer getClientByClientName(String clientName) {
		return (Customer) sessionFactory.getCurrentSession().get(
				Customer.class, clientName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getClientList(Integer offset, Integer maxResults) {

		return sessionFactory.openSession().createCriteria(Customer.class)
				.setFirstResult(offset != null ? offset : 0)
				.setMaxResults(maxResults != null ? maxResults : 10).list();
	}
	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date now = new Date();
	String timeClientRegistered = sdfDate.format(now);
	@Override
	public String saveCustomer(CustomerBean customerBean) {

		CustomerContactDetails contactDetails, contactDetails1 = null;
		List<CustomerContactDetails> list = null;
		Customer tempCustomer = new Customer();
		try {

			customer = getClientByClientName(customerBean.getCustomerName());
			if (customer == null) {

				// Customer Object
				tempCustomer.setActive(true);
				tempCustomer.setCity_town(customerBean.getCity_town());
				tempCustomer.setCustomerName(customerBean.getCustomerName());
				tempCustomer.setEmail(customerBean.getEmail());
				tempCustomer.setFaxNumber(customerBean.getFaxNumber());
				tempCustomer.setProvince(customerBean.getProvince());
				tempCustomer.setStreetName(customerBean.getStreetName());
				tempCustomer.setStreetNumber(customerBean.getStreetNumber());
				tempCustomer.setContactEmail(customerBean.getContactEmail());
				tempCustomer.setDateTime(timeClientRegistered);
				tempCustomer.setTelephoneNumber(customerBean
						.getTelephoneNumber());
				tempCustomer.setZipcode(customerBean.getZipcode());

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
			tempCustomer.setDateTime(timeClientRegistered);
			tempCustomer.setZipcode(customerBean.getZipcode());

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

	@Override
	public Integer count() {
		return (Integer) sessionFactory.getCurrentSession()
				.createCriteria(Customer.class)
				.setProjection(Projections.rowCount()).uniqueResult();

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
			returnCustomerContact.setTelephoneNumber(customer
					.getTelephoneNumber());
			returnCustomerContact.setZipcode(customer.getZipcode());
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
				custBean.setContactPersonEmail(cust.getEmail());
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

	@Override
	public JRDataSource getCustomerDetailsDataSource(String customerName) {
		JRDataSource ds = null;
		List<CustomerReportBean> result = new ArrayList<CustomerReportBean>();
		
		try{
			customer = getClientByClientName(customerName);
			for(Customer cust:clientList){
				CustomerReportBean custBean = new CustomerReportBean();
				
				custBean.setCustomerName(cust.getCustomerName());
				custBean.setTelephoneNumber(cust.getTelephoneNumber());
				custBean.setFaxNumber(cust.getFaxNumber());
				custBean.setStreetName(cust.getStreetName());
				custBean.setStreetNumber(cust.getStreetNumber());
				custBean.setCity_town(cust.getCity_town());
				
				/*custBean.setFirstName(cust.getFirstName());				
				custBean.setLastName(cust.getLastName());
				custBean.setContactEmail(cust.getContactEmail());
				custBean.setContactCellNumber(cust.getContactCellNumber());				
				custBean.setContactTelephoneNumber(cust.getContactTelephoneNumber());
				
				custBean.setLastName1(cust.getLastName1());
				custBean.setFirstName1(cust.getFirstName1());
				custBean.setContactEmail1(cust.getContactEmail1());	
				custBean.setContactCellNumber1(cust.getContactCellNumber1());
				custBean.setContactTelephoneNumber1(cust.getContactTelephoneNumber1());*/
				
				
				result.add(custBean);
				ds = new JRBeanCollectionDataSource(result);
			}
		}catch(Exception e){
			e.getMessage();
		}
		return ds;
	}
	
	
	
	

}

package com.demo.service;

import java.util.List;

import com.demo.bean.CustomerBean;
import com.demo.model.CustomerContactDetails;

public interface CustomerContactDetailsServiceInt {
	CustomerBean contactDetails(String customerName);
	List<CustomerContactDetails> contacts(String customerName);
	List<CustomerContactDetails> contacts();
	CustomerContactDetails getContactPerson(String customerName);

}

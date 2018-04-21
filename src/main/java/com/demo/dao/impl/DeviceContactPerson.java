package com.demo.dao.impl;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.deviceContactPersonDaoInt;

@Repository("deviceContactPersonDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class DeviceContactPerson implements deviceContactPersonDaoInt{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private String retMessage;

	@Override
	public String saveContactPerson(
			com.demo.model.DeviceContactPerson contactPerson) {
		try{
			sessionFactory.getCurrentSession().saveOrUpdate(contactPerson);
			retMessage = "OK";
			
		}catch(Exception e){
			retMessage = e.getMessage();
		}
		
		return retMessage;
	}


}

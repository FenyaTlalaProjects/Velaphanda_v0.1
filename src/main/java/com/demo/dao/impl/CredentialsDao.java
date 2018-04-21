package com.demo.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;



import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.CredentialsDaoInt;
import com.demo.model.Credentials;


@Repository("credentialsDao")
@Transactional(propagation=Propagation.REQUIRED)
public class CredentialsDao implements CredentialsDaoInt{

	@Autowired
	private SessionFactory sessionFactory;
	
	private Credentials credentials=null;
	private Date currentDate, firstDate,secondDate = null;
	private SimpleDateFormat myFormat = null;
	@Override
	public boolean saveNewPassword(Credentials credentials) {
		boolean retFlag = false;
		try{
			   retFlag =  checkPasswordExists(credentials.getEmail(),credentials.getPassword());
			   
			   if(retFlag == false){
				   credentials.setStatus("Current");
				   sessionFactory.getCurrentSession().save(credentials);
				   retFlag = true;
			   }else{
				   retFlag = false;
			   }
		}catch(Exception e){
			retFlag = false;
			e.getMessage();
			
		}
		return retFlag;
	}
	@Override
	public List<Credentials> getCredentialsForUser(String email) {
		return null;
	}
	@Override
	public List<Credentials> getCredentialsForUser() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Credentials.class);
		return (List<Credentials>)criteria.list();
	}
	
	private boolean checkPasswordExists(String username, String newPassword){
		boolean retFlag = false;
		try{
			List <Credentials> currentPasswords = getCredentialsForUser();
			for(Credentials cred : currentPasswords){
				if(cred.getEmail().equalsIgnoreCase(username)&& cred.getPassword().equalsIgnoreCase(newPassword)){
					retFlag = true;
					break;
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		if(retFlag ==false){
			
		}
		return retFlag;
	}
	@Override
	public long passwordDateDifference(String userName) {
		long diffDays = 0;
		String date1 = null;
		String date2 = null;
		long days = 1L;
		
		
	    myFormat = new SimpleDateFormat("yyyy-MM-dd");
	    currentDate  = new Date();
	    firstDate = new Date();
	    secondDate = new Date();
	    credentials = new Credentials();
		try{
			credentials = getCurrentPasswordDate(userName);
			date1 = credentials.getPasswordDateInserted();
			date2 = myFormat.format(currentDate);
			
			firstDate = myFormat.parse(date1);
			secondDate = myFormat.parse(date2);
			diffDays =  secondDate.getTime() -firstDate.getTime();
			days = TimeUnit.DAYS.convert(diffDays, TimeUnit.MILLISECONDS);
			 
		}catch(Exception e){
			e.getMessage();
		}
		
		return days;
	}
	@Override
	public Credentials getEmployeePasswordInfo(String userName) {
		
		return (Credentials) sessionFactory.getCurrentSession().get(Credentials.class, userName);
	}
	
	public Credentials getCurrentPasswordDate(String userName){
		Credentials credentials = null;
		try{
			
			List <Credentials> currentPasswords = getCredentialsForUser();
			
			for(Credentials credObj:currentPasswords){
				if(credObj.getEmail().equalsIgnoreCase(userName) && credObj.getStatus().equalsIgnoreCase("Current")){
					credentials = credObj;
					break;
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return credentials;
	}

}

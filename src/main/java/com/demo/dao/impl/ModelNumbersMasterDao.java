package com.demo.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.ModelNumbersMasterDaoInt;
import com.demo.model.ModelNumbers;
import com.demo.model.SpareMaster;

import edu.emory.mathcs.backport.java.util.Arrays;



@Repository("modelNumbersMasterDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class ModelNumbersMasterDao implements ModelNumbersMasterDaoInt{

	
	@Autowired
	private SessionFactory sessionFactory;
	
	private ModelNumbers modelNumbers = null;
	@SuppressWarnings("unchecked")
	private String retMessage = null;
	private String errorRetMessage = null;
	
	//Get current Date time
	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date now = new Date();
	String dateTimeStamp = sdfDate.format(now);
	
	@Override
	public List<ModelNumbers> getModelNumbersFromMasterData() {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ModelNumbers.class);
		return (List<ModelNumbers>)criteria.list(); 
	}
	@Override
	public ModelNumbers getModelNumbersMaster(String modelNumber) {
		
		return (ModelNumbers) sessionFactory.getCurrentSession().get(ModelNumbers.class, modelNumber);
	}
	@Override
	public String[] getModelNumbers() {
		List<ModelNumbers> list = null;
		ArrayList<String> newList = null;
		String array[] = null;
		try{
			list = getModelNumbersFromMasterData();
			newList = new ArrayList<String>();
			System.err.printf( "Provide me with a list",newList);			
			for(ModelNumbers models:list){
				newList.add(models.getModelNumber());
			}
			array = new String[newList.size()];
			
			for(int i =0;i<newList.size();i++){
				array[i] = newList.get(i);
			}
		}
		catch(Exception e){
			e.getMessage();
		}
		return array;
	}
	
	@Override
	public List<String> getAllModelNumbers(String modelNumber) {
		modelNumbers = new ModelNumbers();
		String modelParts = null;
		List<String> myList = null;
		try{
			modelNumbers = getModelNumbersMaster(modelNumber);
			if(modelNumbers !=null){
				modelParts = modelNumbers.getModelNumber();
				
				myList = new ArrayList<String>(Arrays.asList(modelParts.split(",")));
			}
			else{
				myList =new ArrayList<String>(Arrays.asList(null));
			}
			
		}catch(Exception e){
			e.getMessage();
		}
		
		return myList;
	}
	@Override
	public String saveModelNumberData(ModelNumbers modelNumber) {
		try{
			ModelNumbers modelNumbers = getModelNumbersMaster(modelNumber.getModelNumber());
			if(modelNumbers != null){
				
				retMessage = "Model Number already exist";
				
			}else{
				
				modelNumber.setDateTime(dateTimeStamp);
				sessionFactory.getCurrentSession().save(modelNumber);
			
				retMessage = "Model Number "+ modelNumber.getModelNumber()+" successfully added.";
			}
		}catch(Exception e){
			retMessage = e.getMessage()+".";
		}
		return retMessage;
	}
	
}

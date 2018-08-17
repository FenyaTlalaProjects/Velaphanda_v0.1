package com.demo.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.SpareMasterDaoInt;
import com.demo.model.SpareMaster;

import edu.emory.mathcs.backport.java.util.Arrays;



@Repository("spareMasterDAO")
@Transactional(propagation=Propagation.REQUIRED)
public class SpareMasterDao implements SpareMasterDaoInt{

	
	@Autowired
	private SessionFactory sessionFactory;
	
	private SpareMaster spareMaster = null;
	@SuppressWarnings("unchecked")
	private String retMessage = null;
	private String errorRetMessage = null;
	
	//Get current Date time
	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date now = new Date();
	String dateTimeStamp = sdfDate.format(now);
	
	@Override
	public List<SpareMaster> getSparesFromMastaData() {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SpareMaster.class);
		return (List<SpareMaster>)criteria.list(); 
	}
	@Override
	public SpareMaster getSpareMaster(String partNumber) {
		
		return (SpareMaster) sessionFactory.getCurrentSession().get(SpareMaster.class, partNumber);
	}
	
	@Override
	public String[] getSerials() {
		List<SpareMaster> list = null;
		ArrayList<String> newList = null;
		String array[] = null;
		try{
			list = getSparesFromMastaData();
			newList = new ArrayList<String>();
			
			for(SpareMaster master:list){
				newList.add(master.getPartNumber());
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getModelDevice(String partNumber) {
		spareMaster = new SpareMaster();
		String modelParts = null;
		List<String> myList = null;
		try{
			spareMaster = getSpareMaster(partNumber);
			if(spareMaster !=null){
				modelParts = spareMaster.getCompitableDevice();
				
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
	public String saveSpareMasterData(SpareMaster spareMaster) {
		try{
			SpareMaster spareMasters = getSpareMaster(spareMaster.getPartNumber());
			if(spareMasters != null){
				
				retMessage = "Part number already exist";
				
			}else{
				spareMaster.setDateCaptured(dateTimeStamp);
				sessionFactory.getCurrentSession().save(spareMaster);
			
				retMessage = "Part "+ spareMaster.getPartNumber()+" successfully added.";
			}
		}catch(Exception e){
			retMessage = e.getMessage()+".";
		}
		return retMessage;
	}

}

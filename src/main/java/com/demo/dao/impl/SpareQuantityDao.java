package com.demo.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.bean.SpareQuantity;
import com.demo.dao.BootStockDaoInt;
import com.demo.dao.EmployeeDaoInt;
import com.demo.dao.SiteStocDaoInt;
import com.demo.dao.SpareQuantityDaoInt;
import com.demo.model.BootStock;
import com.demo.model.Employee;
import com.demo.model.SiteStock;

@Repository("spareQuantityDao")
@Transactional(propagation = Propagation.REQUIRED)
public class SpareQuantityDao implements SpareQuantityDaoInt{

	@Autowired
	private SiteStocDaoInt siteStockInt;
	@Autowired
	private BootStockDaoInt bootStockDaoInt;
	@Autowired
	private EmployeeDaoInt employeeDaoInt;
	
	private Employee emp = null;
	private SpareQuantity spareQuantity;
	
	@Override
	public List<SpareQuantity> spareQuantity() {
		
		List<SpareQuantity> temptQuanity = null;
		List<SiteStock> tempSiteStock = siteStockInt.getAllSiteStock();
		temptQuanity = new ArrayList<SpareQuantity>();
		List<String> customerNames = new ArrayList<String>();
		
		for(SiteStock stock: tempSiteStock){
			customerNames.add(stock.getCustomerName());
		}
		
		HashSet<String> hashset = new HashSet<String>();
	   
	    hashset.addAll(customerNames);
	 
	    // Removing ArrayList elements
	    customerNames.clear();
	 
	    // Adding LinkedHashSet elements to the ArrayList
	    customerNames.addAll(hashset );
	  
	    for(String temp:customerNames){
	    	spareQuantity = new SpareQuantity();
			spareQuantity.setCustomerName(temp);
			spareQuantity.setPartQuanty(siteStockInt.countPartsForCustomer(temp));
			spareQuantity.setTonerQuantity(siteStockInt.countTonerForCustomer(temp));
			
			temptQuanity.add(spareQuantity);
	    }
		
		return temptQuanity;
	}

	@Override
	public List<SpareQuantity> spareQuantityForTechnicians() {
		
		List<SpareQuantity> temptQuanity = null;
		List<BootStock> tempSiteStock = bootStockDaoInt.getAllOrdersWithoutZeros();
		List<String> techEmailsEmails = new ArrayList<String>();
		temptQuanity = new ArrayList<SpareQuantity>();
		//String [] techNamesArray = new String[tempSiteStock.size()];
		for(BootStock stock: tempSiteStock){
			techEmailsEmails.add(stock.getTechnicianEmail());
		}
		HashSet<String> hashset = new HashSet<String>();
		HashSet<String> hashsetNames = new HashSet<String>();
		   
	    hashset.addAll(techEmailsEmails);
	 
	    // Removing ArrayList elements
	    techEmailsEmails.clear();
	    hashsetNames.clear();
	 
	    // Adding LinkedHashSet elements to the ArrayList
	    techEmailsEmails.addAll(hashset );
	
		for(String techName:techEmailsEmails){
			emp = employeeDaoInt.getEmployeeByEmpNum(techName);
			spareQuantity = new SpareQuantity();
			spareQuantity.setCustomerName(techName);			
			spareQuantity.setPartQuanty(bootStockDaoInt.countPartsForTechnician(techName));
			spareQuantity.setTonerQuantity(bootStockDaoInt.countTonerForTechnician(techName));
			spareQuantity.setTechName(emp.getFirstName()+" "+ emp.getLastName());
						
			temptQuanity.add(spareQuantity);
		}
		return temptQuanity;
	}

	@Override
	public List<SpareQuantity> spareQuantityForTechnician(String technicianName) {
	
		List<SpareQuantity> temptQuanity = new ArrayList<SpareQuantity>();
		List<BootStock> tempSiteStock = bootStockDaoInt.getAllBootStockByTechnician(technicianName);
		List<String> techEmailsEmails = new ArrayList<String>();
		//List<String> tecniciansNames = new ArrayList<String>();
			
		for(BootStock stock: tempSiteStock){
			if(stock.getTechnicianName().equalsIgnoreCase(technicianName)){
				//tecniciansNames.add(stock.getTechnicianName());
				techEmailsEmails.add(stock.getTechnicianEmail());
			}
		}
		
		HashSet<String> hashset = new HashSet<String>();
		hashset.addAll(techEmailsEmails);   
	    //hashset.addAll(tecniciansNames);
	 
	    // Removing ArrayList elements
		techEmailsEmails.clear();
	    //tecniciansNames.clear();
	 
	    // Adding LinkedHashSet elements to the ArrayList
		techEmailsEmails.addAll(hashset );
	    //tecniciansNames.addAll(hashset );
		
		for(String techName:techEmailsEmails){
			emp = employeeDaoInt.getEmployeeByEmpNum(techName);
			spareQuantity = new SpareQuantity();
			spareQuantity.setCustomerName(techName);			
			spareQuantity.setPartQuanty(bootStockDaoInt.countPartsForTechnician(techName));
			spareQuantity.setTonerQuantity(bootStockDaoInt.countTonerForTechnician(techName));
			spareQuantity.setTechName(emp.getFirstName()+" "+ emp.getLastName());
						
			temptQuanity.add(spareQuantity);
		}
	    
		/*for(String str: tecniciansNames){
			spareQuantity = new SpareQuantity();
			spareQuantity.setCustomerName(str);
			spareQuantity.setPartQuanty(bootStockDaoInt.countPartsForTechnician(str));
			spareQuantity.setTonerQuantity(bootStockDaoInt.countTonerForTechnician(str));
			spareQuantity.setTechName(bootStockDaoInt.getTechName(str));
			temptQuanity.add(spareQuantity);
			
		}*/
		return temptQuanity;
	}

	@Override
	public List<SpareQuantity> spareQuantityForTechnicianSiteStock() {
		
		List<SpareQuantity> temptQuanity = null;
		temptQuanity = new ArrayList<SpareQuantity>();
		List<String> customerName = new ArrayList<String>();
		List<SiteStock> tempSiteStock = siteStockInt.getAllSiteStock();
		
		for(SiteStock stock: tempSiteStock){
			customerName.add(stock.getCustomerName());
		}
		HashSet<String> hashset = new HashSet<String>();
		   
	    hashset.addAll(customerName);
	 
	    // Removing ArrayList elements
	    customerName.clear();
	 
	    // Adding LinkedHashSet elements to the ArrayList
	    customerName.addAll(hashset );
		
		for(String tempStr: customerName){
			spareQuantity = new SpareQuantity();
			spareQuantity.setCustomerName(tempStr);
			spareQuantity.setPartQuanty(siteStockInt.countPartsForCustomer(tempStr));
			spareQuantity.setTonerQuantity(siteStockInt.countTonerForCustomer(tempStr));
			
			temptQuanity.add(spareQuantity);
			
		}
		return temptQuanity;
	}

}

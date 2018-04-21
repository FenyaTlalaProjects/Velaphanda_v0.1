package com.demo.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.BootStockDaoInt;
import com.demo.dao.EmployeeDaoInt;
import com.demo.dao.HOStockDaoInt;
import com.demo.dao.SiteStocDaoInt;
import com.demo.dao.TicketsDaoInt;
import com.demo.model.BootStock;
import com.demo.model.Employee;
import com.demo.model.HOStock;
import com.demo.model.SiteStock;
import com.demo.model.OrderDetails;
import com.demo.model.Tickets;
@Repository("siteStockDao")
@Transactional(propagation=Propagation.REQUIRED)
public class SiteStockDao implements SiteStocDaoInt {
	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TicketsDaoInt ticketsDaoInt;
	@Autowired
	private EmployeeDaoInt daoInt;
	@Autowired
	private BootStockDaoInt bootStockDaoInt;
	@Autowired
	private HOStockDaoInt hoStockDaoInt;
	
	
	private SiteStock siteStock,siteStockForCustomer;
	
	private BootStock bootSite;
	
	private HOStock hoStock,hoStockFromCustomer;
	
	List<SiteStock> sitetStockList = null;
	List<SiteStock> siteStocks = null;
	
	Calendar cal = Calendar.getInstance();
	DateFormat dateFormat = null;
	Date date = null;

	
	@Override
	public List<SiteStock> getAllSiteStock() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				SiteStock.class);
		return (List<SiteStock>) criteria.list();
	}
	
	private SiteStock getSelectedSiteStock(String partNumber){
		try{
			sitetStockList = getAllSiteStock();
			for(SiteStock stock:sitetStockList){
				if (stock.getPartNumber().equalsIgnoreCase(partNumber)){
					siteStock = stock;
				}
			}
			
		}catch(Exception ex){
			
		}
		return siteStock;
	}
	@Override
	public void saveSiteStock(List<OrderDetails> detailsDaos) {
		try {
			for(OrderDetails stock:detailsDaos){
		      siteStock = getSelectedSiteStock(stock.getPartNumber());
				 if(siteStock != null && stock.getPartNumber().equalsIgnoreCase(siteStock.getPartNumber())&& siteStock.getCustomerName().equalsIgnoreCase(stock.getOrderHeader().getCustomer().getCustomerName())){
					 int incrementQuantity = stock.getQuantity() + siteStock.getQuantity();
					 siteStock.setCompatibleDevice(stock.getCompatibleDevice());
					 siteStock.setQuantity(incrementQuantity);
					 siteStock.setModelBrand(stock.getModelBrand());
					 siteStock.setColor(stock.getColor());
					 sessionFactory.getCurrentSession().update(siteStock);
		
					 
				 }else{
					 siteStock = new SiteStock();
						siteStock.setCustomerName(stock.getOrderHeader().getCustomer().getCustomerName());
						siteStock.setItemDescription(stock.getItemDescription());
						siteStock.setItemType(stock.getItemType());
						siteStock.setLocation(stock.getLocation());
						siteStock.setPartNumber(stock.getPartNumber());
						siteStock.setQuantity(stock.getQuantity());
						siteStock.setCompatibleDevice(stock.getCompatibleDevice());
						siteStock.setTechnicianEmail(stock.getTechnician());
						siteStock.setTechnicianName(stock.getTechnician());
						siteStock.setModelBrand(stock.getModelBrand());
						siteStock.setColor(stock.getColor());
						sessionFactory.getCurrentSession().save(siteStock);
				 }
			 }
		} catch (Exception exception) {
			exception.getMessage();

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SiteStock> getAllOrders() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SiteStock.class);
		return (List<SiteStock>) criteria.list();
	}

	@Override
	public List<SiteStock> getOrdersForCustomer(String customerName) {
		sitetStockList = new ArrayList<SiteStock>();
		try{
			siteStocks = getAllOrders();
			 for(SiteStock stock:siteStocks){
				 if(stock.getCustomerName().equalsIgnoreCase(customerName)){
					 sitetStockList.add(stock);
				 }
			 }
		}catch(Exception e){
			
		}
		return sitetStockList;
	}

	@Override
	public List<SiteStock> getOrdersByTechnician(String technicianName) {
		sitetStockList = new ArrayList<SiteStock>();
		try{
			siteStocks = getAllOrders();
			 for(SiteStock site:siteStocks){
				 String name = site.getTechnicianName();
				 if(name.equalsIgnoreCase(technicianName)){
					 sitetStockList.add(site);
				 }
			 }
		}catch(Exception e){
			e.getMessage();
		}
		return sitetStockList;
	}

	@Override
	public List<SiteStock> getOrdersForCustomer(String customerName,
			Long ticketID) {
		Tickets ticket = ticketsDaoInt.getLoggedTicketsByTicketNumber(ticketID);
		
		String tempDeviceModelNumber = ticket.getDevice().getModelNumber();
		
		List<String> spare = null;
		sitetStockList = new ArrayList<SiteStock>();
		try{
			siteStocks = getAllOrders();
			 for(SiteStock stock:siteStocks){
				 if(stock.getCustomerName().equalsIgnoreCase(customerName) && stock.getQuantity()>0){
					 
					 spare = new ArrayList<String>(Arrays.asList(stock
								.getCompatibleDevice().split("/")));
						for (int i = 0; i < spare.size(); i++) {
							if (spare.get(i)
									.equalsIgnoreCase(tempDeviceModelNumber)) {
								sitetStockList.add(stock);
							}
						}
				 }
			 }
		}catch(Exception e){
			
		}
		return sitetStockList;
	}

	@Override
	public SiteStock getSiteStock(String partNumber) {
		return (SiteStock) sessionFactory.getCurrentSession().get(SiteStock.class, partNumber);
	}

	@Override
	public SiteStock getSiteStock(String partNumber, String customerName) {
		SiteStock localtemp = null;
		List<SiteStock> siteStock = getOrdersForCustomer(customerName);
		for(SiteStock stock:siteStock){
			if(stock.getPartNumber().equalsIgnoreCase(partNumber)){
				localtemp=stock;
			}
		}
		return localtemp;
	}
	
	
	@Override
	public int countSiteStock() {
//		List<SiteStock> tempSiteList = new ArrayList<SiteStock>();
		int siteCount = 0;
		try{
			List<SiteStock> tempSiteList = getAllSiteStock();
			for(SiteStock stock:tempSiteList){
				if(stock.getQuantity()> 0){
					siteCount = siteCount + stock.getQuantity();
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return siteCount;
	}
	
	
	@Override
	public int countPartsForCustomer(String customerName) {
		int bootCount = 0;
		try{
			
			List<SiteStock> tempSiteList = getAllSiteStock();
			
			for(SiteStock stock:tempSiteList){
				if(stock.getCustomerName().equalsIgnoreCase(customerName)&&stock.getItemType().equalsIgnoreCase("Part")){
					bootCount = bootCount + stock.getQuantity();
					
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return bootCount;
	}
	
	@Override
	public List<SiteStock> getPartsForCustomer(String customerName) {
 		List<SiteStock> currentList = new ArrayList<SiteStock>();
		try{
			List<SiteStock> tempBootList = getAllSiteStock();
			for(SiteStock stock:tempBootList){
				if(stock.getItemType().equalsIgnoreCase("Part") && stock.getCustomerName().equalsIgnoreCase(customerName)){
					currentList.add(stock);
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return currentList;
	}
	
	
	@Override
	public int countTonerForCustomer(String customerName) {
		int bootCount = 0;
		try{
			
			List<SiteStock> tempSiteList = getAllSiteStock();
			
			for(SiteStock stock:tempSiteList){
				if(stock.getCustomerName().equalsIgnoreCase(customerName)&&stock.getItemType().equalsIgnoreCase("Toner")){
					bootCount = bootCount + stock.getQuantity();
					
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return bootCount;
	}
	
	@Override
	public List<SiteStock> getTonerForCustomer(String customerName) {
 		List<SiteStock> currentList = new ArrayList<SiteStock>();
		int bootCount = 0;
		try{
			List<SiteStock> tempBootList = getAllSiteStock();
			for(SiteStock stock:tempBootList){
				if(stock.getItemType().equalsIgnoreCase("Toner") && stock.getCustomerName().equalsIgnoreCase(customerName)){
					currentList.add(stock);
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return currentList;
	}
	
	@Override
	public List<SiteStock> getOrdersForCustomer(String customerName,
			String compatibleDevice) {
		List<SiteStock> currentList = new ArrayList<SiteStock>();
		try{
			List<SiteStock> siteList = getAllSiteStock();
			for(SiteStock stock:siteList){
				if(stock.getItemType().equalsIgnoreCase("Toner") && stock.getQuantity()>0 && stock.getCustomerName().equalsIgnoreCase(customerName)&& stock.getCompatibleDevice().contains(compatibleDevice)){
					currentList.add(stock);
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return currentList;
	}


	@Override
	public int countSiteStock(String technicianName) {
		int siteCount = 0;
		try{
			List<SiteStock> tempSiteList = getAllSiteStock();
			for(SiteStock stock:tempSiteList){
				if(stock.getQuantity()> 0 && stock.getTechnicianName().equalsIgnoreCase(technicianName)){
					siteCount = siteCount + stock.getQuantity();
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return siteCount;
	}


	@Override
	public List<SiteStock> getAllSiteStock(String technicianName) {
		List<SiteStock> tempSiteList = null;
		List<SiteStock> newList = null;
		try{
			tempSiteList = getAllSiteStock();
			newList = new ArrayList<SiteStock>();
			for(SiteStock stock:tempSiteList){
				if(stock.getTechnicianName().equalsIgnoreCase(technicianName)&& stock.getQuantity()>0){
					
					newList.add(stock);
				}
			}
			
		}catch(Exception e){
			e.getMessage();
		}
		return newList;
	}


	@Override
	public int countTonerForCustomer(String customerName, String technicianName) {
		int bootCount = 0;
		try{
			
			List<SiteStock> tempSiteList = getAllSiteStock();
			
			for(SiteStock stock:tempSiteList){
				if(stock.getCustomerName().equalsIgnoreCase(customerName)&&stock.getItemType().equalsIgnoreCase("Toner") && stock.getTechnicianName().equalsIgnoreCase(technicianName)){
					bootCount = bootCount + stock.getQuantity();
					
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return bootCount;
	}


	@Override
	public int countPartsForCustomer(String customerName, String technicianName) {
		int bootCount = 0;
		try{
			
			List<SiteStock> tempSiteList = getAllSiteStock();
			
			for(SiteStock stock:tempSiteList){
				if(stock.getCustomerName().equalsIgnoreCase(customerName)&&stock.getItemType().equalsIgnoreCase("Part")&& stock.getTechnicianName().equalsIgnoreCase(technicianName)){
					bootCount = bootCount + stock.getQuantity();
					
				}
			}
		}catch(Exception e){
			e.getMessage();
		}
		return bootCount;
	}

	@Override
	public String moveStock(String fromCustomerName, String fromTechnicianName,
			String partNumberList, String quantityList, String options,
			String customerName, String technicianName, String reasonForSite,
			String reasonForBoot,String headOffice, String reasonForHeadOffice) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = new Date();
		int count = 0;
		String retMessage = null;
		try{
			String [] serialNumbers =partNumberList.split(",");
			String [] tempQuantity = quantityList.split(",");
			
			
			sitetStockList = new ArrayList<SiteStock>();
			for(String tempPartNumber:serialNumbers){
				siteStock = new SiteStock();
				bootSite = new BootStock();
				hoStock = new HOStock();
				
				int result = 0;
				if(fromCustomerName.length()>3){
					
					SiteStock stock = getSiteStock( tempPartNumber,fromCustomerName);
					
				    result = Integer.parseInt(tempQuantity[count]);
					int curentQuantity = stock.getQuantity()- result;
					stock.setQuantity(curentQuantity);
					sessionFactory.getCurrentSession().update(stock);
					Employee emp = daoInt.getEmployeeByEmpNum(technicianName);
                    if(reasonForSite.length()>3){
						
						siteStock.setCustomerName(customerName);
						siteStock.setItemDescription(stock.getItemDescription());
						siteStock.setItemType(stock.getItemType());
						siteStock.setLocation(stock.getLocation());
						siteStock.setPartNumber(stock.getPartNumber());
						siteStock.setCompatibleDevice(stock.getCompatibleDevice());
						
						siteStock.setModelBrand(stock.getModelBrand());
						siteStock.setColor(stock.getColor());
						siteStock.setDateSparesMoved(dateFormat.format(date));
						siteStock.setMoveSparesFrom(fromCustomerName);
						siteStock.setMoveSparesTo(customerName);
						
						siteStock.setQuantity(result);
						
						siteStock.setReasonForMovement(reasonForSite);
						sessionFactory.getCurrentSession().saveOrUpdate(siteStock);
						retMessage = "Spares successfully  moved to "+ customerName +" Site" ;
					}else if(reasonForBoot.length()>3){
						
						bootSite.setCompatibleDevice(stock.getCompatibleDevice());
						bootSite.setColor(stock.getColor());
						bootSite.setModelBrand(stock.getModelBrand());
						bootSite.setReasonForMovement(reasonForBoot);
						bootSite.setDateSparesMoved(dateFormat.format(date));
						bootSite.setItemDescription(stock.getItemDescription());
						bootSite.setItemType(stock.getItemType());
						bootSite.setMoveSparesFrom(fromCustomerName);
						bootSite.setMoveSparesTo(technicianName);
						bootSite.setPartNumber(stock.getPartNumber());
						bootSite.setQuantity(result);
						bootSite.setTechnicianEmail(emp.getFirstName()+ " "+emp.getLastName());
						bootSite.setTechnicianName(emp.getFirstName()+ " "+emp.getLastName());
						sessionFactory.getCurrentSession().saveOrUpdate(bootSite);
						
						retMessage = "Spares successfully  moved to "+ emp.getFirstName()+ " "+emp.getLastName();
						
					}else{
						
						hoStock = hoStockDaoInt.getSparePartBySerial(tempPartNumber);
						int countQuantity = hoStock.getQuantity()+ result;
						hoStock.setQuantity(countQuantity);
						
						retMessage = "Spares successfully  moved to head office";
					}
					
				}else if(fromTechnicianName.length()>3){
					
					BootStock bootStock = bootStockDaoInt.getBootStock(tempPartNumber, fromTechnicianName);
					result = Integer.parseInt(tempQuantity[count]);
					int curentQuantity = bootStock.getQuantity() - result;
					
					bootStock.setQuantity(curentQuantity);
					sessionFactory.getCurrentSession().update(bootStock);
					
				
					Employee emp = daoInt.getEmployeeByEmpNum(technicianName);
					
					if(reasonForBoot.length()>3){
						bootSite.setCompatibleDevice(bootStock.getCompatibleDevice());
						bootSite.setColor(bootStock.getColor());
						bootSite.setModelBrand(bootStock.getModelBrand());
						bootSite.setReasonForMovement(reasonForBoot);
						bootSite.setDateSparesMoved(dateFormat.format(date));
						bootSite.setItemDescription(bootStock.getItemDescription());
						bootSite.setItemType(bootStock.getItemType());
						bootSite.setMoveSparesFrom(fromCustomerName);
						bootSite.setMoveSparesTo(technicianName);
						bootSite.setPartNumber(bootStock.getPartNumber());
						bootSite.setQuantity(result);
						bootSite.setTechnicianEmail(emp.getFirstName()+ " "+emp.getLastName());
						bootSite.setTechnicianName(emp.getFirstName()+ " "+emp.getLastName());
						sessionFactory.getCurrentSession().saveOrUpdate(bootSite);
						
						retMessage = "Spares successfully  moved to "+ emp.getFirstName()+ " "+emp.getLastName() ;
						
						
					}else if(reasonForSite.length()>3){

						siteStock.setCustomerName(customerName);
						siteStock.setItemDescription(bootStock.getItemDescription());
						siteStock.setItemType(bootStock.getItemType());
						siteStock.setPartNumber(bootStock.getPartNumber());
						siteStock.setCompatibleDevice(bootStock.getCompatibleDevice());
						
						siteStock.setModelBrand(bootStock.getModelBrand());
						siteStock.setColor(bootStock.getColor());
						siteStock.setDateSparesMoved(dateFormat.format(date));
						siteStock.setMoveSparesFrom(fromTechnicianName);
						siteStock.setMoveSparesTo(customerName);
						
						siteStock.setQuantity(result);
						
						siteStock.setReasonForMovement(reasonForSite);
						sessionFactory.getCurrentSession().saveOrUpdate(siteStock);
						
						retMessage = "Spares successfully  moved to "+ customerName +" Site" ;
					}else{
						hoStock = hoStockDaoInt.getSparePartBySerial(tempPartNumber);
						int countQuantity = hoStock.getQuantity()+ result;
						hoStock.setQuantity(countQuantity);
						//hoStock.setReceivedBy("");
						sessionFactory.getCurrentSession().update(hoStock);
						
						retMessage = "Spares successfully  moved to head office";
					}
				
				}
				count++;
			}
			
		}catch(Exception ex){
			ex.getMessage();
		}
		
		return retMessage;
	}

	
}

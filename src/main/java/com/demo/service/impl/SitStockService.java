package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.SiteStocDaoInt;
import com.demo.model.SiteStock;
import com.demo.service.SiteStockInt;

@Service("siteStockService")
public class SitStockService implements SiteStockInt{

	@Autowired
	private SiteStocDaoInt siteStock;
	@Override
	public List<SiteStock> getAllOrders() {
		return siteStock.getAllOrders();
	}
	@Override
	public List<SiteStock> getOrdersForCustomer(String customerName) {
		return siteStock.getOrdersForCustomer(customerName);
	}
	@Override
	public List<SiteStock> getOrdersByTechnician(String technician) {
		return siteStock.getOrdersByTechnician(technician);
	}
	@Override
	public List<SiteStock> getOrdersForCustomer(String customerName,
			Long ticketID) {
		
		return siteStock.getOrdersForCustomer(customerName, ticketID);
	}
	@Override
	public int countSiteStock() {
		return siteStock.countSiteStock();
	}
	@Override
	public List<SiteStock> getTonerForCustomer(String customerName) {
		// TODO Auto-generated method stub
		return siteStock.getTonerForCustomer(customerName);
	}
	@Override
	public List<SiteStock> getPartsForCustomer(String customerName) {
		// TODO Auto-generated method stub
		return siteStock.getPartsForCustomer(customerName);
	}
	@Override
	public int countTonerForCustomer(String customerName) {
		// TODO Auto-generated method stub
		return siteStock.countTonerForCustomer(customerName);
	}
	@Override
	public int countPartsForCustomer(String customerName) {
		// TODO Auto-generated method stub
		return siteStock.countPartsForCustomer(customerName);
	}
	@Override
	public List<SiteStock> getAllSiteStock() {
		// TODO Auto-generated method stub
		return siteStock.getAllSiteStock();
	}
	@Override
	public int countSiteStock(String technicianName) {
		// TODO Auto-generated method stub
		return siteStock.countSiteStock(technicianName);
	}
	@Override
	public List<SiteStock> getAllSiteStock(String technicianName) {
		// TODO Auto-generated method stub
		return siteStock.getAllSiteStock(technicianName);
	}
	@Override
	public int countTonerForCustomer(String customerName, String technicianName) {
		return siteStock.countPartsForCustomer(customerName, technicianName);
	}
	@Override
	public int countPartsForCustomer(String customerName, String technicianName) {
		// TODO Auto-generated method stub
		return siteStock.countPartsForCustomer(customerName, technicianName);
	}
	@Override
	public List<SiteStock> getOrdersForCustomer(String customerName,
			String compatibleDevice) {
		return siteStock.getOrdersForCustomer(customerName, compatibleDevice);
	}
	@Override
	public String moveStock(String fromCustomerName, String fromTechnicianName,
			String partNumberList, String quantity, String options,
			String customerName, String technicianName, String reasonForSite,
			String reasonForBoot,String headOffice,String reasonForHeadOffice) {
		return siteStock.moveStock(fromCustomerName, fromTechnicianName, partNumberList, quantity, options, customerName, technicianName, reasonForSite, reasonForBoot,headOffice,reasonForHeadOffice);
	}


}

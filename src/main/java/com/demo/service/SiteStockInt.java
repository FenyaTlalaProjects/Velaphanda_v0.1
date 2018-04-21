package com.demo.service;

import java.util.List;

import com.demo.model.SiteStock;

public interface SiteStockInt {
	List<SiteStock> getAllOrders();
	List<SiteStock> getOrdersForCustomer(String customerName);
	List<SiteStock> getOrdersForCustomer(String customerName,String compatibleDevice);
	List<SiteStock> getOrdersByTechnician(String technician);
	List<SiteStock> getOrdersForCustomer(String customerName,Long ticketID);
	int countSiteStock();
	int countSiteStock(String technicianName);
	List<SiteStock> getTonerForCustomer(String customerName);
	List<SiteStock> getPartsForCustomer(String customerName);
	int countTonerForCustomer(String customerName);
	int countTonerForCustomer(String customerName,String technicianName);
	int countPartsForCustomer(String customerName);
	int countPartsForCustomer(String customerName, String technicianName);
	List<SiteStock> getAllSiteStock();
	List<SiteStock> getAllSiteStock(String technicianName);
	String moveStock(String fromCustomerName,String fromTechnicianName,String partNumberList,String quantity,String options,String customerName,String technicianName,String reasonForSite,String reasonForBoot,String headOffice,String reasonForHeadOffice);

}

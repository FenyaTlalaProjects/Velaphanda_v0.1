package com.demo.dao;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.demo.model.OrderDetails;
import com.demo.model.SiteStock;

public interface SiteStocDaoInt {
	
	void saveSiteStock(List<OrderDetails> detailsDaos);
	List<SiteStock> getAllOrders();
	List<SiteStock> getOrdersForCustomer(String customerName);
	List<SiteStock> getOrdersForCustomer(String customerName,String compatibleDevice);
	List<SiteStock> getOrdersByTechnician(String technician);
	List<SiteStock> getOrdersForCustomer(String customerName,Long ticketID);
	SiteStock getSiteStock(String partNumber);
	SiteStock getSiteStock(String partNumber,String customerName);
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
	String moveStock(String fromCustomerName,String fromTechnicianName,String partNumberList,String quantity,String options,String customerName,String technicianName,String reasonForSite,String reasonForBoot, String headOffice, String reasonForHeadOffice);

}

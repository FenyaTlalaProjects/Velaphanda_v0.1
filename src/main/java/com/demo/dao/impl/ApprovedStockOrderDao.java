package com.demo.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.ApprovedOrderStockDaoInt;
import com.demo.model.ApprovedOrderStock;
import com.demo.model.OrderDetails;


@Repository("approvedStockOrdersDao")
@Transactional(propagation=Propagation.REQUIRED)
public class ApprovedStockOrderDao implements ApprovedOrderStockDaoInt{

	@Autowired
	private SessionFactory sessionFactory;
	private ApprovedOrderStock approvedOrderStock;
	
	private String retMessage = null;
	@Override
	public String approveOrderStock(List<OrderDetails> detailsDaos) {
		try{
			for(OrderDetails orderDetails:detailsDaos){
				approvedOrderStock = new ApprovedOrderStock();
				approvedOrderStock.setItemDescription(orderDetails.getItemDescription());
				approvedOrderStock.setItemType(orderDetails.getItemType());
				approvedOrderStock.setPartNumber(orderDetails.getPartNumber());
				approvedOrderStock.setQuantity(orderDetails.getQuantity());
				approvedOrderStock.setRecordID(orderDetails.getOrderDertailNumber());
				approvedOrderStock.setTechnicianEmail(orderDetails.getTechnician());
				approvedOrderStock.setTechnicianName(orderDetails.getTechnician());
				
				sessionFactory.getCurrentSession().saveOrUpdate(approvedOrderStock);
			}
			retMessage = "OK";
		}catch(Exception exception){
			retMessage = null;
		}
		return retMessage;
	}
	@Override
	public List<ApprovedOrderStock> getApprovedOrdersByOrderNumber(
			String orderNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

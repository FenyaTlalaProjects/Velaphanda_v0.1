package com.demo.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.DeliveryNoteLineItemsDaoInt;
import com.demo.model.DeliveryNoteLineItem;
import com.demo.model.OrderDetails;


@Repository("deliveryNoteLineItemsDao")
@Transactional(propagation = Propagation.REQUIRED)
public class DeliveryNoteLineItemsDao implements DeliveryNoteLineItemsDaoInt{

	@Autowired
	private SessionFactory sessionFactory;
	private String orderNumber = "ORD000";
	@Override
	public void saveDeliveryNoteLineItems(List<OrderDetails> orderDetails) {
		
		try{
			for(OrderDetails items: orderDetails){
				DeliveryNoteLineItem lineItems = new DeliveryNoteLineItem();
				lineItems.setItemDescription(items.getItemDescription());
				lineItems.setModel(items.getModel());
				lineItems.setOrderNum(orderNumber+items.getOrderHeader().getRecordID());
				lineItems.setQuantity(items.getQuantity());
				lineItems.setRecordID(items.getOrderDertailNumber());
				lineItems.setPartNumber(items.getPartNumber());
				
				sessionFactory.getCurrentSession().save(lineItems);
			}
		}catch(Exception e){
			
		}
	}

}

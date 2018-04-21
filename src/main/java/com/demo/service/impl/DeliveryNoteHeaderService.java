package com.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.DeliveryNoteHeaderDaoInt;
import com.demo.model.DeliveryNoteHeader;
import com.demo.service.DeliveryNoteHeaderServiceInt;



@Service("deliveryNoteHeaderService")
public class DeliveryNoteHeaderService implements DeliveryNoteHeaderServiceInt{

	
	@Autowired
	private DeliveryNoteHeaderDaoInt deliveryNodeHeader;
	
	
	@Override
	public DeliveryNoteHeader getDeliveryNoteHeader(Integer recordID) {
		
		return deliveryNodeHeader.getDeliveryNoteHeader(recordID);
	}

}

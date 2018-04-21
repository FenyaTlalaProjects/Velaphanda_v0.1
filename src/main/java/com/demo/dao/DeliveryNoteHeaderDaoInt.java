package com.demo.dao;

import com.demo.model.DeliveryNoteHeader;
import com.demo.model.OrderHeader;

public interface DeliveryNoteHeaderDaoInt {

	void saveDeliveryNoteHeader(OrderHeader orderHeader);
	DeliveryNoteHeader getDeliveryNoteHeader(Integer recordID);
}

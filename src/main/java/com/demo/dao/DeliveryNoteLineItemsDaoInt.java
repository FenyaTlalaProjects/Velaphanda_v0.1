package com.demo.dao;

import java.util.List;

import com.demo.model.OrderDetails;

public interface DeliveryNoteLineItemsDaoInt {

	void saveDeliveryNoteLineItems(List<OrderDetails> orderDetails);
}

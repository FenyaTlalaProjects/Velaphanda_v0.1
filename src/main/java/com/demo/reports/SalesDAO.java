package com.demo.reports;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class SalesDAO {
	
	public  JRDataSource getDataSource() {
		// Create an array list of Sales 
		List<Sales> items = new ArrayList<Sales>();
		
		// We'll add three Sales items
		// You can populate data from a custom JDBC or DAO layer
		// For this demo, we'll create an in-memory list of items
		
		// Create first item
		Sales item1 = new Sales();
		item1.setPartNumber("CLT-1001L");
		item1.setSerialNumber("07SUVSSSK");
		item1.setDescription("Yellow Toner");
		item1.setQtyOrdered("10");
		item1.setQtyDelivered("10");
		
		// Create second item
		Sales item2 = new Sales();
		item2.setPartNumber("CLT-KDKDKD");
		item2.setSerialNumber("074SDSSAD");
		item2.setDescription("Black Toner");
		item2.setQtyOrdered("2");
		item2.setQtyDelivered("2");
		
		// Create third item
		Sales item3 = new Sales();
		item3.setPartNumber("CLT-TRDKKD");
		item3.setSerialNumber("08DSSARS");
		item3.setDescription("Magenta Toner");
		item3.setQtyOrdered("1");
		item3.setQtyDelivered("1");
		
		// Add to list
		items.add(item1);
		items.add(item2);
		items.add(item3);
		
		// Wrap the collection in a JRBeanCollectionDataSource
		// This is one of the collections that Jasper understands
		JRDataSource ds = new JRBeanCollectionDataSource(items);	
		
		// Return the wrapped collection
		return ds;
	}


}

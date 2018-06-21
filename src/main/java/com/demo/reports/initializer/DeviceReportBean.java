package com.demo.reports.initializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeviceReportBean {
	
	private String model;
	private String partNumber;
	private int quantity;
	private String itemDescription;
	
	private String customerName;
	private String address;
	private String province;
	
	private String techName;
	private String techCellNo;
	private String techEmail;
	
	private String deliveryNoteNo;
	private String customerOrderNum;
	private String dateDelivered;
	
	private String serialNumber;
	private String modelNumber; 
	private String modelBrand;
	private String date;
	private String streetNumber; 
	private String streetName;
	private String city_town;	
	private String  zipcode; 

}

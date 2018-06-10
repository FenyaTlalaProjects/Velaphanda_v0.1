package com.demo.reports.initializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderReportBean {
	
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

}

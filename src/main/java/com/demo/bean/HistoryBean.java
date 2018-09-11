package com.demo.bean;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class HistoryBean {

	private Long historyID;
	private String classification;	
	private String objectId;
	private String userEmail;
	private String userName;
	private String action;
	private String dateTime;
	private int quantity;
	private String dataField1;
	private String dataField2;
	private String description;
	//for Head Office
	private String hoObjectId;
	private String hoSpareRecievedBy;
	private String hoActionSpares;
	private String hoDateSpareRecieved;
	private String hoSupplierName;
	private String hoSupplierOrderNo;
	private int hoQuantityRecieved;
	
}

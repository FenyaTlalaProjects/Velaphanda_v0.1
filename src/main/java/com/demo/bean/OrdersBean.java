package com.demo.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class OrdersBean {
	

	private String orderNum;
	private boolean delivery;
	private boolean delivered;
	private boolean received;
	private String dateOrdered;
	private boolean approved;
	private String dateApproved;
	private String comments;
	private String description;
	private String approdedBy;
	private String status;
	private String location;
	
	private String employee;
	private String technicianUserName;
	private String userLoggedIn;
	private String part;
	private String customer;
	private String device;

	private String []quantity;
	private String[]selectedItem;
	private String []quantityList;
	private String[]partNumberList;
	private String stockType;
	private String technician;
	private String approver;
	private Long recordID;
	
}

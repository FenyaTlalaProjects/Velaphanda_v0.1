package com.demo.bean;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class CustomerDeviceHistoryBean {

	private Long historyID;
	private String classification;	
	private String objectId;
	private String userEmail;
	private String userName;
	private String action;
	private String dateTime;
	private String description;
	
	
}

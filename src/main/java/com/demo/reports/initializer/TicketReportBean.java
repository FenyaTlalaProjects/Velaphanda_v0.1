package com.demo.reports.initializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketReportBean {
	
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
	private String zipcode; 
	
	private String startDate;
	private String installationDate;
	private String endDate;
	private String colourReading;
	private String monoReading;
	private String monoCopyCost;
	private String colourCopyCost;
	private String accessotyType;
	private String serial;
	
	private String ticketContactPersonFirstLastName;	
	private String contactPersonLastName;
	private String ticketContactPersonCellphone;
	private String ticketContactPersonTellphone;
	private String ticketcontactPersonEmail;
		
	private String ticketNo;
	private String problemDescription;
	private String comment;
	private String actionTaken;
	private String status;
	private String priority;
	private String firstName;
	private String lastName;
	private String deviceContactPersonFirstLastName;
	private String deviceContactPersonCellphone;
	private String deviceContactPersonTellphone;
	private String deviceContactPersonEmail;
	private String contactPersonEmail;
	private String assignedTo;
	private String technicianEmail;
	private String usedSpareParts;
	
}

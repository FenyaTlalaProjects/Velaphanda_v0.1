package com.demo.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CustomerBean {
	
	// Customer
	private String customerName;
	private boolean isActive;
	private String telephoneNumber;
	private String email;
	private String streetName;
	private String city_town;
	private String province;
	private String zipcode;
	private String faxNumber;
	private String streetNumber;
	//Contact person 1
	private String contactKey;
	private String firstName;
	private String lastName;
	private String contactTelephoneNumber;
	private String contactCellNumber;
	private String contactEmail;
	private String contactType;	
	//Contactperson 2
	private String firstName1;
	private String lastName1;
	private String contactTelephoneNumber1;
	private String contactCellNumber1;
	private String contactEmail1;
	private String contactType1;
	//updating customer	
	private String description;
	
	
	
	
}
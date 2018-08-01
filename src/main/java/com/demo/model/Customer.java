package com.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;


@Entity
@Table(name="Customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Customer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="Customer_Name")
	private String customerName;
	@Column(name="IsACTIVE")
	private boolean isActive;
	@Column(name="Telephone_Number")
	private String telephoneNumber;
	@Column(name="Emal")
	private String email;
	@Column(name="Contact_Email")
	private String contactEmail;
	@Column(name="Street_Name")
	private String streetName;
	@Column(name="City_Town")
	private String city_town;
	@Column(name="Province")
	private String province;
	@Column(name="Area_Code")
	private String zipcode;
	@Column(name="Fax_No")
	private String faxNumber;
	@Column(name="Street_No")
	private String streetNumber;
	@Column(name="Quantity")
	private String quantity;
	@Column(name="DateTimeClientAdded")
	//@Temporal(TemporalType.TIMESTAMP)
	private String dateTimeClientAdded;
		
	@OneToMany(mappedBy ="customerContactDetails", cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<CustomerContactDetails> customerContactDetails;
	
	@OneToMany(mappedBy ="customerDevice", cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<Device> customerDevice;
	
	@OneToMany(mappedBy="customer")
	private Set<TechnicianSite> technicianSites;

}

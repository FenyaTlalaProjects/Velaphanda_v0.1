
package com.demo.model;

import java.io.Serializable;


import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="DEVICES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Device implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Serial_Number")
	private String serialNumber;
	@Column(name="Device_Model")
	private String modelNumber;
	@Column(name="Contract_Start_Date")
	private String startDate;
	@Column(name="Contract_End_Date")
	private String endDate;
	@Column(name="Installation_Date")
	private String installationDate;
	@Column(name="Device_Location")
	private String deviceLocation;	
	@Column(name="Colour_Reading")
	private String colourReading;
	@Column(name="Mono_Reading")
	private String monoReading;	
	@Column(name="Province")
	private String province;
	@Column(name="City_Town")
	private String city_town;
	@Column(name="Street_Number")
	private String streetNumber;
	@Column(name="Street_Name")
	private String streetName;
	@Column(name="Building_Name")
	private String buildingName;
	@Column(name="Floor_Number")
	private String floorNumber;
	@Column(name="Area_Code")
	private String areaCode;
	@Column(name="DateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;
	@Column(name="Model_Brand")
	private String modelBrand;
	@Column(name="Mono_Copy_Cost")
	private String monoCopyCost;
	@Column(name="Colour_Copy_Cost")
	private String colourCopyCost;
	
	@ManyToOne
	@JoinColumn(name="Device_Contact")
	private DeviceContactPerson contactPerson;	
	
	@ManyToOne
	@JoinColumn(name="Customer")
	private Customer customerDevice;
    
	@OneToMany(mappedBy ="device", cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<Accessories> accessories;
	
	
	
}

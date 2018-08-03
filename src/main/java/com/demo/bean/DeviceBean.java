package com.demo.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DeviceBean {

	private String customerName;
	private String serialNumber;
	private String modelNumber;
	private String modelBrand;
	private String startDate;
	private String endDate;
	private String installationDate;
	private String colourReading;
	private String monoReading;
	private String monoCopyCost;
	private String colourCopyCost;
	

	// Machine Accessories input check box
	private String bridgeUnitSerialType;
	private String finisherType;
	private String ltcType;
	private String faxUnitSerialType;
	private String oneBinSerialType;
	private String OneBinTrayType;
	private String creType;
	private String addType;
	
	private String []chkAccessories;

	// Machine Accessories input text box
	private String bridgeUnitSerialTypeSerialNo;
	private String finisherTypeSerialNo;
	private String ltcTypeSerial;
	private String OneBinTrayTypeSerialNo;
	private String faxUnitSerialTypeSerialNo;
	private String creTypeserial;
	private String addTypeserial;

	// other Machine Accessories
	private String machineType;
	private String serialNumberOtherAccessory;
	private String accessoryName;
	private String accessorySerial;
	// new ones
	private String othersMachineType;
	private String othersSerialNumber;

	private String buildingName;
	private String floorNumber;
	private String streetName;
	private String city_town;
	private String province;
	private String zipcode;
	private String streetNumber;
	private String email;
	private String firstName;
	private String lastName;
	private String cellphone;
	private String telephone;
	private String tellphoneNumber;

	private String updateFlag;
	
	//updating device	
	private String decription;

}

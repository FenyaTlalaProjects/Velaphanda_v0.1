package com.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="HO_Stock")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HOStock implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Part_Number")
	private String partNumber;
	@Column(name="Item_Type")
	private String itemType;
	@Column(name="Item_Description")
	private String itemDescription;
	@Column(name="Compitable_Devices")
	private String compitableDevice;
	@Column(name="Quantity")
	private int quantity;
	@Column(name="ReceivedBy")
	private String receivedBy;
	@Column(name="Date_Arrived")
	private String dateTime;
	@Column(name="Model_Brand")
	private String modelBrand;
	@Column(name="Colour")
	private String color;
	@Column(name="Supplier")
	private String supplierName;
}

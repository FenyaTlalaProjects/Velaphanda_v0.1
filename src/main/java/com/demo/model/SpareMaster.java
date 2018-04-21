package com.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name="SpareMaster")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpareMaster implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Part_Number")
	private String partNumber;
	@Column(name="Item_Type")
	private String itemType;
	@Column(name="Description")
	private String itemDescription;
	@Column(name="Compitable_Devices")
	private String compitableDevice;
	@Column(name="Captured_By")
	private String capturedBy;
	@Column(name="Date_Captured")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCaptured;
	@Column(name="Model_Brand")
	private String modelBrand;
	@Column(name="Colour")
	private String color;

}

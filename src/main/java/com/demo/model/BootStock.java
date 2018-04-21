package com.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Boot_Stock") 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BootStock implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	@Column(name="Record_ID", unique = true, nullable = false, precision = 15, scale = 0)
	private Long recordID;
	@Column(name="Part_Number")
	private String partNumber;
	@Column(name="Item_Type")
	private String itemType;
	@Column(name="Item_Description")
	private String itemDescription;
	@Column(name="Technician_Name")
	private String technicianName;
	@Column(name="Technician_Email")
	private String technicianEmail;
	@Column(name="Quantity")
	private int quantity;
	@Column(name="Compatible_Device")
	private String compatibleDevice;
	@Column(name="Model_Brand")
	private String modelBrand;
	@Column(name="Colour")
	private String color;
	@Column(name="ReasonForMovement")
	private String reasonForMovement;
	@Column(name="DateSparesMoved")
	private String dateSparesMoved;
	@Column(name="MoveSparesTo")
	private String moveSparesTo;
	@Column(name="MoveSparesFrom")
	private String moveSparesFrom;

}

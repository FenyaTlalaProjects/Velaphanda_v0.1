package com.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name= "Order_Details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetails implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="Order_DetailNumber")
	private int orderDertailNumber;
	@Column(name="Stock_Type")
	private String stockType;
	@Column(name="Location")
	private String location;
	@Column(name="Item_Type")
	private String itemType;
	@Column(name="Part_Number")
	private String partNumber;
	@Column(name="Model")
	private String model;
	@Column(name="Description")
	private String itemDescription;
	@Column(name="DateTime")
	private String dateTime;
	@Column(name="Quantity")
	private int quantity;
	@Column(name="Technician")
	private String technician;
	@Column(name="Compatible_Device")
	private String compatibleDevice;
	@Column(name="Model_Brand")
	private String modelBrand;
	@Column(name="Colour")
	private String color;
	@ManyToOne
	@JoinColumn(name="Order_Number")
	private OrderHeader orderHeader;
	

}

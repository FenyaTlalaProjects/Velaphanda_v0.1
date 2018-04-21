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
@Table(name= "Delivery_LineItems")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryNoteLineItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="RecordID")
	private Integer recordID;
	@Column(name="Order_Number")
	private String orderNum;
	@Column(name="Model")
	private String model;
    @Column(name="Description")
	private String itemDescription;
	@Column(name="Quantity")
	private int quantity;
	@Column(name="Part_Number")
	private String partNumber;
	
	
	

}

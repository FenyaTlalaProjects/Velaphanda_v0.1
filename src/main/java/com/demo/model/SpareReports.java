package com.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
@Table(name="SpareReports")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpareReports implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Part_Number")
	private String partNumber;
	@Column(name="Item_Type")
	private String itemType;
	@Column(name="Description")
	private String description;
	@Column(name="Quantity")
	private int quantity;
	@Column(name="Spupplier_Name")
	private String supplierName;
	@Column(name="Received_By")
	private String receivedBy;
	@Column(name="Received_DateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;

}

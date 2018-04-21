package com.demo.model;

import java.io.Serializable;
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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name= "Delivery_Header")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryNoteHeader implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="RecordID")
	private Long recordID;
	@Column(name="Order_Number")
	private String orderNum;
	@Column(name="Date_Ordered")
	private String dateOrdered;
    @Column(name="Contact_Person")
	private String contactPerson;
	@Column(name="Contact_Number")
	private String contactNumber;
	@Column(name="Contact_Email")
	private String contactEmail;
	@Column(name="Customer_Name")
	private String customerName;
	@Column(name="Customer_Street")
	private String customerStreet;
	@Column(name="Customer_City")
	private String customerCity;
	@Column(name="Customer_AreaCode")
	private String customerAreaCode;
	@Column(name="Customer_Province")
	private String customerProvince;
	@Column(name="Company_Name")
	private String companyName;
	@Column(name="Company_Registration")
	private String companyRegistration;
	@Column(name="Company_Street")
	private String companyStreet;
	@Column(name="Company_City")
	private String companyCity;
	@Column(name="Company_Province")
	private String companyProvince;
	@Column(name="Company_Telephone")
	private String companyTelephone;
	@Column(name="Company_Fax")
	private String companyFax;
	@Column(name="Company_Email")
	private String companyEmail;
	
	
	}

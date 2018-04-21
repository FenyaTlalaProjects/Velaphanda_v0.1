package com.demo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name= "Order_Header")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderHeader implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	@Column(name="RecordID", unique = true, nullable = false, precision = 15, scale = 0)
	private Long recordID;
	@Column(name="Delivered")
	private boolean delivered;
	@Column(name="Date_Orderd")
	private String dateOrdered;
	@Column(name="Approved")
	private boolean approved;
	@Column(name="Date_Approved")
	private String dateApproved;
	@Column(name="Declined_Reason")
	private String comments;
	@Column(name="Approver")
	private String approver;
	@Column(name="Order_Status")
	private String status;
	@Column(name="Stock_Type")
	private String stockType;
	@Column(name="Order_Received_Date")
	private String orderReceivedDateTime;
	@Column(name="Shipping_Date")
	private String shippingDate;
	
	
	@ManyToOne
	@JoinColumn(name="OrderBy")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="Customer_Name")
	private Customer customer;
	
	
	@OneToMany(mappedBy ="orderHeader", cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<OrderDetails> orderDetails = new HashSet<OrderDetails>();
	
	@OneToMany(mappedBy ="orderHeader", cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<Tickets> tickets;
}

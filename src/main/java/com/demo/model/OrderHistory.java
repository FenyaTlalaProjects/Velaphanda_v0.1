package com.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name= "Order_History")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderHistory implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="RecordID")
	private Integer recordID;
	@Column(name="Order_Number")
	private String orderNum;
	@Column(name="Status_Date_Time")
	private String statusDateTime;
	@Column(name="Order_Status")
	private String orderStatus;
}

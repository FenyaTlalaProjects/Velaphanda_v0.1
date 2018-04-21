package com.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Technician_Sites") 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TechnicianSite implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="RecordID")
	private int recordID;
	
	@ManyToOne
	@JoinColumn(name="Technician_Email")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="Customer_Name")
	private Customer customer;
	
	

}

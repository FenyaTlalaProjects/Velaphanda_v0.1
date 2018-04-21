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
@Table(name="LEAVESTATUS") 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeaveStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="Leave_ID")
	private Long leaveID;
	@Column(name="First_Leave_Date")
	private String startDate;
	@Column(name="Last_Leave_Date")
	private String endDate;
	@Column(name="Status")
	private String status;
	@Column(name="Reason_Declined")
	private String reasonDeclined;
	
	@ManyToOne
	@JoinColumn(name="Requested_By")
	private Employee employee;
	

}

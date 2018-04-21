package com.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="LEAVES") 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Leave implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	@Column(name="Leave_ID", unique = true, nullable = false, precision = 15, scale = 0)
	private Long leaveID;
	@Column(name="Leave_Type")
	private String leaveType;
	@Column(name="First_Leave_Date")
	private String startDate;
	@Column(name="Last_Leave_Date")
	private String endDate;
	@Column(name="Contact_Number")
	private String contactNumber;
	@Column(name="Address")
	private String address;
	@Column(name="Status")
	private String status;
	@Column(name="LeaveDate")
	private String leaveDate;
	@Column(name="Leave_Approval")
	private String leaveApproval;
	@Column(name="Approved_By")
	private String approvedBy;
	@Column(name="Declined_Reason")
	private String comments;
	
	
	@ManyToOne
	@JoinColumn(name="Requested_By")
	private Employee employee;
	

}

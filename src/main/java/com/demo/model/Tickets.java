package com.demo.model;

import java.io.Serializable;
import java.util.Calendar;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="Tickets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tickets implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	@Column(name="RecordID", unique = true, nullable = false, precision = 15, scale = 0)
	private Long recordID;
	@Column(name="Comments")
	private String comments;
	@Column(name="Status")
	private String status;
	@Column(name="Priority")
	private String priority;
	@Column(name="Logged_Date")
	private String dateTime;
	@Column(name="EscalatedTo")
	private String escalatedTo;
	@Column (name="Subject")
	private String subject;
	@Column(name="Description")
	private String description;
	@Column(name="Technician_Acknowledged")
	private boolean technicianAcknowledged;
	@Column(name="SLA")
	private String slaStart;
	@Column(name="Escalate_Reason")
	private String escalateReason;
	@Column(name="SLA_Acknowledge_DateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar slaAcknowledgeDateTime;
	@Column (name="Solution")
	private String solution;
	@Column (name="One_HourEmailFlag")
	private boolean oneHourFlag;
	@Column (name="Four_HourEmailFlag")
	private boolean fourHourFlag;
	@Column(name="Used_Part_Numbers")
	private String usedPartNumbers;
	@Column(name="Action_Taken")
	private String actionTaken;
	@Column(name="Date_Resolved")
	private String dateResolved;
	@Column(name="FirstName")
	private String firstName;
	@Column(name="LastName")
	private String lastName;
	@Column(name="ContactEmail")
	private String contactEmail;
	@Column(name="ContactCellNumber")
	private String contactCellNumber;
	@Column(name="ContactTelephoneNumber")
	private String contactTelephoneNumber;
	@Column(name="BridgedReason")
	private String bridgedReason;
	@Column (name="ReopenReason")
	private String reopenReason;
	@Column(name="TicketLoggedBy")
	private String ticketLoggedBy;
	
	
	
	
	@ManyToOne
	@JoinColumn(name="AssignedTechnician")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="Serial_Number")
	private Device device;
	
	@ManyToOne
	@JoinColumn(name="Order_Number")
	private OrderHeader orderHeader;
	
	@OneToMany(mappedBy= "tickets",cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<TicketHistory> ticketHistory; 
	
}

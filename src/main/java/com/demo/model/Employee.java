package com.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;





@Entity
@Table(name="EMPLOYEE")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)  
@DiscriminatorColumn(name="Type",discriminatorType= DiscriminatorType.STRING)  
@DiscriminatorValue(value="employee")  
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="EMAIL")
	private String email;
	@Column(name="PASSWORD")
	private String password;
	@Column(name="TITLE")
	private String title;
	@Column(name="FIRST_NAME")
	private String firstName;
	@Column(name="LAST_NAME")
    private String lastName;
	@Column(name="ROLE")
    private String role;
	@Column(name="GENDER")
	private String gender;
	@Column(name="FIRSTTIMELOGIN")
	private boolean isFirstTimeLogin;
	@Column(name="STATUS")
	private String status;
	@Column(name="LEAVE_STATUS")
	private String leaveStatus;
	@Column(name="DateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;
	@Column(name="Cell_Number")
	private String cellNumber;
	@Column(name="Quantity")
	private String quantity;
	
	@OneToMany(mappedBy ="employee", cascade= CascadeType.ALL)
	private Set<Tickets> logTickets;
	
	@OneToMany(mappedBy="employee",cascade=CascadeType.ALL)
	private Set<OrderHeader> orderHeader;
	
	@OneToMany(mappedBy="employee",cascade=CascadeType.ALL)
	private Set<TicketHistory> ticketHistory;

	
	@OneToMany(mappedBy="employee")
	private Set<UserLogDetails> userLogDetails;
	
	@OneToMany(mappedBy="employee")
	private Set<Leave> leave;
	
	@OneToMany(mappedBy="employee")
	private Set<TechnicianSite> technicianSites;
}

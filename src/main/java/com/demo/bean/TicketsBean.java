package com.demo.bean;

import java.util.Calendar;

import javax.persistence.Column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TicketsBean {
	
	private String ticketNumber;
	private String description;
	private String type;
	private String status;
	private String priority;
	private String group;
	private String date;
    private String employee;
	private String device;
	private String comments;
	private String customer;
	private String technicianUserName;
	private boolean technicianAcknowledged;
	private String escalatedTo;
	private String escalateReason;
	private Calendar slaAcknowledgeDateTime;
	private String solution;
	private int openTickets;
	private int escalatedTickets;
	private int closedTickets;
	private int loggedTickets;
	private String orderNumber;
	private String subject;
	private Integer orderNum;
	private String usedPartNumbers;
	private String colourReading;
	private String monoReading;
	private String actionTaken;
	private String ticketAction;
	private String bridgedReason;
	private String reopenReason;
	private Long recordID;
	private String firstName;
	private String lastName;
	private String contactEmail;
	private String contactCellNumber;
	private String contactTelephoneNumber;
	private String bootType;
	private String groupboot;
	
}

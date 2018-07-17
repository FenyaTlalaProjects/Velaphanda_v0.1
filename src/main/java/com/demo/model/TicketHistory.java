package com.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="TicketHistory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketHistory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="TicketHistoryID")
	private Long historyId;
	@Column(name="TicketNumber")
	private String ticketNumber;
	@Column(name="TicketNo")
	private Long ticketNo;
	@Column(name="EscalatedResoan")
	private String escalatedReason;
	@Column(name="BridgedResoan")
	private String bridgedReason;
	@Column (name="ReopenReason")
	private String reopenReason;
	@Column(name="EscalatedDate")
	private String escalatedDate;
	@Column(name="Solution")
	private String solution;
	@Column(name="Comment")
	private String comment;
	@Column(name="Description")
	private String description;
	@Column(name="Action_Taken")
	private String actionTaken;
	@Column(name="Mono_Reading")
	private String monoReading;
	@Column(name="Colour_Reading")
	private String colourReading;
	@Column(name="Status")
	private String status;
	
		
	@ManyToOne
	@JoinColumn(name="EscalatedTo")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="Device")
	private Tickets tickets;

}

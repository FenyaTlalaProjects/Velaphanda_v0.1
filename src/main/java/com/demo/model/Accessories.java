package com.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="Accessories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Accessories implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	@Column(name="Record_ID", unique = true, nullable = false, precision = 15, scale = 0)
	private Long recordID;
	@Column(name="Serial")
	private String serial;
	@Column(name="Type")
	private String accessotyType;
	
	@ManyToOne
	@ForeignKey(name="Device_Serial")
	private Device device;
	@Column(name="DateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;
}

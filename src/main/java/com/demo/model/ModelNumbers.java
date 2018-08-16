package com.demo.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

@Entity
@Table(name="ModelNumbers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModelNumbers implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id	
	@Column(name="ModelNumber")
	private String modelNumber;
	@Column(name="Brand")
	private String brand;
	@Column(name="Supplier")
	private String supplierName;
	@Column(name="Colour")
	private String color;
	@Column(name="UserName")
	private String userName;
	@Column(name="DateTimeStamp")
	private String dateTime;
}

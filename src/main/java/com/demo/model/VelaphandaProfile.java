package com.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="Velaphanda_Profie") 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VelaphandaProfile implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="Company_Name")
	private String companyName;
	@Column(name="Street_Number")
	private String streetNumber;
	@Column(name="Street_Name")
	private String streetName;
	@Column(name="City")
	private String city;
	private String areaCode;
	@Column(name="Telephone_Number")
	private String telephoneNumber;
	@Column(name="Fax_Number")
	private String faxNumber;
	@Column(name="Email_Address")
	private String emailAdress;
	@Column(name="Company_Registration")
	private String companyRegistration;
	@Column(name="Remit_Email")
	private String remitEmail;
	@Column(name="Company_Province")
	private String companyProvince;
}

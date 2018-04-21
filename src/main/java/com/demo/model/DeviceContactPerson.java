package com.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="DeviceContactPerson")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeviceContactPerson implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="Email")
	private String email;
	@Column(name="FirstName")
	private String firstName;
	@Column(name="LastName")
	private String lastName;
	@Column(name="Telephone")
	private String telephone;
	@Column(name="Cellphone")
	private String cellphone;
	
	@OneToMany(mappedBy ="contactPerson", cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	private Set<Device> devices;

}

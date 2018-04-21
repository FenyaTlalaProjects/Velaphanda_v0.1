package com.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="LoginAttempt") 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginAttempt implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="User_Name")
	private String userName;
	@Column(name="Attempt_Count")
	private int attemptCount;

}

package com.co.entity;

import lombok.Data;

import java.time.LocalDate;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "ACC_ID")
public class ApplicationRegistration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "APP_ID")
	private Long appId;
	
	@Column(name = "FULLNAME")
	private String fullName;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PHNO")
	private String phoneNo;
	
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "STATE_NAME")
	private String stateName;
	
	@Column(name = "SSN")
	private String ssn;
	
	@Column(name = "DOB")
	private LocalDate dob;
	
	@Column(name = "ACTIVE_SW" , columnDefinition = "varchar(1) default 'A'")
	private String activeSw;
}
